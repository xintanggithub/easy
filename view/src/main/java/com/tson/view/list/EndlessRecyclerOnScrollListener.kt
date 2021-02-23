package com.tson.view.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tangxin
 *
 * @author tangxin
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener, OnScrollCallback {

    private var mLinearLayoutManager: LinearLayoutManager? = null

    /**
     * Instantiates a new Endless recycler on scroll listener.
     *
     * @param linearLayoutManager the linear layout manager
     */
    constructor(
        linearLayoutManager: LinearLayoutManager
    ) {
        this.mLinearLayoutManager = linearLayoutManager
    }

    /**
     * Instantiates a new Endless recycler on scroll listener.
     */
    constructor() {

    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        //判断上滑下滑
        if (dy > 0) {
            //下滑
            onScrolledDown()
            if (null != mLinearLayoutManager) {
                val lastVisiblePos = mLinearLayoutManager!!.findLastVisibleItemPosition()
                val totalItemCount = mLinearLayoutManager!!.itemCount
                if (lastVisiblePos == totalItemCount - 1) {
                    //滑动到此处就自动加载下一页
                    //加载时可以根据加载状态限制是否再次加载（加载过程中也可能存在反复滑动至底部）
                    onLoadMore()
                } else {
                    onExistenceDistance()
                }
            }
        } else {
            //上滑
            onScrolledUp()
        }
    }

    override fun onScrolledDown() {
    }

    override fun onScrolledUp() {
    }

    /**
     * On load more.
     */
    abstract fun onLoadMore()

    /**
     * On existence distance.
     */
    abstract fun onExistenceDistance()
}

interface OnScrollCallback {
    fun onScrolledUp()

    fun onScrolledDown()
}