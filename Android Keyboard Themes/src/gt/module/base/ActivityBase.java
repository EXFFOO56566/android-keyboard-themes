package gt.module.base;

import gt.module.constants.ApplicationPrefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ActivityBase extends Activity{

	protected Context context;
	protected Session session;
	protected Resources resources;
	protected ApplicationPrefs applicationPrefs;


	public void initializeBase() {
		context = getApplicationContext();
		session = Session.getInstance();
		resources = getResources();
		applicationPrefs = ApplicationPrefs.getInstance(getApplicationContext());
		
	}

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		initializeBase();
		//initializeHeader();
	}

	/*@SuppressWarnings("deprecation")
	public boolean isOnline() {
		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {

			AlertDialog alertDialog = new AlertDialog.Builder(this).create();

			// Setting Dialog Title
			alertDialog.setTitle("No Internet connection");

			// Setting Dialog Message
			alertDialog.setMessage("You have no internet connection");

			// Setting Icon to Dialog
			// alertDialog.setIcon(R.drawable.success);

			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Write your code here to execute after dialog closed
				}
			});

			// Showing Alert Message
			alertDialog.show();

			return false;
		}
		return true;
	}*/
	
	
	
	public void initializeHeader() {

		
	}

	
	protected void copyFile(File sourceFile, File destFile) throws IOException {
		if (!sourceFile.exists()) {
		return;
		}

		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
		destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
		source.close();
		}
		if (destination != null) {
		destination.close();
		}

		}
	
	@SuppressLint("NewApi")
	public void checkPermission() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	public boolean hasData(String... datas) {

		boolean hasData = true;
		for (String data : datas) {

			if (!hasData(data)) {
				hasData = false;
				break;
			}
		}
		return hasData;
	}

	public boolean hasData(String text) {

		if (text == null || text.length() == 0)
			return false;

		return true;
	}

	public boolean hasData(View view, Animation animation) {

		if (animation == null)
			return false;

		if (view == null)
			return false;

		return true;
	}

	public void startAnimation(TextView textView) {

		// if (!hasData(textView, anim))
		// return;

		// textView.startAnimation(anim);
	}

	public void startAnimation(ImageView imageView) {

		// if (!hasData(imageView, anim))
		// return;

		// imageView.startAnimation(anim);
	}

	public void showMessage(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (view instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}

	@Override
	protected void onDestroy() {

		context = null;
		super.onDestroy();
		callGC();
	}

	public void callGC() {
		System.gc();
		Runtime.getRuntime().gc();
	}

	
}