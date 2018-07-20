package com.support.kotlin

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.parth.kotlinpractice_2.support.*
import com.support.NavigationDrawerBuilder
import com.support.POJOModel
import com.support.RecyclerViewBuilder

fun Context.showAlert(alertDialog: AlertDialogBuilder.() -> Unit) = AlertDialogBuilder(this).apply(alertDialog)

fun CoreActivity<*, *, *>.launchFragment(
        title: String = "title",
        addToBackStack: Boolean = false,
        fragment: BaseFragment,
        containerId: Int
) {
    getSupportFragmentManager().beginTransaction().apply {
        if (addToBackStack)
            add(containerId, fragment)
        else
            replace(containerId, fragment)
        commit()
    }
    setActionBarTitle(title)
}

fun CoreActivity<*, *, *>.startFragment(
        fragment: BaseFragment,
        title: String = "title",
        addToBackStack: Boolean = false,
        containerId: Int = 0
) {

    fragment.newInstance().let {
        launchFragment(fragment = it, title = title, containerId = containerId, addToBackStack = addToBackStack)
    }
}

fun Fragment.toast(msg: String, duration: Duration) {
    activity?.toast(msg, duration)
}

fun Context.toast(msg: String, duration: Duration) {
    val dur: Int
    when (duration) {
        Duration.LONG -> dur = Toast.LENGTH_LONG
        Duration.SHORT -> dur = Toast.LENGTH_SHORT
        else -> {
            dur = Toast.LENGTH_SHORT
        }
    }
    Toast.makeText(this, msg, dur).show()
}

fun View.snack(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
}

//fun View.snackWithAction(msg: String) {
//    val snackBar = Snackbar.make(this,msg, Snackbar.LENGTH_INDEFINITE)
//    snackBar.setAction(R.string.OK)
//
//}

enum class Duration {
    SHORT, LONG, INDEFINITE
}

fun CoreActivity<*, *, *>.setUpBottomNavigation(builder: BottomNavigationBuilder.() -> Unit) = BottomNavigationBuilder(this).apply(builder)
fun <T : ActivityViewModel> CoreActivity<*, *, *>.setUpNavigationDrawer(viewModel: T, builder: NavigationDrawerBuilder<T>.() -> Unit) = NavigationDrawerBuilder<T>(this, viewModel).apply(builder)
fun <T: POJOModel, U: ViewDataBinding> RecyclerView.setUpRecyclerView_Binding(itemList: ArrayList<T>, builder: RecyclerViewBuilder<T,U>.() -> Unit) = RecyclerViewBuilder<T,U>(this, itemList).apply(builder)