package gt.module.base;

import android.graphics.Bitmap;
import android.net.Uri;

public class Session {

private static Session session;
	
	
	
	private Session() {
	}

	public static Session getInstance() {
		if (session == null) {
			session = new Session();
		}
		return session;
	}
	
	
	private Bitmap bitmap;
	private String ImageUrl;
	private String categoriesName;
	private int imageResId;
	private String temp_imagename;
	
	private String imagepath;
	
	public String getImagePath()
	{
		return imagepath;
	}

	public void setImagePath(String imagepath) {
		this.imagepath = imagepath; 
	}
	public int getImageResId() {
		return imageResId;
	}

	public void setImageResId(int imageResId) {
		this.imageResId = imageResId;
	}

	public String getCategoriesName() {
		return categoriesName;
	}

	public void setCategoriesName(String categoriesName) {
		this.categoriesName = categoriesName;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getTemp_imagename() {
		return temp_imagename;
	}

	public void setTemp_imagename(String temp_imagename) {
		this.temp_imagename = temp_imagename;
	}
	
	
	
}
