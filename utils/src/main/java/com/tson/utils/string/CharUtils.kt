package com.tson.utils.string

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern
import kotlin.experimental.and

/**
 *  Date 2021/3/17 8:28 PM
 *
 * @author Tson
 */
object CharUtils {

    fun md5Upper(string: String): String {
        return md5(string, true)
    }

    fun md5Lower(string: String): String {
        return md5(string, false)
    }

    fun md5(string: String, isUpper: Boolean): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(string.toByteArray())
            val result = StringBuilder()
            for (b in bytes) {
                var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result.append(temp)
            }
            return if (isUpper) {
                result.toString().toUpperCase()
            } else {
                result.toString().toLowerCase()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param str 字符串
     * @return 是否纯数字 boolean
     */
    fun isNumber(str: String): Boolean {
        for (element in str) {
            if (!Character.isDigit(element)) {
                return false
            }
        }
        return true
    }

    fun isEmail(email: String?): Boolean {
        return email?.run {
            val regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
            return  Pattern.compile(regEx1).matcher(email).matches()
        } ?: false
    }

//    public static boolean isEmail(String email){
//        if (null==email || "".equals(email)){
//            return false;
//        }
//        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//        Pattern p = Pattern.compile(regEx1);
//        Matcher m = p.matcher(email);
//        if(m.matches()){
//            return true;
//        }else{
//            return false;
//        }
//    }
}