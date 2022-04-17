package com.example.trackme

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class TrackMe: Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(this)
    }
}