package com.nemesis.todo_client.di

import com.nemesis.todo_client.presenation.utils.ErrorMessageHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    single { ErrorMessageHandler(androidContext()) }
}