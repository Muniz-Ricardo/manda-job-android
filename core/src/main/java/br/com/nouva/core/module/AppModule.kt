package br.com.nouva.core.module

import br.com.nouva.core.network.Services
import br.com.nouva.core.repository.MainRepository
import br.com.nouva.core.repository.MainRepositoryImpl
import br.com.nouva.core.service.ServiceGenerator
import br.com.nouva.core.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    fun eachModules() = arrayListOf(
        module,
        repository,
        service
    )

    private val module = module {
        viewModel { MainViewModel(androidApplication(), repository = get(), get()) }
    }

    private val repository = module {
        single<MainRepository>{ MainRepositoryImpl(get()) }
    }

    val service = module {
        single {
            ServiceGenerator(
                url = "https://", headers = listOf(
                    Pair("Content-Type", "application/json"),
                    Pair("Accept", "application/json"),
                    Pair("Connection", "close"),
                    Pair("x-platform", "Mobile.Android")
                )
            ).generate() as Services
        }
    }
}