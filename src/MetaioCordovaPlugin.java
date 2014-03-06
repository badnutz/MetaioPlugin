package org.apache.cordova.MetaioPlugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.Context;
import com.metaio.sdk.MetaioDebug;

/**
 * @author Michael Bednorz
 * @version 1.0
 * This plugin will start the Metaio ARELActivity.  
 */

public class MetaioCordovaPlugin extends CordovaPlugin {
	private static final String ACTION_STARTAREL = "StartArelView";
	private ArelViewStarter task;

	/**
	 * Executes the arelViewActivity
	 * @param action Name of the action which will be called
	 * @param args  args[0] Name of the Filepath
	 * @param callbackContext Context for the Callback
	 * */
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		try {
			if (ACTION_STARTAREL.equals(action)) {
				String message = args.getString(0);
				this.startArelView(message, callbackContext);
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}

	}

	private void startArelView(String message, CallbackContext callbackContext) {
		if (message != null && message.length() > 0) {
			Context context = this.cordova.getActivity()
					.getApplicationContext();
			// extract all the assets
			this.task = new ArelViewStarter(context, message);
			this.task.execute(0);

			callbackContext.success(message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}

	private class ArelViewStarter extends AsyncTask<Integer, Integer, Boolean> {
		private Context context;
		private String message;

		public ArelViewStarter(Context context, String message) {
			this.context = context;
			this.message = message;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Boolean doInBackground(Integer... params) {

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// Getting a file path for tracking configuration XML file
			final String arelConfigFile = "arelConfig.xml";
			final String arelConfigFilePath = this.message + "/" + arelConfigFile;
			MetaioDebug.log("arelConfig to be passed to intent: "
					+ arelConfigFilePath);

			Intent intent = new Intent(this.context, ARELViewActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			/**
			 * TODO Change first Parameter with the package name to your cordova
			 * main java file
			 * */
			intent.putExtra("com.metaio.cordova.template.AREL_SCENE",
					arelConfigFilePath);
			// mLaunchingTutorial = true;
			this.context.startActivity(intent);

		}

	}
}
