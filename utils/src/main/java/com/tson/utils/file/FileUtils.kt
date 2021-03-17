package com.tson.utils.file

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import java.io.*

/**
 *  Date 2021/3/17 7:47 PM
 *
 * @author Tson
 */
class FileUtils {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { FileUtils() }
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context the context
     * @param uri     the uri
     * @return the real file path from uri
     */
    fun getRealFilePathFromUri(context: Context, uri: Uri): String {
        val scheme = uri.scheme
        var data: String = ""
        if (scheme == null) {
            data = uri.path ?: ""
        } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
            data = uri.path ?: ""
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
            val cursor = context.contentResolver.query(
                uri, arrayOf(
                    MediaStore
                        .Images.ImageColumns.DATA
                ), null, null, null
            )
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * 检查文件是否存在
     *
     * @param dirPath the dir path
     * @return the string
     */
    fun checkDirPath(dirPath: String): String {
        if (TextUtils.isEmpty(dirPath)) {
            return ""
        }
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dirPath
    }

    /**
     * 检测SDCard是否可用
     *
     * @return boolean
     */
    fun checkSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 文件夹路径转file，没有就创建
     *
     * @param path the path
     * @return directory directory
     */
    fun getDirectory(path: String): File {
        val appDir = File(path)
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        return appDir
    }

    /**
     * 递归删除文件夹
     *
     * @param p 路径
     * @return boolean
     */
    fun deleteDirectory(p: String): Boolean {
        var sPath = p
        var flag = false
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
        if (files.isNullOrEmpty()) {
            return true
        }
        for (i in files.indices) {
            // 删除子文件
            if (files[i].isFile) {
                flag = deleteFile(files[i].absolutePath)
                if (!flag)
                    break
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].absolutePath)
                if (!flag)
                    break
            }
        }
        if (!flag)
            return false
        // 删除当前目录
        return dirFile.delete()
    }

    /**
     * 创建文件父级路径
     *
     * @param file the file
     * @throws Exception the exception
     */
    @Throws(Exception::class)
    fun createParentFolder(file: File) {
        if (file.parentFile?.exists() != true) {
            if (file.parentFile?.mkdirs() != true) {
                throw Exception("create parent directory failure!")
            }
        }
    }

    /**
     * 创建文件
     *
     * @param file the file
     * @return the file
     * @throws Exception the exception
     */
    @Throws(Exception::class)
    fun createFile(file: File): File {
        createParentFolder(file)
        if (!file.createNewFile()) {
            throw Exception("create file failure!")
        }
        return file
    }

    /**
     * 创建文件
     *
     * @param p the path
     * @return the file
     * @throws Exception the exception
     */
    @Throws(Exception::class)
    fun createFile(p: String): File {
        var path = p
        path = separatorReplace(path)
        val file = File(path)
        if (file.isFile) {
            return file
        } else if (file.isDirectory) {
            deleteFolder(path)
        }
        return createFile(file)
    }

    /**
     * 获取文件夹
     *
     * @param p the path
     * @return the folder
     * @throws FileNotFoundException the file not found exception
     */
    @Throws(FileNotFoundException::class)
    fun getFolder(p: String): File {
        var path = p
        path = separatorReplace(path)
        val folder = File(path)
        if (!folder.isDirectory) {
            throw FileNotFoundException("folder not found!")
        }
        return folder
    }

    /**
     * 删除文件夹
     *
     * @param p the path
     * @throws Exception the exception
     */
    @Throws(Exception::class)
    fun deleteFolder(p: String) {
        var path = p
        path = separatorReplace(path)
        val folder = getFolder(path)
        val files = folder.listFiles()
        if (files.isNullOrEmpty()) {
            return
        }
        for (file in files) {
            if (file.isDirectory) {
                deleteFolder(file.absolutePath)
            } else if (file.isFile) {
                deleteFile(file.absolutePath)
            }
        }
        folder.delete()
    }

    /**
     * 删除文件
     *
     * @param sPath 路径
     * @return boolean
     */
    fun deleteFile(sPath: String): Boolean {
        if (sPath.isEmpty()) {
            return false
        }
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
     * 格式化路径
     */
    fun separatorReplace(path: String): String {
        return path.replace("\\", "/")
    }

    /**
     * 创建文件夹
     */
    fun createFolder(p: String) {
        var path = p
        path = separatorReplace(path)
        val folder = File(path)
        if (folder.isDirectory) {
            return
        } else if (folder.isFile) {
            deleteFile(path)
        }
        folder.mkdirs()
    }

    /**
     * 将流写入文件
     *
     * @param path the path
     * @param in   the in
     * @return 1 : 写入成功 0: 写入失败
     */
    fun writeFile(path: String, `in`: InputStream?): Int {
        try {
            if (`in` == null)
                return 0
            val f = File(path)
            if (f.exists()) {
                f.delete()
            }
            if (f.createNewFile()) {
                val utput = FileOutputStream(f)
                val buffer = ByteArray(1024)
                var count: Int
                while ((`in`.read(buffer).also { count = it }) != -1) {
                    utput.write(buffer, 0, count)
                    utput.flush()
                }
                utput.close()
                `in`.close()
            } else {
                return 0
            }
        } catch (e: Exception) {
            return 0
        }
        return 1
    }

    /**
     * 保存bitmap为图片
     *
     * @param path   生成图片的路径（例：/a/b/c/d.jpg）
     * @param bitmap 需要生成图片的位图
     */
    fun saveBitmap(path: String, bitmap: Bitmap) {
        try {
            val f = File(path)
            if (f.exists())
                f.delete()
            f.createNewFile()
            val fOut: FileOutputStream?
            fOut = FileOutputStream(f)
            val bos = BufferedOutputStream(fOut)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取文件大小
     *
     * @param file 文件路径
     * @return 文件大小
     */
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            if (fileList.isNullOrEmpty()) {
                return size
            }
            for (i in fileList.indices) {
                // 如果下面还有文件
                size += if (fileList[i].isDirectory) {
                    getFolderSize(fileList[i])
                } else {
                    fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 复制文件
     *
     * @param oldPath 需要被复制文件的路径  如   D:/a/b/c.txt
     * @param newPath 需要将文件复制过来的目标路径 如  C:/e/f/c.txt
     */
    fun copyFile(oldPath: String, newPath: String) {
        try {
            var byteSum = 0
            var byteRead = 0
            val oldFile = File(oldPath)
            //文件存在时
            if (oldFile.exists()) {
                //读入原文件
                val inStream = FileInputStream(oldPath)
                val fs = FileOutputStream(newPath)
                val buffer = ByteArray(1444)
                val length: Int
                while ((inStream.read(buffer).also { byteRead = it }) != -1) {
                    //字节数 文件大小
                    byteSum += byteRead
                    println(byteSum)
                    fs.write(buffer, 0, byteRead)
                }
                inStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}