package com.example.storedemo.data

import com.example.storedemo.ui.products.Product

data class AddedProduct(val name: String, var quantity: Int, val price: Double) {
  fun total(): Double = price.times(quantity)
}

class CartRepository {
  private val products = ArrayList<AddedProduct>()
  private var total = 0

  fun products(): List<AddedProduct> {
    return products
  }

  fun add(product: Product) {
    val toModify = products.find { it.name == product.name }
    if (toModify == null) {
      add(arrayListOf(AddedProduct(product.name, 1, product.price())))
    } else {
      val index = products.indexOf(toModify)
      if (toModify.quantity == 3) {
        set(products.filter { it.name != product.name })
      } else {
        products[index].quantity = products[index].quantity + 1
        total = if (this.products.isNotEmpty()) this.products.map { it.quantity }
          .reduce { acc, current -> acc + current } else 0
      }
    }
  }

  private fun add(products: List<AddedProduct>) {
    this.products.addAll(products)
    total = if (this.products.isNotEmpty()) this.products.map { it.quantity }
      .reduce { acc, current -> acc + current } else 0
  }

  fun total(): Int {
    return total
  }

  fun set(products: List<AddedProduct>) {
    this.products.clear()
    add(products)
  }
}
