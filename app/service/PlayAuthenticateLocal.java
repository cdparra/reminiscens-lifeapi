package service;

import static play.libs.Json.toJson;

import java.io.UnsupportedEncodingException;

import play.Logger;
import play.Play;
import play.api.libs.Crypto;
import play.i18n.Messages;
import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import play.mvc.Result;
import pojos.ResponseStatusBean;
import pojos.UserBean;
import providers.MyUsernamePasswordAuthProvider;
import utils.PlayDozerMapper;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.exceptions.AuthException;
import com.feth.play.module.pa.providers.AuthProvider;
import com.feth.play.module.pa.user.AuthUser;

import controllers.routes;
import delegates.UtilitiesDelegate;
import enums.ResponseStatus;

public class PlayAuthenticateLocal extends PlayAuthenticate {

	private static final String SETTING_KEY_ACCOUNT_AUTO_LINK = "accountAutoLink";
	private static final String SETTING_KEY_ACCOUNT_AUTO_MERGE = "accountAutoMerge";

	/**
	 * Deprecated by public static Result loginAndRedirect(final Context
	 * context, final AuthUser loginUser, Object payload)
	 * 
	 * Kept to allow reverse compatibility
	 * 
	 * @param context
	 * @param loginUser
	 * @return
	 */
	public static Result loginAndRedirect(final Context context,
			final AuthUser loginUser) {

		return loginAndRedirect(context, loginUser, null);
	}

	/**
	 * Login and redirect signs in the user and sends him back the user
	 * information along with the sessionKey. Payload is an object containing
	 * information about what was the original call (signup or login) in order
	 * to allow signup with automatic login afterwards
	 * 
	 * @param context
	 * @param loginUser
	 * @param payload
	 * @return
	 */
	public static Result loginAndRedirect(final Context context,
			final AuthUser loginUser, Object payload) {

		String encoded = "";
		String signed = "";

		try {
			StringBuffer sb = new StringBuffer();

			if (loginUser.expires() != AuthUser.NO_EXPIRATION) {
				sb.append("pa.u.exp:");
				sb.append(loginUser.expires());
				sb.append("\u0000");
			}
			sb.append("pa.p.id:");
			sb.append(loginUser.getProvider());
			sb.append("\u0000pa.u.id:");
			sb.append(loginUser.getId());
			encoded = java.net.URLEncoder.encode(sb.toString(), "UTF-8");
			signed = Crypto.sign(encoded);
		} catch (UnsupportedEncodingException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
		}

		if (Logger.isDebugEnabled()) {
			Logger.debug("session generated: " + "PLAY_SESSION=" + signed + "-"
					+ encoded);
		}

		models.User user = models.User.getByEmail(loginUser.getId());

		if (payload != null && payload.toString().equals("SIGNUP")) {
			// send verification email
			// added to support login after signup
			Logger.debug("signing up a new user:");
			Logger.debug("--> id: " + loginUser.getId());
			Logger.debug("--> provider: " + loginUser.getProvider());
			Logger.debug("sending verification email:");
			Logger.debug("--> provider: " + loginUser.getProvider());
			MyUsernamePasswordAuthProvider provider = MyUsernamePasswordAuthProvider
					.getProvider();
			provider.sendVerifyEmailMailingAfterSignup(user, context);

		}

		// Log Action
		int logAction = Play.application().configuration()
				.getInt("log.actions");	
		if (logAction == 1) {
			UtilitiesDelegate.getInstance().logActivity(user, payload.toString(), context.request().path());
		}
		
		UserBean u = PlayDozerMapper.getInstance().map(user, UserBean.class);
		u.setSessionKey(signed + "-" + encoded);
		return Controller.ok(toJson(u));
		// return Controller.ok(toJson("PLAY_SESSION=" + signed + "-" + encoded));
	}

