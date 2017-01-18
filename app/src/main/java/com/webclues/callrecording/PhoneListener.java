package com.webclues.callrecording;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneListener extends PhoneStateListener {

    public static final String TAG = PhoneListener.class.getSimpleName();
    public static String poneno = "No_Number_Found";
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static boolean isIncoming;
    private Context context;

    public PhoneListener(Context c) {
        Log.i("CallRecorder", "PhoneListener constructor");
        context = c;
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        Log.d("CallRecorder", "PhoneListener::onCallStateChanged state:"
                + state + " incomingNumber:" + incomingNumber);

		/*switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
			Log.e("CallRecorder", "CALL_STATE_IDLE, stoping recording");
			*//*Boolean stopped = context.stopService(new Intent(context,
					RecordService.class));
			Log.i("CallRecorder", "stopService for RecordService returned "
					+ stopped);*//*
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			Log.e("CallRecorder", "CALL_STATE_RINGING");
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.e("CallRecorder", "CALL_STATE_OFFHOOK starting recording");
			poneno = CallBroadcastReceiver.numberToCall;
			Intent callIntent = new Intent(context, RecordService.class);
			ComponentName name = context.startService(callIntent);

			if (null == name) {
				Log.e("CallRecorder",
						"startService for RecordService returned null ComponentName");
			} else {
				Log.e("CallRecorder",
						"startService returned " + name.flattenToString());
			}
			break;
		}*/

		/*if (lastState == state) {
			//No change, debounce extras
			Log.e(TAG, "=========onCallStateChanged: =====lastState == state=====");
			return;
		}*/
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.e(TAG, "=========CALL_STATE_RINGING: =========");
                isIncoming = true;
				/*callStartTime = new Date();
				savedNumber = number;*/

                //onIncomingCallReceived(context, mCallRecord, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:

                Log.e(TAG, "=========CALL_STATE_OFFHOOK: =========");
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    Log.e(TAG, "=========CALL_STATE_OFFHOOK: ====True =====");
                    isIncoming = false;
                    Intent callIntent = new Intent(context, RecordService.class);
                    context.startService(callIntent);
					/*callStartTime = new Date();
					onOutgoingCallStarted(context, mCallRecord, savedNumber, callStartTime);*/
                } else {

                    Log.e(TAG, "=========CALL_STATE_OFFHOOK: ====Else =====");

                    isIncoming = true;
                    Intent callIntent1 = new Intent(context, RecordService.class);
                    context.startService(callIntent1);
					/*callStartTime = new Date();

					onIncomingCallAnswered(context, mCallRecord, savedNumber, callStartTime);*/
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:

                Log.e(TAG, "=========CALL_STATE_IDLE: =========");

                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
				/*if (lastState == TelephonyManager.CALL_STATE_RINGING) {
					//Ring but no pickup-  a miss
					Log.e(TAG, "=========CALL_STATE_IDLE: === First======");
					//onMissedCall(context, mCallRecord, savedNumber, callStartTime);
				} else */
                if (isIncoming) {

                    Log.e(TAG, "=========CALL_STATE_IDLE: ===isIncoming True======");

                    context.stopService(new Intent(context,
                            RecordService.class));
                    //onIncomingCallEnded(context, mCallRecord, savedNumber, callStartTime, new Date());
                } else {

                    Log.e(TAG, "=========CALL_STATE_IDLE: ===isIncoming Else======");

                    context.stopService(new Intent(context,
                            RecordService.class));
                    //onOutgoingCallEnded(context, mCallRecord, savedNumber, callStartTime, new Date());
                }
                break;
        }

        //lastState = state;
    }
}
