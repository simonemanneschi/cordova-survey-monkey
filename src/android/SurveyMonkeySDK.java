package cordova.plugin.surveymonkey;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;

public class SurveyMonkeySDK extends CordovaPlugin {
    public static final int SM_REQUEST_CODE = 0;
    private SurveyMonkey s = new SurveyMonkey();
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showSurvey")) {
            String hash_encuesta = args.getString(0);
            String id_user = args.getString(1);
            this.showSurvey(hash_encuesta,id_user, callbackContext);
            return true;
        }
        return false;
    }
   private void showSurvey(String hash_encuesta,String id_user, CallbackContext callbackContext) {
        if (hash_encuesta != null && hash_encuesta.length() > 0) {
            final CordovaPlugin that = this;
            final String hash_encuesta_that = hash_encuesta;
            final String id_user_that = id_user;
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("id_user", id_user_that);
                        s.startSMFeedbackActivityForResult(that.cordova.getActivity(), SM_REQUEST_CODE, hash_encuesta_that,jsonObj);
                    }catch(JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
