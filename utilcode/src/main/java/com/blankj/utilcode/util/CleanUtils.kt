package com.blankj.utilcode.util

import android.os.Environment

import java.io.File

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : 清除相关工具类
</pre> *
 */
object CleanUtils {

    /**
     * 清除内部缓存
     *
     * /data/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalCache(): Boolean {
        return deleteFilesInDir(Utils.app.cacheDir)
    }

    /**
     * 清除内部文件
     *
     * /data/data/com.xxx.xxx/files
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalFiles(): Boolean {
        return deleteFilesInDir(Utils.app.filesDir)
    }

    /**
     * 清除内部数据库
     *
     * /data/data/com.xxx.xxx/databases
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalDbs(): Boolean {
        return deleteFilesInDir(File(Utils.app.filesDir.parent, "databases"))
    }

    /**
     * 根据名称清除数据库
     *
     * /data/data/com.xxx.xxx/databases/dbName
     *
     * @param dbName 数据库名称
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalDbByName(dbName: String): Boolean {
        return Utils.app.deleteDatabase(dbName)
    }

    /**
     * 清除内部 SP
     *
     * /data/data/com.xxx.xxx/shared_prefs
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalSp(): Boolean {
        return deleteFilesInDir(File(Utils.app.filesDir.parent, "shared_prefs"))
    }

    /**
     * 清除外部缓存
     *
     * /storage/emulated/0/android/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanExternalCache(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && deleteFilesInDir(Utils.app.externalCacheDir)
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanCustomCache(dirPath: String): Boolean {
        return deleteFilesInDir(dirPath)
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanCustomCache(dir: File): Boolean {
        return deleteFilesInDir(dir)
    }

    fun deleteFilesInDir(dirPath: String): Boolean {
        return deleteFilesInDir(getFileByPath(dirPath))
    }

    private fun deleteFilesInDir(dir: File?): Boolean {
        if (dir == null) return false
        // 目录不存在返回 true
        if (!dir.exists()) return true
        // 不是目录返回 false
        if (!dir.isDirectory) return false
        // 现在文件存在且是文件夹
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
        return true
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir == null) return false
        // 目录不存在返回 true
        if (!dir.exists()) return true
        // 不是目录返回 false
        if (!dir.isDirectory) return false
        // 现在文件存在且是文件夹
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
        return dir.delete()
    }

    private fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}
