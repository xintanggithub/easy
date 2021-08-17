### 常用工具集合库

#### PackageUtils包管理

- 判断应用是否安装
```
fun isInstalled(context: Context, pkgName: String): Boolean
```

- 获取应用版本号
```
fun getAppVersionCode(context: Context, packageName: String): Int
```

- 对比指定版本，确认是否需要更新
```
fun isUpdate(context: Context, packageName: String, versionCode: Int): Boolean
```

- 根据报名判断应用状态（未安装、已安装、需更新）
```
fun checkAppStatus(context: Context, packageName: String, versionCode: Int): Int
```

- 获取已安装Apk文件的源Apk文件
```
fun getSourceApkPath(context: Context, packageName: String): String?
```

- 安装apk前权限验证
```
fun installPermissionCheck(context: Activity, next: () -> Unit = {})
```

- 发起apk安装
```
fun installApk(context: Activity, path: String)
```

#### DisplayUtils屏幕工具

- 将px值转换为dp值
```
fun px2dp(context: Context, pxValue: Float): Int
```

- 将dp值转换为px值
```
fun dp2px(context: Context, dpValue: Float): Int
```

- 将px值转换为sp值
```
fun px2sp(context: Context, pxValue: Float): Int
```

- 将sp值转换为px值
```
fun sp2px(context: Context, dpValue: Float): Int
```

- 获取屏幕宽度
```
fun getScreenWidthPixels(context: Activity): Int
```

- 获取屏幕高度
```
fun getScreenHeightPixels(context: Activity): Int
```

- 将一个view转换成bitmap位图
```
fun viewToBitmap(view: View): Bitmap
```

- 获取模糊虚化的bitmap
```
fun getBlurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap
```

- android系统的模糊方法
```
fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap
```

#### ActivityUtils

集合存储之前打开过的activity，创建加入，销毁删除

```kotlin
// 加入集合（一般在onCreate中调用）
fun addActivity(act: Activity)

// 移除集合（一般在onDestroy中调用）
fun remove(act: Activity)

// 根据clz: Class<*>关闭指定activity
fun finishByClz(clz: Class<*>)

// 关闭所有的
fun closeAll()
```

#### CleanDataUtils缓存工具

- 查询所有的缓存大小(结果已格式化)
```
fun getTotalCacheFormatSize(context: Context): String
```

- 查询所有的缓存大小(字节大小，未格式化)
```
fun getTotalCacheSize(context: Context): Long
```

- 清空缓存
```
fun clearAllCache(context: Context)
```

- 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
```
fun cleanInternalCache(context: Context)
```

- 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
```
fun cleanDatabases(context: Context)
```

- 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
```
fun cleanSharedPreference(context: Context)
```

- 按数据库名字清除本应用数据库
```
fun cleanDatabaseByName(context: Context, dbName: String)
```

- 清除/data/data/com.xxx.xxx/files下的内容
```
fun cleanFiles(context: Context)
```

- 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
```
fun cleanExternalCache(context: Context)
```

- 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
```
fun cleanCustomCache(filePath: String)
```

- 清除本应用所有的数据
```
fun cleanApplicationData(context: Context, vararg filepath: String)
```

- 获取文件大小
```
fun getFolderSize(file: File?): Long
```

- 格式化单位
```
fun getFormatSize(size: Double): String
```

#### FileUtils文件工具

