package com.avesophos.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.avesophos.lockscreen.utils.LockscreenService;
import com.avesophos.lockscreen.utils.LockscreenUtils;
import java.io.*;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import android.graphics.*;

public class LockScreenActivity extends Activity implements
		LockscreenUtils.OnLockStatusChangedListener {

    // User-interface
    private Button btnOpt1;
    private Button btnOpt2;
    private Button btnOpt3;
    private Button btnOpt4;
    private TextView tvWord;
    private TextView tvTime;

    // Member variables
    private String answer;
	private LockscreenUtils mLockscreenUtils;
    Random rand = new Random(Calendar.getInstance().getTimeInMillis());

	// Set appropriate flags to make the screen appear over the keyguard
	@Override
	public void onAttachedToWindow() {
		this.getWindow().setType(
				WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						);

		super.onAttachedToWindow();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_lockscreen);

		init();

		// unlock screen in case of app get killed by system
		if (getIntent() != null && getIntent().hasExtra("kill")
				&& getIntent().getExtras().getInt("kill") == 1) {
			enableKeyguard();
			unlockHomeButton();
		} else {

			try {
				// disable keyguard
				disableKeyguard();

				// lock home button
				lockHomeButton();

				// start service for observing intents
				startService(new Intent(this, LockscreenService.class));

				// listen the events get fired during the call
				StateListener phoneStateListener = new StateListener();
				TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				telephonyManager.listen(phoneStateListener,
						PhoneStateListener.LISTEN_CALL_STATE);

			} catch (Exception e) {
			}

		}
	}

	private void init() {
		mLockscreenUtils = new LockscreenUtils();
        btnOpt1 = (Button) findViewById(R.id.btnOpt1);
        btnOpt2 = (Button) findViewById(R.id.btnOpt2);
        btnOpt3 = (Button) findViewById(R.id.btnOpt3);
        btnOpt4 = (Button) findViewById(R.id.btnOpt4);
        tvWord = (TextView) findViewById(R.id.tvWord);
        tvTime = (TextView) findViewById(R.id.tvTime);
        answer = btnOpt1.getText().toString();
        resetQuestion();
	}
            
    private void resetQuestion() {
        // Reset button colors
        btnOpt1.setBackgroundColor(getColor(Color.GRAY));
	    btnOpt2.setBackgroundColor(getColor(Color.GRAY));
        btnOpt3.setBackgroundColor(getColor(Color.GRAY));
        btnOpt4.setBackgroundColor(getColor(Color.GRAY));
        
        // Set answers
        StringBuilder buf=new StringBuilder();
		try {
        BufferedReader in = new BufferedReader(new InputStreamReader(getAssets().open("ru-en.tsv")));
        
        String str;
        int ans = rand.nextInt(4) + 1;
        
        int int1 = rand.nextInt(800) + 1;
        for (int i = 0; (str=in.readLine()) != null && i < int1; i++) {}
        String[] columns = new String[2];
        columns = str.split("\t");
        btnOpt1.setText(columns[0]);
        if (ans==1) answer = columns[1];
        
        int1 = rand.nextInt(800) + 1;
        for (int i = 0; (str=in.readLine()) != null && i < int1; i++) {}
        columns = str.split("\t");
        btnOpt2.setText(columns[0]);
        if (ans==2) answer = columns[1];
        
        int1 = rand.nextInt(800) + 1;
        for (int i = 0; (str=in.readLine()) != null && i < int1; i++) {}
        columns = str.split("\t");
        btnOpt3.setText(columns[0]);
        if (ans==3) answer = columns[1];
        
        int1 = rand.nextInt(800) + 1;
        for (int i = 0; (str=in.readLine()) != null && i < int1; i++) {}
        columns = str.split("\t");
        btnOpt4.setText(columns[0]);
        if (ans==4) answer = columns[1];
        
        in.close();
		} catch (IOException e) {
		}
        
        // Set listeners
        final String m_answer = answer;
        OnClickListener m_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                
                // Correct answer - unlock home button and then screen on button press
                if (m_answer.equals(btn.getText().toString())){
                    resetQuestion();
                    unlockHomeButton();
                }
                    
                // Incorrect answer - turn the button red and do nothing else
                else {
                    btn.setBackgroundColor(Color.RED);
                }
            }
        };
        
        btnOpt1.setOnClickListener(m_listener);
        btnOpt2.setOnClickListener(m_listener);
        btnOpt3.setOnClickListener(m_listener);
        btnOpt4.setOnClickListener(m_listener);
                    
        // Set question
        tvWord.setText(answer);
    }

	// Handle events of calls and unlock screen if necessary
	private class StateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				unlockHomeButton();
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			}
		}
	};

	// Don't finish Activity on Back press
	@Override
	public void onBackPressed() {
		return;
	}

	// Handle button clicks
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
				|| (keyCode == KeyEvent.KEYCODE_POWER)
				|| (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
				|| (keyCode == KeyEvent.KEYCODE_CAMERA)) {
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_HOME)) {

			return true;
		}

		return false;

	}

	// handle the key press events here itself
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
				|| (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
				|| (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
			return false;
		}
		if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {

			return true;
		}
		return false;
	}

	// Lock home button
	public void lockHomeButton() {
		mLockscreenUtils.lock(LockScreenActivity.this);
	}

	// Unlock home button and wait for its callback
	public void unlockHomeButton() {
		mLockscreenUtils.unlock();
	}

	// Simply unlock device when home button is successfully unlocked
	@Override
	public void onLockStatusChanged(boolean isLocked) {
		if (!isLocked) {
			unlockDevice();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		unlockHomeButton();
	}

	@SuppressWarnings("deprecation")
	private void disableKeyguard() {
		KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
		mKL.disableKeyguard();
	}

	@SuppressWarnings("deprecation")
	private void enableKeyguard() {
		KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
		mKL.reenableKeyguard();
	}
	
	//Simply unlock device by finishing the activity
	private void unlockDevice()
	{
		finish();
	}

}
