package org.apache.cordova.MetaioPlugin;

import java.io.File;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.content.Context;
import android.util.Log;
import com.metaio.sdk.MetaioDebug;


public class MetaioCordovaPlugin extends CordovaPlugin {
	private static final String ACTION_STARTAREL = "StartArelView";
	private AssetsExtracter mTask;

	/**
	 * Task that will extract all the assets
	 */
	// private AssetsExtracter mTask;
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
			mTask = new AssetsExtracter(context, message);
			Log.d("Die Message aus Cordova ist: ", message);
			mTask.execute(0);

			callbackContext.success(message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}

	/**
	 * This task extracts all the assets to an external or internal location to
	 * make them accessible to metaio SDK
	 */
	private class AssetsExtracter extends AsyncTask<Integer, Integer, Boolean> {
		private Context mContext;
		private String mMessage;

		public AssetsExtracter(Context context, String message) {
			mContext = context;
			mMessage = message;
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
			// create AREL template and present it
			Log.d("Die Message aus Cordova ist: ", mMessage);
			File dataFile = Environment.getExternalStorageDirectory();
			File[] allFiles = dataFile.listFiles();
			Log.d("Pfad von Root",
					dataFile.getAbsolutePath() + "--" + dataFile.getName());
			String metaioFilepath = "";
			for (int i = 0; i < allFiles.length; i++) {
				Log.d("Filepath of " + allFiles[i].getName(),
						allFiles[i].getPath());
				if (allFiles[i].getName().equals("MetaioAssets")) {
					metaioFilepath = allFiles[i].getPath() + "/Tutorial1";
					Log.d("MetaioFilePath", metaioFilepath);
				}
			}
			// Getting a file path for tracking configuration XML file
			final String arelConfigFile = "arelConfig1.xml";
			final String arelConfigFilePath = metaioFilepath + "/"
					+ arelConfigFile;
			;
			MetaioDebug.log("arelConfig to be passed to intent: "
					+ arelConfigFilePath);

			Intent intent = new Intent(mContext, ARELViewActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("com.scheer.interact4App.AREL_SCENE",
					arelConfigFilePath);
			// mLaunchingTutorial = true;
			mContext.startActivity(intent);


		}

	}
}
