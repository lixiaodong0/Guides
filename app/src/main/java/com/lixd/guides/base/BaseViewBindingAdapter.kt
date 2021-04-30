package com.lixd.guides.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by lixiaodong
 * Created bt date 2021/4/28 6:17 PM
 *
 * @description
 */
abstract class BaseViewBindingAdapter<VB : ViewBinding, T>(var context: Context) :
    RecyclerView.Adapter<BaseViewBindingAdapter.BaseViewBindingHolder<VB>>() {

    private var data: List<T> = arrayListOf()

    /**
     * 设置新的数据
     * @param data 新的数据
     */
    fun setNewData(data: List<T>?) {
        if (data != null) {
            this.data = data
        }
        notifyDataSetChanged()
    }

    /**
     * 追加新的数据
     * @param data 新的数据
     */
    fun addData(data: List<T>?) {
        if (data != null) {
            (this.data as ArrayList).addAll(data)
            notifyItemRangeInserted(this.data.size - data.size, data.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewBindingHolder<VB> =
        BaseViewBindingHolder(getViewBinding(parent))

    override fun onBindViewHolder(holder: BaseViewBindingHolder<VB>, position: Int) {
        convert(holder, data[position], position)
        holder.viewBinding.root.setOnClickListener {
            onItemClicked(data[position], position)
        }
    }

    /**
     * 点击事件，子类可以选择性重写
     */
    open fun onItemClicked(data: T, position: Int) {

    }

    abstract fun convert(holder: BaseViewBindingHolder<VB>, data: T, position: Int)

    abstract fun getViewBinding(viewGroup: ViewGroup): VB

    override fun getItemCount(): Int = data.size

    class BaseViewBindingHolder<VB : ViewBinding>(var viewBinding: VB) :
        RecyclerView.ViewHolder(viewBinding.root)
}