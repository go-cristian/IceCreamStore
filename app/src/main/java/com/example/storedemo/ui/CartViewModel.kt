package com.example.storedemo.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.storedemo.data.AddedProduct
import com.example.storedemo.data.CartRepository
import com.example.storedemo.ui.products.Product

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
  private val products = MutableLiveData(cartRepository)

  fun observe(context: AppCompatActivity, callback: (CartRepository) -> Unit) {
    products.observe(context, Observer { cart -> callback(cart) })
    products.value = cartRepository
  }

  fun add(product: Product) {
      cartRepository.add(product)
      products.value = cartRepository
  }

  fun productsDescription(): String = cartRepository.products()
    .joinToString("\n") { "${it.name} x ${it.quantity} => ${it.total()}" }

  fun totalValue(): String {
    val total = cartRepository.products()
      .map { it.total() }
      .reduce { acc, curr -> acc + curr }
    return "$${total}"
  }
}
