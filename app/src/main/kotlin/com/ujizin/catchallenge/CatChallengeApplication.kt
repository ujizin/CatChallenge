package com.ujizin.catchallenge

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.ujizin.catchallenge.factory.CoilImageFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CatChallengeApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var coilImageFactory: CoilImageFactory

    override fun newImageLoader(): ImageLoader {
        return coilImageFactory.newImageLoader()
    }
}
