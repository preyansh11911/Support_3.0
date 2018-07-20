package com.example.parth.kotlinpractice_2.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class CoreFragment<T : CoreFragment<T, VM>, VM : FragmentViewModel> : BaseFragment() {

    lateinit var coreFragment: T
    var vm: VM? = null
    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRoot(inflater, container)
    }

    fun getRoot(inflater: LayoutInflater, container: ViewGroup?): View {
        coreFragment = getFragmentContext()
        rootView = inflater.inflate(getLayoutView(), container, false)
        getViewModel()
        workArea()
        return rootView
    }

    abstract fun workArea()

    abstract fun getLayoutView(): Int

    abstract fun getFragmentContext(): T

    fun getViewModel(): VM {
        if (vm == null) vm = createViewModel()
        return vm as VM
    }

    abstract fun createViewModel(): VM
}