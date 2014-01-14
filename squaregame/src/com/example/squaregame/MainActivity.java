package com.example.squaregame;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Timer timer;
	ImageButton btnSq;
	int maxHeight;
	int maxWidth;
	int imgHeight;
	int imgWidth;
	int left;
	int top;
	static boolean wait;
	int gameScore = 0;
	LinearLayout relLayout;
	FrameLayout sqFrame;
	FrameLayout.LayoutParams params;
	Random randomGen;
	TextView scoreTxt;
	TextView timeLeftTxt;
	int successLimit = 59;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		relLayout = (LinearLayout) findViewById(R.id.linLayout);
		sqFrame = (FrameLayout) findViewById(R.id.sqArea);
		btnSq = (ImageButton) findViewById(R.id.btnSq);
		scoreTxt = (TextView) findViewById(R.id.scoreTxt);
		timeLeftTxt = (TextView) findViewById(R.id.timeLeftTxt);
		randomGen = new Random();
		btnSq.setOnClickListener(this);
	}

	class RepositionImgButtonTask extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() 
			{  
				@Override
				public void run() 
				{
					if(!wait)
					{
						RepositionImgButton();
					}
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		maxHeight = (int) ((relLayout.getHeight())*(0.88));
		maxWidth = relLayout.getWidth();
		imgHeight = btnSq.getHeight();
		imgWidth = btnSq.getWidth();
		StartTimer(1000, 750, 1);
	}


	private void StartTimer(int delay, int interval, int option)
	{
		timer = new Timer();
		timer.scheduleAtFixedRate(new RepositionImgButtonTask(), delay, interval);
		if(option == 1)
		{
			new CountDownTimer(60000, 1000) {

				public void onTick(long millisUntilFinished) 
				{
					timeLeftTxt.setText("seconds remaining: " + millisUntilFinished / 1000);
				}
				public void onFinish() 
				{
					FinishGame();
				}
			}.start();
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == btnSq)
		{
			gameScore++;
			if(gameScore == successLimit)
			{
				FinishGame();
			}
			else if(gameScore < successLimit)
			{
				handler.sendEmptyMessage(1);
				view.setEnabled(false);
				scoreTxt.setText(String.valueOf(gameScore));
				timer.cancel();
				timer.purge();
				wait = false;
				StartTimer(0, 750, 2);
			}
		}
	}


	private boolean RepositionImgButton()
	{
		if(!btnSq.isEnabled())
		{
			btnSq.setEnabled(true);
		}
		left = GetRandomNum(1);
		top = GetRandomNum(2);
		params = (android.widget.FrameLayout.LayoutParams) btnSq.getLayoutParams();
		params.leftMargin = left;
		params.topMargin = top;
		btnSq.setLayoutParams(params);
		return true;
	}


	protected static final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what == 10)
			{
				wait = true;
			}
		}
	};

	protected int GetRandomNum(int option)
	{
		switch(option)
		{
		case 1:
			return randomGen.nextInt(maxWidth - imgWidth);
		case 2:
			return randomGen.nextInt(maxHeight - imgHeight);
		default:
			return -1;
		}

	}

	private void FinishGame()
	{
		TextView winLosemsg = new TextView(getApplicationContext());
		winLosemsg.setGravity(Gravity.CENTER_HORIZONTAL);
		winLosemsg.setTextSize(40);
		winLosemsg.setTextColor(Color.BLUE);
		relLayout.removeAllViews();
		timer.cancel();
		timer.purge();
		if(gameScore > successLimit)
		{
			winLosemsg.setText("YOU WIN");
		}
		else
		{
			winLosemsg.setText("YOU LOSE");
		}
		relLayout.addView(winLosemsg);
	}

}
