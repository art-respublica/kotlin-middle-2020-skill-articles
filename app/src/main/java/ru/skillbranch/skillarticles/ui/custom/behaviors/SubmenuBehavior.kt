package ru.skillbranch.skillarticles.ui.custom.behaviors

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import ru.skillbranch.skillarticles.ui.custom.ArticleSubmenu
import ru.skillbranch.skillarticles.ui.custom.Bottombar

class SubmenuBehavior() : CoordinatorLayout.Behavior<ArticleSubmenu>() {
    constructor(context: Context, attrs: AttributeSet) : this()

    // set view as dependent on bottombar
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ArticleSubmenu,
        dependency: View
    ): Boolean {
        return dependency is Bottombar
    }

    // will be called if dependent view has been changed
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ArticleSubmenu,
        dependency: View
    ): Boolean {
        return if (child.isOpen && dependency is Bottombar && dependency.translationY >= 0) {
            animate(child, dependency)
            true
        } else {
            false
        }
    }

    private fun animate(child: ArticleSubmenu, dependency: Bottombar) {
        val fraction = dependency.translationY / dependency.height
        child.translationX = (child.width + child.marginRight) * fraction
    }
}