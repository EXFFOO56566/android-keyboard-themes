package gt.keybord;

import java.io.File;










import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import android.content.ActivityNotFoundException;
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
import android.widget.LinearLayout;
import android.widget.Toast;
import gt.module.base.ActivityBase;
import gt.module.constants.AppConstants;

public class CustomeThemesActivity extends ActivityBase implements OnClickListener{

	LinearLayout l_lay_customethemes_background, l_lay_customethemes_keybackground, l_lay_customethemes_gallerybackground, l_lay_customethemes_fontcolor,l_lay_customethemes_set;
	private static final int PICK_FROM_GEALLERY = 101;
	InterstitialAd mInterstitialAd;
	private StartAppAd startAppAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 StartAppSDK.init(CustomeThemesActivity.this, AppConstants.DeveloperID, AppConstants.ApplicationID, true);
			
		setContentView(R.layout.activity_custome_themes);
		 startAppAd = new StartAppAd(getApplicationContext());
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
		l_lay_customethemes_background = (LinearLayout) findViewById(R.id.l_lay_customethemes_background);
		//l_lay_customethemes_keybackground = (LinearLayout) findViewById(R.id.l_lay_customethemes_keybackground);
		l_lay_customethemes_gallerybackground = (LinearLayout) findViewById(R.id.l_lay_customethemes_gallerybackground);
		//l_lay_customethemes_fontcolor = (LinearLayout) findViewById(R.id.l_lay_customethemes_fontcolor);
		l_lay_customethemes_set = (LinearLayout) findViewById(R.id.l_lay_customethemes_set);
		
		l_lay_customethemes_background.setOnClickListener(this);
		//l_lay_customethemes_keybackground.setOnClickListener(this);
		l_lay_customethemes_gallerybackground.setOnClickListener(this);
		//l_lay_customethemes_fontcolor.setOnClickListener(this);
		l_lay_customethemes_set.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.l_lay_customethemes_background:
			startActivityForResult(new Intent(getApplicationContext(), ThemesBackgroundActivity.class), 1);
			 startAppAd.showAd(); // show the ad
			 startAppAd.loadAd(); // load the next ad
			break;
	/*	case R.id.l_lay_customethemes_keybackground:
			startActivityForResult(new Intent(getApplicationContext(), ThemesKeyBackgroundActivity.class), 2);
			break;*/
			
		case R.id.l_lay_customethemes_gallerybackground:
			takePicture();
			break;
			
		case R.id.l_lay_customethemes_set:
			applicationPrefs.setThemesId(1000);
			 startAppAd.showAd(); // show the ad
			 startAppAd.loadAd(); // load the next ad
			Toast.makeText(getApplicationContext(), "set keyboard theme..", Toast.LENGTH_LONG).show();
			break;
			
		/*case R.id.l_lay_customethemes_fontcolor:
			onClickColorPickerDialog();
			pickColor();
			break;*/
		default:
			break;
		}
	}
	
	 private void pickColor() {
		// TODO Auto-generated method stub
		 
		
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
				 startAppAd.showAd(); // show the ad
				 startAppAd.loadAd(); // load the next ad
			}
	  	}
	  catch (ActivityNotFoundException localActivityNotFoundException)
	  {
	  }
	}
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
		 startAppAd.showAd(); // show the ad
		 startAppAd.loadAd(); // load the next ad
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
		 
		 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			 startAppAd.showAd(); // show the ad
  			 startAppAd.loadAd(); // load the next ad
		}
		 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			 startAppAd.showAd(); // show the ad
  			 startAppAd.loadAd(); // load the next ad
		}
}
