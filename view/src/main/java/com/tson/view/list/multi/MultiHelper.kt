package com.tson.view.list.multi

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter

/**
 *  Date 2020-10-21 18:47
 *
 * @author Tson
 */
data class Bind(var rv: RecyclerView, var adapter: MultiTypeAdapter)

fun <T : RecyclerView> T.bindAdapter(
    baseWeight: Int, weightArray: MutableList<Int>,
    bind: T.() -> Bind
) {
    bind().also { b ->
        b.rv.run {
            layoutManager = GridLayoutManager(context, baseWeight).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(p0: Int): Int {
                        return weightArray[b.adapter.getItemViewType(p0)]
                    }
                }
            }
            adapter = b.adapter
        }
    }
}