package com.example.sundayout

import android.app.Application
import com.example.sundayout.data.AppContainer
import com.example.sundayout.data.DefaultAppContainer

class SundayoutApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}