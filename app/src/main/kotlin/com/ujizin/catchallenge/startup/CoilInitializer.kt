package com.ujizin.catchallenge.startup

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.ujizin.catchallenge.factory.CoilImageFactory
import javax.inject.Inject

class CoilInitializer : Initializer<Unit>, ImageLoaderFactory {

    @Inject
    lateinit var coilImageFactory: CoilImageFactory

    override fun newImageLoader(): ImageLoader {
        return coilImageFactory.newImageLoader()
    }

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context).inject(this)
        Coil.setImageLoader(coilImageFactory)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}