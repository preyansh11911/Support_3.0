package com.support

import android.databinding.DataBindingUtil
import android.support.design.widget.NavigationView
import android.view.View
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.support.databinding.NavHeaderDrawerBinding
import kotlinx.android.synthetic.main.activity_drawer.*

class NavigationDrawerBuilder<T : ActivityViewModel>(val coreActivity: CoreActivity<*, *, *>, val viewModel: T) {

    val navigationView: NavigationView

    init {
        coreActivity.hasNavigationDrawer = true
        coreActivity.setNavigationDrawer()
        navigationView = coreActivity.nav_view
    }

    fun setNavigationDrawerMenu(menuRes: Int) {
        navigationView.inflateMenu(menuRes)
    }

    fun setNavigationDrawerHeader(layoutRes: Int) {
        val headerView: View = navigationView.inflateHeaderView(layoutRes)
        val navHeaderBinding = DataBindingUtil.bind<NavHeaderDrawerBinding>(headerView)
        navHeaderBinding?.vm = viewModel
    }

    fun itemSelectedHandler(navigation: (T, NavigationView) -> Unit) {
        navigation(viewModel, navigationView)
    }
}