package com.example.notes;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

public class NoteReminderNotification {

    private static final String NOTIFICATION_TAG = "NoteReminder";

    private static void notify(final Context context, final String exampleString, final int number){
        final Resources res = context.getResources();

       final String ticker = exampleString;
       final String title = "Note Reminder: {exampleString}";
        final String text = "Note Reminder: {exampleString}";

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

    }
}
