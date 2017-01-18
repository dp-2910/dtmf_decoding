package com.webclues.callrecording;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
    static public final String PREF_RECORD_CALLS = "PREF_RECORD_CALLS";
    static public final String PREF_AUDIO_SOURCE = "PREF_AUDIO_SOURCE";
    static public final String PREF_AUDIO_FORMAT = "PREF_AUDIO_FORMAT";
    Context context;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
        context = this;
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
