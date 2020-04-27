package com.example.storedemo.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storedemo.R
import com.example.storedemo.ui.ProductsViewModel
import com.example.storedemo.ui.products.ProductsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

  private val products: ProductsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash_layout)
    products.observe(this) {
      startActivity(Intent(this, ProductsActivity::class.java))
      finish()
    }
  }
}
