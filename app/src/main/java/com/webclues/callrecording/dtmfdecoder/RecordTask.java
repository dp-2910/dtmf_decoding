package com.webclues.callrecording.dtmfdecoder;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.webclues.callrecording.Preferences;

import java.util.concurrent.BlockingQueue;

public class RecordTask extends AsyncTask<Void, Object, Void> {


    int frequency = 16000;
    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

    int blockSize = 1024;

    Controller controller;
    BlockingQueue<DataBlock> blockingQueue;
    Context mContext;

    public RecordTask(Context context, Controller controller, BlockingQueue<DataBlock> blockingQueue) {
        mContext = context;
        this.controller = controller;
        this.blockingQueue = blockingQueue;
    }

    @Override
    protected Void doInBackground(Void... params) {

        int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        int audiosource = Integer.parseInt(prefs.getString(
                Preferences.PREF_AUDIO_SOURCE, "1"));
        Log.e("audiosource", "doInBackground: ============audiosource========> " + audiosource);
        AudioRecord audioRecord = new AudioRecord(audiosource, frequency, channelConfiguration, audioEncoding, bufferSize);

        try {

            short[] buffer = new short[blockSize];

            audioRecord.startRecording();

            while (controller.isStarted()) {
                int bufferReadSize = audioRecord.read(buffer, 0, blockSize);

                DataBlock dataBlock = new DataBlock(buffer, blockSize, bufferReadSize);

                blockingQueue.put(dataBlock);
            }

        } catch (Throwable t) {
            Log.e("AudioRecord", "Recording Failed");
        }

        audioRecord.stop();

        return null;
    }
}