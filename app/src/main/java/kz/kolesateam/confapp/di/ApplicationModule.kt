package kz.kolesateam.confapp.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

const val APPLICATION_KEY = "application"

val applicationModule: Module = module{

    single {
        val context = androidApplication()

        context.getSharedPreferences(APPLICATION_KEY, Context.MODE_PRIVATE)
    }
    single {
        ObjectMapper()
    }
}