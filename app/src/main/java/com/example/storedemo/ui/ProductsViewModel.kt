package com.example.storedemo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storedemo.data.ProductsRepository
import com.example.storedemo.ui.products.Product
import kotlinx.coroutines.launch

class ProductsViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

  fun observe(context: Context, callback: (List<Product>) -> Unit) {
    viewModelScope.launch {
      callback(productsRepository.all()
        .map {
          Product(listOf(it.name1, it.name2).joinToString(" "), it.price, it.bg_color,
            context.resources.getIdentifier(it.type, "drawable", context.packageName))
        })
    }
  }
}
