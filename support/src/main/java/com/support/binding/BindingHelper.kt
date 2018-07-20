package com.example.parth.kotlinpractice_2.support.binding

import android.databinding.BindingAdapter
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView

class BindingHelper {

    /**
     * @param bottomNavigationView
     * @param listener
     */
    @BindingAdapter("bottomNavigationListener")
    fun setBottomNavigationListener(bottomNavigationView: BottomNavigationView,
                                    listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
        if (listener != null)
            bottomNavigationView.setOnNavigationItemSelectedListener(listener)
    }

    @BindingAdapter("viewMenu")
    fun setNavigationViewMenu(navigationView: NavigationView, menuResID: Int) {
        navigationView.inflateMenu(menuResID)
    }
}