package com.blankj.utilcode.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Parcel
import android.os.Parcelable
import com.blankj.utilcode.util.TestConfig.FILE_SEP
import com.blankj.utilcode.util.TestConfig.PATH_CACHE
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.io.Serializable
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/26
 * desc  : CacheUtils 单元测试
</pre> *
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CacheUtilsTest {

    private val cache1Path = PATH_CACHE + "cache1" + FILE_SEP
    private val cache2Path = PATH_CACHE + "cache2" + FILE_SEP
    private val cache1File = File(cache1Path)
    private val cache2File = File(cache2Path)

    private var mCacheUtils1: CacheUtils? = null
    private var mCacheUtils2: CacheUtils? = null

    private val mBytes = "CacheUtils".toByteArray()
    private val mString = "CacheUtils"
    private val mJSONObject = JSONObject()
    private val mJSONArray = JSONArray()
    private val mParcelableTest = ParcelableTest("Blankj", "CacheUtils")
    private val mSerializableTest = SerializableTest("Blankj", "CacheUtils")
    private val mBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
    private val mDrawable = BitmapDrawable(Utils.app.resources, mBitmap)

    init {
        try {
            mJSONObject.put("class", "CacheUtils")
            mJSONObject.put("author", "Blankj")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        try {
            mJSONArray.put(0, mJSONObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        if (mCacheUtils1 == null) {
            mCacheUtils1 = CacheUtils.getInstance(cache1File)
            mCacheUtils1!!.put("bytes1", mBytes, 60 * CacheUtils.SEC)
            mCacheUtils1!!.put("string1", mString, 60 * CacheUtils.MIN)
            mCacheUtils1!!.put("jsonObject1", mJSONObject, 24 * CacheUtils.HOUR)
            mCacheUtils1!!.put("jsonArray1", mJSONArray, 365 * CacheUtils.DAY)
            mCacheUtils1!!.put("bitmap1", mBitmap, 60 * CacheUtils.SEC)
            mCacheUtils1!!.put("drawable1", mDrawable, 60 * CacheUtils.SEC)
            mCacheUtils1!!.put("parcelable1", mParcelableTest, 60 * CacheUtils.SEC)
            mCacheUtils1!!.put("serializable1", mSerializableTest, 60 * CacheUtils.SEC)
        }
        if (mCacheUtils2 == null) {
            mCacheUtils2 = CacheUtils.getInstance(cache2File)
            mCacheUtils2!!.put("bytes2", mBytes)
            mCacheUtils2!!.put("string2", mString)
            mCacheUtils2!!.put("jsonObject2", mJSONObject)
            mCacheUtils2!!.put("jsonArray2", mJSONArray)
            mCacheUtils2!!.put("parcelable2", mParcelableTest)
            mCacheUtils2!!.put("serializable2", mSerializableTest)
            mCacheUtils2!!.put("bitmap2", mBitmap)
            mCacheUtils2!!.put("drawable2", mDrawable)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getBytes() {
        assertEquals(mString, String(mCacheUtils1!!.getBytes("bytes1")!!))
        assertEquals(mString, String(mCacheUtils1!!.getBytes("bytes1", null)!!))
        assertNull(mCacheUtils1!!.getBytes("bytes2", null))

        assertEquals(mString, String(mCacheUtils2!!.getBytes("bytes2")!!))
        assertEquals(mString, String(mCacheUtils2!!.getBytes("bytes2", null)!!))
        assertNull(mCacheUtils2!!.getBytes("bytes1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getString() {
        assertEquals(mString, mCacheUtils1!!.getString("string1"))
        assertEquals(mString, mCacheUtils1!!.getString("string1", null))
        assertNull(mCacheUtils1!!.getString("string2", null))

        assertEquals(mString, mCacheUtils2!!.getString("string2"))
        assertEquals(mString, mCacheUtils2!!.getString("string2", null))
        assertNull(mCacheUtils2!!.getString("string1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getJSONObject() {
        assertEquals(mJSONObject.toString(), mCacheUtils1!!.getJSONObject("jsonObject1")!!.toString())
        assertEquals(mJSONObject.toString(), mCacheUtils1!!.getJSONObject("jsonObject1", null)!!.toString())
        assertNull(mCacheUtils1!!.getJSONObject("jsonObject2", null))

        assertEquals(mJSONObject.toString(), mCacheUtils2!!.getJSONObject("jsonObject2")!!.toString())
        assertEquals(mJSONObject.toString(), mCacheUtils2!!.getJSONObject("jsonObject2", null)!!.toString())
        assertNull(mCacheUtils2!!.getJSONObject("jsonObject1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getJSONArray() {
        assertEquals(mJSONArray.toString(), mCacheUtils1!!.getJSONArray("jsonArray1")!!.toString())
        assertEquals(mJSONArray.toString(), mCacheUtils1!!.getJSONArray("jsonArray1", null)!!.toString())
        assertNull(mCacheUtils1!!.getJSONArray("jsonArray2", null))


        assertEquals(mJSONArray.toString(), mCacheUtils2!!.getJSONArray("jsonArray2")!!.toString())
        assertEquals(mJSONArray.toString(), mCacheUtils2!!.getJSONArray("jsonArray2", null)!!.toString())
        assertNull(mCacheUtils2!!.getJSONArray("jsonArray1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getBitmap() {
        val bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100"
        assertTrue(mCacheUtils1!!.getString("bitmap1") == bitmapString)
        assertTrue(mCacheUtils1!!.getString("bitmap1", null) == bitmapString)
        assertNull(mCacheUtils1!!.getString("bitmap2", null))

        assertTrue(mCacheUtils2!!.getString("bitmap2") == bitmapString)
        assertTrue(mCacheUtils2!!.getString("bitmap2", null) == bitmapString)
        assertNull(mCacheUtils2!!.getString("bitmap1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getDrawable() {
        val bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100"
        assertTrue(mCacheUtils1!!.getString("drawable1") == bitmapString)
        assertTrue(mCacheUtils1!!.getString("drawable1", null) == bitmapString)
        assertNull(mCacheUtils1!!.getString("drawable2", null))

        assertTrue(mCacheUtils2!!.getString("drawable2") == bitmapString)
        assertTrue(mCacheUtils2!!.getString("drawable2", null) == bitmapString)
        assertNull(mCacheUtils2!!.getString("drawable1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getParcel() {
        assertTrue(mCacheUtils1!!.getParcelable("parcelable1", ParcelableTest.CREATOR) == mParcelableTest)
        assertTrue(mCacheUtils1!!.getParcelable("parcelable1", ParcelableTest.CREATOR, null) == mParcelableTest)
        assertNull(mCacheUtils1!!.getParcelable("parcelable2", ParcelableTest.CREATOR, null))

        assertTrue(mCacheUtils2!!.getParcelable("parcelable2", ParcelableTest.CREATOR) == mParcelableTest)
        assertTrue(mCacheUtils2!!.getParcelable("parcelable2", ParcelableTest.CREATOR, null) == mParcelableTest)
        assertNull(mCacheUtils2!!.getParcelable("parcelable1", ParcelableTest.CREATOR, null))
    }

    @Test
    @Throws(Exception::class)
    fun getSerializable() {
        assertTrue(mCacheUtils1!!.getSerializable("serializable1") == mSerializableTest)
        assertTrue(mCacheUtils1!!.getSerializable("serializable1", null) == mSerializableTest)
        assertNull(mCacheUtils1!!.getSerializable("parcelable2", null))

        assertTrue(mCacheUtils2!!.getSerializable("serializable2") == mSerializableTest)
        assertTrue(mCacheUtils2!!.getSerializable("serializable2", null) == mSerializableTest)
        assertNull(mCacheUtils2!!.getSerializable("parcelable1", null))
    }

    @Test
    @Throws(Exception::class)
    fun getCacheSize() {
        assertEquals(FileUtils.getDirLength(cache1File), mCacheUtils1!!.cacheSize)

        assertEquals(FileUtils.getDirLength(cache2File), mCacheUtils2!!.cacheSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCacheCount() {
        assertEquals(8, mCacheUtils1!!.cacheCount.toLong())

        assertEquals(8, mCacheUtils2!!.cacheCount.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun remove() {
        assertNotNull(mCacheUtils1!!.getString("string1"))
        mCacheUtils1!!.remove("string1")
        assertNull(mCacheUtils1!!.getString("string1"))

        assertNotNull(mCacheUtils2!!.getString("string2"))
        mCacheUtils2!!.remove("string2")
        assertNull(mCacheUtils2!!.getString("string2"))
    }

    @Test
    @Throws(Exception::class)
    fun clear() {
        assertNotNull(mCacheUtils1!!.getBytes("bytes1"))
        assertNotNull(mCacheUtils1!!.getString("string1"))
        assertNotNull(mCacheUtils1!!.getJSONObject("jsonObject1"))
        assertNotNull(mCacheUtils1!!.getJSONArray("jsonArray1"))
        assertNotNull(mCacheUtils1!!.getString("bitmap1"))
        assertNotNull(mCacheUtils1!!.getString("drawable1"))
        assertNotNull(mCacheUtils1!!.getParcelable("parcelable1", ParcelableTest.CREATOR))
        assertNotNull(mCacheUtils1!!.getSerializable("serializable1"))
        mCacheUtils1!!.clear()
        assertNull(mCacheUtils1!!.getBytes("bytes1"))
        assertNull(mCacheUtils1!!.getString("string1"))
        assertNull(mCacheUtils1!!.getJSONObject("jsonObject1"))
        assertNull(mCacheUtils1!!.getJSONArray("jsonArray1"))
        assertNull(mCacheUtils1!!.getString("bitmap1"))
        assertNull(mCacheUtils1!!.getString("drawable1"))
        assertNull(mCacheUtils1!!.getParcelable("parcelable1", ParcelableTest.CREATOR))
        assertNull(mCacheUtils1!!.getSerializable("serializable1"))


        assertNotNull(mCacheUtils2!!.getBytes("bytes2"))
        assertNotNull(mCacheUtils2!!.getString("string2"))
        assertNotNull(mCacheUtils2!!.getJSONObject("jsonObject2"))
        assertNotNull(mCacheUtils2!!.getJSONArray("jsonArray2"))
        assertNotNull(mCacheUtils2!!.getString("bitmap2"))
        assertNotNull(mCacheUtils2!!.getString("drawable2"))
        assertNotNull(mCacheUtils2!!.getParcelable("parcelable2", ParcelableTest.CREATOR))
        assertNotNull(mCacheUtils2!!.getSerializable("serializable2"))
        mCacheUtils2!!.clear()
        assertNull(mCacheUtils2!!.getBytes("bytes2"))
        assertNull(mCacheUtils2!!.getString("string2"))
        assertNull(mCacheUtils2!!.getJSONObject("jsonObject2"))
        assertNull(mCacheUtils2!!.getJSONArray("jsonArray2"))
        assertNull(mCacheUtils2!!.getString("bitmap2"))
        assertNull(mCacheUtils2!!.getString("drawable2"))
        assertNull(mCacheUtils2!!.getParcelable("parcelable2", ParcelableTest.CREATOR))
        assertNull(mCacheUtils2!!.getSerializable("serializable2"))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mCacheUtils1!!.clear()
        mCacheUtils2!!.clear()
    }


    internal class ParcelableTest : Parcelable {
        var author: String
        var className: String

        constructor(author: String, className: String) {
            this.author = author
            this.className = className
        }

        constructor(`in`: Parcel) {
            author = `in`.readString()
            className = `in`.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(author)
            dest.writeString(className)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun equals(obj: Any?): Boolean {
            return obj is ParcelableTest && obj.author == author && obj.className == className
        }

        companion object {

            val CREATOR: Parcelable.Creator<ParcelableTest> = object : Parcelable.Creator<ParcelableTest> {
                override fun createFromParcel(`in`: Parcel): ParcelableTest {
                    return ParcelableTest(`in`)
                }

                override fun newArray(size: Int): Array<ParcelableTest?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    internal class SerializableTest(var author: String, var className: String) : Serializable {

        override fun equals(obj: Any?): Boolean {
            return (obj is SerializableTest
                    && obj.author == author
                    && obj.className == className)
        }

        companion object {

            private const val serialVersionUID = -5806706668736895024L
        }
    }

    companion object {

        init {
            TestUtils.init()
        }

        private val map: LinkedHashMap<String, String>? = null
    }
}

