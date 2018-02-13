package com.blankj.utilcode.util

import com.blankj.utilcode.util.TestConfig.PATH_TEMP
import com.blankj.utilcode.util.TestConfig.PATH_ZIP
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/10
 * desc  : ZipUtils 单元测试
</pre> *
 */
class ZipUtilsTest {

    private val zipFile = PATH_TEMP + "zipFile.zip"
    private val zipFiles = PATH_TEMP + "zipFiles.zip"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        FileUtils.createOrExistsDir(PATH_TEMP)
        assertTrue(ZipUtils.zipFile(PATH_ZIP, zipFile, "测试zip"))
    }

    @Test
    @Throws(Exception::class)
    fun zipFiles() {
        val files = ArrayList<String>()
        files.add(PATH_ZIP + "test.txt")
        files.add(PATH_ZIP)
        files.add(PATH_ZIP + "testDir")
        assertTrue(ZipUtils.zipFiles(files, zipFiles))
    }

    @Test
    @Throws(Exception::class)
    fun unzipFile() {
        println(ZipUtils.unzipFile(zipFile, PATH_TEMP))
    }

    @Test
    @Throws(Exception::class)
    fun unzipFileByKeyword() {
        println(ZipUtils.unzipFileByKeyword(zipFile, PATH_TEMP, null).toString())
    }

    @Test
    @Throws(Exception::class)
    fun getFilesPath() {
        println(ZipUtils.getFilesPath(zipFile))
    }

    @Test
    @Throws(Exception::class)
    fun getComments() {
        println(ZipUtils.getComments(zipFile))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        FileUtils.deleteAllInDir(PATH_TEMP)
    }
}