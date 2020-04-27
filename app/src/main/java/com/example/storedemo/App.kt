package com.example.storedemo

import android.app.Application
import com.example.storedemo.data.CartRepository
import com.example.storedemo.data.ProductsRepository
import com.example.storedemo.ui.CartViewModel
import com.example.storedemo.ui.ProductsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

  private val appModule = module {
    single { ProductsRepository() }
    single { CartRepository() }
    viewModel { ProductsViewModel(get()) }
    viewModel { CartViewModel(get()) }
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@App)
      modules(appModule)
    }
  }
}

