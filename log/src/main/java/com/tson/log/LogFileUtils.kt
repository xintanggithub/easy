package com.tson.log


import android.annotation.SuppressLint
import android.os.Environment
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created tangxin
 * Time 2019/3/13 11:04 AMs
 *
 * @author tangxin
 */
internal class LogFileUtils {

    companion object {
        private val SDCard = Environment.getExternalStorageDirectory().absolutePath

        /**
         * 秒
         */
        private val SECOND: Long = 1000

        /**
         * 分
         */
        private val MINUTE = SECOND * 60

        /**
         * 小时
         */
        private val HOUR = MINUTE * 60

        /**
         * 天
         */
        private val DAY = HOUR * 24

        /**
         * 周
         */
        private val WEEK = DAY * 7

        /**
         * 1MB字节数
         */
        private val MB_SIZE: Long = 1048576

        /**
         * 返回数据目录
         */
        val dataPath: String
            @SuppressLint("SdCardPath")
            get() = LogUtils.getCtx().filesDir.path + "/" + fileName

        private val fileName: String
            get() = LogUtils.getCtx().packageName + "/log"

        /**
         * 获取日志文件大小
         *
         * @return 日志文件大小，单位：字节
         */
        val logFolderSize: Long
            get() {
                val path = dataPath
                val file = File(path)
                return getFolderSize(file)
            }

        /**
         * 获取日志文件大小
         *
         * @return 日志文件大小，自动换算带单位
         */
        val logFolderFormatSize: String
            get() = formatFileSize(logFolderSize)

        /**
         * 将字符串写入到文本文件中
         */
        internal fun writeTxtToFile(str: String) {
            val file = makeFilePath()
            // 每次写入时，都换行写
            val strContent = str + "\r\n"
            try {
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
                val raf = RandomAccessFile(file, "rwd")
                raf.seek(file.length())
                raf.write(strContent.toByteArray())
                raf.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 生成文件
         *
         *
         * 目录结构：
         *
         *
         * ----- rootFile (不同的secret，目录不一样)
         * --------- log
         * -------------- 2019-03-13  (跟随日期变动)
         * ------------------- 1  (按数量递增)
         */
        private fun makeFilePath(): File {
            //日志根目录
            val rootPath = dataPath
            //生成子目录
            val filePath = rootPath + "/" + stampToDate(System.currentTimeMillis())
            val filePathFile = File(filePath)
            //判断目录是否存在，不存在就创建
            if (!filePathFile.exists()) {
                filePathFile.mkdirs()
            }
            //生成文件名 默认从1开始
            var fileName = "1.txt"
            val oldFile = File(filePath)
            //得到该目录下所有文件名
            val fileNames = oldFile.listFiles()
            //如果文件名列表不为空，说明已存在文件，判断文件是否大于10MB，如果不存在，从1开始创建
            if (null != fileNames) {
                val length = fileNames.size
                if (length > 0) {
                    //获得最近的一个文件名
                    val oldFileName = length.toString()
                    val tempFilePath = "$filePath/$oldFileName.txt"
                    val tempFile = File(tempFilePath)
                    //判断最近的文件名是否存在（可能出现了名字错误等）
                    //如果不存在,创建并使用
                    fileName = if (!tempFile.exists()) {
                        tempFile.mkdirs()
                        "$oldFileName.txt"
                    } else {
                        //如果存在，判断大小
                        val fileSize = getFileSize(tempFile)
                        //如果文件大于10MB，则需要创建新的文件
                        if (fileSize >= MB_SIZE * 10) {
                            (length + 1).toString() + ".txt"
                        } else {
                            //如果小于10MB，则可以继续向该文件写入内容
                            "$oldFileName.txt"
                        }
                    }
                }
            }
            val file = File("$filePath/$fileName")
            if (!file.exists()) {
                try {
                    val createResult = file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return file
        }

        /**
         * 检测SDCard是否可用
         *
         * @return 可以true
         */
        private fun checkSDCard(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 删除文件
         *
         * @param sPath 路径
         */
        private fun deleteFile(sPath: String): Boolean {
            var flag = false
            val file = File(sPath)
            // 路径为文件且不为空则进行删除
            if (file.isFile && file.exists()) {
                file.delete()
                flag = true
            }
            return flag
        }

        /**
         * 删除文件夹
         *
         * @param sPaths 路径
         */
        private fun deleteDirectory(sPaths: String): Boolean {
            var sPath = sPaths
            var flag: Boolean
            // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
            if (!sPath.endsWith(File.separator)) {
                sPath += File.separator
            }
            val dirFile = File(sPath)
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if (!dirFile.exists() || !dirFile.isDirectory) {
                return false
            }
            flag = true
            // 删除文件夹下的所有文件(包括子目录)
            val files = dirFile.listFiles()
            for (file in files) {
                // 删除子文件
                if (file.isFile) {
                    flag = deleteFile(file.absolutePath)
                    if (!flag) {
                        break
                    }
                } // 删除子目录
                else {
                    flag = deleteDirectory(file.absolutePath)
                    if (!flag) {
                        break
                    }
                }
            }
            return if (!flag) {
                false
            } else dirFile.delete()
            // 删除当前目录
        }

        /**
         * 将时间戳转换为时间
         */
        private fun stampToDate(s: Long): String {
            return stampToDate(s, "yyyy-MM-dd")
        }

        /**
         * 将时间戳转换为时间
         * yyyy-MM-dd HH:mm:ss SSS
         */
        internal fun stampToDate(s: Long, type: String): String {
            val res: String

            @SuppressLint("SimpleDateFormat")
            val simpleDateFormat = SimpleDateFormat(type)
            val date = Date(s)
            res = simpleDateFormat.format(date)
            return res
        }

        /**
         * 获取指定文件大小
         */
        fun getFileSize(file: File): Long {
            var size: Long = 0
            try {
                if (file.exists()) {
                    val fis = FileInputStream(file)
                    size = fis.available().toLong()
                } else {
                    file.createNewFile()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return size
        }

        /**
         * 获取文件夹大小
         *
         * @param file File实例
         * @return long
         */
        fun getFolderSize(file: File): Long {
            var size: Long = 0
            try {
                val fileList = file.listFiles()
                for (aFileList in fileList) {
                    if (aFileList.isDirectory) {
                        size += getFolderSize(aFileList)
                    } else {
                        size += aFileList.length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return size
        }

        /**
         *  * 转换文件大小
         *  * @param fileS
         *  * @return
         *  
         */
        fun formatFileSize(size: Long): String {
            val df = DecimalFormat("#.00")
            var fileSizeString = ""
            val wrongSize = "0B"
            if (size == 0L) {
                return wrongSize
            }
            fileSizeString = if (size < 1024) {
                df.format(size.toDouble()) + "B"
            } else if (size < 1048576) {
                df.format(size.toDouble() / 1024) + "KB"
            } else if (size < 1073741824) {
                df.format(size.toDouble() / 1048576) + "MB"
            } else {
                df.format(size.toDouble() / 1073741824) + "GB"
            }
            return fileSizeString
        }

        /**
         * 删除所有日志文件
         */
        fun removeLogCache(): Boolean {
            return deleteDirectory(dataPath)
        }

        /**
         * 读取SD卡文件内容
         *
         * @param path 路径
         * @return 文件内容
         */
        fun readFormSdcard(path: String): String {
            if (!File(path).exists()) {
                return ""
            } else {
                try {
                    val fis = FileInputStream(path)
                    val br = BufferedReader(
                        InputStreamReader(
                            fis
                        )
                    )
                    val sb = StringBuilder("")
                    var line = ""
                    while ((br.readLine().also {
                            if (null !== it && it.isNotEmpty()) {
                                line = it
                            }
                        }) != null) {
                        sb.append(line)
                    }
                    return sb.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return ""
            }
        }

        fun saveToFile(path: String, content: String) {
            val file = File(path)
            var out: BufferedWriter? = null
            try {
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
                /*
                输出流的构造参数1：可以是File对象  也可以是文件路径
                输出流的构造参数2：默认为False=>覆盖内容； true=>追加内容
                 */
                out = BufferedWriter(OutputStreamWriter(FileOutputStream(file, false)))
                out.run {
                    newLine()
                    write(content)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (out != null) {
                    try {
                        out.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        fun toHtml(str: String): String {
            return "<html><head><body>$str</body></head></html>"
        }

        /**
         * 获取路径下的所有文件列表
         */
        fun getFilesByBase(base: String): MutableList<File> {
            val resultList = mutableListOf<File>()
            val rootFile = File(base)
            if (rootFile.exists()) {
                val files = rootFile.listFiles()
                if (null != files && files.isNotEmpty()) {
                    resultList.addAll(files.toMutableList())
                }
            }
            return resultList
        }

    }
}
