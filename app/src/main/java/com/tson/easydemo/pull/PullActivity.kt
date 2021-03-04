package com.tson.easydemo.pull

import android.graphics.Color
import android.os.Handler
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
        val adaptered =MultiOpenAdapter(mutableListOf("123","123","123","123","123","123","123","123","123","123","123","123","123"))
        pl.run {
            layoutManager = LinearLayoutManager(this.context)
            adapter = adaptered
        }
        rl.addTLRUiHandler(object :TLRUIHandler{
            override fun onFinish(target: View?, isRefresh: Boolean, isSuccess: Boolean, errorCode: Int) {
            }

            override fun onRefreshStatusChanged(target: View?, status: TLRLinearLayout.RefreshStatus?) {
                if (status === TLRLinearLayout.RefreshStatus.REFRESHING){
                    Log.d(TAG,"onRefreshStatusChanged  ===>  2")
                    rl.finishRefresh(true)
                    adaptered.addByIndex(0,"loading ....")
                    pl.scrollToPosition(0)
//                    rl.isEnableLoad = false
//                    Handler().postDelayed({
//                        rl.finishRefresh(true)
//                        rl.isEnableLoad = true
//                    },2000)
                }
            }

            override fun onOffsetChanged(target: View?, isRefresh: Boolean, totalOffsetY: Int, totalThresholdY: Int, offsetY: Int, threshOffset: Float) {
            }

            override fun onLoadStatusChanged(target: View?, status: TLRLinearLayout.LoadStatus?) {
                if (status === TLRLinearLayout.LoadStatus.LOADING) {
                    Log.d(TAG, "onLoadStatusChanged   ===> 4")
                    rl.isEnableRefresh = false
                    Handler().postDelayed({
                        rl.finishLoad(true)
                        rl.isEnableRefresh = true
                    }, 2000)
                }
            }
        })

        rl.headerView.setBackgroundColor(Color.WHITE)


    }

}