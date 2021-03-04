package com.tson.easydemo.pull

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tson.easydemo.R
import com.tson.easydemo.custom.BaseActivity
import com.tson.easydemo.databinding.PaBinding
import com.tson.easydemo.model.PAViewModel
import com.tson.view.list.pull.TLRLinearLayout
import com.tson.view.list.pull.TLRUIHandler
import kotlinx.android.synthetic.main.pa.*

class PullActivity(override val layoutId: Int = R.layout.pa) :
    BaseActivity<PaBinding, PAViewModel>(PAViewModel::class.java) {

    companion object {
        private const val TAG = "PullActivity"
    }

    override fun initView() {
        pl.run {
            layoutManager = LinearLayoutManager(this.context)
            adapter = MultiOpenAdapter(mutableListOf("123","123","123","123","123","123","123"))
        }
        rl.addTLRUiHandler(object :TLRUIHandler{
            override fun onFinish(target: View?, isRefresh: Boolean, isSuccess: Boolean, errorCode: Int) {
                Log.d(TAG,"onFinish  ===>  onFinish")
            }

            override fun onRefreshStatusChanged(target: View?, status: TLRLinearLayout.RefreshStatus?) {
                Log.d(TAG,"onRefreshStatusChanged  ===>  2")
            }

            override fun onOffsetChanged(target: View?, isRefresh: Boolean, totalOffsetY: Int, totalThresholdY: Int, offsetY: Int, threshOffset: Float) {
            }

            override fun onLoadStatusChanged(target: View?, status: TLRLinearLayout.LoadStatus?) {
                Log.d(TAG,"onLoadStatusChanged   ===> 4")
                rl.isEnableRefresh = false
            }
        })

        rl.headerView.setBackgroundColor(Color.TRANSPARENT)


    }

}