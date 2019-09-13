package com.nemesis.todo_client

import android.app.Application
import com.nemesis.todo_client.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(viewModelModule, dataSourceModule, utilsModule, useCaseModule, repositoryModule))
            androidContext(this@App)
            androidLogger()
        }
    }
}