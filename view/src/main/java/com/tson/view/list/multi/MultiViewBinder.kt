package com.tson.view.list.multi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.drakeet.multitype.ItemViewBinder
import com.tson.view.adapter.holder.BaseViewHolder

/**
 *  Date 2020-10-21 17:53
 *
 * @author Tson
 */
abstract class MultiViewBinder<T, E : ViewDataBinding>(var layoutId: Int) :
    ItemViewBinder<T, BaseViewHolder<E>>() {

    var propertyChangeCallback = object : PropertyChangeCallback<T> {
        override fun onPropertyChanged(data: T, position: Int) {
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<E> {
        val view: E =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        return BaseViewHolder(view)
    }

}

interface PropertyChangeCallback<T> {
    fun onPropertyChanged(data: T, position: Int)
}