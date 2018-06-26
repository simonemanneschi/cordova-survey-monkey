package cordova.plugin.surveymonkey;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;
import android.app.Activity;
import android.content.Intent;

public class SurveyMonkeySDK extends CordovaPlugin {
    public static final int SM_REQUEST_CODE = 0;
    private CallbackContext callbackContext;
    private SurveyMonkey s = new SurveyMonkey();
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("showSurvey")) {
            String hash_encuesta = args.getString(0);
            String json_string = args.getString(1);
            this.showSurvey(hash_encuesta,json_string, callbackContext);
            return true;
        }
        return false;
    }
   private void showSurvey(String hash_encuesta,String json_string, CallbackContext callbackContext) {
        if (hash_encuesta != null && hash_encuesta.length() > 0) {
            final CordovaPlugin that = this;
            final String hash_encuesta_that = hash_encuesta;
            final String json_string_that = json_string;
            cordova.setActivityResultCallback (this);
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        JSONObject jsonObj = new JSONObject(json_string_that);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (this.callbackContext != null) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String respondent = intent.getStringExtra("smRespondent");
                    JSONObject surveyResponse = new JSONObject(respondent);
                    this.callbackContext.success(surveyResponse);
                } catch (JSONException e) {
                    this.callbackContext.error(e.getMessage());
                }
            } else {
                SMError e = (SMError) intent.getSerializableExtra("smError");
                this.callbackContext.error(e.getDescription());
            }
        }
    }
}
