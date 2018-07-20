package com.example.parth.kotlinpractice_2.support

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class CoreFragment_DataBinding<T : CoreFragment_DataBinding<T, DB, VM>, DB : ViewDataBinding, VM : FragmentViewModel> : BaseFragment() {

    lateinit var coreFragment: T
    lateinit var binding: DB
    var vm: VM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRoot()
    }

    fun getRoot(): View {
        coreFragment = getFragmentContext()
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutView(), null, false)
        setVM(binding)
        workArea()
        return binding.root
    }

    abstract fun workArea()

    abstract fun getLayoutView(): Int

    abstract fun getFragmentContext(): T

    fun getViewModel(): VM {
        if (vm == null) vm = createViewModel()
        return vm as VM
    }

    abstract fun createViewModel(): VM

    abstract fun setVM(binding: DB)
}