package com.st18rai.datewidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DateWidget: my_wake_tag");

        //Acquire the lock
        wl.acquire(60 * 1000L /*1 minute*/);

        try {
            ComponentName thisWidget = new ComponentName(context, DateWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {

                //Get the remote views
                //You can do the processing here update the widget/remote views.
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.date_widget);

                Date now = new Date();
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
                SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

                remoteViews.setTextViewText(R.id.hour, hourFormat.format(now));
                remoteViews.setTextViewText(R.id.minute, minuteFormat.format(now));
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }
        } finally {
            //Release the lock
            wl.release();
        }

    }

}