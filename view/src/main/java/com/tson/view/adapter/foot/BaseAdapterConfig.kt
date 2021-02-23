package com.tson.view.adapter.foot

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tson.view.adapter.holder.BaseFooterViewHolder

/**
 * Created tangxin
 * Time 2019/5/5 6:21 PM
 */
interface BaseAdapterConfig<T, E : ViewDataBinding> {

    /**
     * 获取dataBinding
     *
     * @param parent viewGroup
     * @return dataBinding
     */
    fun dataBinding(parent: ViewGroup): E

    /**
     * 返回foot view
     *
     * @param holder    holder
     * @param mData     数据
     * @param loadState 状态
     */
    fun footerHolder(holder: BaseFooterViewHolder<*>, mData: MutableList<T>, loadState: Int)

    /**
     * 获取layoutManager
     *
     * @return layoutManager
     */
    fun layoutManager(): RecyclerView.LayoutManager

}
