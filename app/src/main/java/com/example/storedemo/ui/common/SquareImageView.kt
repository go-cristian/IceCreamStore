package com.example.storedemo.ui.common

import android.content.Context
import android.util.AttributeSet

class SquareImageView : androidx.appcompat.widget.AppCompatImageView {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
    defStyleAttr)

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = measuredWidth
    setMeasuredDimension(width, width)
  }
}