	public static Result handleAuthentication(final String provider,
			final Context context, final Object payload) {
		final AuthProvider ap = getProvider(provider);
		if (ap == null) {
			// Provider wasn't found and/or user was fooling with our stuff -
			// tell him off:
			// return Controller.notFound(Messages.get(
			// "playauthenticate.core.exception.provider_not_found",
			// provider));
			ResponseStatusBean response = new ResponseStatusBean();
			response.setResponseStatus(ResponseStatus.NOTAVAILABLE);
			response.setStatusMessage("playauthenticate.core.exception.provider_not_found="
					+ provider);
			return Controller.notFound(toJson(response));
		}
		try {
			final Object o = ap.authenticate(context, payload);
			if (o instanceof String) {
				if ("NOT_FOUND".equals(o)) {
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.UNAUTHORIZED);
					response.setStatusMessage(Messages
							.get("playauthenticate.password.login.unknown_user_or_pw"));
					return Controller.unauthorized(toJson(response));
					// In case redirection is needed again
					// return Controller.unauthorized(
					// Messages.get("playauthenticate.password.login.unknown_user_or_pw"));
				} else if (routes.Signup.unverified().url().equals(o)) {
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.UNAUTHORIZED);
					response.setStatusMessage(Messages
							.get("playauthenticate.verify.email.cta"));
					return Controller.unauthorized(toJson(response));
				} else if (routes.Signup.exists().url().equals(o)) {
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.UNAUTHORIZED);
					response.setStatusMessage(Messages
							.get("playauthenticate.user.exists.message"));
					return Controller.unauthorized(toJson(response));
				} else {
					// return Controller.redirect((String) o);
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.SERVERERROR);
					response.setStatusMessage("not implemented when the authenticate response is: "
							+ o);
					return Controller.internalServerError(toJson(response));
				}
			} else if (o instanceof AuthUser) {

				final AuthUser newUser = (AuthUser) o;
				final Session session = context.session();

				/* 
				 * We might want to do merging here:
				 *
				 * Adapted from:
				 * http://stackoverflow.com/questions/6666267/architecture-for-merging-multiple-user-accounts-together
				 * 1. The account is linked to a local account and no session
				 * 	  cookie is present --> Login
				 * 2. The account is linked to a local account and a session
				 *    cookie is present --> Merge
				 * 3. The account is not linked to a local account and no
				 *    session cookie is present --> Signup
				 * 4. The account is not linked to a local account and a session
				 *    cookie is present --> Linking Additional account
				 */
				
				// Get the user with which we are logged in - is null if we are
				// not logged in (does NOT check expiration)
				AuthUser oldUser = getUser(session);

				// checks if the user is logged in (also checks the expiration!)
				boolean isLoggedIn = isLoggedIn(session);

				Object oldIdentity = null;

				// check if local user still exists - it might have been deactivated/deleted,
				// so this is a signup, not a link
				if (isLoggedIn) {
					oldIdentity = getUserService().getLocalIdentity(oldUser);
					isLoggedIn &= oldIdentity != null;
					if (!isLoggedIn) {
						// if isLoggedIn is false here, then the local user has been deleted/deactivated
						// so kill the session
						logout(session);
						oldUser = null;
					}
				}

				final Object loginIdentity = getUserService().getLocalIdentity(
						newUser);
				final boolean isLinked = loginIdentity != null;

				final AuthUser loginUser;
				if (isLinked && !isLoggedIn) {
					// 1. -> Login
					loginUser = newUser;
				} else if (isLinked && isLoggedIn) {
					// 2. -> Merge

					// merge the two identities and return the AuthUser we want
					// to use for the log in
					if (isAccountMergeEnabled()
							&& !loginIdentity.equals(oldIdentity)) {
						// account merge is enabled
						// and
						// The currently logged in user and the one to log in
						// are not the same, so shall we merge?

						if (isAccountAutoMerge()) {
							// Account auto merging is enabled
							loginUser = getUserService()
									.merge(newUser, oldUser);
						} else {
							// Account auto merging is disabled - forward user
							// to merge request page
							final Call c = getResolver().askMerge();
							if (c == null) {
								throw new RuntimeException(
										Messages.get(
												"playauthenticate.core.exception.merge.controller_undefined",
												SETTING_KEY_ACCOUNT_AUTO_MERGE));
							}
							storeMergeUser(newUser, session);
							// TODO solve this avoiding redirect
							return Controller.redirect(c);
						}
					} else {
						// the currently logged in user and the new login belong
						// to the same local user,
						// or Account merge is disabled, so just change the log
						// in to the new user
						loginUser = newUser;
					}

				} else if (!isLinked && !isLoggedIn) {
					// 3. -> Signup
					loginUser = signupUser(newUser);
					
				} else {
					// !isLinked && isLoggedIn:

					// 4. -> Link additional
					if (isAccountAutoLink()) {
						// Account auto linking is enabled

						loginUser = getUserService().link(oldUser, newUser);
					} else {
						// Account auto linking is disabled - forward user to
						// link suggestion page
						final Call c = getResolver().askLink();
						if (c == null) {
							throw new RuntimeException(
									Messages.get(
											"playauthenticate.core.exception.link.controller_undefined",
											SETTING_KEY_ACCOUNT_AUTO_LINK));
						}
						storeLinkUser(newUser, session);
						// TODO change this avoiding redirect
						return Controller.redirect(c);
					}

				}

				return loginAndRedirect(context, loginUser, payload);
			} else {
				ResponseStatusBean response = new ResponseStatusBean();
				response.setResponseStatus(ResponseStatus.SERVERERROR);
				response.setStatusMessage("playauthenticate.core.exception.general");
				return Controller.internalServerError(toJson(response));
			}
		} catch (final AuthException e) {
			final Call c = getResolver().onException(e);
			if (c != null) {
				// TODO Solve avoiding redirects
				return Controller.redirect(c);
			} else {
				final String message = e.getMessage();
				if (message != null) {
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.SERVERERROR);
					response.setStatusMessage(message);
					return Controller.internalServerError(toJson(response));
					// return Controller.internalServerError(message);
				} else {
					ResponseStatusBean response = new ResponseStatusBean();
					response.setResponseStatus(ResponseStatus.SERVERERROR);
					response.setStatusMessage("Internal server error");
					return Controller.internalServerError(toJson(response));
					// return Controller.internalServerError();
				}
			}
		}
	}

	private static AuthUser signupUser(final AuthUser u) throws AuthException {
		final AuthUser loginUser;
		final Object id = getUserService().save(u);
		if (id == null) {
			throw new AuthException(
					Messages.get("playauthenticate.core.exception.singupuser_failed"));
		}

		loginUser = u;
		return loginUser;
	}

}
