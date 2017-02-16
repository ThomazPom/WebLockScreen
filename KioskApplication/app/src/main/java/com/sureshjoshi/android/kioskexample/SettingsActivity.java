package com.sureshjoshi.android.kioskexample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final String password = MainActivity.sharedPreferences.getString("password","secure");
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

    }
}
