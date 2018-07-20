package com.example.parth.kotlinpractice_2.support

import android.app.Activity
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View

open class ActivityViewModel(_activity: Activity) : BaseObservable() {
    var activity = _activity
    var isCustomActionbar = ObservableBoolean(false)
    var actionBarTitle = ObservableField<String>("zxcvb")
    var isBackEnabled = ObservableBoolean(false)
    var hasNavigationDrawer = ObservableBoolean(false)
    var hasBottom_DrawerNav = ObservableBoolean(false)
    var hasBottomNavigation = ObservableBoolean(false)

    var navHeaderTitle = ObservableField<String>("")
    var navHeaderContent = ObservableField<String>("")

    fun onBackPressed(view: View) {
        activity.onBackPressed()
    }

    fun startActivity(intent: Intent) {
        activity.startActivity(intent)
        if (!intent.getBooleanExtra(Constants.BACK_KEY, true))
            activity.finish()
    }

    fun getVisibility(): Int {
        if (hasBottomNavigation.get())
            return View.VISIBLE
        else
            return View.GONE
    }

    fun getVisi(): Int {
        if (hasBottom_DrawerNav.get())
            return View.VISIBLE
        else
            return View.GONE
    }
}
