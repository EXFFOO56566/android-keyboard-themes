package gt.keybord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
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
import android.widget.Button;

import com.edmodo.cropper.CropImageView;

public class Crope extends Activity implements OnClickListener{
    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";
    private static final int ON_TOUCH = 1;
    public static final  String RETURN_DATA            = "return-data";
    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    private Bitmap mBitmap;
    private String mImagePath;
    final int IMAGE_MAX_SIZE = 1024;
    
    Button buttonCropeImageSave,cancelCropeImage;
     CropImageView cropImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cropeview);
		/* Intent intent = getIntent();
	        Bundle extras = intent.getExtras();*/
		
		Intent intent = getIntent();
	        Bundle exBundle = intent.getExtras();
	        mBitmap =  decodeSampledBitmapFromFile(
	        		intent.getExtras().getString("image-path"), 1000, 700);
	        
	        buttonCropeImageSave = (Button) findViewById(R.id.saveCropeImage);
	        buttonCropeImageSave.setOnClickListener(this);
	        cancelCropeImage = (Button) findViewById(R.id.cancelCropeImage);
	        cancelCropeImage.setOnClickListener(this);
		cropImageView = (CropImageView) findViewById(R.id.CropImageView);
		cropImageView.setImageBitmap(mBitmap);
		cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
			int reqHeight) { // BEST QUALITY MATCH

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize, Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		int inSampleSize = 1;

		if (height > reqHeight) {
			inSampleSize = Math.round((float) height / (float) reqHeight);
		}
		int expectedWidth = width / inSampleSize;

		if (expectedWidth > reqWidth) {
			// if(Math.round((float)width / (float)reqWidth) > inSampleSize) //
			// If bigger SampSize..
			inSampleSize = Math.round((float) width / (float) reqWidth);
		}

		options.inSampleSize = inSampleSize;

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, options);
	}
	
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 Intent rturnInte = new Intent();
		switch (v.getId()) {
		case R.id.saveCropeImage:
			
			/*
			  Bundle myExtras = getIntent().getExtras();
		        if (myExtras != null && (myExtras.getParcelable("data") != null
		                || myExtras.getBoolean(RETURN_DATA))) {*/

		          /*  Bundle extras = new Bundle();
		            Intent rturnInte = new Intent();
		            rturnInte.putExtra("bitimage",cropImageView.getCroppedImage() );
		            setResult(RESULT_OK,rturnInte);
		            finish();
		            */
		            
		            OutputStream fOut = null;
		            File file = new File(Environment.getExternalStorageDirectory()
		    				+ File.separator + "keyboardback.jpg"); // the File to save to
			try {
				fOut = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap pictureBitmap = cropImageView.getCroppedImage(); // obtaining the Bitmap
			pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
			try {
				fOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			   rturnInte.putExtra("saveurl", file);
			   setResult(RESULT_OK,rturnInte);
	            finish();
			/*
		        }*/
			
			
			break;
			
		case R.id.cancelCropeImage:
			 setResult(RESULT_CANCELED,rturnInte);
	            finish();
			break;

		default:
			break;
		}
	}
}
