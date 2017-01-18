package com.webclues.callrecording;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class CallRecorder extends Activity {
    Context context;
    //ImageView Preferences1, CallLog;
    ImageView image;
    Button start_receiver, stop_receiver;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        //Preferences1 = (ImageView) findViewById(R.id.Preferences);
        //CallLog = (ImageView) findViewById(R.id.Call);
        start_receiver = (Button) findViewById(R.id.start_receiver);
        stop_receiver = (Button) findViewById(R.id.stop_receiver);
        image = (ImageView) findViewById(R.id.image);
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.myanimation);
        image.setAnimation(animation);

        image.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.myanimation);
                image.setAnimation(animation);
//				Preferences1.setBackgroundColor(Color.GRAY);
//				CallLog.setBackgroundColor(Color.GRAY);
                Intent it = new Intent(CallRecorder.this, Preferences.class);
                startActivity(it);

                return false;
            }
        });

        start_receiver.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //register receiver
                IntentFilter mainFilter = new IntentFilter();
                receiver = new CallReceiver();
                registerReceiver(receiver, mainFilter);
            }
        });

        stop_receiver.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //unregister receiver
                receiver = new CallReceiver();
                unregisterReceiver(receiver);
            }
        });

		/*Preferences1.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(CallRecorder.this, Preferences.class);
				startActivity(it);
//				Preferences1.setBackgroundColor(Color.GRAY);
//				CallLog.setBackgroundColor(Color.DKGRAY);
			}
		});

		CallLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(CallRecorder.this, CallLog.class);
				startActivity(it);
//				CallLog.setBackgroundColor(Color.GRAY);
//				Preferences1.setBackgroundColor(Color.DKGRAY);

			}
		});*/
    }

	
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.likeus:
			Intent it = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/WebCluesInfotech"));
			startActivity(it);
			break;
		case R.id.rateus:
			Uri uri = Uri.parse("market://details?id="
					+ context.getPackageName());
			Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
			try {
				startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://play.google.com/store/apps/details?id="
								+ context.getPackageName())));

			}
			break;
		case R.id.shareapp:
			try {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_SUBJECT,
						"FunkyArt is the best, fastest and most smooth photo editing app.");
				String sAux = "\nDownload it from below link and enjoy\n\n";
				sAux = sAux + "https://play.google.com/store/apps/details?id="
						+ context.getPackageName();
				i.putExtra(Intent.EXTRA_TEXT, sAux);
				startActivity(Intent.createChooser(i, "choose one"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		// default:
		// return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}*/
}
