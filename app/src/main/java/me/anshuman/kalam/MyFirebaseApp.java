package me.anshuman.kalam;

import com.google.firebase.database.FirebaseDatabase;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

public class MyFirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Sentry.init("https://9aafd451e10a459eab9d2dfd281c29b3@sentry.io/1887432", new AndroidSentryClientFactory(this));
    }
}