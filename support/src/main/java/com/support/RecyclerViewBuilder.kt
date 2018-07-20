package com.support

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecyclerViewBuilder<T: POJOModel, U: ViewDataBinding>
/**
 * @param - Recycler View
 * @param - LIst to bind with RecyclerView
 **/
(val recyclerView : RecyclerView, val mItems: ArrayList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_ITEM = 1011
        val VIEW_TYPE_LOADER = 1021
    }

    var itemView : Int = 0
        get() = field
        set(value) { field = value }
    var isLoading = false
    var contentBindingListener: ((T, U) -> Unit)? = null
    var loadMoreListener: (() -> Unit)? = null

    fun contentBinder(l: (T, U) -> Unit) { contentBindingListener = l }

    init {
        setHasStableIds(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<U>(inflater, itemView, parent,false)
            return MyContentViewHolder(binding)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.load_more_progressbar,parent,false)
            return LoaderViewHolder(v)
        }
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerViewBuilder<*, *>.MyContentViewHolder) {
            holder.binding.setVariable(BR.vm,mItems[position])
            holder.binding.executePendingBindings()
            contentBindingListener?.invoke(mItems[position], holder.binding as U)
        }
    }

    override fun getItemId(position: Int): Long {
        return mItems[position].hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position].isLoader) VIEW_TYPE_LOADER else VIEW_TYPE_ITEM
    }

    inner class MyContentViewHolder(val binding: U) : RecyclerView.ViewHolder(binding.root)
    inner class LoaderViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView)

    fun enableLoadMore(l: () -> Unit) {
        loadMoreListener = l

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val totalItemCount = recyclerView?.layoutManager?.getItemCount()
                val lastVisibleItem = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val visibleThreshold = 5
                if (!isLoading && totalItemCount!! <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMoreListener != null) {
                        loadMoreListener?.invoke()
                    }
                    isLoading = true
                }
            }
        })
    }

    fun showLoader() {
        val pModel = POJOModel().apply { isLoader = true } as T
        mItems.add(pModel)
        notifyItemInserted(itemCount - 1)
    }

    fun stopLoader() {
        isLoading = false
    }

    fun removeLastItem() {
        mItems.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount)
    }

    fun addAll(list: ArrayList<T>) {
        val position = itemCount
        notifyItemRangeInserted(position, list.size)
    }
}