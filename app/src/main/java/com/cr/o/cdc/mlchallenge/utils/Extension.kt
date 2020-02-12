package com.cr.o.cdc.mlchallenge.utils

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadFromUrl(
    imageUrl: String
) {
    Picasso.get()
        .load(imageUrl)
        .into(this)
}

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}