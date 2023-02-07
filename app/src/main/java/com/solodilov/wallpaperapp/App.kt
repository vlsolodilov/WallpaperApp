package com.solodilov.wallpaperapp

import android.app.Application
import com.solodilov.wallpaperapp.di.DaggerApplicationComponent

class App : Application() {

    val appComponent = DaggerApplicationComponent.factory().create(this)
}