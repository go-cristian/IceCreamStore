package com.example.storedemo.ui.cart

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storedemo.R
import com.example.storedemo.ui.CartViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {

  private val cart: CartViewModel by viewModel()
  private val contentView by lazy { findViewById<TextView>(R.id.cart_content) }
  private val totalView by lazy { findViewById<TextView>(R.id.cart_total) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.cart_layout)
    cart.observe(this) {
      contentView.text = cart.productsDescription()
      totalView.text = getString(R.string.total_format, cart.totalValue())
    }
  }
}
