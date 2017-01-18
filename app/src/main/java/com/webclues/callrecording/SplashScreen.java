package com.webclues.callrecording;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private static String TAG = SplashScreen.class.getName();
    private static long SLEEP_TIME = 5; //sleep for some Time
    ImageView spal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Remove notification ber

        setContentView(R.layout.splash);
        spal = (ImageView) findViewById(R.id.spal);
//		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
//		spal.setAnimation(animation);

        //Start time and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {

        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                //sleeping
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            //start main activity
            Intent intent = new Intent(SplashScreen.this, CallRecorder.class);
            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();

        }

    }

}
