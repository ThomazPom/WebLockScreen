package com.sureshjoshi.android.kioskexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Switch switchKisosk = (Switch) findViewById(R.id.kioskswitch);
        switchKisosk.setChecked(MainActivity.mIsKioskEnabled);

        final String password = MainActivity.sharedPreferences.getString("password",getString(R.string.defaultpassword));
        final EditText tb_setpassword = (EditText) findViewById(R.id.tb_setpassword);
        tb_setpassword.setText(password);
        tb_setpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Change Password", String.valueOf(tb_setpassword.getText()));
                SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                edit.putString("password",String.valueOf(tb_setpassword.getText()));
                edit.commit();

            }
        });


        final String startURL = MainActivity.sharedPreferences.getString("startURL", getString(R.string.startUrl));
        final EditText tb_seturl = (EditText) findViewById(R.id.tb_seturl);
        tb_seturl.setText(startURL);
        tb_seturl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Change url", String.valueOf(tb_seturl.getText()));
                SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                edit.putString("startURL",String.valueOf(tb_seturl.getText()));
                edit.commit();

            }
        });

        final String kioskname = MainActivity.sharedPreferences.getString("kioskname", getString(R.string.kioskname));
        final EditText tb_kioskname = (EditText) findViewById(R.id.tb_kioskname);
        tb_kioskname.setText(kioskname);
        tb_kioskname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Change kioskname", String.valueOf(tb_kioskname.getText()));
                SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                edit.putString("kioskname",String.valueOf(tb_kioskname.getText()));
                edit.commit();

            }
        });


    }

    @OnCheckedChanged(R.id.kioskswitch)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       MainActivity.singletonMainActivity.enableKioskMode(isChecked);
        SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
        edit.putBoolean("kioskmodenabled",isChecked);
        edit.commit();
    }
    @OnClick(R.id.reset_default_btn)
    public void resetdefaults()
    {
        SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
        edit.remove("startURL");
        edit.remove("password");
        edit.remove("kioskmodenabled");
        edit.remove("kioskname");
        edit.commit();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
