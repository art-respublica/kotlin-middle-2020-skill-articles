package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.navigation.NavDestination

fun View.setMarginOptionally(
    left: Int = marginLeft, top: Int = marginTop,
    right: Int = marginRight, bottom: Int = marginBottom
) {
    val layoutParams = this.layoutParams as? ViewGroup.MarginLayoutParams
    layoutParams?.setMargins(left, top, right, bottom)
    this.layoutParams = layoutParams
}

fun View.setPaddingOptionally(
    left: Int = paddingLeft, right: Int = paddingRight,
    top: Int = paddingTop, bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.selectDestination(destination: NavDestination) {
    // TODO homework
}