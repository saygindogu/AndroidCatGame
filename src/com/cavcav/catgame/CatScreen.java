package com.cavcav.catgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class CatScreen extends Activity implements OnTouchListener{
	
	public static int DEFAULT_SPEED = 8;
	CatScreenView ourSurfaceView;
	float targetX, targetY;
	float oldX, oldY;
	float speed;
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSurfaceView.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ourSurfaceView.resume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ourSurfaceView = new CatScreenView( this);
		ourSurfaceView.setOnTouchListener( this);
		setContentView(ourSurfaceView);
		speed = DEFAULT_SPEED;
		targetX = targetY = oldX = oldY = 0;
		//cat = BitmapFactory.decodeResource( getResources(), R.drawable.img_cat );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cat_screen, menu);
		return true;
	}

	@Override
	public boolean onTouch(View view, MotionEvent mEvent) {
		// TODO Auto-generated method stub
		//oldX = targetX;
		//oldY = targetY;
		targetX = mEvent.getX();
		targetY = mEvent.getY();
		return false;
	}
	

	public class CatScreenView extends SurfaceView implements Runnable {
		
		SurfaceHolder holder;
		Thread thread = null;
		Bitmap cat;
		boolean isRunning = false; 
		float changeY;
		float changeX;
		float catSpeed;
		
		public CatScreenView(Context context) {
			super(context);
			
			holder = getHolder();
			cat = BitmapFactory.decodeResource( getResources(), R.drawable.img_cat);
			changeY = 0;
			changeX = 0;
		}
		
		public void pause(){
			isRunning = false;
			while( true){
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			thread = null;
		}
		
		public void resume(){
			isRunning = true;
			thread = new Thread( this);
			thread.start();
		}
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while( isRunning ){
				if(!holder.getSurface().isValid() )
					continue;
				
				if( oldX > targetX ){
					oldX = oldX - speed;
				}
				else if( targetX - oldX > speed){
					oldX = oldX + speed;
				}
				else{
					oldX = targetX;
				}
				
				if( oldY > targetY ){
					oldY = oldY - speed;
				}
				else if( targetY - oldY > speed){
					oldY = oldY + speed;
				}
				else{
					oldY = targetY;
				}
				
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB(105, 106, 120);
				if( oldX != 0 && oldY != 0 ){
					//if( oldX >= cat.getWidth()/2 && oldY >= cat.getHeight()/2 )
					//{
						canvas.drawBitmap(cat, oldX - cat.getWidth()/2, oldY - cat.getHeight()/2, null);
					/*}
					else
					{
						canvas.drawBitmap(cat, 0,0, null);
					}*/
				}
				
				holder.unlockCanvasAndPost(canvas);
				
			}
		}
		
	}

}
