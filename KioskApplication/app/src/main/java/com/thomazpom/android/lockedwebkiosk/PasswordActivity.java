package com.thomazpom.android.lockedwebkiosk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import butterknife.ButterKnife;

public class PasswordActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        setTitle(MainActivity.sharedPreferences.getString("kioskname",getString(R.string.kioskname))+" - Mot de passe");
        final EditText tb_checkPassword = (EditText) findViewById( R.id.tb_checkPassword);
        ButterKnife.bind(this);

        final String password = MainActivity.sharedPreferences.getString("password","secure");

        final PasswordActivity self = this;
        if (MainActivity.mIsKioskEnabled && !password.equals(new String())|| password.equals(tb_checkPassword.getText()))
        {
            tb_checkPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (String.valueOf(tb_checkPassword.getText()).equals(password)){
                        Intent intent = new Intent(self, SettingsActivity.class);
                        startActivity(intent);
                        self.finish();
                    }
                    Log.d("Password wanted", password);
                    Log.d("Password got", String.valueOf(tb_checkPassword.getText()));

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        else
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
