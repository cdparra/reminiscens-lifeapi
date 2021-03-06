FORMAT: X-1A

# Reminiscens Life API version 1.0 #
Reminiscens Life API is a RESTful API that provides HTTP access to resources that support the Reminiscens' tablet application 
for stimulating elderly and their families in sharing memories and motivate conversation by exploring contextual content for these 
memories, based on their place and period of time. The goal is to stimulate a healthy practice of positive reminiscence that will benefit 
elders' subjective wellbeing. 

<h3> About the Reminiscens Project</h3>
Reminiscens is a social informatics project funded by the <a href="http://www.lifeparticipation.org/"> 
Lifeparticipation Research Group</a> at the <a href="http://www.disi.unitn.it/"> University of Trento </a>. It is 
the result of participatory research action and design with local elders, with the goal of motivating face to face 
social interactions between older adults and their younger relatives. Not only we have worked on designing an 
application, but we have worked to build an interesting knowledge base of content that will stimulate people to 
reminisce. It is work in progress, towards the finalization of a Ph.D. in Information and Communication Technologies 


# Resource Beans
 
<h3><b><a id="session">Session Resource:</a></b></h3>
<p><pre><code>
{
    "sessionKey" : "[session token]-[encoded user information]"                                  
}
</pre></code></p>

<p> The <a href="#session">Session</a> resource is where the experience begins. Applications create a session when signing
in to the application with an username/password pair or an authentication token provided by a third party service (e.g., facebook, 
google). Sessions are composed by a simple **sessionKey** containing all the information about the user and the authentication service 
provider. After creating the session, the created sessionKey must be included in all of the API calls as a header with the 
key <b>PLAY_SESSION</b> Is the only non-RESTful element of this API (but implemented in the most RESTful way possible).

<h3><b><a id="login">Login Resource:</a></b></h3>
<p><pre><code>
{
    "email"    : "[user email]",
    "password" : "[the password]",
    "provider" : "[facebook|google|password]"
}
</pre></code></p>
<p>The <a href="#login">Login</a> resource it is used only once, for signing in. It is composed by: </p>

 + **email:** user email for login
 + **password:** authentication string provided by the service. 
 + **(OPTIONAL) provider:** the name of the authentication service provider (facebook, google, )

Various authentication services can be used for login, but currently, only our own email/password service is available (named "password").

<h3><b><a id="user">The User Resource:</a></b></h3>
<p><pre><code>
{
    "userId"                : [user unique identificator],
    "email"                 : "[user's registered email]",
    "username"              : "[username to be used for authentication in the future]",
    "profilePic"            : "[URL to a profile picture]",
    "locale"                : "[User's language of preference]",
    "emailVerified"         : [true or false, indicate if the user has verified his email],
    "usernameVerified"      : [true or false, indicate if the user has verified/updated his username (generated first by the system)],
    "creationDate"          : [date in which the user signed up, in unix timestamp format],
    "creationDateAsString"  : "[date in which the user signed up, in 'yyyy-MM-dd HH:mm:ss' format]",
    "sessionKey"            : "[session key generated after login]",
    "person" : { 
        "[person attributes]":"[person values]" 
    }
}
</pre></code></p>
<p>An <a href="#user">User</a> resource represents the aggregated information of the account (for authentication) and of the person using
the system. Although not currently implemented, non verified accounts will be limited to no more than 10 stories and 50 mementos. 

Moreover, for signup a new User,  **<a href="#"person>Person</a>** information must include at least: 

 + **firstname:** person's firstname 
 + **lastname:** person's lastname 
 + **birthdate:** person's birthdate in "yyyy-MM-dd HH:mm:ss" format (notice that time must be introduced)
 + **birthplace:** person's birthplace, represented by a <a href="#city">City</a> resource   

Notice that there is a separation between the entities 
<a href="#user">User</a> and <a href="#person">Person</a>. A person might exist in our dataset wihtout user 
information, referring to people that is mentioned in life stories or to <a href="publicmemento">Famous People</a> 
whose information is public and are available in our historical knowledge base, used to stimulate reminiscence.

</p>

