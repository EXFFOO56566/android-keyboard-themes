package gt.keybord;

import java.io.File;

import gt.module.base.ActivityBase;
import gt.module.constants.ApplicationPrefs;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;



public class KeyBoardActivity extends ActivityBase implements OnClickListener{

	ApplicationPrefs applicationPrefs;
	private static final int PICK_FROM_GEALLERY = 1;
	 InterstitialAd mInterstitialAd;
	LinearLayout l_lay_keyboard_enableKeyboard, l_lay_keyboard_SetInputMethod, l_lay_keyboard_setThemes, l_lay_keyboard_setThemesBackground;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboard);
		applicationPrefs = ApplicationPrefs.getInstance(this);
	
	        mInterstitialAd = new InterstitialAd(this);
	        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
	        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
	        mInterstitialAd.loadAd(adRequest1);
	        mInterstitialAd.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					showInterstitial();
				}
			});
		
		initUI();
	}

	 private void showInterstitial() {
	        // Show the ad if it's ready. Otherwise toast and restart the game.
	            mInterstitialAd.show();
	       
	    }

	private void initUI() {
		// TODO Auto-generated method stub
		
		/*Find views*/
		
		l_lay_keyboard_enableKeyboard = (LinearLayout) findViewById(R.id.l_lay_keyboard_enableKeyboard);
		l_lay_keyboard_SetInputMethod = (LinearLayout) findViewById(R.id.l_lay_keyboard_SetInputMethod);
		l_lay_keyboard_setThemes = (LinearLayout) findViewById(R.id.l_lay_keyboard_setThemes);
		l_lay_keyboard_setThemesBackground = (LinearLayout) findViewById(R.id.l_lay_keyboard_setThemesBackground);
		
		
		/*Add Litener*/
		l_lay_keyboard_enableKeyboard.setOnClickListener(this);
		l_lay_keyboard_SetInputMethod.setOnClickListener(this);
		l_lay_keyboard_setThemes.setOnClickListener(this);
		l_lay_keyboard_setThemesBackground.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		
		case R.id.l_lay_keyboard_enableKeyboard:
			startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
	
			break;
			
		case R.id.l_lay_keyboard_SetInputMethod:
			 ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
             .showInputMethodPicker();
			break;
		
		case R.id.l_lay_keyboard_setThemes:
			Intent intent2 = new Intent(getApplicationContext(),ThemesListActivity.class);
			startActivity(intent2);
			break;
			
		case R.id.l_lay_keyboard_setThemesBackground:
			Intent intent = new Intent(getApplicationContext(),CustomeThemesActivity.class);
			startActivity(intent);
			//takePicture();
			break;
		default:
			break;
		}
	}
	
	 private void startCropImages(String file2) {
			
			Intent intent = new Intent(getApplicationContext(),Crope.class);
			  intent.putExtra("image-path", file2);
			startActivityForResult(intent, 3);
			// TODO Auto-generated method stub
			//Intent intent = new Intent(getApplicationContext(),Crope.class);
			//  intent.putExtra("image-path", mFileTemp.getPath());
			 // Log.i("----------------", mFileTemp.getAbsolutePath());
			//startActivity(intent);
			
		}
	
	 private void takePicture()
	  {
	  	try
	  	{
	  		if (Environment.getExternalStorageState().equals("mounted")) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_PICK);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture:"),
						PICK_FROM_GEALLERY);
			}
	  	}
	  catch (ActivityNotFoundException localActivityNotFoundException)
	  {
	  }
	}
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			if (resultCode != RESULT_OK)
				return;

			switch (requestCode) {
			case PICK_FROM_GEALLERY:

				Uri selectedImageUri = data.getData();
				String selectedImagePath = getPath(selectedImageUri);
				Bitmap photo = getPreview(selectedImagePath);
				// imageView.setImageBitmap(photo);
				startCropImages(selectedImagePath);
				session.setBitmap(photo);

				break;
			

			}

		}
	 
	 public Bitmap getPreview(String fileName) {
			File image = new File(fileName);

			BitmapFactory.Options bounds = new BitmapFactory.Options();
			bounds.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(image.getPath(), bounds);
			if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
				return null;
			}
			int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
					: bounds.outWidth;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = originalSize / 64;
			// opts.inSampleSize = originalSize;
			return BitmapFactory.decodeFile(image.getPath());
		}

		public String getPath(Uri uri) {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}

}
