package com.example.welan

import android.app.Application
import com.example.welan.data.AppContainer
import com.example.welan.data.DefaultAppContainer

class WeLanApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}