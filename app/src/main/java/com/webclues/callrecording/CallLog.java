package com.webclues.callrecording;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class CallLog extends Activity {

    private final String TAG = "CallRecorder";
    Context context;
    private ListView fileList = null;
    private ArrayAdapter<String> fAdapter = null;
    // Custome_Adapter fAdapter = null;
    private ArrayList<String> recordingNames = null;
    private MediaController controller = null;

    private void loadRecordingsFromProvider() {

        fAdapter.clear();
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(RecordingProvider.CONTENT_URI, null, null, null, null);
        String[] names = new String[c.getCount()];
        int i = 0;

        if (c.moveToFirst()) {
            do {
                // Extract the recording names
                fAdapter.add(c.getString(RecordingProvider.DETAILS_COLUMN));
                i++;
            } while (c.moveToNext());
        }

        fAdapter.notifyDataSetChanged();
    }

    private void loadRecordingsFromDir() {
        fAdapter.clear();
        File dir = new File(RecordService.DEFAULT_STORAGE_LOCATION);
        String[] dlist = dir.list();

        if (dlist == null) {
            Toast.makeText(getApplicationContext(), "File Not Found",
                    Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < dlist.length; i++) {
                fAdapter.add(dlist[i]);
            }
            fAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log);
        context = this;
        // recordingNames = new String[0];
        fileList = (ListView) findViewById(R.id.play_file_list);

        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
            // This device does not have a compass, turn off the compass feature
            //disableCompassFeature();
        }

        Context context = getApplicationContext();
        fAdapter = new ArrayAdapter<String>(context, R.layout.rowlayout);
        // fAdapter = new Custome_Adapter(context);
        fileList.setAdapter(fAdapter);
        fileList.setOnItemClickListener(new CallItemClickListener());
    }

    public void onStart() {
        super.onStart();
        Log.i(TAG, "CallLog onStart");
    }

    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "CallLog onRestart");
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG,
                "CallLog onResume about to load recording list again, does this work?");

        loadRecordingsFromDir();
        // Once we switch from path to provider based storage, use this method
        // loadRecordingsFromProvider();
    }

    private void playFile(String fName) {
        Log.i(TAG, "playFile: " + fName);
        Context context = getApplicationContext();
        Intent playIntent = new Intent(context, PlayService.class);
        playIntent.putExtra(PlayService.EXTRA_FILENAME,
                RecordService.DEFAULT_STORAGE_LOCATION + "/" + fName);
        ComponentName name = context.startService(playIntent);

        if (null == name) {
            Log.w(TAG, "CallLog unable to start PlayService with intent: "
                    + playIntent.toString());
        } else {
            Log.i(TAG, "CallLog started service: " + name);
        }
    }

    public void onDestroy() {
        Context context = getApplicationContext();
        Intent playIntent = new Intent(context, PlayService.class);
        context.stopService(playIntent);

        super.onDestroy();
    }

    @Override
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
    }

    private class CallItemClickListener implements
            AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            CharSequence s = (CharSequence) parent.getItemAtPosition(position);
            Log.w(TAG, "CallLog just got an item clicked: " + s);
            File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
                    + s.toString());

            boolean useMediaController = true;

            if (useMediaController) {
                Intent playIntent = new Intent(getApplicationContext(),
                        CallPlayer.class); // Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(f);
                playIntent.setData(uri);
                startActivity(playIntent);
            } else {
                playFile(s.toString());
            }
        }
    }

}
