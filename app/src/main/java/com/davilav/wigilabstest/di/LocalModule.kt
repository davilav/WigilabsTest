package com.davilav.wigilabstest.di

import com.davilav.wigilabstest.data.local.SharedPreferenceHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { SharedPreferenceHelper(androidApplication()) }
}