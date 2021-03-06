// JavaScript Document
var storage = $.localStorage;
console.log("loading storage");
function SetBaseUrl(baseUrl) {
    storage.set('baseUrl', baseUrl);
}

function GetBaseUrl() {
    return storage.get('baseUrl');
}

function SetSessionKey(Key)
{
	storage.set('sessionKey',Key);
}

function GetSessionKey()
{
	return storage.get('sessionKey');
}

function SetPersonId(Id)
{
	storage.set('personId',Id);
}

function GetPersonId()
{
	return storage.get('personId');
}

function SetUserFullname(Id)
{
	storage.set('userFullname',Id);
}

function GetUserFullname()
{
	return storage.get('userFullname');
}

function SetUserList(userList)
{
	storage.set('userList',userList);
}

function GetUserList()
{
	return storage.get('userList');
}

function SetPersonBirthDate(BirthDate)
{
	storage.set('personBirthDate',BirthDate);
}

function GetPersonBirthDate()
{
	return storage.get('personBirthDate');
}

function SetUserId(Id)
{
	storage.set('userId',Id);
}

function GetUserId()
{
	return storage.get('userId');
}

function GetPersonDeacadeBirthDate()
{
	return storage.get('deacadeBirthDate');	
}

function SetPersonDeacadeBirthDate(decade)
{
	storage.set('deacadeBirthDate',decade);
}

function GetPersonYearBirthDate()
{
	return storage.get('yearBirthDate');	
}

function SetPersonYearBirthDate(year)
{
	console.log("setting year birthdate")
	storage.set('yearBirthDate',year);
}

function GetPersonName()
{
	return storage.get('personName');	
}

function SetPersonName(name)
{
	storage.set('personName',name);
}