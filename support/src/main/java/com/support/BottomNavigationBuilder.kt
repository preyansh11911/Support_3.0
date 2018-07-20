package com.example.parth.kotlinpractice_2.support

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.example.parth.kotlinpractice_2.support.kotlin.getColor
import kotlinx.android.synthetic.main.activity_core.*
import kotlinx.android.synthetic.main.content_drawer.*


class BottomNavigationBuilder(val coreActivity: CoreActivity<*, *, *>) {

    var view: BottomNavigationView

    init {
        if (coreActivity.hasNavigationDrawer) {
            coreActivity.coreViewModel.hasBottom_DrawerNav.set(true)
            view = coreActivity.bottom_navigation_nav_drawer
        } else {
            coreActivity.coreViewModel.hasBottomNavigation.set(true)
            view = coreActivity.bottom_navigation
        }
    }

    /**
     * Sets the items to BottomNavigation.
     * @param menuResID
     * Pass MENU resource ID to be set in BottomNavigation
     */
    fun setMenu(menuResID: Int) {
        view.inflateMenu(menuResID)
    }

    /**
     * Sets the BackgroundColor to BottomNavigation
     * ID of DRAWABLE / COLOR resource can be passed here.
     * To highlight the selected item background color,
     * make a selector of your choice in drawable and pass its resource ID
     *
     * @param resID
     * Pass DRAWABLE / COLOR resource ID to be set as BackgroundColor in BottomNavigation
     */
    fun setBackgroundColor(resID: Int) {
        view.itemBackgroundResource = resID
    }


    /**
     * This method changes icon color and text color of selected item
     * @param selectedColorResID
     * Pass COLOR resource ID to be set as
     * Selected item icon color & text color in BottomNavigation
     * @param defaultColorResID
     * Pass COLOR resource ID to be set as
     * Default item icon color & text color in BottomNavigation
     */
    fun setItemColor(selectedColorResID: Int, defaultColorResID: Int) {

        val states = arrayOf(
                intArrayOf(android.R.attr.state_checked), // checked
                intArrayOf(-android.R.attr.state_checked) // unchecked
        )

        val colors = intArrayOf(
                selectedColorResID.getColor(coreActivity),
                defaultColorResID.getColor(coreActivity)
        )

        view.itemIconTintList = ColorStateList(states, colors)
        view.itemTextColor = ColorStateList(states, colors)
    }

    /**
     * Use this method to enable / disable the shifting mode of BottomNavigation
     * @param isShifting
     * Pass TRUE/FALSE to enable/disable the shifting mode respectively
     */
    @SuppressLint("RestrictedApi")
    fun shiftModeEnabled(isShifting: Boolean) {
        if (!isShifting) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView

                    item.setShiftingMode(false)
                    // set once again checked value, so view will be updated

                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: NoSuchFieldException) {
                Log.e("BNVHelper", "Unable to get shift mode field", e)
            } catch (e: IllegalAccessException) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e)
            }
        }
    }

    fun itemSelectedHandler(navigation: (BottomNavigationView) -> Unit) {
        navigation(view)
    }
}

