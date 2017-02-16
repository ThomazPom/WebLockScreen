package com.sureshjoshi.android.kioskexample;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class MainActivity extends Activity {



    public  static SharedPreferences sharedPreferences;
    @Bind(R.id.webview)
    public WebView mWebView;
    public static MainActivity singletonMainActivity;
    private View mDecorView;
    public static DevicePolicyManager mDpm;
    public static String packageName;
    public static boolean mIsKioskEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        singletonMainActivity = this;
        sharedPreferences = getPreferences(MODE_PRIVATE);
        packageName=this.getPackageName();
        ComponentName deviceAdmin = new ComponentName(this, AdminReceiver.class);
        mDpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);


        if (!mDpm.isAdminActive(deviceAdmin)) {
            Toast.makeText(this, getString(R.string.not_device_admin), Toast.LENGTH_SHORT).show();
        }

        if (mDpm.isDeviceOwnerApp(packageName)) {
            mDpm.setLockTaskPackages(deviceAdmin, new String[]{packageName});
        } else {
            Toast.makeText(this, getString(R.string.not_device_owner), Toast.LENGTH_SHORT).show();
        }

        mDecorView = getWindow().getDecorView();
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl( sharedPreferences.getString("startURL",getString(R.string.startUrl)));

        enableKioskMode( sharedPreferences.getBoolean("kioskmodenabled",mIsKioskEnabled));


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void enableKioskMode(boolean enabled) {
        try {
            if (enabled) {
                if (mDpm.isLockTaskPermitted(packageName)) {
                    startLockTask();
                    mIsKioskEnabled = true;
                } else {
                    Toast.makeText(this, getString(R.string.kiosk_not_permitted), Toast.LENGTH_SHORT).show();
                }
            } else {
                stopLockTask();
                mIsKioskEnabled = false;
                }
        } catch (Exception e) {
            // TODO: Log and handle appropriately
        }
    }
    @OnClick(R.id.btn_settings)
    public void settingsOnclick()
    {

        Log.d("settings btn","Pressed");
        Intent intent = new Intent(this, PasswordActivity.class);

        startActivity(intent);
    }
    @OnClick (R.id.btn_home)
    public void homeOnClick()
    {
        Log.d("Home btn","Pressed");
        mWebView.loadUrl( sharedPreferences.getString("startURL",getString(R.string.startUrl)));

    }
    @OnClick(R.id.btn_left_arrow)
    public void backOnClick()
    {

        Log.d("Back btn","Pressed");
        if (mWebView.canGoBack())
        {
            mWebView.goBack();
        }
    }
}
