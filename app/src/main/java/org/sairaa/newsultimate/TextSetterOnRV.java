package org.sairaa.newsultimate;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import org.sairaa.newsultimate.IdlingResource.SimpleIdlingResource;

import java.util.ArrayList;

public class TextSetterOnRV {
    private static final int DELAY_MILLIS = 0;
    // Create an ArrayList of Header Content
    final static ArrayList<String> headerList = new ArrayList<String>();

    interface DelayerCallback{
        void onDone(ArrayList<String> newsHeader);
    }

    static void textSetter(Context context, final DelayerCallback callback,
                              @Nullable final SimpleIdlingResource idlingResource){
        /**
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Fill ArrayList with Tea objects
        headerList.add(context.getString(R.string.All));
        headerList.add(context.getString(R.string.sports));
        headerList.add(context.getString(R.string.technology));
        headerList.add(context.getString(R.string.business));
        headerList.add(context.getString(R.string.entertainment));
        headerList.add(context.getString(R.string.education));
        headerList.add(context.getString(R.string.politics));
        headerList.add(context.getString(R.string.culture));

        /**
         * {@link postDelayed} allows the {@link Runnable} to be run after the specified amount of
         * time set in DELAY_MILLIS elapses. An object that implements the Runnable interface
         * creates a thread. When this thread starts, the object's run method is called.
         *
         * After the time elapses, if there is a callback we return the text resource ID and
         * set the idle state to true.
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(headerList);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }

}
