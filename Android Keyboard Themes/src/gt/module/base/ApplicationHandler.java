package gt.module.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;


public class ApplicationHandler {
	
	public File getOrCreateFolder(String folder, IMAGES imagePath){
		
		String strImageFolder = folder + File.separator + imagePath.name();
		File imageFolder = new File(strImageFolder);
		if(imageFolder != null && !imageFolder.exists()){
			imageFolder.mkdirs();
		}
		return imageFolder;
	}
	
	private static final String DOT_PNG = ".png";
	
	public boolean isExist(String folder, String fileName){
		
		boolean isExist = false;
		String imageFilePath = folder + File.separator + fileName + DOT_PNG;
		
		File filePath = new File(imageFilePath);
		if(filePath != null && filePath.exists()){
			isExist = true;
		}
		filePath = null;
		imageFilePath = null;
		
		return isExist;
	}
	
	public void removeFilesInFolder(String folder){
		
		File file = new File(folder);
		if(file != null && file.exists() && file.listFiles().length > 0){
			
			for(File fileImage : file.listFiles()){
				fileImage.delete();				
			}
			
		}else{
			file.mkdirs();
		}
		file = null;
	}
	
	public void createImageFile(String imageUrl, String folder, String file){
		
		String fileName =  file + DOT_PNG;
		
		URL url = null;
		InputStream input = null;
		OutputStream output = null;
		
		try {
			url = new URL(imageUrl);
			input = url.openStream();
			
			output = new FileOutputStream (new File(folder, fileName));
		    
	        byte[] buffer = new byte[255];
	        int bytesRead = 0;
	        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
	            output.write(buffer, 0, bytesRead);
	        }
	    
	        output.close();
	        input.close();
	        		    
		}catch(IOException iex){
			iex.printStackTrace();
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}finally {
			
			try{
				if(output != null){
					output.close();
				}
			}catch (Exception e) {
			}finally{
				output = null;
			}
			
			try{
				if(input != null){
					input.close();
				}
			}catch (Exception e) {
			}finally{
				input = null;
			}
			
			url = null;
		}
	}
	
	public Drawable getDrawable(String imagePath, String fileName){			
		String path = imagePath + File.separator + fileName + DOT_PNG;		
		return Drawable.createFromPath(path);
	}
	

	public void callGC() {
		// System.gc();
		// Runtime.getRuntime().gc();
	}

	private static ApplicationHandler handler;

	private ApplicationHandler() {
	}

	public static ApplicationHandler getInstance() {

		if (handler == null)
			handler = new ApplicationHandler();

		return handler;
	}
	
	public enum IMAGES{
		
		Cache,
		FrameImages,
		
		;
		
	}
	
}