- 根据Uri返回文件绝对路径(兼容了file:///开头的 和 content://开头的情况)
```
fun getRealFilePathFromUri(context: Context, uri: Uri): String
```

- 检查文件是否存在
```
fun checkDirPath(dirPath: String): String
```

- 检测SDCard是否可用
```
fun checkSDCard(): Boolean
```

- 文件夹路径转file，没有就创建
```
fun getDirectory(path: String): File
```

- 递归删除文件夹
```
fun deleteDirectory(p: String): Boolean
```

- 创建文件父级路径
```
fun createParentFolder(file: File)
```

- 创建文件
```
fun createFile(file: File): File
```

- 创建文件
```
fun createFile(p: String): File
```

- 获取文件夹
```
fun getFolder(p: String): File
```

- 删除文件夹
```
fun deleteFolder(p: String)
```

- 删除文件
```
fun deleteFile(sPath: String): Boolean
```

- 格式化路径(比如Mac路径和Windows路径转换为Android统一的)
```
fun separatorReplace(path: String): String
```

- 创建文件夹
```
fun createFolder(p: String)
```

- 将流写入文件
```
fun writeFile(path: String, `in`: InputStream?): Int
```

- 保存bitmap为图片
```
fun saveBitmap(path: String, bitmap: Bitmap)
```

- 获取文件大小
```
fun getFolderSize(file: File): Long
```

- 复制文件
```
fun copyFile(oldPath: String, newPath: String)
```

#### InputUtils键盘管理工具

- 显示键盘，且聚焦到指定输入框
```
fun showInput(context: Context, et: EditText)
```

- 收起键盘
```
fun hide(context: Context, edit: EditText)
```

- 收起键盘
```
fun hideInput(context: Activity)
```

#### CharUtils字符串工具
- 生成MD5字符串 大写
```
fun md5Upper(string: String): String
```

- 生成MD5字符串 小写
```
fun md5Lower(string: String): String
```

- 生成MD5字符串 自定义大小写
```
fun md5(string: String, isUpper: Boolean): String
```

- 判断字符串是否为纯数字
```
fun isNumber(str: String): Boolean
```

- 判断字符串是否是邮箱
```
fun isEmail(email: String?): Boolean
```

#### PrintUtils对象输出工具

- 指定对象转为字符串
```
public static <T> String modelToString(T t)
```

#### threadUtils线程切换

```
// 切换到主线程
 this.ktxRunOnUi {
    // todo
 }

 // 延迟delayMillis后切换到主线程
 this.ktxRunOnUiDelay {
    // todo
 }

 // 子线程执行。SingleThreadPool
 this.ktxRunOnBgSingle {
    // todo
 }

 // 子线程执行。FixedThreadPool
 this.ktxRunOnBgFix {
    // todo
 }

 // 子线程执行。CachedThreadPool
 this.ktxRunOnBgCache {
    // todo
 }
```

#### EasyTimer定时器
- 初始化一个定时器
```
    /**
     * [delay] 延迟多久触发，默认值1000 (1秒)
     *
     * [period] 间隔多久执行，默认值1000 (1秒)
     */
    fun run(delay: Long, period: Long, c: TimerCallBack)
```

- 启动/停止
```
// 启动
fun start()

// 停止
fun stop()
```

- 示例
```
// 初始化
EasyTimer.instance.run(1000L, 1000L, object : TimerCallBack {
            override fun doNotify() {
            }

            override fun start() {
            }

            override fun stop() {
            }
        })
// 启动
EasyTimer.instance.start()
// 停止
EasyTimer.instance.stop()
```

#### 延时工具
- 泛型对象，以及可指定延时时长，默认500ms
```
fun <T> T.wait(block: T.() -> Unit, interval: Long = 500L)
```

- 使用示例
```
// 延时500ms执行
wait({
    // todo
})

// 延时5000ms执行
wait({
    // todo
},5000)
```

#### TimeUtils时间工具类

- 获取现在时间 以pattern参数自定义的格式返回
```
fun getFormatCurrentTime(pattern: String): String
```

- 将指定的毫秒数转换为 以pattern参数自定义的格式返回
```
fun ms2Date(_ms: Long, pattern: String): String
```

- 根据date获取是周几(返回几就是周几)
```
fun getDayOfWeek(date: Date): Int
```

- 将指定以pattern参数自定义的格式的时间转换为毫秒值
```
fun Date2Ms(_data: String, pattern: String): Long
```

- 获取当前时间段(半夜、早上、凌晨、上午、中午等)
```
    fun getAmOrPm(h: String): String
```

- 获取 `date2` 比 `date1` 多的天数
```
fun differentDays(date1: Date, date2: Date): Int
```