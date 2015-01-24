package de.sweet.facewhats;
import android.hardware.*;
import java.io.*;
import android.os.*;
import android.util.*;
import android.content.*;
import android.widget.*;
import java.text.*;
import java.util.*;
import android.net.*;

public class PhotoHandler implements Camera.PictureCallback
{
	private static final String DEBUG_TAG = "PhotoHandler";
	
	private final Context context;
	
	public PhotoHandler(Context context)
	{
		this.context = context;
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera)
	{
		File picutureFileDir = getDir();
		if (!picutureFileDir.exists() && !picutureFileDir.mkdirs())
		{
			Log.d(DEBUG_TAG, "Can't create directory to save image");
			Toast.makeText(context, "Can't create directory to save image", Toast.LENGTH_LONG).show();
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddHHMMSS");
		String date = dateFormat.format(new Date());
		String photoFile = "Picture_" + date + ".jpg";
		String filename = picutureFileDir.getPath() + File.separator + photoFile;
		File pictureFile = new File(filename);
		
		try
		{
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			Toast.makeText(context, "New Image saved: " + filename, Toast.LENGTH_LONG).show();
		}
		catch (FileNotFoundException e)
		{
			Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_LONG).show();
		}
		

	}
	
	private File getDir()
	{
		File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "FaceWhats");
	}
	
	
}
