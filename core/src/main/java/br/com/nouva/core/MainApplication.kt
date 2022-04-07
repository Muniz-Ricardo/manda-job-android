package br.com.nouva.core

import android.app.Application
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.utils.KoinUtilities
import org.koin.core.context.loadKoinModules

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        KoinUtilities.loadKoin(applicationContext)
        loadKoinModules(AppModule.eachModules())
    }
}