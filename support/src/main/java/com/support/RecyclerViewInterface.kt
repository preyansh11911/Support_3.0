package com.support

import android.databinding.ViewDataBinding

interface RecyclerViewInterface<T, U: ViewDataBinding> {
    fun onViewHolderBindingFinished(item: T, binding: U);
}