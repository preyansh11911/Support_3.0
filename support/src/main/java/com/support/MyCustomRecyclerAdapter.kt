package com.support

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class MyCustomRecyclerAdapter<T,U: ViewDataBinding>(
        val mItems: ArrayList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<U>(inflater,viewType,parent,false)
        return MyCustomViewHolder(binding)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyCustomRecyclerAdapter<*, *>.MyCustomViewHolder) {
            holder.binding.setVariable(getVM(),mItems[position])
            holder.binding.executePendingBindings()
            bindContent(holder.binding as U,mItems[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItemView()
    }

    override fun getItemId(position: Int): Long {
        return mItems[position]?.hashCode()?.toLong()!!
    }

    abstract fun bindContent(binding: U, item: T)

    @LayoutRes
    abstract fun getItemView() : Int

    abstract fun getVM():Int

    inner class MyCustomViewHolder(val binding: U) : RecyclerView.ViewHolder(binding.root)
}