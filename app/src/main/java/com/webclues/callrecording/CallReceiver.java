package com.webclues.callrecording;

import android.content.Context;
import android.util.Log;

import com.webclues.callrecording.dtmfdecoder.Controller;

import java.util.Date;

/**
 * Created by android on 4/1/17.
 */

public class CallReceiver extends PhonecallReceiver {

    public static final String TAG = CallReceiver.class.getSimpleName();

    //SessionManager sessionManager;
    Controller controller;

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.e(TAG, "===========onIncomingCallStarted: =============");
        //sessionManager = new SessionManager(ctx);
        controller = new Controller(ctx, false);
        //if (!sessionManager.getServiceStatus()) {
            /*Intent callIntent = new Intent(ctx, RecordService.class);
            ctx.startService(callIntent);*/
        controller.changeState();
        //}
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.e(TAG, "===========onOutgoingCallStarted: =============");
        //sessionManager = new SessionManager(ctx);
        controller = new Controller(ctx, false);
        //if (!sessionManager.getServiceStatus()) {
            /*Intent callIntent = new Intent(ctx, RecordService.class);
            ctx.startService(callIntent);*/
        controller.changeState();
        //}
    }

    @Override
    protected void onIncomingCallEnded(final Context ctx, String number, Date start, Date end) {
        Log.e(TAG, "===========onIncomingCallEnded: =============");
        //sessionManager = new SessionManager(ctx);
        controller = new Controller(ctx, true);
        //if (sessionManager.getServiceStatus()) {
        //ctx.stopService(new Intent(ctx, RecordService.class));

        controller.changeState();
        //}
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.e(TAG, "===========onOutgoingCallEnded: =============");
        //sessionManager = new SessionManager(ctx);
        controller = new Controller(ctx, true);
        //if (sessionManager.getServiceStatus()) {
        //ctx.stopService(new Intent(ctx, RecordService.class));
        controller.changeState();
        //}
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.e(TAG, "===========onMissedCall: =============");
    }

}
