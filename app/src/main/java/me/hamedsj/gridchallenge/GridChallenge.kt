package me.hamedsj.gridchallenge

import android.app.Application
import android.content.Context

class GridChallenge: Application() {

    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = this
    }
}