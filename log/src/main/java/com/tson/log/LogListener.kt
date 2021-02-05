package com.tson.log

/**
 *  Date 2020-09-23 11:15
 *
 * @author Tson
 */
interface LogListener {
    fun log(lv: String, tag: String, content: String)
}