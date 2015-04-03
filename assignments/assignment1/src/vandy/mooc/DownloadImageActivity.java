package vandy.mooc;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * An Activity that downloads an image, stores it in a local file on the local
 * device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
	/**
	 * Debugging tag used by the Android logger.
	 */
	private final String TAG = getClass().getSimpleName();

	/**
	 * Hook method called when a new instance of Activity is created. One time
	 * initialization code goes here, e.g., UI layout and some class scope
	 * variable initialization.
	 * 
	 * @param Bundle
	 *            object that contains saved state information.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Always call super class for necessary
		// initialization/implementation.
		// @@ TODO -- you fill in here.
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			// The activity is being re-created. Use the
			// savedInstanceState bundle for initializations either
			// during onCreate or onRestoreInstanceState().
			Log.d(TAG,
					"onCreate(): activity re-created from savedInstanceState");

		} else {
			// Activity is being created anew. No prior saved
			// instance state information available in Bundle object.
			Log.d(TAG, "onCreate(): activity created anew");
		}
		// Get the URL associated with the Intent data.
		// @@ TODO -- you fill in here.
		final Uri url = this.getIntent().getData();

			Thread mThread = null;

		Runnable downloadRunnable = new Runnable() {

			@Override
			public void run() {
				DownloadUtils.downloadImage(getApplicationContext(),
						url);
				
				
			}
		};
		// Create and Start a new Thread to perform the download and
		// display the results to the user.
		mThread = new Thread(downloadRunnable);
		mThread.start();

		// @@ TODO -- you fill in here using the Android "HaMeR"
		// concurrency framework. Note that the finish() method
		// should be called in the UI thread, whereas the other
		// methods should be called in the background thread.
		DownloadImageActivity.this.runOnUiThread(new Runnable() {


			@Override
			public void run() {
				Uri mUri=DownloadUtils.downloadImage(getApplicationContext(),
						url);
				Intent intent = new Intent();
				intent.putExtra("pathToImageFile", mUri.toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}