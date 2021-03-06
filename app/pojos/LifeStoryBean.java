package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import utils.JodaDateTime;


public class LifeStoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -962352695398206053L;

    private Long lifeStoryId;
	private String headline;
	private String text;
	private String richtext;
	private Long contributor;
	@JodaDateTime(format = "yyyy-MM-dd HH:mm:ss")
	private DateTime creationDate = DateTime.now();
	private String locale = "it_IT";
	private LocationBean location;
	private FuzzyDateBean startDate;
	private boolean synced;
	private List<ParticipationBean> participationList;
	private List<MementoBean> mementoList;
	private Long questionId;
	private Long publicMementoId;
	
//	Fields in Database not exposed through API 
//	private String type;
//	private Integer visibility;
//	private FuzzyDateBean endDate;
//	private List<PersonBean> participants;
	
	/**
	 * @return the lifeStoryId
	 */
	public Long getLifeStoryId() {
		return lifeStoryId;
	}

	/**
	 * @param lifeStoryId the lifeStoryId to set
	 */
	public void setLifeStoryId(Long lifeStoryId) {
		this.lifeStoryId = lifeStoryId;
	}

	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * @param headline the headline to set
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the richtext
	 */
	public String getRichtext() {
		return richtext;
	}

	/**
	 * @param richtext the richtext to set
	 */
	public void setRichtext(String richtext) {
		this.richtext = richtext;
	}

//	/**
//	 * @return the type
//	 */
//	public String getType() {
//		return type;
//	}
//
//	/**
//	 * @param type the type to set
//	 */
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	/**
//	 * @return the visibility
//	 */
//	public Integer getVisibility() {
//		return visibility;
//	}
//
//	/**
//	 * @param visibility the visibility to set
//	 */
//	public void setVisibility(Integer visibility) {
//		this.visibility = visibility;
//	}

	/**
	 * @return the contributorId
	 */
	public Long getContributorId() {
		return contributor;
	}

	/**
	 * @param contributorId the contributorId to set
	 */
	public void setContributorId(Long contributorId) {
		this.contributor = contributorId;
	}

	/**
	 * @return the creation_date
	 */
	public DateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creation_date the creation_date to set
	 */
	public void setCreationDate(DateTime creation_date) {
		this.creationDate = creation_date;
	}

	public String getCreationDateAsString () {
		return creationDate == null ? null : creationDate.toString("yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}


	/**
	 * @return the startDate
	 */
	public FuzzyDateBean getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(FuzzyDateBean startDate) {
		this.startDate = startDate;
	}
//
//	/**
//	 * @return the endDate
//	 */
//	public FuzzyDateBean getEndDate() {
//		return endDate;
//	}
//
//	/**
//	 * @param endDate the endDate to set
//	 */
//	public void setEndDate(FuzzyDateBean endDate) {
//		this.endDate = endDate;
//	}

	public LocationBean getLocation() {
		return location;
	}

	public void setLocation(LocationBean location) {
		this.location = location;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public List<ParticipationBean> getParticipationList() {
		return participationList;
	}

	public void setParticipationList(List<ParticipationBean> participationList) {
		this.participationList = participationList;
	}

	public List<MementoBean> getMementoList() {
		return mementoList;
	}

	public void setMementoList(List<MementoBean> mementoList) {
		this.mementoList = mementoList;
	}

	public void addParticipant(ParticipationBean part) {
		if (this.participationList == null) {
			this.participationList = new ArrayList<ParticipationBean>();
			this.participationList.add(part);
		} else {
			this.participationList.add(part);
		}
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getPublicMementoId() {
		return publicMementoId;
	}

	public void setPublicMementoId(Long publicMementoId) {
		this.publicMementoId = publicMementoId;
	}
}
