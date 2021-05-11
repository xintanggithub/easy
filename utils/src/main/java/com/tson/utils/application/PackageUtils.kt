package com.tson.utils.application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File


/**
 *  Date 2020-09-17 20:14
 *
 * @author Tson
 */
class PackageUtils {

    companion object {

        const val TAG = "PackageUtils"

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { PackageUtils() }

        const val REQUEST_INSTALL_UNKNOWN_CODE = 123

        /**
         * 未安装
         */
        const val APP_STATUS_NOT_INSTALL = 1

        /**
         * 已安装，需要更新
         */
        const val APP_STATUS_NEED_UPDATE = 2

        /**
         * 已安装,不需要更新
         */
        const val APP_STATUS_ALREADY_INSTALL = 3
    }

    /**
     * 判断应用是否已安装
     *
     * @param context 上下文
     * @param pkgName 需要判断是否已安装应用的包名
     * @return ** true 已安装 false 未安装****
     ** */
    fun isInstalled(context: Context, pkgName: String): Boolean {
        var packageInfo: PackageInfo? = null
        try {
            val packageManager = context.packageManager
            packageInfo = packageManager.getPackageInfo(pkgName, PackageManager.GET_GIDS)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.w(TAG, ":check isInstalled error ,package name not found")
        }
        return null != packageInfo
    }

    /**
     * 获取版本号 versionCode
     *
     * @param context     上下文
     * @param packageName 需要获取版本号的包名
     * @return **版本号versionCode**
     */
    fun getAppVersionCode(context: Context, packageName: String): Int {
        var versioncode = -1
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            versioncode = pi.versionCode
        } catch (e: Exception) {
            Log.w(TAG, ":Get VersionInfo Exception" + e.message)
        }

        return versioncode
    }

    /**
     * 判断应用是否需要更新
     *
     * @param context     上下文
     * @param packageName 需要校验的包名
     * @param versionCode 用以对比已安装APP版本的versionCode，以此确认是否需要更新
     * @return **true 需要更新 false 不需要更新**
     */
    fun isUpdate(context: Context, packageName: String, versionCode: Int): Boolean {
        //如果比versionCode大，或者相等，则不需要更新
        return getAppVersionCode(context, packageName) < versionCode
    }

    /**
     * 根据包名判断应用状态
     *
     * @param context     上下文
     * @param packageName 需要校验的应用包名
     * @return **APP_STATUS_NOT_INSTALL****   未安装<br></br>
     * **APP_STATUS_NEED_UPDATE**    需要更新<br></br>
     * **APP_STATUS_ALREADY_INSTALL**   已安装<br></br>
     ** */
    fun checkAppStatus(context: Context, packageName: String, versionCode: Int): Int {
        //判断应用是否已安装
        return if (isInstalled(context, packageName)) {
            //已安装
            //判断应用是否需要更新
            if (isUpdate(context, packageName, versionCode)) {
                //如果需要更新
                APP_STATUS_NEED_UPDATE
            } else {//已是最新
                APP_STATUS_ALREADY_INSTALL
            }
        } else {//未安装
            APP_STATUS_NOT_INSTALL
        }
    }

    /**
     * 获取已安装Apk文件的源Apk文件
     * 如：/data/app/com.sina.weibo-1.apk
     *
     * @param context     the context
     * @param packageName the package name
     * @return the source apk path
     */
    fun getSourceApkPath(context: Context, packageName: String): String? {
        if (TextUtils.isEmpty(packageName)) {
            return null
        }
        if (!isInstalled(context, packageName)) {
            return null
        }
        try {
            val appInfo = context.packageManager
                .getApplicationInfo(packageName, 0)
            return appInfo.sourceDir
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    fun installPermissionCheck(context: Activity, next: () -> Unit = {}) {
        val canInstall = context.packageManager.canRequestPackageInstalls()
        if (!canInstall) {
            val packageURI = Uri.parse("package:" + context.packageName)
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
            context.startActivityForResult(intent, REQUEST_INSTALL_UNKNOWN_CODE)
        } else {
            next()
        }
    }

    fun installApk(context: Activity, path: String) {
        installPermissionCheck(context) {
            val authority = "com.xuantian.distribution.fileProvider"
            Log.d(TAG, "authority:$authority")
            val apkUri: Uri = FileProvider.getUriForFile(context, authority, File(path))
            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            install.setDataAndType(apkUri, "application/vnd.android.package-archive")
            context.startActivity(install)

        }
    }

}