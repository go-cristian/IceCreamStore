package com.example.storedemo.ui.products

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storedemo.R
import com.example.storedemo.data.AddedProduct
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

data class Product(val name: String, val price: String, val color: String,
  @DrawableRes val icon: Int) {
  fun price(): Double = NumberFormat.getCurrencyInstance(Locale.getDefault())
    .parse(price)
    .toDouble()
}

class ProductsView : RecyclerView {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
    defStyleAttr) {
    adapter = ProductAdapter(context)
    layoutManager = GridLayoutManager(context, 2)
  }

  fun add(products: List<Product>) {
    (adapter as ProductAdapter).add(products)
  }

  fun setOnProductTap(callback: (product: Product) -> Unit) {
    (adapter as ProductAdapter).setOnProductTap(callback)
  }

  fun addFromCart(products: List<AddedProduct>) {
    (adapter as ProductAdapter).update(products)
  }
}

class ProductAdapter(context: Context) : RecyclerView.Adapter<Holder>() {

  private val data = ArrayList<Product>()
  private val quantities = ArrayList<AddedProduct>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private var callback: (product: Product) -> Unit = {}

  fun add(products: List<Product>) {
    data.clear()
    data.addAll(products)
    notifyDataSetChanged()
  }

  fun setOnProductTap(callback: (product: Product) -> Unit) {
    this.callback = callback
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    Holder(inflater.inflate(R.layout.product_item, parent, false)) {
      this.callback(data[it])
    }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: Holder, position: Int) {
    val product = data[position]
    val quantity = quantities.find { it.name == product.name }?.quantity ?: 0
    holder.use(product, quantity)
  }

  fun update(products: List<AddedProduct>) {
    quantities.clear()
    quantities.addAll(products)
    notifyDataSetChanged()
  }
}

class Holder(itemView: View, val callback: (index: Int) -> Unit) :
  RecyclerView.ViewHolder(itemView) {

  private var counterView: TextView = itemView.findViewById(R.id.product_counter)
  private var imageView: ImageView = itemView.findViewById(R.id.product_image)
  private var backgroundView: ImageView = itemView.findViewById(R.id.product_image_background)
  private var nameView: TextView = itemView.findViewById(R.id.product_name)
  private var priceView: TextView = itemView.findViewById(R.id.product_price)

  fun use(product: Product, quantity: Int) {
    counterView.text = "$quantity"
    itemView.setOnClickListener { callback(adapterPosition) }
    imageView.setImageResource(product.icon)
    (backgroundView.background as GradientDrawable).setColor(Color.parseColor(product.color))
    nameView.text = product.name
    priceView.text = product.price
  }
}