<h3><b><a id="person">Person Resource:</a></b></h3>
<p><pre><code>
{
    "personId"          : [person's unique id],
    "firstname"         : "[person's firstname]",
    "lastname"          : "[person's lastname]",
    "birthdate"         : [person's birthdate in unix timestamp format],
    "birthdateAsString" : "[person's birthdate in 'yyyy-MM-dd HH:mm:ss' format]",
    "gender"            : "[person's gender, i.e., male, female]",
    "famousId"          : [id of the famous person record, if this is a famous historical person],
    "famous" : {
        "[The famous person record]" : "[The famous person record values]"
    },
    "birthplace": {
        "[City attributes]" : "[Citty values]"
    }
}
</pre></code></p>

<p> Every user is a <a href="#person">Person</a> with many <a href="#person">Life Stories</a> to share. Every person 
is identified by one unique <code>id</code> and has at least a firstname, a lastname, a birthdate and birhtplace. A person can 
exist without a <a href="#user">User</a> associated to it, in which case the person refers to a public figure, existing in our 
historical knowledge base, or it is simply a named mentioned within a story or multimedia 
(see the <a href="http://docs.reminiscens.apiary.io/">Lifecontext API</a> to know more about this knolwedge base)</p>

<h3><b><a id="lifestory">Life Story Resource (TO UPDATE):</a></b></h3>
<p><pre><code>
    "LifeStory" : {
        "storyId": [lifestory id],
        "decade": 1930,
        "visibility":"public",
        "startDate": "1934,7,7 0:00:00",
        "endDate": "1934,7,7 0:00:00",
        "headline": "Nascita",
        "text": "Ugo Fantozzi e nato a Genova",
        "richtext": "/story/999/richtext",
        "participants" : [ <person ids of people that particpated in the story> ],
        "aboutPersonList : [ <person ids of people that is focus of the story, one the timeline's owner> ], 
        "createdBy" : 999, 
        "tags":  ["birth", "childhood", ...],
        "mementoList": [ <list of mementos (i.e., resources related to this story> ]
    }
</pre></code></p>


<p> 
TODO: RESOURCES BELOW ARE STILL TO BE UPDATED 
Every person has <a href="#lifestory"> Life Stories</a> that together form a <a href="#timeline">Timeline</a>. Each 
life story has also a list of multimedia attached to it, referred to as <a href="#memento">Mementos</a>, which are 
picture, audio or video resources that remind to that story. Mementos are also part of the contextual recommendations, 
in which case they come from the reminiscens knowledge base. Only the person who owns these stories and people in his/her friend list can access and view his stories in the timeline.
Some stories might be marked as "public", and only those are viewable by all. A person can also have
Timeline Curators, who can access and edit thes stories in his/her timeline</p>

<h3><b><a id="memento">Memento Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
    "Memento" : {
        "mementoId" : 999,
        "storyId" : 999,                 
        "url": "https://dl.dropbox.com/u/2797542/timeline/fantozzi/Fantozzi_il_ritorno.png",
        "thumbnail": "",
        "decade": 1930,
        "startDate": "",
        "endDate": "",
        "participants" : [ <person ids of people visible in the memento> ],
        "headline": "Foto di Ugo circa la sua nascita in 1934",
        "type": "photo",
        "category" : "photo", 
        "cover": "true",
        "index": 1,
        "credit" : "Wikipedia",
        "creditURL : "<url to the source> ",
        "text": "MG M del 1930 ovvero la prima MG Midget",
        "tags": [ "photo", "automobile", "ogetto" ]
    }
</pre></code></p>

<h3><b><a id="timeline">The Timeline Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
    "Timeline" : {
        "timelineId" : 999,
        "aboutPersonId" : 999,
        "curators" : [ <person ids of the people with full access to this timeline> ],
        "storyList" : [ <list of life stories that belong to this timeline> ]
    }
}
</pre></code></p>

<h3><b><a id="city">City Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
{
    "cityId"    : 9092,
    "name"      : "Luque",
    "country"   : { },
    "lat"       : -25.27,
    "lon"       : -57.487222
}
</p></pre></code>


<h3><b><a id="country">Country Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
{
    "countryId"     : 172,
    "short_name"    : "Paraguay",
    "spanish_name"  : "Paraguay",
    "italian_name"  : null
}
</p></pre></code>

<h3><b><a id="contextmemento">Public Memento Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
    "ContextMemento" : {
        "contextMementoId" : 999,
        "contextId" : 999,                 
        "url": "https://dl.dropbox.com/u/2797542/timeline/fantozzi/Fantozzi_il_ritorno.png",
        "thumbnail": "",
        "decade": 1930,
        "startDate": "",
        "endDate": "",
        "participants" : [ <person ids of people visible in the memento> ],
        "headline": "Foto di Ugo circa la sua nascita in 1934",
        "type": "photo",
        "category" : "photo", 
        "index": 1,
        "credit" : "Wikipedia",
        "creditURL : "<url to the source> ",
        "text": "MG M del 1930 ovvero la prima MG Midget",
        "tags": [ "photo", "automobile", "ogetto" ], 
        "questionList": [ <list of question resources that can trigger discussion and reminiscence around this memento> ]      
    }
</pre></code></p>

<h3><b><a id="question">Question Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
    "Question" : {
        "questionId": 789,
        "question": "Ti ricordi della prima volta che hai guidato una macchina?",
        "locale":"it_IT", 
        "tags": [
            "automobile",
            "ogetto"
        ]
        "translations" : [ { } ]
    }
</pre></code></p>

<h3><b><a id="translation">Question Translation Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
{
    "questionId": 789,
    "question": "Ti ricordi della prima volta che hai guidato una macchina?",
    "locale":"it_IT", 
    "tags": [
        "automobile",
        "ogetto"
    ]
}
</pre></code></p>

<h3><b><a id="context">Context Resource (TO UPDATE)</a></b></h3>
<p><pre><code>
    "Context" : {
        "contextId" : 999,
        "forPersonId" : 999,
        "title" : "The contextual reminiscence multimedia for Ugo Fantozzi",
        "subtitle" : "A set of old public collective memory items that can help Ugo Fantozzi remember his life",
        "curators" : [ <person ids of the people with full access to this timeline> ],
        "mementoList" : [ <list of life stories that belong to this timeline> ]
    }
}
</pre></code></p>

<h3><b><a id="response_status">Response Status Resource (TODO):</a></b></h3>
<p><pre><code>
{
    "status_code": "BADREQUEST", 
    "status_message":"User was created",
    "errorTrace" : "[error complete trace for sending to developers]" 
}    
</pre></code></code>

<p><b>Observation:</b>for every date information in the response, there will be both the Unix Timestamp (e.g., "date" : 1357016400000) 
representaion and the string representation (e.g., "dateAsString" : "2013-01-01 00:00:00"</p> 

# Group User management
<p> The user management section of this API groups the endpoints related to signing up, in and out of the application. 
These are the entry points to Reminiscens' services</p>
<p> <b><a href="user">Users</a></b> endpoints are also included, giving access to operations on user data, including also his
<a href="#person">Person</a> profile information. </p>

## Login 

<p><b>Signing in and creating a session.</b> </p>

<p>Request: </p>

 + <b> Mandatory. </b> The authentication <a href="#login">Service</a> resource.

<p>Response:</p>

 + The newly created <a href="#session">Session</a> as part of <a href="#user">User</a> resource that created the session.
 
<p>Below and example of the HTTP request for login implemented in Javascript. In the future, we will the 
support password that are not send in clear text 
</p>
<script src="https://gist.github.com/cdparra/6111666.js"></script>
<p> Once the sessionKey is obtained, it will have to be added as a header for all the other requests </p> 
<script src="https://gist.github.com/cdparra/6185587.js"></script>

POST /login
> Content-Type: application/json
{  
    "email": "reminiscensapp+4@gmail.com",
    "password": "testing-password"
}
< 200
< Content-Type: application/json
{
    "userId": 4,
    "email": "reminiscensapp+4@gmail.com",
    "username": "yparra.test",
    "profilePic": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/profile.JPG",
    "locale": "es_ES",
    "emailVerified": true,
    "usernameVerified": true,
    "active": true,
    "creationDate": 1357016400000,
    "sessionKey": "6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com",
    "person": {
        "personId": 3,
        "firstname": "Ygnacio",
        "lastname": "Parra",
        "birthdate": -881866800000,
        "deathdate": null,
        "gender": "male",
        "birthplace": {
            "cityId": 9092,
            "name": "Luque",
            "country": {
                "countryId": 172,
                "short_name": "Paraguay",
                "spanish_name": "Paraguay",
                "italian_name": null
            },
            "lat": -25.27,
            "lon": -57.487222
        },
        "famous": null,
        "famousId": null,
        "birthdateAsString": "1942-01-21 00:00:00",
        "deathdateAsString": null
    },
    "creationDateAsString": "2013-01-01 00:00:00"
}
+++++
< 401
< Content-Type: application/json
{  
    "responseStatus": "UNAUTHORIZED",
    "statusMessage": "playauthenticate.password.login.unknown_user_or_pw",
    "errorTrace": null
}
+++++
< 404
< Content-Type: application/json
{  
    "status_code": "NOTAVAILABLE", 
    "status_message":"playauthenticate.core.exception.provider_not_found=facebook",
    "errorTrace": null
}
+++++
< 500
< Content-Type: application/json
{  
    "status_code": "SERVERERROR", 
    "status_message":"playauthenticate.core.exception.general"
}

<p><b>Signup a new <a href="#user">User</a> endpoint </b></p>
<p>Request:</p>
 
 + A <a href="#user">User</a> including: 
    + <b>Mandatory.</b> email.
    + <b>Mandatory.</b> password. (which is stored encrypted with BCrypt)
    + <b>Mandatory.</b> repeatedPassword
    + A <a href="#person">Person</a>. resource with:  
        + <b>Mandatory.</b> firstname  
        + <b>Mandatory.</b> lastname
        + <b>Mandatory.</b> birthdate (in "yyyy-MM-dd HH:mm:ss" format)
        + <b>Mandatory.</b> birthplaceId of the <a href="#city">City</a> resource in which the person was born.
        
<p><b>Observation: </b> A list of cities that we currently support, with their respective IDs, can be downloaded from 
<a href="http://test.reminiscens.me/lifeapi/city"> here</a></p>

<p>Response: </p> 

 + The newly created <a href="#user">User</a> resource with a generated nickname and corresponding <a href="#person">Person</a> 
and <a href ="#session"> Session </a> key

POST /signup
> Content-Type: application/json
{  
    "email":"resminiscensapp+99@gmail.com",
    "password":"testing-password",
    "repeatPassword":"testing-password",
    "name":"Testing user",
    "person": {
        "firstname"  : "Testing",
        "lastname"   : "User",
        "birthdate"  : "1984-06-21 12:00:00",
        "birthplaceId" : 9091
  }
}
< 200
< Content-Type: application/json
{
    "userId": 99,
    "email": "reminiscensapp+99@gmail.com",
    "username": "testing.user99",
    "profilePic": null,
    "locale": null,
    "emailVerified": true,
    "usernameVerified": true,
    "active": true,
    "creationDate": 1357016400000,
    "sessionKey": "6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com",
    "person": {
        "personId": 99,
        "firstname": "Testing",
        "lastname": "user",
        "birthdate": -881866800000,
        "gender": null,
        "birthplace": {
            "cityId": 9092,
            "name": "Luque",
            "country": {
                "countryId": 172,
                "short_name": "Paraguay",
                "spanish_name": "Paraguay",
                "italian_name": null
            },
            "lat": -25.27,
            "lon": -57.487222
        },
        "famous": null,
        "famousId": null,
        "birthdateAsString": "1942-01-21 00:00:00",
    },
    "creationDateAsString": "2013-01-01 00:00:00"
}
+++++
< 400
< Content-Type: application/json
{  
    "responseStatus": "BADREQUEST",
    "statusMessage": "playauthenticate.password.filledFormHasErrors:{person is required}",
    "errorTrace": null
}
+++++
< 500
< Content-Type: application/json
{  
    "status_code": "SERVERERROR", 
    "status_message":"playauthenticate.core.exception.general"
}

<p><b>Update user endpoint</b></p>
<p>Request: </p>
 
 + The <a href="#user">User</a> with the udpated fields
 + The <a href="#person">person</a> with the udpated fields

<p>Response:</p> 

 + The <a href="#user">User</a> resource with its updated information
 + The <a href="#user">Person</a> resource with its updated information

PUT /user/{id}
> Content-Type: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com 
{"url":"/user/999"}
{   
  {
  "email":"reminiscensapp+99@gmail.com",
  "password":"testing-password",
  "repeatPassword":"testing-password",
  "locale":"it_IT",
  "person": {
      "firstname"  : "Pinco",
    "lastname"   : "Pallino",
    "birthdate"  : "1984-06-21 18:00:00",
    "birthplaceId" : 9091
  }
}
< 200
< Content-Type: application/json
{
    "userId": 999,
    "email": "reminiscensapp+99@gmail.com",
    "username": "testing.user99",
    "profilePic": null,
    "locale": "it_IT",
    "emailVerified": true,
    "usernameVerified": true,
    "active": true,
    "creationDate": 1357016400000,
    "sessionKey": "6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com",
    "person": {
        "personId": 99,
        "firstname": "Testing",
        "lastname": "user",
        "birthdate": -881866800000,
        "gender": null,
        "birthplace": {
            "cityId": 9092,
            "name": "Luque",
            "country": {
                "countryId": 172,
                "short_name": "Paraguay",
                "spanish_name": "Paraguay",
                "italian_name": null
            },
            "lat": -25.27,
            "lon": -57.487222
        },
        "famous": null,
        "famousId": null,
        "birthdateAsString": "1942-01-21 00:00:00",
    },
    "creationDateAsString": "2013-01-01 00:00:00"
}
+++++
< 401
< Content-Type: application/json
{  
    "Response_Status": {
        "status_code": "42", 
        "status_message":"Auth string is not valid"
    }
}
+++++
< 501
< Content-Type: application/json
{   "Response_Status": {
        "status_code": "51", 
        "status_message":"The resource you tried to create is incomplete"
    }
}
+++++
< 501
< Content-Type: application/json
{  
    "Response_Status": {
        "status_code": "52", 
        "status_message":"The resource you tried to create already exists"
    }
}

<p><b>Soft Delete user endpoint</b></p>
<p>Request: </p>
 
 + The <a href="#user">Session</a> to delete (only the user itself and his/her main curators can delete 
 the user (which means deleting all of his/her data too)
 
DELETE /user/{id}
> Accept: text/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com 
{"url":"/user/999"}
< 200
< Content-Type: application/x-www-form-urlencoded
{
    "responseStatus": "OK",
    "statusMessage": "Entity deleted with success",
    "errorTrace": null
}

<p><b>Hard Delete user endpoint</b></p>
<p>Request: </p>
 
 + The <a href="#user">Session</a> to delete (only the user itself and his/her main curators can delete 
 the user (which means deleting all of his/her data too)
 
DELETE /user/{id}/force
> Accept: text/json
{"url":"/user/999"}
< 200
< Content-Type: application/x-www-form-urlencoded
{
    "responseStatus": "OK",
    "statusMessage": "Entity deleted with success",
    "errorTrace": null
}

--
People, Life Stories and Timelines  (TO UPDATE)

<p> In this section we group API endpoints that allow applications to access personal information and 
stories of both public people and registered <a href="#user">Users</a> that interact with the system by adding, 
visualizing or editing life stories and exploring the surrounding <a href="#context">Context</a> 
(composed by public related content).</p>

<p> The life API provides endpoints to read, update, delete and create life stories and multimedia related to 
them (i.e., mementos). Moreover, stories and mementos are organized in a timeline that is about a particular
person (that represents the user or a friend of the user). </p>

<p>Through these endpoints, users can only access a person's available information. For creation and update operations on the 
<a href="person">Person</a> resource, the <a href="#user">User's</a> endpoints must be used</p>
--

<p> Get the basic information related to the Person identified by <code>id</code>
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a>

<p>Response:</p> 
 + <a href="#person">Person</a> 
 
<p>Authorization:</p> 
 
 + User must be ADMIN

GET /person/{id}
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url":"/person/3"}
< 200 
< Content-Type: application/json
{
    "personId": 3,
    "firstname": "Ygnacio",
    "lastname": "Parra",
    "birthdate": -881866800000,
    "deathdate": null,
    "gender": "male",
    "birthplace": {
        "cityId": 9092,
        "name": "Luque",
        "country": {
            "countryId": 172,
            "short_name": "Paraguay",
            "spanish_name": "Paraguay",
            "italian_name": null
        },
        "lat": -25.27,
        "lon": -57.487222
    },
    "famous": null,
    "famousId": null,
    "birthdateAsString": "1942-01-21 00:00:00",
    "deathdateAsString": null
}

<p> Create a person record (to reference within stories or relationships)
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a> header
 +  <b> Mandatory. </b> <a href="#person">Person</a> in the body

<p>Response:</p> 

 + <a href="#person">Person</a> just created  
 
<p>Authorization:</p> 
 
 + User must be MEMBER

POST /person
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{
  "firstname" : "I am a TEST", 
  "lastname" : "Please call me person"
}
< 200 
< Content-Type: application/json
{
    "personId": 999,
    "firstname": "I am a TEST",
    "lastname": "Please call me person",
    "birthdate": null,
    "deathdate": null,
    "gender": null,
    "birthplace": null,
    "famous": null,
    "famousId": null,
    "birthdateAsString": null,
    "deathdateAsString": null
}

<p> Update a person record 
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a> header
 +  <b> Mandatory. </b> <a href="#person">Person</a> in the body

<p>Response:</p> 

 + <a href="#person">Person</a> just updated  
 
<p>Authorization:</p> 
 
 + User must be the same person

PUT /person/{id}
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{
  "url" : "/person/999",
  "personId":999,
  "firstname":"Francisco", 
  "lastname":"Parra Marmolejo", 
  "birthdate":"1907-01-21 00:00:00",  
  "gender":"male", 
  "birthplace": {
    "cityId":9092
  }
}
< 200 
< Content-Type: application/json
{
    "personId": 999,
    "firstname":"Francisco", 
    "lastname":"Parra Marmolejo", 
    "birthdate": -881866800000,
    "deathdate": null,
    "gender": "male",
    "birthplace":  {
        "cityId": 9092,
        "name": "Luque",
        "country": {
            "countryId": 172,
            "short_name": "Paraguay",
            "spanish_name": "Paraguay",
            "italian_name": null
        },
        "lat": -25.27,
        "lon": -57.487222
    },
    "famous": null,
    "famousId": null,
    "birthdateAsString": "1907-01-21 00:00:00",
    "deathdateAsString": null
}

<p> Update a person record 
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a> header
 +  <b> Mandatory. </b> <a href="#person">Person</a> in the body

<p>Response:</p> 

 + <a href="#person">Person</a> just updated  
 
<p>Authorization:</p> 
 
 + User must be the same person

DELETE /person/{id}
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url" : "/person/999"}
< 200 
< Content-Type: application/json
{
    "personId": 999,
    "firstname":"Francisco", 
    "lastname":"Parra Marmolejo", 
    "birthdate": -881866800000,
    "deathdate": null,
    "gender": "male",
    "birthplace":  {
        "cityId": 9092,
        "name": "Luque",
        "country": {
            "countryId": 172,
            "short_name": "Paraguay",
            "spanish_name": "Paraguay",
            "italian_name": null
        },
        "lat": -25.27,
        "lon": -57.487222
    },
    "famous": null,
    "famousId": null,
    "birthdateAsString": "1907-01-21 00:00:00",
    "deathdateAsString": null
}

<p> Get the timeline of life stories of a person: </p>
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a>

<p>Response: </p> 

+ A <a href="#timeline">Timeline</a> resource 
 
GET /person/{id}/timeline
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url":"/person/4/timeline"}
< 200 
< Content-Type: application/json
{
    "aboutPerson": {
        "personId": 4,
        "firstname": "Bruno",
        "lastname": "Kessler",
        "birthdate": -1447614000000,
        "deathdate": 669358800000,
        "gender": "male",
        "birthplace": {
            "cityId": 4015,
            "name": "Trento",
            "country": null,
            "lat": 46.069692,
            "lon": 11.121089
        },
        "famous": {
            "famousId": 4,
            "source": null,
            "sourceUrl": null,
            "resourceUrl": "https://dl.dropboxusercontent.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
            "locale": "it_IT",
            "tags": null,
            "indexed": false,
            "lastUpdate": 1371787199000,
            "fullname": null,
            "firstname": "Bruno",
            "lastname": "Kessler",
            "famousFor": "Presidente del Consiglio di Trento",
            "birthDate": null,
            "deathDate": null,
            "birhplace": null,
            "deathplace": null,
            "country": {
                "countryId": 109,
                "short_name": "Italy",
                "spanish_name": "Italia",
                "italian_name": null
            },
            "status": "CONFIRMED",
            "creatorType": "SYSTEM",
            "creationDate": 1357102799000,
            "creationDateAsString": "2013-01-01 23:59:59",
            "lastUpdateAsString": "2013-06-20 23:59:59"
        },
        "birthdateAsString": "1924-02-17 00:00:00",
        "deathdateAsString": "1991-03-19 00:00:00",
        "famousId": 4
    },
    "storyList": [
        {
            "lifeStoryId": 2,
            "headline": "Birth",
            "text": "Bruno Kessler is born in Peio, Province of Trento, Italy.",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": {
                "questionId": 376,
                "question": "When and where were you born?",
                "category": "Thoughtful",
                "chapter": "The Basics",
                "locale": "en_EN",
                "ranking": 12348,
                "translations": [
                    {
                        "translationId": 1375,
                        "questionId": 376,
                        "question_text": "Quando e dove sei nato?",
                        "category": "Riflessivo",
                        "chapter": "Le nozioni di base",
                        "locale": "it_IT"
                    },
                    {
                        "translationId": 2375,
                        "questionId": 376,
                        "question_text": "¿Cuándo y dónde nació?",
                        "category": "Pensativo",
                        "chapter": "Los fundamentos",
                        "locale": "es_ES"
                    }
                ]
            },
            "startDate": {
                "fuzzyDateId": 10460,
                "textual_date": null,
                "exactDate": -1447614000000,
                "decade": 1920,
                "year": 1924,
                "month": "2",
                "day": "17",
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1924-02-17 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 8,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 2
                }
            ],
            "mementoList": [
                {
                    "mementoId": 2,
                    "headline": "\"A picture of Bruno in his childhood aroud 1935. In the picture, Bruno is with his older brother\"",
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/childhood-fake.JPG",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 2
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 3,
            "headline": "Bachelor in Law",
            "text": "in 1950 graduated in law to ' University of Padua",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 2297,
                "location_textual": "University of Padua",
                "name": "University of Padua",
                "country": "Italy",
                "region": null,
                "cityName": "Padova",
                "coordinates_trust": null,
                "lat": null,
                "lon": null,
                "locale": null,
                "city": {
                    "cityId": 4521,
                    "name": "Padova",
                    "country": null,
                    "lat": 45.406435,
                    "lon": 11.876761
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10461,
                "textual_date": null,
                "exactDate": -631134000000,
                "decade": 1950,
                "year": 1950,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1950-01-01 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 9,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 3
                }
            ],
            "mementoList": [
                {
                    "mementoId": 3,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/kessler.jpeg",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 3
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 4,
            "headline": "Council of Trento",
            "text": "In 1956 was first elected to the Council of the Autonomous Province of Trento with the Christian Democrats [2] (remaining in the Council until 1976 [3]",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10462,
                "textual_date": null,
                "exactDate": -441831600000,
                "decade": 1950,
                "year": 1956,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1956-01-01 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 10,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 4
                }
            ],
            "mementoList": [
                {
                    "mementoId": 4,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 4
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 5,
            "headline": "President of Autonomous Province of Trento",
            "text": "He was president of the Autonomous Province of Trento from 1960 to 1973",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10463,
                "textual_date": null,
                "exactDate": -304113600000,
                "decade": 1960,
                "year": 1960,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1960-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 11,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 5
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 6,
            "headline": "Founded the Trentinian Culture Institute",
            "text": "\"In 1962 he founded the ' Istituto Trentino di Cultura , the first core of the ' University of Trento . The faculty of sociology is the first d ' Italy\"",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10464,
                "textual_date": null,
                "exactDate": -241041600000,
                "decade": 1960,
                "year": 1962,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1962-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 12,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 6
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 7,
            "headline": "Deputy",
            "text": "He was deputy in the VII [4] and in the ' eighth Legislature [5] ( 1976 - 1983 ) and Secretary to ' Interior in the Government Cossiga ( 1979 - 1980 ). [6]",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 1528,
                "location_textual": "Roma",
                "name": null,
                "country": null,
                "region": null,
                "cityName": "Roma",
                "coordinates_trust": 0,
                "lat": 41.892916,
                "lon": 12.48252,
                "locale": null,
                "city": {
                    "cityId": 5906,
                    "name": "Roma",
                    "country": null,
                    "lat": 41.892916,
                    "lon": 12.48252
                },
                "accuracy": 1
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10465,
                "textual_date": null,
                "exactDate": 200808000000,
                "decade": 1970,
                "year": 1976,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1976-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 13,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 7
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 8,
            "headline": "Senate",
            "text": "Elected to the Senate in 1983",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 1528,
                "location_textual": "Roma",
                "name": null,
                "country": null,
                "region": null,
                "cityName": "Roma",
                "coordinates_trust": 0,
                "lat": 41.892916,
                "lon": 12.48252,
                "locale": null,
                "city": {
                    "cityId": 5906,
                    "name": "Roma",
                    "country": null,
                    "lat": 41.892916,
                    "lon": 12.48252
                },
                "accuracy": 1
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10466,
                "textual_date": null,
                "exactDate": 421646400000,
                "decade": 1980,
                "year": 1983,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1983-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 14,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 8
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 9,
            "headline": "Death",
            "text": "Bruno Kessler dies in Trento",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10467,
                "textual_date": null,
                "exactDate": 669358800000,
                "decade": 1990,
                "year": 1991,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1991-03-19 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 15,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 9
                }
            ],
            "mementoList": [
                {
                    "mementoId": 9,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/kessler-1.jpeg",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 9
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 10,
            "headline": "Bruno Kessler Foundation",
            "text": "\"The Foundation was created on 1 March 2007. FBK inherits the activities of the Istituto Trentino di Cultura, which was based on the ideas of Bruno Kessler, a long-time member of the local government and founder of the University of Trento. \"",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10468,
                "textual_date": null,
                "exactDate": 1172725200000,
                "decade": 2000,
                "year": 2007,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "2007-03-01 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 16,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 10
                }
            ],
            "mementoList": [
                {
                    "mementoId": 10,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/FBK_polo_scientifico.png",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 10
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        }
    ],
    "curators": null
}

<p> Get the timeline of life stories of a person, including only stories from START to END (by order of insersion): </p>
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a>

<p>Response: </p> 

+ A <a href="#timeline">Timeline</a> resource 
 
GET /person/{id}/timeline/{start}/{end}
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url":"/person/4/timeline/2/4"}
< 200 
< Content-Type: application/json
{
    "aboutPerson": {
        "personId": 4,
        "firstname": "Bruno",
        "lastname": "Kessler",
        "birthdate": -1447614000000,
        "deathdate": 669358800000,
        "gender": "male",
        "birthplace": {
            "cityId": 4015,
            "name": "Trento",
            "country": null,
            "lat": 46.069692,
            "lon": 11.121089
        },
        "famous": {
            "famousId": 4,
            "source": null,
            "sourceUrl": null,
            "resourceUrl": "https://dl.dropboxusercontent.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
            "locale": "it_IT",
            "tags": null,
            "indexed": false,
            "lastUpdate": 1371787199000,
            "fullname": null,
            "firstname": "Bruno",
            "lastname": "Kessler",
            "famousFor": "Presidente del Consiglio di Trento",
            "birthDate": null,
            "deathDate": null,
            "birhplace": null,
            "deathplace": null,
            "country": {
                "countryId": 109,
                "short_name": "Italy",
                "spanish_name": "Italia",
                "italian_name": null
            },
            "status": "CONFIRMED",
            "creatorType": "SYSTEM",
            "creationDate": 1357102799000,
            "creationDateAsString": "2013-01-01 23:59:59",
            "lastUpdateAsString": "2013-06-20 23:59:59"
        },
        "birthdateAsString": "1924-02-17 00:00:00",
        "deathdateAsString": "1991-03-19 00:00:00",
        "famousId": 4
    },
    "storyList": [
        {
            "lifeStoryId": 4,
            "headline": "Council of Trento",
            "text": "In 1956 was first elected to the Council of the Autonomous Province of Trento with the Christian Democrats [2] (remaining in the Council until 1976 [3]",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10462,
                "textual_date": null,
                "exactDate": -441831600000,
                "decade": 1950,
                "year": 1956,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1956-01-01 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 10,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 4
                }
            ],
            "mementoList": [
                {
                    "mementoId": 4,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 4
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 5,
            "headline": "President of Autonomous Province of Trento",
            "text": "He was president of the Autonomous Province of Trento from 1960 to 1973",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10463,
                "textual_date": null,
                "exactDate": -304113600000,
                "decade": 1960,
                "year": 1960,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1960-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 11,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 5
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        }
    ],
    "curators": null
}

<p> Synch local timeline </p>
<p>Request: </p>
 
 +  <b> Mandatory. </b> <a href="#session">Session</a>
 +  <b> Mandatory. </b> <a href="#session">Timeline</a>

<p>Response: </p> 

+ <a href="#timeline">Timeline</a> with StoryList having missing stories in the local version

POST /person/{id}/timeline
> Accept: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url":"/person/999/timeline"}
{
    "storyList": [
        {
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }
    ]
}
< 200 
< Content-Type: application/json
{
    "aboutPerson": {
        "personId": 4,
        "firstname": "Bruno",
        "lastname": "Kessler",
        "birthdate": -1447614000000,
        "deathdate": 669358800000,
        "gender": "male",
        "birthplace": {
            "cityId": 4015,
            "name": "Trento",
            "country": null,
            "lat": 46.069692,
            "lon": 11.121089
        },
        "famous": {
            "famousId": 4,
            "source": null,
            "sourceUrl": null,
            "resourceUrl": "https://dl.dropboxusercontent.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
            "locale": "it_IT",
            "tags": null,
            "indexed": false,
            "lastUpdate": 1371787199000,
            "fullname": null,
            "firstname": "Bruno",
            "lastname": "Kessler",
            "famousFor": "Presidente del Consiglio di Trento",
            "birthDate": null,
            "deathDate": null,
            "birhplace": null,
            "deathplace": null,
            "country": {
                "countryId": 109,
                "short_name": "Italy",
                "spanish_name": "Italia",
                "italian_name": null
            },
            "status": "CONFIRMED",
            "creatorType": "SYSTEM",
            "creationDate": 1357102799000,
            "creationDateAsString": "2013-01-01 23:59:59",
            "lastUpdateAsString": "2013-06-20 23:59:59"
        },
        "birthdateAsString": "1924-02-17 00:00:00",
        "deathdateAsString": "1991-03-19 00:00:00",
        "famousId": 4
    },
    "storyList": [
        {
            "lifeStoryId": 4,
            "headline": "Council of Trento",
            "text": "In 1956 was first elected to the Council of the Autonomous Province of Trento with the Christian Democrats [2] (remaining in the Council until 1976 [3]",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10462,
                "textual_date": null,
                "exactDate": -441831600000,
                "decade": 1950,
                "year": 1956,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1956-01-01 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 10,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 4
                }
            ],
            "mementoList": [
                {
                    "mementoId": 4,
                    "headline": null,
                    "text": null,
                    "type": "PHOTO",
                    "url": "http://dl.dropbox.com/u/2797542/timeline/bkessler/bruno_kessler_imagelarge.jpeg",
                    "thumbnailUrl": null,
                    "category": "PICTURE",
                    "isCover": true,
                    "position": 1,
                    "publicMemento": true,
                    "location": null,
                    "startDate": null,
                    "participants": [],
                    "lifeStoryId": 4
                }
            ],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "lifeStoryId": 5,
            "headline": "President of Autonomous Province of Trento",
            "text": "He was president of the Autonomous Province of Trento from 1960 to 1973",
            "richtext": null,
            "creationDate": 1348718400000,
            "locale": "it_IT",
            "location": {
                "locationId": 477,
                "location_textual": "Trento",
                "name": null,
                "country": "Italia",
                "region": "Trentino-Alto Adige/Sudtirol",
                "cityName": "Trento",
                "coordinates_trust": 1,
                "lat": 46.069692,
                "lon": 11.121089,
                "locale": null,
                "city": {
                    "cityId": 4015,
                    "name": "Trento",
                    "country": null,
                    "lat": 46.069692,
                    "lon": 11.121089
                },
                "accuracy": 11
            },
            "question": null,
            "startDate": {
                "fuzzyDateId": 10463,
                "textual_date": null,
                "exactDate": -304113600000,
                "decade": 1960,
                "year": 1960,
                "month": null,
                "day": null,
                "accuracy": 11,
                "locale": null,
                "exactDateAsString": "1960-05-13 00:00:00"
            },
            "participationList": [
                {
                    "participationId": 11,
                    "contributorId": 1,
                    "personId": 4,
                    "protagonist": true,
                    "lifeStoryId": 5
                }
            ],
            "mementoList": [],
            "creationDateAsString": "2012-09-27 00:00:00",
            "contributorId": 2
        },
        {
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }
    ],
    "curators": null
}

<p> Create a new story: </p>

<p>Request: </p> 
    + <b> Mandatory. </b> <a href="#session">Session</a>
    + <b> Mandatory. </b> <a href="#session">Life Story</a> with at least:
        + Title
        + Decade or year
        + Location 

<p>Response:</p> 
    + <a href="#timeline">Life Story</a> ID 

POST /lifestory
> Content-Type: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }   
< 200
< Content-Type: application/json
{
            "storyId" : 99,
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }

<p> Get a life story: </p>

<p>Request: </p> 
    + <b> Mandatory. </b> <a href="#session">Session</a>

<p>Response:</p> 
    + <a href="#timeline">Life Story</a> 
GET /lifestory/{id}
> Content-Type: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{"url":"/lifestory/999"}
< 200
< Content-Type: application/json
{
        "storyId" : 999,
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }


Update a story

PUT /lifestory/{id}
> Content-Type: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }
< 200
< Content-Type: application/json
{
        "storyId"  : 99,
            "headline": "Nacimiento de mi primera nieta",
            "text": "En el año 2007, nace Sofia, la primera nieta de la familia hija de Cynthia",
            "richtext": null,
            "type": "milestone",
            "visibility": 1,
            "contributorId": 3,
            "locale": "es_ES",
            "location": {
                "location_textual": "Sanatorio La Costa, Asunción, Paraguay"
            },
            "startDate": {
                "exactDate": "16-08-2007 12:00"
            },
            "synced": false,
            "mementoList": [
                {
                    "url": "https://dl.dropboxusercontent.com/u/2797542/timeline/fliaparra/ygnacio/Sofi.JPG",
                    "type": "photo",
                    "category": "photo",
                    "isCover": true,
                    "index": 1,
                    "headline": "Sofi en la hamaca",
                    "public_memento": false
                }
            ]
        }

--
Contextual Reminiscence  (TO UPDATE)

<p> Reminiscence is a phenomena that can be fostered when people interact with content that reminds them about 
their lives. For supporting this, for each user in our system, a semi-automatic <a href="#context">Context</a>
is prepared consisting of a list of <a href="#contextmemento"> Contextual Mementos</a> that are somehow 
related to the life stories of the person, including pictures of places where the person has been, 
music from his youth, people from the different period of his life, events that happened around his own 
stories. All these content is served through this API to stimulate people to reminisce. </p> 

<p> The context is semi-automatic because it starts with an automatic set of mementos, created by the 
<a href="http://docs.lifecontext.apiary.io/">Lifecontext API</a>, but can later be updated by 
a list of human curators that know the person who is going to be stimulated (e.g., his/her family). 
</p> 

<p> To further stimulate (or facilitate the practice of reminiscence to people), relevant contextual
<a href="#question"> Questions</a> surround each contextual memento, to which the person can reply 
by creating a new story. Questions also appear when there are no stories in a decade, as an starter
for storytelling</p>
--

Get the context for a person
GET /person/{id}/context
> Content-Type: application/json
{"url":"/person/999/context"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was created"
    },
    "Context": {
        "contextId": 999,
        "forPersonId": 999,
        "curators": [
            777,
            778,
            779
        ],
        "mementoList": [
            {
                "contextMementoId": 999,
                "contextId": 999,
                "url": "http://farm8.staticflickr.com/7230/7262518946_81794f46d0_o.jpg",
                "thumbnail": "",
                "headline": "MG M - 1930",
                "decade": 1930,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "type": "photo",
                "category": "photo",
                "index": 2,
                "credit": "Some rights reserved by ciric",
                "creditURL": "",
                "text": "MG M del 1930 ovvero la prima MG Midget",
                "tags": [
                    "photo",
                    "automobile",
                    "ogetto"
                ],
                "questionList": [
                    {
                        "questionId": 999,
                        "question": "Riconosci questa macchina?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    },
                    {
                        "questionId": 998,
                        "question": "Hai mai guidato una macchina come questa?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    }
                ]
            },
            {
                "contextMementoId": 997,
                "contextId": 999,
                "url": "http://farm3.staticflickr.com/2781/4437292021_3f6b977f8a.jpg",
                "thumbnail": "",
                "decade": 1960,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "headline": "Monte Gazzo 21",
                "type": "photo",
                "category": "photo",
                "index": 2,
                "credit": "Some rights reserved by ciric",
                "creditURL": "",
                "text": "1960: veduta area del Santuario del Monte Gazzo.",
                "tags": [
                    "photo",
                    "landscape"
                ],
                "questionList": [
                    {
                        "questionId": 997,
                        "question": "Riconosci questo posto?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    },
                    {
                        "questionId": 996,
                        "question": "Lei è mai stat in questo castello?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    }
                ]
            },
            {
                "contextMementoId": 996,
                "contextId": 999,
                "url": "https://www.youtube.com/watch?feature=player_detailpage&v=78LXfimeh8g",
                "thumbnail": "",
                "decade": 1960,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "headline": "Claudio Villa in Rai Due",
                "type": "music",
                "category": "music",
                "index": 3,
                "credit": "Uploaded by Youtube user Kartastone",
                "creditURL": "https://www.youtube.com/user/Kartastone?feature=watch",
                "text": "",
                "tags": [
                    "musica",
                    "youtube",
                    "musicista",
                    "video"
                ],
                "questionList": [
                    {
                        "questionId": 997,
                        "question": "Ti piace questa musica?",
                        "tags": [
                            "musica"
                        ]
                    },
                    {
                        "questionId": 996,
                        "question": "Lei è mai stat in questo castello?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    }
                ]
            }
        ]
    }
}

Get a context by ID
GET /context/{id}
> Content-Type: application/json
{"url":"/context/999"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was created"
    },
    "Context": {
        "contextId": 999,
        "forPersonId": 999,
        "curators": [
            777,
            778,
            779
        ],
        "mementoList": [
            {
                "contextMementoId": 999,
                "contextId": 999,
                "url": "http://farm8.staticflickr.com/7230/7262518946_81794f46d0_o.jpg",
                "thumbnail": "",
                "headline": "MG M - 1930",
                "decade": 1930,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "type": "photo",
                "category": "photo",
                "index": 2,
                "credit": "Some rights reserved by ciric",
                "creditURL": "",
                "text": "MG M del 1930 ovvero la prima MG Midget",
                "tags": [
                    "photo",
                    "automobile",
                    "ogetto"
                ],
                "questionList": [
                    {
                        "questionId": 999,
                        "question": "Riconosci questa macchina?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    },
                    {
                        "questionId": 998,
                        "question": "Hai mai guidato una macchina come questa?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    }
                ]
            },
            {
                "contextMementoId": 997,
                "contextId": 999,
                "url": "http://farm3.staticflickr.com/2781/4437292021_3f6b977f8a.jpg",
                "thumbnail": "",
                "decade": 1960,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "headline": "Monte Gazzo 21",
                "type": "photo",
                "category": "photo",
                "index": 2,
                "credit": "Some rights reserved by ciric",
                "creditURL": "",
                "text": "1960: veduta area del Santuario del Monte Gazzo.",
                "tags": [
                    "photo",
                    "landscape"
                ],
                "questionList": [
                    {
                        "questionId": 997,
                        "question": "Riconosci questo posto?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    },
                    {
                        "questionId": 996,
                        "question": "Lei è mai stat in questo castello?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    }
                ]
            },
            {
                "contextMementoId": 996,
                "contextId": 999,
                "url": "https://www.youtube.com/watch?feature=player_detailpage&v=78LXfimeh8g",
                "thumbnail": "",
                "decade": 1960,
                "startDate": "",
                "endDate": "",
                "participants": [],
                "headline": "Claudio Villa in Rai Due",
                "type": "music",
                "category": "music",
                "index": 3,
                "credit": "Uploaded by Youtube user Kartastone",
                "creditURL": "https://www.youtube.com/user/Kartastone?feature=watch",
                "text": "",
                "tags": [
                    "musica",
                    "youtube",
                    "musicista",
                    "video"
                ],
                "questionList": [
                    {
                        "questionId": 997,
                        "question": "Ti piace questa musica?",
                        "tags": [
                            "musica"
                        ]
                    },
                    {
                        "questionId": 996,
                        "question": "Lei è mai stat in questo castello?",
                        "tags": [
                            "landscape",
                            "castell"
                        ]
                    }
                ]
            }
        ]
    }
}

Get a contextual memento
GET /context/{id}/memento/{id}
> Content-Type: application/json
{"url":"/context/999"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was created"
    },
    "Memento": {
        "contextMementoId": 999,
        "contextId": 999,
        "url": "http://farm8.staticflickr.com/7230/7262518946_81794f46d0_o.jpg",
        "thumbnail": "",
        "headline": "MG M - 1930",
        "decade": 1930,
        "startDate": "",
        "endDate": "",
        "participants": [],
        "type": "photo",
        "category": "photo",
        "index": 2,
        "credit": "Some rights reserved by ciric",
        "creditURL": "",
        "text": "MG M del 1930 ovvero la prima MG Midget",
        "tags": [
            "photo",
            "automobile",
            "ogetto"
        ],
        "questionList": [
                    {
                        "questionId": 999,
                        "question": "Riconosci questa macchina?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    },
                    {
                        "questionId": 998,
                        "question": "Hai mai guidato una macchina come questa?",
                        "tags": [
                            "automobile",
                            "ogetto"
                        ]
                    }
                ]
    }
}


Add a memento to the context of a person
POST /context/{id}/memento
> Content-Type: application/json
{"url":"/context/999/memento"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    },
    "Memento": {
        "contextMementoId": 999,
        "contextId": 999,
        "url": "http://farm8.staticflickr.com/7230/7262518946_81794f46d0_o.jpg",
        "thumbnail": "",
        "headline": "MG M - 1930",
        "decade": 1930,
        "startDate": "",
        "endDate": "",
        "participants": [],
        "type": "photo",
        "category": "photo",
        "index": 2,
        "credit": "Some rights reserved by ciric",
        "creditURL": "",
        "text": "MG M del 1930 ovvero la prima MG Midget",
        "tags": [
            "photo",
            "automobile",
            "ogetto"
        ]
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was created"
    }, 
    "Memento" : {
        "url":"/context/999/memento/999
    }
}

Update a memento in the context of a person
PUT /context/{id}/memento
> Content-Type: application/json
{"url":"/context/999/memento"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    },
    "Memento": {
        "contextMementoId": 999,
        "contextId": 999,
        "url": "http://farm8.staticflickr.com/7230/7262518946_81794f46d0_o.jpg",
        "thumbnail": "",
        "headline": "MG M - 1930",
        "decade": 1930,
        "startDate": "",
        "endDate": "",
        "participants": [],
        "type": "photo",
        "category": "photo",
        "index": 2,
        "credit": "Some rights reserved by ciric",
        "creditURL": "",
        "text": "MG M del 1930 ovvero la prima MG Midget",
        "tags": [
            "photo",
            "automobile",
            "ogetto"
        ]
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was created"
    }, 
    "Memento" : {
        "url":"/context/999/memento/999
    }
}

Delete a memento from the context of a person
DELETE /context/{id}/memento/{id}
> Content-Type: application/json
{"url":"/context/999/memento/999"}
{  
    "Session" : {
        "auth_token" : "054b13b875efac0604bfa8f21ab92dc26e1e34f5" 
    }
}
< 200
< Content-Type: application/json
{  
    "Response_Status" : {
        "status_code": 1, 
        "status_message":"Resource was deleted"
    }
}

Get questions based on birthyear and a focus year
GET /question/{birthyear}/{focusyear}
> Content-Type: application/json
{ "url":"/person/999/question/1940"}
{ "url":"/question/1940/1948"}
< 200
< Content-Type: application/json
[
    {
        "questionId": 72,
        "question": "Where did you go to college and why did you choose that school?",
        "category": "Education",
        "chapter": "Youth",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1071,
                "questionId": 72,
                "question_text": "Dove sei andato al college e perché hai scelto questa scuola?",
                "category": "Infanzia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2071,
                "questionId": 72,
                "question_text": "¿A dónde fuiste a la universidad y por qué elegiste esa escuela?",
                "category": "Infancia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 493,
        "question": "Did you go to University? Where?",
        "category": "Career",
        "chapter": "Youth",
        "locale": "en_EN",
        "ranking": 1500,
        "translations": [
            {
                "translationId": 1492,
                "questionId": 493,
                "question_text": "Sei andato a Università? Dove?",
                "category": "Carriera",
                "chapter": "Gioventù",
                "locale": "it_IT"
            },
            {
                "translationId": 2492,
                "questionId": 493,
                "question_text": "¿Fuiste a la universidad? ¿Dónde?",
                "category": "Carrera",
                "chapter": "Juventud",
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 71,
        "question": "When did you graduate from college?",
        "category": "Education",
        "chapter": "Youth",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1070,
                "questionId": 71,
                "question_text": "Quando ti sei diplomato al college?",
                "category": "Infanzia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2070,
                "questionId": 71,
                "question_text": "¿Cuándo te gradúes de la universidad?",
                "category": "Infancia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 130,
        "question": "What's your favorite family tradition?",
        "category": "Family",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1129,
                "questionId": 130,
                "question_text": "Qual è la vostra tradizione di famiglia preferito?",
                "category": "Famiglia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2129,
                "questionId": 130,
                "question_text": "¿Cuál es su tradición familiar favorita?",
                "category": "Familia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 129,
        "question": "Describe a time when you felt especially close to your family.",
        "category": "Family",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1128,
                "questionId": 129,
                "question_text": "Descrivi un momento in cui ti sei sentito particolarmente vicino alla tua famiglia.",
                "category": "Famiglia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2128,
                "questionId": 129,
                "question_text": "Describa un momento en que se sintió especialmente cerca de su familia.",
                "category": "Familia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 90,
        "question": "Have you ever taken a picture with a famous person?",
        "category": "Entertainment",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1089,
                "questionId": 90,
                "question_text": "Hai mai preso una foto con una persona famosa?",
                "category": "Intrattenimento",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2089,
                "questionId": 90,
                "question_text": "¿Alguna vez has tomado una foto con una persona famosa?",
                "category": "Entretenimiento",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    }
]

Question for a life chapter 
GET /question/{lifechapter}
> Content-Type: application/json
> PLAY_SESSION: 6500442a574245339a98eb5a2c690af368058d6f-pa.u.exp%3A1376397116201%00pa.p.id%3Apassword%00pa.u.id%3Areminiscensapp%2B4%40gmail.com
{ "url":"/question/childhood"}
< 200
< Content-Type: application/json
[
    {
        "questionId": 85,
        "question": "Is there a photograph from when you took your first steps?",
        "category": "Childhood",
        "chapter": "Childhood",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1084,
                "questionId": 85,
                "question_text": "C'è una fotografia da quando hai preso i tuoi primi passi?",
                "category": "Infanzia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2084,
                "questionId": 85,
                "question_text": "¿Hay una fotografía de cuando se tomó sus primeros pasos?",
                "category": "Infancia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 81,
        "question": "What's your earliest baby photo?",
        "category": "Childhood",
        "chapter": "Childhood",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1080,
                "questionId": 81,
                "question_text": "Qual è il tuo primo bambino foto?",
                "category": "Infanzia",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2080,
                "questionId": 81,
                "question_text": "¿Cuál es tu primer bebé de la foto?",
                "category": "Infancia",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 431,
        "question": "What was your favorite childhood toy? Add a picture.",
        "category": "Childhood",
        "chapter": "Childhood",
        "locale": "en_EN",
        "ranking": 983,
        "translations": [
            {
                "translationId": 1430,
                "questionId": 431,
                "question_text": "Qual era il tuo giocattolo preferito? Aggiungi una foto.",
                "category": "Infanzia",
                "chapter": "Infanzia",
                "locale": "it_IT"
            },
            {
                "translationId": 2430,
                "questionId": 431,
                "question_text": "¿Cuál era tu juguete favorito de la infancia? Añadir una foto.",
                "category": "Infancia",
                "chapter": "Infancia",
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 90,
        "question": "Have you ever taken a picture with a famous person?",
        "category": "Entertainment",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1089,
                "questionId": 90,
                "question_text": "Hai mai preso una foto con una persona famosa?",
                "category": "Intrattenimento",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2089,
                "questionId": 90,
                "question_text": "¿Alguna vez has tomado una foto con una persona famosa?",
                "category": "Entretenimiento",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 117,
        "question": "If you could be a character from any book you've read who would you be?",
        "category": "Entertainment",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1116,
                "questionId": 117,
                "question_text": "Se potessi essere un personaggio di qualsiasi libro che hai letto chi saresti?",
                "category": "Intrattenimento",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2116,
                "questionId": 117,
                "question_text": "Si pudieras ser un personaje de un libro que hayas leído que serías?",
                "category": "Entretenimiento",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    },
    {
        "questionId": 91,
        "question": "When have you performed in front of an audience?",
        "category": "Entertainment",
        "chapter": "All times",
        "locale": "en_EN",
        "ranking": 0,
        "translations": [
            {
                "translationId": 1090,
                "questionId": 91,
                "question_text": "Dopo aver eseguito davanti ad un pubblico?",
                "category": "Intrattenimento",
                "chapter": null,
                "locale": "it_IT"
            },
            {
                "translationId": 2090,
                "questionId": 91,
                "question_text": "Cuando haya realizado en frente de una audiencia?",
                "category": "Entretenimiento",
                "chapter": null,
                "locale": "es_ES"
            }
        ]
    }
]


--
Work-In-Progress

<p> API endpoints currently still in development process. Some might be included in final version while other might not. </p>

# Account verification
GET     /accounts/verify/:token         controllers.Signup.verify(token: String)
GET     /accounts/unverified            controllers.Signup.unverified()
GET     /accounts/exists                controllers.Signup.exists()
GET     /login/denied                    controllers.Application.onLoginUserNotFound()

### Authentication
GET     /login                          controllers.Application.index()
GET     /authenticated                  controllers.Restricted.index()
GET     /login/:id                      controllers.Restricted.id(id: String)
GET     /logout                         com.feth.play.module.pa.controllers.Authenticate.logout()
GET     /authenticate/:provider         controllers.AuthenticateLocal.authenticate(provider: String)
GET     /authenticate/:provider/denied  controllers.Application.oAuthDenied(provider: String)

# People, Life Stories and Timeline
POST    /person                         controllers.PersonControl.createPerson()
POST    /person/mention                 controllers.PersonControl.createMentionPerson()
PUT     /person/:id                     controllers.PersonControl.updatePerson(id: Long)
DELETE  /person/:id                     controllers.PersonControl.deletePerson(id: Long)

GET     /person/:id/lifestory           controllers.LifeStoryControl.getPersonLifeStoryAll(id: Long)
GET     /person/:id/lifestory/proganist controllers.LifeStoryControl.getProtagonistLifeStoryAll(id: Long)
DELETE  /lifestory/:lsid                controllers.LifeStoryControl.deleteLifeStory(lsid: Long)
POST    /lifestory/:lsid/person/:id     controllers.LifeStoryControl.addParticipantToLifeStory(lsid: Long, id: Long)
POST    /lifestory/:lsid/protag/:id     controllers.LifeStoryControl.addProtagonistToLifeStory(lsid: Long, id: Long)
DELETE  /lifestory/:lsid/person/:id     controllers.LifeStoryControl.deletePersonFromLifeStory(lsid: Long, id: Long)
POST    /lifestory/:lsid/memento/:mid   controllers.LifeStoryControl.addMementoToLifeStory(lsid: Long, mid: Long)
POST    /lifestory/:lsid/memento        controllers.LifeStoryControl.addNewMementoToLifeStory(lsid: Long)

GET     /person/:id/memento             controllers.MementoControl.getAllPersonMemento(id: Long)
#GET     /person/:id/memento/protagonist controllers.MementoControl.getAllPersonMemento(id: Long)
#GET     /person/:id/memento/mention     controllers.MementoControl.getAllPersonMemento(id: Long)
GET     /memento/:id                    controllers.MementoControl.getMemento(id: Long)
POST    /memento                        controllers.MementoControl.createMemento()
PUT     /memento/:id                    controllers.MementoControl.updateMemento(id: Long)
DELETE  /memento/:id                    controllers.MementoControl.deleteMemento(id: Long)
POST    /memento/:id/person             controllers.MementoControl.addMementoParticipant(id: Long)
POST    /memento/:id/person/:pid        controllers.MementoControl.addMementoParticipantByPersonId(id: Long, pid: Long)

GET     /person/:id/relationships       controllers.RelationshipControl.getPersonRelationships(id: Long)
GET     /person/:id/curators            controllers.RelationshipControl.getPersonCurators(id: Long)
PUT     /relationship/:id               controllers.RelationshipControl.updateRelationship(id: Long)
POST    /relationship                   controllers.RelationshipControl.addRelationship()
DELETE  /relationship/:id               controllers.RelationshipControl.deleteRelationship(id: Long)

# Contextual Reminiscence
GET     /context/:cid                   controllers.ContextControl.getContext(cid: Long)
GET     /context/:cid/curators          controllers.ContextControl.getContextCurators(cid: Long)
GET     /context/:cid/memento/:mid      controllers.ContextControl.getContextMemento(cid: Long, mid: Long)
POST    /context/:cid/memento           controllers.ContextControl.createContextMemento(cid: Long)
PUT     /context/:cid/memento           controllers.ContextControl.updateContextMemento(cid: Long)
DELETE  /context/:cid/memento/:mid      controllers.ContextControl.deleteContextMemento(cid: Long, mid: Long)
GET     /context/:cid/question          controllers.ContextControl.getContextQuestions(cid: Long)
GET     /context/:cid/question/:decade  controllers.ContextControl.getContextQuestionsForDecade(cid: Long, decade: Long)
GET     /context/:cid/question/curated  controllers.ContextControl.getContextQuestionsByCurators(cid: Long)
POST    /context/:cid/question          controllers.ContextControl.createContextCuratedQuestions(cid: Long)

# General Public Mementos API (lifecontext proxy)
GET     /memento/random/:lang           controllers.PublicMementoControl.getRandomMemento(lang: String)
GET     /memento/:mid                   controllers.PublicMementoControl.getMemento(mid: Long)
GET     /memento/:decade/:place         controllers.PublicMementoControl.getMementoList(decade: Long, place: String, lat ?= null, lon ?= null, rad ?= null, lang: String ?= "it_IT")

# Utilities (cities, countries, other stuff)
GET     /city                           controllers.Application.getCities()
GET     /country/:countryId/city        controllers.Application.getCitiesByCountryId(countryId: Long)
GET     /country/:countryName/city      controllers.Application.getCitiesByCountryName(countryName: String)
GET     /city/sync/:lastCityId          controllers.Application.getNewCities(lastCityId: Long)
GET     /city/:cityName                 controllers.Application.getCitiesByCountryName(cityName: String)


--
