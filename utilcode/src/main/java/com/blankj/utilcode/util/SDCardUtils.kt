package com.blankj.utilcode.util

import android.content.Context
import android.os.storage.StorageManager
import java.lang.reflect.Array
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/11
 * desc  : SD 卡相关工具类
</pre> *
 */
object SDCardUtils {


    /**
     * 判断 SD 卡是否可用
     *
     * @return true : 可用<br></br>false : 不可用
     */
    val isSDCardEnable: Boolean
        get() = !sdCardPaths.isEmpty()

    /**
     * 获取 SD 卡路径
     *
     * @param removable true : 外置 SD 卡<br></br>false : 内置 SD 卡
     * @return SD 卡路径
     */
    fun getSDCardPaths(removable: Boolean): List<String> {
        val paths = ArrayList<String>()
        val sm = Utils.app.getSystemService(Context.STORAGE_SERVICE) as? StorageManager
        try {
            val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
            val getVolumeList = StorageManager::class.java.getMethod("getVolumeList")
            val getPath = storageVolumeClazz.getMethod("getPath")
            val isRemovable = storageVolumeClazz.getMethod("isRemovable")
            val result = getVolumeList.invoke(sm)
            val length = Array.getLength(result)
            for (i in 0 until length) {
                val storageVolumeElement = Array.get(result, i)
                val path = getPath.invoke(storageVolumeElement) as String
                val res = isRemovable.invoke(storageVolumeElement) as Boolean
                if (removable == res) {
                    paths.add(path)
                }
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return paths
    }

    /**
     * 获取 SD 卡路径
     *
     * @return SD 卡路径
     */
    val sdCardPaths: List<String>
        get() {
            val storageManager = Utils.app
                    .getSystemService(Context.STORAGE_SERVICE) as? StorageManager
            var paths: List<String> = ArrayList()
            try {
                val getVolumePathsMethod = StorageManager::class.java.getMethod("getVolumePaths")
                getVolumePathsMethod.isAccessible = true
                val invoke = getVolumePathsMethod.invoke(storageManager) as kotlin.Array<String>?
                if (invoke != null) {
                    paths = listOf(*invoke)
                }
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

            return paths
        }
}

