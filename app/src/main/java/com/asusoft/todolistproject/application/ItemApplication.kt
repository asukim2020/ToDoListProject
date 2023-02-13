package com.asusoft.todolistproject.application

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.asusoft.todolistproject.util.PreferenceManager
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.LogLevel
import io.realm.log.RealmLog

class ItemApplication: Application() {

    companion object {
        val TAG = ItemApplication::class.java.simpleName ?: "ItemApplication"
        lateinit var instance: ItemApplication
        const val THROTTLE = 1000L
        const val CLICK_DELAY = 200L

        private fun getContext(): Context {
            return instance.baseContext
        }

        fun getRealmConfig(): RealmConfiguration {
            return RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

//            return RealmConfiguration.Builder()
//                    .schemaVersion(1)
//                    .migration(MyRealmMigration())
//                    .build()
        }

        fun getColor(id: Int): Int {
            return ContextCompat.getColor(getContext(), id)
        }

        fun getDrawable(id: Int): Drawable? {
            return ContextCompat.getDrawable(getContext(), id)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")

        instance = this
        Realm.init(this)

        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.DEBUG)
        }

        PreferenceManager.setApplicationContext(this)
    }

}