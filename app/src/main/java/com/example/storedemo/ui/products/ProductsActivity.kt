package com.example.storedemo.ui.products

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.storedemo.R
import com.example.storedemo.data.CartRepository
import com.example.storedemo.ui.CartViewModel
import com.example.storedemo.ui.ProductsViewModel
import com.example.storedemo.ui.cart.CartActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsActivity : AppCompatActivity() {

  private val products: ProductsViewModel by viewModel()
  private val cart: CartViewModel by viewModel()
  private val productsView by lazy { findViewById<ProductsView>(R.id.products) }
  private val orderView by lazy { findViewById<Button>(R.id.order) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.products_layout)
    products.observe(this) { products: List<Product> ->
      productsView.add(products)
    }
    cart.observe(this) { cartRepository: CartRepository ->
      val products = cartRepository.products()
      productsView.addFromCart(products)
      orderView.isEnabled = cartRepository.total() != 0
      if (cartRepository.total() == 0) {
        orderView.text = getString(R.string.order_format_zero)
      } else {
        orderView.text = resources.getString(R.string.order_format_other, cartRepository.total())
      }
    }
    productsView.setOnProductTap { product -> cart.add(product) }
    orderView.setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
  }
}
