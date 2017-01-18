package com.webclues.callrecording;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallBroadcastReceiver extends BroadcastReceiver {
    public static String numberToCall;

    public void onReceive(Context context, Intent intent) {
        Log.d("CallRecorder", "CallBroadcastReceiver::onReceive got Intent: "
                + intent.toString());

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            numberToCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.e("CallRecorder",
                    "CallBroadcastReceiver intent has EXTRA_PHONE_NUMBER: "
                            + numberToCall);
        }

        PhoneListener phoneListener = new PhoneListener(context);
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Log.d("PhoneStateRec", "set PhoneStateListener");
    }
}
