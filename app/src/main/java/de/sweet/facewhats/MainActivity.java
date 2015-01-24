package de.sweet.facewhats;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.hardware.Camera.*;
import android.content.pm.*;
import android.widget.*;

public class MainActivity extends Activity
{
	
	
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	
	private CameraPreview cameraPreview;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	}

	public void takePicture(View view)
	{
		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		cameraPreview = new CameraPreview(this);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(cameraPreview);
		
	}

	@Override
	protected void onPause()
	{
		if (cameraPreview != null && cameraPreview.getCamera() != null)
		{
			Camera camera = cameraPreview.getCamera();
			
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		if (cameraPreview != null && cameraPreview.getCamera() != null)
		{
			Camera camera = cameraPreview.getCamera();
			
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
		super.onDestroy();
	}
	
	
	
	
}
