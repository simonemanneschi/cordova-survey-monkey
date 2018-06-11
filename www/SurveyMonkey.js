var exec = require('cordova/exec');

exports.showSurvey = function (arg0,arg1, success, error) {
    exec(success, error, 'SurveyMonkeySDK', 'showSurvey', [arg0,arg1]);
};
