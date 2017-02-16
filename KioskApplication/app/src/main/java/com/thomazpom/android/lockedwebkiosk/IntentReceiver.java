package com.thomazpom.android.lockedwebkiosk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Thoma on 14/02/2017.
 */

public class IntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Log.d("Intent ",intent.getAction());
            if(intent.getAction().equals("com.pom.pm.changepassword"))
            {
                String newPassword = intent.getStringExtra("newpassword");
                if(newPassword!=null) {
                    Log.d("Change Password", newPassword);
                    SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                    edit.putString("password",newPassword);
                    edit.commit();
                    // int age = intent.getIntExtra("age",0);

                }
                else
                {
                    Log.e("Change Password","No password provided");
                }
            }
            if(intent.getAction().equals("com.pom.pm.removeadminrights"))
            {

                Log.d("Change rights","remove rights"+MainActivity.packageName);
                if(MainActivity.mDpm.isDeviceOwnerApp(MainActivity.packageName)) {
                    MainActivity.mDpm.clearDeviceOwnerApp(MainActivity.packageName);
                }
            }
        }
    }
}

