package com.tson.easydemo.pull

import com.tson.easydemo.R
import com.tson.easydemo.databinding.DbListItemBinding
import com.tson.view.adapter.BaseAdapter
import com.tson.view.adapter.holder.BaseViewHolder

/**
 *  Date 2021/2/24 3:37 PM
 *
 * @author Tson
 */
class MultiOpenAdapter(list: MutableList<String>) :
    BaseAdapter<String, DbListItemBinding>(list, R.layout.db_list_item) {
    var itemClick: (String) -> Unit = {}
    override fun itemBindView(
        itemData: String,
        holder: BaseViewHolder<*>,
        position: Int
    ) {
        val binding = holder.itemBinding as DbListItemBinding
        binding.item = itemData
        binding.show = true
        binding.delete.setOnClickListener {
            itemClick(itemData)
        }
    }

}