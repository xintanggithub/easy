package com.tson.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tson.view.adapter.click.OnclickListener
import com.tson.view.adapter.foot.BaseAdapterConfig
import com.tson.view.adapter.holder.BaseFooterViewHolder
import com.tson.view.adapter.holder.BaseViewHolder

/**
 * Date 2019-11-25 16:55
 *
 * @author tangxin
 */
abstract class BaseAdapter<T, E : ViewDataBinding>(
    var data: MutableList<T>,
    var layoutId: Int,
    private var baseAdapterConfig: BaseAdapterConfig<T, ViewDataBinding>?
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2

        //加载中
        const val LOADING = 101

        //加载完成
        const val LOAD_COMPLETE = 102

        //没有更多
        const val LOAD_NO_MORE = 103
    }

    private var status = LOADING

    private var showFooter = false
    private lateinit var baseFooterViewHolder: BaseFooterViewHolder<*>

    init {
        setGridLayoutManager()
    }

    constructor(data: MutableList<T>, layoutId: Int) : this(data, layoutId, null) {
        this.showFooter = false
    }

    fun hideFooter() {
        showFooter = false
        if (this::baseFooterViewHolder.isInitialized) {
            baseFooterViewHolder.itemBinding.root.visibility = View.GONE
        }
    }

    /**
     *   加载中 LOADING = 101
     *
     *   加载完成 LOAD_COMPLETE = 102
     *
     *   没有更多 LOAD_NO_MORE = 103
     *
     *   [Limit.SIM]
     */
    fun showFooter(@Limit.SIM state: Int) {
        notifyFooter(state)
    }

    private fun setGridLayoutManager() {
        if (null != baseAdapterConfig) {
            this.showFooter = true
            baseAdapterConfig!!.layoutManager().also { lm ->
                if (lm is GridLayoutManager) {
                    lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val type = getItemViewType(position)
                            return if (type == TYPE_ITEM) {
                                1
                            } else {
                                lm.spanCount
                            }
                        }
                    }
                }
            }
        } else {
            this.showFooter = false
        }
    }

    var onclickListener = object : OnclickListener() {
        override fun onclick(view: View) {
        }
    }

    abstract fun itemBindView(
        itemData: T,
        holder: BaseViewHolder<*>,
        position: Int
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view: E = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
                BaseViewHolder(view)
            }
            else -> {
                return BaseFooterViewHolder(
                    baseAdapterConfig!!.dataBinding(
                        parent
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (position == data.size) {
            if (null != baseAdapterConfig) {
                baseFooterViewHolder = holder as BaseFooterViewHolder<*>
                baseAdapterConfig!!.footerHolder(
                    baseFooterViewHolder,
                    data,
                    status
                )
            }
        } else {
            itemBindView(data[position], holder, position)
        }
    }

    fun more() {
        notifyFooter(LOADING)
    }

    fun complete() {
        notifyFooter(LOAD_COMPLETE)
    }

    fun noMore() {
        notifyFooter(LOAD_NO_MORE)
    }

    private fun notifyFooter(@Limit.SIM state: Int) {
        showFooter = true
        status = state
        if (this::baseFooterViewHolder.isInitialized) {
            baseFooterViewHolder.itemBinding.root.visibility = View.VISIBLE
            baseAdapterConfig!!.footerHolder(baseFooterViewHolder, data, state)
        }
        try {
            notifyItemChanged(itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addList(list: MutableList<T>) {
        data.addAll(list)
        val startIndex = data.size - list.size
        try {
            notifyItemRangeChanged(startIndex, list.size)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
    }

    fun addByIndex(index: Int, item: T) {
        addByIndex(index, mutableListOf(item))
    }

    fun addByIndex(index: Int, list: MutableList<T>) {
        data.addAll(index, list)
        notifyItemRangeInserted(index, list.size)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addItem(itemData: T) {
        data.add(itemData)
        notifyItemInserted(data.size)
    }

    fun resetItem(index: Int, itemData: T) {
        if (index == -1) {
            return
        }
        if (index > data.size - 1) {
            return
        }
        data[index] = itemData
        notifyItemChanged(index)
    }

    fun resetData(list: MutableList<T>) {
        val allSize = data.size
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        if (data.size <= 0) {
            Log.w("BaseAdapter", "remove error ! data.size = 0")
            return
        }
        data.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, data.size - index)
    }

    override fun getItemCount(): Int = if (showFooter) (data.size + 1) else data.size

    override fun getItemViewType(position: Int): Int = if (showFooter) {
        if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    } else {
        TYPE_ITEM
    }

}
