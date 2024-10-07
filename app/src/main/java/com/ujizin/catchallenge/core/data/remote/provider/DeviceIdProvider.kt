package com.ujizin.catchallenge.core.data.remote.provider

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceIdProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @SuppressLint("HardwareIds")
    operator fun invoke() = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID,
    )
}