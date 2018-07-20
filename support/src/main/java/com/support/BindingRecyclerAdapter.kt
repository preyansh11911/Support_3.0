package com.support

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

open abstract class BindingRecyclerAdapter<T,U : ViewDataBinding>(val layoutRes: LayoutRes) : RecyclerView.Adapter<BindingRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}