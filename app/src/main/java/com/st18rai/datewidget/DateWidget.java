package com.st18rai.datewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateWidget extends AppWidgetProvider {

    private BroadcastReceiver receiver;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.date_widget);

        Date now = new Date();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        views.setTextViewText(R.id.hour, hourFormat.format(now));
        views.setTextViewText(R.id.minute, minuteFormat.format(now));
        views.setTextViewText(R.id.day, dayFormat.format(now));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(final Context context) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    // update widget time here using

                    updateTime(ctx);

                }
            }
        };

        context.getApplicationContext().registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    private void updateTime(Context context) {

        ComponentName thisWidget = new ComponentName(context, DateWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.date_widget);

        Date now = new Date();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        views.setTextViewText(R.id.hour, hourFormat.format(now));
        views.setTextViewText(R.id.minute, minuteFormat.format(now));

        appWidgetManager.updateAppWidget(thisWidget, views);

    }

    @Override
    public void onDisabled(Context context) {

        if (receiver != null)
            context.getApplicationContext().unregisterReceiver(receiver);
    }
}

