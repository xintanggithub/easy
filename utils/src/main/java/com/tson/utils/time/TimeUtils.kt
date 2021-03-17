package com.tson.utils.time

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Date 2021/3/17 7:17 PM
 *
 * @author Tson
 */
object TimeUtils {

    const val yyyy_MM_dd = "yyyy-MM-dd"
    const val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"

    const val SECOND = 1_000L
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR

    /**
     * 获取现在时间 以pattern参数自定义的格式返回
     * <br/>
     * yyyy-MM-dd HH:mm:ss
     *
     * @return返回自定义时间格式
     */
    @SuppressLint("SimpleDateFormat")
    fun getFormatCurrentTime(pattern: String): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat(pattern)
        return formatter.format(currentTime)
    }

    /**
     * 返回几就是周几
     */
    fun getDayOfWeek(date: Date): Int {
        val c = Calendar.getInstance().also {
            it.time = date
        }
        val weekDay = c.get(Calendar.DAY_OF_WEEK)
        val day = (weekDay - 1)
        return if (day == 0) 7 else day
    }

    /**
     * 将指定的毫秒数转换为 以pattern参数自定义的格式返回
     * <br/>
     * yyyy-MM-dd HH:mm:ss
     *
     * [_ms] 时间戳
     *
     * [pattern] 格式
     *
     */
    fun ms2Date(_ms: Long, pattern: String): String {
        val date = Date(_ms)
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }

    /**
     * 将指定以pattern参数自定义的格式的时间转换为毫秒值
     * <br/>
     * yyyy-MM-dd HH:mm:ss
     *
     * [_data] 时间
     *
     * [pattern] 格式
     *
     */
    @SuppressLint("SimpleDateFormat")
    fun Date2Ms(_data: String, pattern: String): Long {
        val format = SimpleDateFormat(pattern)
        return try {
            val date = format.parse(_data)
            date?.time ?: 0
        } catch (e: Exception) {
            0
        }
    }

    /**
     * 获取当前时间段
     * [h] 小时数，24小时制
     */
    fun getAmOrPm(h: String): String {
        var hn: Int
        try {
            hn = h.toInt()
        } catch (e: Exception) {
            hn = 0
            e.printStackTrace()
        }
        return when (hn) {
            23, 0, 1 -> "半夜"
            in 2..5 -> "凌晨"
            in 6..9 -> "早上"
            in 9 until 12 -> "上午"
            in 12..13 -> "中午"
            in 14..18 -> "下午"
            in 19..22 -> "晚上"
            else -> ""
        }
    }

    /**
     * 获取[date2]比[date1]多的天数
     */
    fun differentDays(date1: Date, date2: Date): Int {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        val day1 = cal1[Calendar.DAY_OF_YEAR]
        val day2 = cal2[Calendar.DAY_OF_YEAR]
        val year1 = cal1[Calendar.YEAR]
        val year2 = cal2[Calendar.YEAR]
        return if (year1 != year2) {//不同一年
            var timeDistance = 0
            for (i in year1 until year2) {
                timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    366
                } else {//不是闰年
                    365
                }
            }
            timeDistance + (day2 - day1)
        } else {//同一年
            day2 - day1
        }
    }

}