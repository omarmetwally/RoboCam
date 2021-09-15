package com.github.niqdev.ipcam;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.niqdev.ipcam.settings.JoyStickClass;
import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.github.niqdev.ipcam.settings.SettingsActivity.PREF_AUTH_PASSWORD;
import static com.github.niqdev.ipcam.settings.SettingsActivity.PREF_AUTH_USERNAME;
import static com.github.niqdev.ipcam.settings.SettingsActivity.PREF_FLIP_HORIZONTAL;
import static com.github.niqdev.ipcam.settings.SettingsActivity.PREF_FLIP_VERTICAL;
import static com.github.niqdev.ipcam.settings.SettingsActivity.PREF_IPCAM_URL;

public class IpCamDefaultActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private static final int TIMEOUT = 5;
    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border,mic;
   // TextView textView1, textView2, textView3, textView4, textView5;

    JoyStickClass js;

    @BindView(R.id.mjpegViewDefault)
    MjpegView mjpegView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ipcam_default);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this);
        myDatabase= FirebaseDatabase.getInstance().getReference("move");
     /*   textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);*/
      //  mic=(ImageView)findViewById(R.id.micpic);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);


        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                   /* textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));*/


                    int direction = js.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                      //  textView5.setText("Direction : Up");
                        String name = "up";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                     //   textView5.setText("Direction : Up Right");
                        String name = "stop";
                        myDatabase.child("move").setValue(name);

                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                      //  textView5.setText("Direction : Right");
                        String name = "right";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                       // textView5.setText("Direction : Down Right");
                        String name = "stop";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                       // textView5.setText("Direction : Down");
                        String name = "down";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                       // textView5.setText("Direction : Down Left");
                        String name = "stop";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                      //  textView5.setText("Direction : Left");
                        String name = "left";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                       // textView5.setText("Direction : Up Left");
                        String name = "stop";
                        myDatabase.child("move").setValue(name);
                    } else if(direction == JoyStickClass.STICK_NONE) {
                       // textView5.setText("Direction : Center");
                        String name = "stop";
                        myDatabase.child("move").setValue(name);
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                   /* textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");*/
                    String name = "stop";
                    myDatabase.child("move").setValue(name);
                }
                return true;

            }
        });

    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager
                .getDefaultSharedPreferences(this);
    }

    private String getPreference(String key) {
        return getSharedPreferences()
            .getString(key, "");
    }

    private Boolean getBooleanPreference(String key) {
        return getSharedPreferences()
                .getBoolean(key, false);
    }

    private DisplayMode calculateDisplayMode() {
        //int orientation = getResources().getConfiguration().orientation;
        //return orientation == Configuration.ORIENTATION_LANDSCAPE ?
          //  DisplayMode.FULLSCREEN : DisplayMode.BEST_FIT;
        return  DisplayMode.FULLSCREEN;
    }

    private void loadIpCam() {
        Mjpeg.newInstance()
            .credential(getPreference(PREF_AUTH_USERNAME), getPreference(PREF_AUTH_PASSWORD))
            .open(getPreference(PREF_IPCAM_URL), TIMEOUT)
            .subscribe(
                inputStream -> {
                    mjpegView.setSource(inputStream);
                    mjpegView.setDisplayMode(calculateDisplayMode());

                    mjpegView.flipHorizontal(getBooleanPreference(PREF_FLIP_HORIZONTAL));
                    mjpegView.flipVertical(getBooleanPreference(PREF_FLIP_VERTICAL));
                    mjpegView.showFps(true);
                },
                throwable -> {
                    Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIpCam();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mjpegView.stopPlayback();
    }

    public void getSpeechInput(View view)
    {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say somthing!");

        try {
            startActivityForResult(i, 10);
        }
        catch (ActivityNotFoundException a) {

            Toast.makeText(IpCamDefaultActivity.this, "Sorry your device doesn't support speech language ", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txvResult.setText(result.get(0));

                  if(result.get(0)=="m"&&result.get(1)=="o"&&result.get(2)=="v"&&result.get(2)=="e")
                   {
                       Toast.makeText(IpCamDefaultActivity.this, "55555555555 done ", Toast.LENGTH_LONG).show();
                   }
                }
                break;
        }
    }

}
