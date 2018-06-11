# cordova-survey-monkey
Implement Survey Monkey Api for Android (iOS SOON!!!).

## Installation


To install from the command line:

```
cordova plugin add cordova-survey-monkey
```

It is also possible to install via repo url directly 

```
cordova plugin add https://github.com/maugonsal/cordova-survey-monkey
```

## Usage

```javascript
//HASH OF THE SURVEY
var hash_survey = "XGF123";
//SEND CUSTOM VARIABLE
var email_user = "example@demo.com";
//SHOW SURVEY
cordova.plugins.SurveyMonkey.showSurvey(hash_survey,email_user);
```