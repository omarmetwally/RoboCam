package com.github.niqdev.ipcam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.niqdev.ipcam.settings.Main2Activity;
import com.github.niqdev.ipcam.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

   @BindView(R.id.buttonDefault)
   ImageView buttonDefault;

   ImageView ch;
    //@BindView(R.id.buttonNative)
   // Button buttonNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ch=(ImageView) findViewById(R.id.chatt);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
        // load default values first time
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        verifySettings();
    }

    private void verifySettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (TextUtils.isEmpty(prefs.getString(SettingsActivity.PREF_IPCAM_URL, ""))) {
            buttonDefault.setEnabled(false);
        }

        // TODO disabled
       // buttonNative.setEnabled(false);
    }

    @OnClick(R.id.buttonDefault)
    public void onClickDefault() {
        startActivity(new Intent(this, IpCamDefaultActivity.class));
    }

   /* @OnClick(R.id.buttonTwoCamera)
    public void onClickTwoCamera() {
        startActivity(new Intent(this, IpCamTwoActivity.class));
    }

    @OnClick(R.id.buttonSnapshot)
    public void onClickSnapshot() {
        startActivity(new Intent(this, IpCamSnapshotActivity.class));
    }

    @OnClick(R.id.buttonNative)
    public void onClickNative() {
        startActivity(new Intent(this, IpCamNativeActivity.class));
    }

    @OnClick(R.id.buttonCustomAppearance)
    public void onClickCustomAppearance() {
        startActivity(new Intent(this, IpCamCustomAppearanceActivity.class));
    }
*/
    @OnClick(R.id.buttonSettings)
    public void onClickSettings() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else {
            Toast.makeText(this, "Settings not available", Toast.LENGTH_LONG).show();
        }
    }

}
