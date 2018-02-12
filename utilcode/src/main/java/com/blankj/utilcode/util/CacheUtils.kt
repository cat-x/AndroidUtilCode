package com.blankj.utilcode.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.os.Process
import android.support.v4.util.SimpleArrayMap
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/24
 * desc  : 缓存相关工具类
</pre> *
 */
class CacheUtils private constructor(cacheDir: File, maxSize: Long, maxCount: Int) {
    private val mCacheManager: CacheManager

    /**
     * 获取缓存大小
     *
     * 单位：字节
     *
     * 调用了 Thread.join()，需异步调用，否则可能主线程会卡顿
     *
     * @return 缓存大小
     */
    val cacheSize: Long
        get() = mCacheManager.getCacheSize()

    /**
     * 获取缓存个数
     *
     * 调用了 Thread.join()，需异步调用，否则可能主线程会卡顿
     *
     * @return 缓存个数
     */
    val cacheCount: Int
        get() = mCacheManager.getCacheCount()

    init {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw RuntimeException("can't make dirs in " + cacheDir.absolutePath)
        }
        mCacheManager = CacheManager(cacheDir, maxSize, maxCount)
    }

    /**
     * 缓存中写入字节数组
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String, value: ByteArray, saveTime: Int = -1) {
        var value = value
        if (value.size <= 0) return
        if (saveTime >= 0) value = CacheHelper.newByteArrayWithTime(saveTime, value)
        val file = mCacheManager.getFileBeforePut(key)
        CacheHelper.writeFileFromBytes(file, value)
        mCacheManager.updateModify(file)
        mCacheManager.put(file)
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getBytes(key: String, defaultValue: ByteArray? = null): ByteArray? {
        val file = mCacheManager.getFileIfExists(key) ?: return defaultValue
        val data = CacheHelper.readFile2Bytes(file) ?: return defaultValue
        if (CacheHelper.isDue(data)) {
            mCacheManager.removeByKey(key)
            return defaultValue
        }
        mCacheManager.updateModify(file)
        return CacheHelper.getDataWithoutDueTime(data)
    }

    /**
     * 缓存中写入 String
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String, value: String, saveTime: Int = -1) {
        put(key, CacheHelper.string2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getString(key: String, defaultValue: String? = null): String? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2String(bytes)
    }

    /**
     * 缓存中写入 JSONObject
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String,
            value: JSONObject,
            saveTime: Int = -1) {
        put(key, CacheHelper.jsonObject2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getJSONObject(key: String, defaultValue: JSONObject? = null): JSONObject? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2JSONObject(bytes)
    }

    /**
     * 缓存中写入 JSONArray
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String, value: JSONArray, saveTime: Int = -1) {
        put(key, CacheHelper.jsonArray2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getJSONArray(key: String, defaultValue: JSONArray? = null): JSONArray? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2JSONArray(bytes)
    }

    /**
     * 缓存中写入 Bitmap
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String, value: Bitmap, saveTime: Int = -1) {
        put(key, CacheHelper.bitmap2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getBitmap(key: String, defaultValue: Bitmap? = null): Bitmap? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2Bitmap(bytes)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Drawable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入 Drawable
     *
     * @param key   键
     * @param value 值
     */
    fun put(key: String, value: Drawable) {
        put(key, CacheHelper.drawable2Bytes(value)!!)
    }

    /**
     * 缓存中写入 Drawable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    fun put(key: String, value: Drawable, saveTime: Int) {
        put(key, CacheHelper.drawable2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getDrawable(key: String, defaultValue: Drawable? = null): Drawable? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2Drawable(bytes)
    }

    /**
     * 缓存中写入 Parcelable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String,
            value: Parcelable,
            saveTime: Int = -1) {
        put(key, CacheHelper.parcelable2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key     键
     * @param creator 建造器
     * @return 存在且没过期返回对应值，否则返回`null`
     */
    fun <T> getParcelable(key: String,
                          creator: Parcelable.Creator<T>): T? {
        return getParcelable(key, creator, null)
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key          键
     * @param creator      建造器
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    fun <T> getParcelable(key: String,
                          creator: Parcelable.Creator<T>,
                          defaultValue: T?): T? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2Parcelable(bytes, creator)
    }

    /**
     * 缓存中写入 Serializable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    @JvmOverloads
    fun put(key: String,
            value: Serializable,
            saveTime: Int = -1) {
        put(key, CacheHelper.serializable2Bytes(value)!!, saveTime)
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在且没过期返回对应值，否则返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getSerializable(key: String, defaultValue: Any? = null): Any? {
        val bytes = getBytes(key) ?: return defaultValue
        return CacheHelper.bytes2Object(getBytes(key))
    }

    /**
     * 根据键值移除缓存
     *
     * @param key 键
     * @return `true`: 移除成功<br></br>`false`: 移除失败
     */
    fun remove(key: String): Boolean {
        return mCacheManager.removeByKey(key)
    }

    /**
     * 清除所有缓存
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun clear(): Boolean {
        return mCacheManager.clear()
    }

    inner class CacheManager constructor(val cacheDir: File, val sizeLimit: Long, val countLimit: Int) {
        private val cacheSize: AtomicLong
        private val cacheCount: AtomicInteger
        private val lastUsageDates = Collections.synchronizedMap(HashMap<File, Long>())
        private val mThread: Thread

        init {
            cacheSize = AtomicLong()
            cacheCount = AtomicInteger()
            mThread = Thread(Runnable {
                var size = 0
                var count = 0
                val cachedFiles = cacheDir.listFiles()
                if (cachedFiles != null) {
                    for (cachedFile in cachedFiles) {
                        size += cachedFile.length().toInt()
                        count += 1
                        lastUsageDates[cachedFile] = cachedFile.lastModified()
                    }
                    cacheSize.getAndAdd(size.toLong())
                    cacheCount.getAndAdd(count)
                }
            })
            mThread.start()
        }

        fun getCacheSize(): Long {
            try {
                mThread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return cacheSize.get()
        }

        fun getCacheCount(): Int {
            try {
                mThread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return cacheCount.get()
        }

        fun getFileBeforePut(key: String): File {
            val file = File(cacheDir, key.hashCode().toString())
            if (file.exists()) {
                cacheCount.addAndGet(-1)
                cacheSize.addAndGet(-file.length())
            }
            return file
        }

        fun getFileIfExists(key: String): File? {
            val file = File(cacheDir, key.hashCode().toString())
            return if (!file.exists()) null else file
        }

        fun put(file: File) {
            cacheCount.addAndGet(1)
            cacheSize.addAndGet(file.length())
            while (cacheCount.get() > countLimit || cacheSize.get() > sizeLimit) {
                cacheSize.addAndGet(-removeOldest())
                cacheCount.addAndGet(-1)
            }
        }

        fun updateModify(file: File) {
            val millis = System.currentTimeMillis()
            file.setLastModified(millis)
            lastUsageDates[file] = millis
        }

        fun removeByKey(key: String): Boolean {
            val file = getFileIfExists(key) ?: return true
            if (!file.delete()) return false
            cacheSize.addAndGet(-file.length())
            cacheCount.addAndGet(-1)
            lastUsageDates.remove(file)
            return true
        }

        fun clear(): Boolean {
            val files = cacheDir.listFiles()
            if (files == null || files.size <= 0) return true
            var flag = true
            for (file in files) {
                if (!file.delete()) {
                    flag = false
                    continue
                }
                cacheSize.addAndGet(-file.length())
                cacheCount.addAndGet(-1)
                lastUsageDates.remove(file)
            }
            if (flag) {
                lastUsageDates.clear()
                cacheSize.set(0)
                cacheCount.set(0)
            }
            return flag
        }

        /**
         * 移除旧的文件
         *
         * @return 移除的字节数
         */
        fun removeOldest(): Long {
            if (lastUsageDates.isEmpty()) return 0
            var oldestUsage: Long = java.lang.Long.MAX_VALUE
            var oldestFile: File? = null
            val entries = lastUsageDates.entries
            synchronized(lastUsageDates) {
                for ((key, lastValueUsage) in entries) {
                    if (lastValueUsage < oldestUsage) {
                        oldestUsage = lastValueUsage
                        oldestFile = key
                    }
                }
            }
            if (oldestFile == null) return 0
            val fileSize = oldestFile!!.length()
            if (oldestFile!!.delete()) {
                lastUsageDates.remove(oldestFile)
                return fileSize
            }
            return 0
        }
    }

    sealed class CacheHelper {
        companion object {
            internal val timeInfoLen = 14

            fun newByteArrayWithTime(second: Int, data: ByteArray): ByteArray {
                val time = createDueTime(second).toByteArray()
                val content = ByteArray(time.size + data.size)
                System.arraycopy(time, 0, content, 0, time.size)
                System.arraycopy(data, 0, content, time.size, data.size)
                return content
            }

            /**
             * 创建过期时间
             *
             * @param second 秒
             * @return _$millis$_
             */
            fun createDueTime(second: Int): String {
                return String.format(
                        Locale.getDefault(), "_$%010d$" + "_",
                        System.currentTimeMillis() / 1000 + second
                )
            }

            fun isDue(data: ByteArray): Boolean {
                val millis = getDueTime(data)
                return millis != -1L && System.currentTimeMillis() > millis
            }

            fun getDueTime(data: ByteArray): Long {
                if (hasTimeInfo(data)) {
                    val millis = String(copyOfRange(data, 2, 12))
                    try {
                        return java.lang.Long.parseLong(millis) * 1000
                    } catch (e: NumberFormatException) {
                        return -1
                    }

                }
                return -1
            }

            fun getDataWithoutDueTime(data: ByteArray): ByteArray {
                return if (hasTimeInfo(data)) {
                    copyOfRange(data, timeInfoLen, data.size)
                } else data
            }

            fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
                val newLength = to - from
                if (newLength < 0) throw IllegalArgumentException(from.toString() + " > " + to)
                val copy = ByteArray(newLength)
                System.arraycopy(original, from, copy, 0, Math.min(original.size - from, newLength))
                return copy
            }

            fun hasTimeInfo(data: ByteArray?): Boolean {
                return (data != null
                        && data.size >= timeInfoLen
                        && data[0] == '_'.toByte()
                        && data[1] == '$'.toByte()
                        && data[12] == '$'.toByte()
                        && data[13] == '_'.toByte())
            }

            fun writeFileFromBytes(file: File, bytes: ByteArray) {
                var fc: FileChannel? = null
                try {
                    fc = FileOutputStream(file, false).channel
                    fc!!.write(ByteBuffer.wrap(bytes))
                    fc.force(true)
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    CloseUtils.closeIO(fc)
                }
            }

            fun readFile2Bytes(file: File): ByteArray? {
                var fc: FileChannel? = null
                try {
                    fc = RandomAccessFile(file, "r").channel
                    val size = fc!!.size().toInt()
                    val mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size.toLong()).load()
                    val data = ByteArray(size)
                    mbb.get(data, 0, size)
                    return data
                } catch (e: IOException) {
                    e.printStackTrace()
                    return null
                } finally {
                    CloseUtils.closeIO(fc)
                }
            }

            fun string2Bytes(string: String?): ByteArray? {
                return string?.toByteArray()
            }

            fun bytes2String(bytes: ByteArray?): String? {
                return if (bytes == null) null else String(bytes)
            }

            fun jsonObject2Bytes(jsonObject: JSONObject?): ByteArray? {
                return jsonObject?.toString()?.toByteArray()
            }

            fun bytes2JSONObject(bytes: ByteArray?): JSONObject? {
                if (bytes == null) return null
                try {
                    return JSONObject(String(bytes))
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }

            fun jsonArray2Bytes(jsonArray: JSONArray?): ByteArray? {
                return jsonArray?.toString()?.toByteArray()
            }

            fun bytes2JSONArray(bytes: ByteArray?): JSONArray? {
                if (bytes == null) return null
                try {
                    return JSONArray(String(bytes))
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }

            fun parcelable2Bytes(parcelable: Parcelable?): ByteArray? {
                if (parcelable == null) return null
                val parcel = Parcel.obtain()
                parcelable.writeToParcel(parcel, 0)
                val bytes = parcel.marshall()
                parcel.recycle()
                return bytes
            }

            fun <T> bytes2Parcelable(bytes: ByteArray?,
                                     creator: Parcelable.Creator<T>): T? {
                if (bytes == null) return null
                val parcel = Parcel.obtain()
                parcel.unmarshall(bytes, 0, bytes.size)
                parcel.setDataPosition(0)
                val result = creator.createFromParcel(parcel)
                parcel.recycle()
                return result
            }

            fun serializable2Bytes(serializable: Serializable?): ByteArray? {
                if (serializable == null) return null
                val baos: ByteArrayOutputStream
                var oos: ObjectOutputStream? = null
                try {
                    baos = ByteArrayOutputStream()
                    oos = ObjectOutputStream(baos)
                    oos.writeObject(serializable)
                    return baos.toByteArray()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    CloseUtils.closeIO(oos)
                }
            }

            fun bytes2Object(bytes: ByteArray?): Any? {
                if (bytes == null) return null
                var ois: ObjectInputStream? = null
                try {
                    ois = ObjectInputStream(ByteArrayInputStream(bytes))
                    return ois.readObject()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    CloseUtils.closeIO(ois)
                }
            }

            fun bitmap2Bytes(bitmap: Bitmap?): ByteArray? {
                if (bitmap == null) return null
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                return baos.toByteArray()
            }

            fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
                return if (bytes == null || bytes.size == 0)
                    null
                else
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }

            fun drawable2Bytes(drawable: Drawable?): ByteArray? {
                return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable))
            }

            fun bytes2Drawable(bytes: ByteArray?): Drawable? {
                return if (bytes == null) null else bitmap2Drawable(bytes2Bitmap(bytes))
            }

            fun drawable2Bitmap(drawable: Drawable): Bitmap {
                if (drawable is BitmapDrawable) {
                    if (drawable.bitmap != null) {
                        return drawable.bitmap
                    }
                }
                val bitmap: Bitmap
                if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                    bitmap = Bitmap.createBitmap(
                            1,
                            1,
                            if (drawable.opacity != PixelFormat.OPAQUE)
                                Bitmap.Config.ARGB_8888
                            else
                                Bitmap.Config.RGB_565
                    )
                } else {
                    bitmap = Bitmap.createBitmap(
                            drawable.intrinsicWidth,
                            drawable.intrinsicHeight,
                            if (drawable.opacity != PixelFormat.OPAQUE)
                                Bitmap.Config.ARGB_8888
                            else
                                Bitmap.Config.RGB_565
                    )
                }
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                return bitmap
            }

            fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
                return if (bitmap == null)
                    null
                else
                    BitmapDrawable(Utils.app.getResources(), bitmap)
            }
        }
    }

    companion object {

        private val DEFAULT_MAX_SIZE = java.lang.Long.MAX_VALUE
        private val DEFAULT_MAX_COUNT = Integer.MAX_VALUE

        val SEC = 1
        val MIN = 60
        val HOUR = 3600
        val DAY = 86400

        private val CACHE_MAP = SimpleArrayMap<String, CacheUtils>()

        /**
         * 获取缓存实例
         *
         * 在 /data/data/com.xxx.xxx/cache/cacheUtils 目录
         *
         * 缓存尺寸不限
         *
         * 缓存个数不限
         *
         * @return [CacheUtils]
         */
        val instance: CacheUtils
            get() = getInstance("", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT)

        /**
         * 获取缓存实例
         *
         * 在 /data/data/com.xxx.xxx/cache/cacheUtils目录
         *
         * @param maxSize  最大缓存尺寸，单位字节
         * @param maxCount 最大缓存个数
         * @return [CacheUtils]
         */
        fun getInstance(maxSize: Long, maxCount: Int): CacheUtils {
            return getInstance("", maxSize, maxCount)
        }

        /**
         * 获取缓存实例
         *
         * 在 /data/data/com.xxx.xxx/cache/cacheName 目录
         *
         * @param cacheName 缓存目录名
         * @param maxSize   最大缓存尺寸，单位字节
         * @param maxCount  最大缓存个数
         * @return [CacheUtils]
         */
        @JvmOverloads
        fun getInstance(cacheName: String, maxSize: Long = DEFAULT_MAX_SIZE, maxCount: Int = DEFAULT_MAX_COUNT): CacheUtils {
            var cacheName = cacheName
            if (isSpace(cacheName)) cacheName = "cacheUtils"
            val file = File(Utils.app.getCacheDir(), cacheName)
            return getInstance(file, maxSize, maxCount)
        }

        /**
         * 获取缓存实例
         *
         * 在 cacheDir 目录
         *
         * @param cacheDir 缓存目录
         * @param maxSize  最大缓存尺寸，单位字节
         * @param maxCount 最大缓存个数
         * @return [CacheUtils]
         */
        @JvmOverloads
        fun getInstance(cacheDir: File,
                        maxSize: Long = DEFAULT_MAX_SIZE,
                        maxCount: Int = DEFAULT_MAX_COUNT): CacheUtils {
            val cacheKey = cacheDir.absoluteFile.toString() + "_" + Process.myPid()
            var cache: CacheUtils? = CACHE_MAP.get(cacheKey)
            if (cache == null) {
                cache = CacheUtils(cacheDir, maxSize, maxCount)
                CACHE_MAP.put(cacheKey, cache)
            }
            return cache
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
}
/**
 * 获取缓存实例
 *
 * 在 /data/data/com.xxx.xxx/cache/cacheName 目录
 *
 * 缓存尺寸不限
 *
 * 缓存个数不限
 *
 * @param cacheName 缓存目录名
 * @return [CacheUtils]
 */
/**
 * 获取缓存实例
 *
 * 在 cacheDir 目录
 *
 * 缓存尺寸不限
 *
 * 缓存个数不限
 *
 * @param cacheDir 缓存目录
 * @return [CacheUtils]
 *////////////////////////////////////////////////////////////////////////////
// bytes 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入字节数组
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取字节数组
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 *////////////////////////////////////////////////////////////////////////////
// String 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 String
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取 String
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 *////////////////////////////////////////////////////////////////////////////
// JSONObject 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 JSONObject
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取 JSONObject
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 *////////////////////////////////////////////////////////////////////////////
// JSONArray 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 JSONArray
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取 JSONArray
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 *////////////////////////////////////////////////////////////////////////////
// Bitmap 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 Bitmap
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取 Bitmap
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 */
/**
 * 缓存中读取 Drawable
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 *////////////////////////////////////////////////////////////////////////////
// Parcelable 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 Parcelable
 *
 * @param key   键
 * @param value 值
 *////////////////////////////////////////////////////////////////////////////
// Serializable 读写
///////////////////////////////////////////////////////////////////////////
/**
 * 缓存中写入 Serializable
 *
 * @param key   键
 * @param value 值
 */
/**
 * 缓存中读取 Serializable
 *
 * @param key 键
 * @return 存在且没过期返回对应值，否则返回`null`
 */
