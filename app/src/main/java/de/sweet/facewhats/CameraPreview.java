package de.sweet.facewhats;
import android.view.*;
import android.hardware.*;
import android.hardware.Camera.*;
import android.app.*;
import android.content.*;
import android.widget.*;
import android.content.pm.*;
import java.io.*;
import android.net.*;
import android.os.*;


public class CameraPreview implements SurfaceHolder.Callback
{
	private Camera camera;
	private Parameters parameters;
	
	
	private int cameraId;
	private Context context;
	
	public CameraPreview(Context context)
	{
		this.context = context;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	public Camera getCamera()
	{
		return camera;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		camera = Camera.open(cameraId);
		try
		{
			camera.setPreviewDisplay(holder);
		}
		catch (IOException e)
		{
			camera.release();
			camera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int p2, int p3, int p4)
	{
		parameters = camera.getParameters();
		camera.setParameters(parameters);
		camera.startPreview();
		camera.takePicture(null, null, new PhotoHandler(context));
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.DIRECTORY_PICTURES)));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if (camera != null)
		{
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}
	
	public void setup()
	{
		if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
		{
			Toast.makeText(context, "No camera found on this device", Toast.LENGTH_LONG).show();
		}
		else
		{
			cameraId = findFrontFacingCamera();
			if (cameraId < 0)
			{
				Toast.makeText(context, "No front facing camera found.", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(context, "There was a front facing camera found.", Toast.LENGTH_LONG).show();
			}
		}
	}

	private int findFrontFacingCamera()
	{
		int cameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i  = 0; i < numberOfCameras; i++)
		{
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT)
			{
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	

	
	
}
