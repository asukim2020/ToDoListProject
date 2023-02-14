package com.asusoft.todolistproject.extension

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.asusoft.todolistproject.R
import com.google.android.material.appbar.AppBarLayout

fun AppCompatActivity.settingActionBar() {

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    toolbar.setBackgroundColor(getColor(R.color.background))
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar)
    val stateListAnimator = StateListAnimator()
    stateListAnimator.addState(
        IntArray(0),
        ObjectAnimator.ofFloat(
            appBarLayout,
            "elevation",
            0f
        )
    )
    appBarLayout.stateListAnimator = stateListAnimator
}
