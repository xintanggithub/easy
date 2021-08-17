package com.tson.easy.model

import androidx.lifecycle.ViewModel
import com.tson.easy.view.LoadInterface

/**
 *  Date 2021/1/29 2:08 PM
 *
 * @author Tson
 */
abstract class InitModel : ViewModel() {

    lateinit var loadStatus: LoadInterface

    var multiLoadStatus = mutableMapOf<String, LoadInterface>()

    abstract fun initModel()

}