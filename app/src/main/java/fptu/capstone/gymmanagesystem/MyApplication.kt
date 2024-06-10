package fptu.capstone.gymmanagesystem

import SessionManager
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.initialize(this)
    }
}