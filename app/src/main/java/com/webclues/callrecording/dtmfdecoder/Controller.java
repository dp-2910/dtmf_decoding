package com.webclues.callrecording.dtmfdecoder;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static android.content.Context.TELEPHONY_SERVICE;


public class Controller {
    public static String Stringkey;
    //private MainActivity mainActivity;
    BlockingQueue<DataBlock> blockingQueue;
    Context mContext;
    private boolean started;
    private RecordTask recordTask;
    private RecognizerTask recognizerTask;
    private Character lastValue;

    public Controller(Context context, boolean starterflag) {
        mContext = context;
        started = starterflag;
        //this.mainActivity = mainActivity;
    }

    public void changeState() {
        if (started == false) {
            lastValue = ' ';
            blockingQueue = new LinkedBlockingQueue<DataBlock>();
            //mainActivity.start();
            recordTask = new RecordTask(mContext, this, blockingQueue);

            recognizerTask = new RecognizerTask(this, blockingQueue);

            recordTask.execute();

            Log.e("Controller", "======changeState: FALSE==========");

            recognizerTask.execute();

            started = true;
        } else {

            Log.e("Controller", "======changeState: TRUE==========");

            //mainActivity.stop();

            /*recognizerTask.cancel(true);
            recordTask.cancel(true);*/
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    /*if (Stringkey != null) {
                        Stringkey = Stringkey + "-" + String.valueOf(key);
                    } else {
                        Stringkey = String.valueOf(key);
                    }
                    Log.e("KEY", "keyReady:********KEY******* " + Stringkey);
                    Toast.makeText(mContext, Stringkey, Toast.LENGTH_SHORT).show();*/

                    /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    builder.setTitle("DTMF Number");
                    builder.setMessage(Stringkey);
                    builder.show();*/
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    // set title
                    alertDialogBuilder.setTitle("DTMF Number");
                    alertDialogBuilder.setMessage(Stringkey);
                    alertDialogBuilder.setPositiveButton("OK", null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alertDialog.setCancelable(false);
                    // show it
                    alertDialog.show();
                }
            });
            started = false;
        }
    }

    public void clear() {
        //mainActivity.clearText();
    }

    public boolean isStarted() {
        return started;
    }

	/*public int getAudioSource()
    {
		return getAudioSource();
	}*/

    public int getAudioSource() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);

        if (telephonyManager.getCallState() != TelephonyManager.PHONE_TYPE_NONE)
            return MediaRecorder.AudioSource.VOICE_DOWNLINK;

        return MediaRecorder.AudioSource.MIC;
    }

    public void spectrumReady(Spectrum spectrum) {
        //mainActivity.drawSpectrum(spectrum);
    }

    public void keyReady(final char key) {
        //mainActivity.setAciveKey(key);

        if (key != ' ') {
            if (lastValue != key) {
                //mainActivity.addText(key);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (Stringkey != null) {
                            Stringkey = Stringkey + "-" + String.valueOf(key);
                        } else {
                            Stringkey = String.valueOf(key);
                        }
                        Log.e("KEY", "keyReady:********KEY******* " + Stringkey);
                        Toast.makeText(mContext, Stringkey, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        lastValue = key;
    }

    public void debug(String text) {
        //mainActivity.setText(text);
    }
}
