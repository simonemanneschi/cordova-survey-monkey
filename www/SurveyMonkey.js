var exec = require('cordova/exec');

exports.mostrarEncuesta = function (arg0,arg1, success, error) {
    exec(success, error, 'SurveyMonkeySDK', 'mostrarEncuesta', [arg0,arg1]);
};
