# cordova-survey-monkey
Implement Survey Monkey Api for Android (iOS SOON!!!).

## Installation

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
cordova.plugins.SurveyMonkey.mostrarEncuesta(hash_survey,email_user);
```