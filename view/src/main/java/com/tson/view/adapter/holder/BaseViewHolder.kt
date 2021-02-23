package com.tson.view.adapter.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *  Date 2019-11-25 16:58
 *
 * @author tangxin
 */
open class BaseViewHolder<E : ViewDataBinding> constructor(itemDataBinding: E) :
    RecyclerView.ViewHolder(itemDataBinding.root) {
    var itemBinding: E = itemDataBinding
}