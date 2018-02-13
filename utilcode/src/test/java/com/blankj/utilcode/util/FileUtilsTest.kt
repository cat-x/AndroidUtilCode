package com.blankj.utilcode.util

import com.blankj.utilcode.util.TestConfig.FILE_SEP
import com.blankj.utilcode.util.TestConfig.PATH_FILE
import com.blankj.utilcode.util.TestConfig.PATH_TEMP
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.io.FileFilter

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/19
 * desc  : FileUtils 单元测试
</pre> *
 */
class FileUtilsTest {

    private val mFilter = FileFilter { pathname -> pathname.name.endsWith("8.txt") }

    private val mListener = object : FileUtils.OnReplaceListener {
        override fun onReplace(): Boolean {
            return true
        }
    }


    @Test
    @Throws(Exception::class)
    fun getFileByPath() {
        assertNull(FileUtils.getFileByPath(" "))
        assertNotNull(FileUtils.getFileByPath(PATH_FILE))
    }

    @Test
    @Throws(Exception::class)
    fun isFileExists() {
        assertTrue(FileUtils.isFileExists(PATH_FILE + "UTF8.txt"))
        assertFalse(FileUtils.isFileExists(PATH_FILE + "UTF8"))
    }

    @Test
    @Throws(Exception::class)
    fun rename() {
        assertTrue(FileUtils.rename(PATH_FILE + "GBK.txt", "GBK1.txt"))
        assertTrue(FileUtils.rename(PATH_FILE + "GBK1.txt", "GBK.txt"))
    }

    @Test
    @Throws(Exception::class)
    fun isDir() {
        assertFalse(FileUtils.isDir(PATH_FILE + "UTF8.txt"))
        assertTrue(FileUtils.isDir(PATH_FILE))
    }

    @Test
    @Throws(Exception::class)
    fun isFile() {
        assertTrue(FileUtils.isFile(PATH_FILE + "UTF8.txt"))
        assertFalse(FileUtils.isFile(PATH_FILE))
    }

    @Test
    @Throws(Exception::class)
    fun createOrExistsDir() {
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE + "new Dir"))
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE))
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"))
    }

    @Test
    @Throws(Exception::class)
    fun createOrExistsFile() {
        assertTrue(FileUtils.createOrExistsFile(PATH_FILE + "new File"))
        assertFalse(FileUtils.createOrExistsFile(PATH_FILE))
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"))
    }

    @Test
    @Throws(Exception::class)
    fun createFileByDeleteOldFile() {
        assertTrue(FileUtils.createFileByDeleteOldFile(PATH_FILE + "new File"))
        assertFalse(FileUtils.createFileByDeleteOldFile(PATH_FILE))
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"))
    }

    @Test
    @Throws(Exception::class)
    fun copyDir() {
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE, mListener))
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE + "new Dir", mListener))
        assertTrue(FileUtils.copyDir(PATH_FILE, PATH_TEMP, mListener))
        assertTrue(FileUtils.deleteDir(PATH_TEMP))
    }

    @Test
    @Throws(Exception::class)
    fun copyFile() {
        assertFalse(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener))
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "new Dir" + FILE_SEP + "GBK.txt", mListener))
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener))
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"))
        assertTrue(FileUtils.deleteDir(PATH_TEMP))
    }

    @Test
    @Throws(Exception::class)
    fun moveDir() {
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE, mListener))
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE + "new Dir", mListener))
        assertTrue(FileUtils.moveDir(PATH_FILE, PATH_TEMP, mListener))
        assertTrue(FileUtils.moveDir(PATH_TEMP, PATH_FILE, mListener))
    }

    @Test
    @Throws(Exception::class)
    fun moveFile() {
        assertFalse(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener))
        assertTrue(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener))
        assertTrue(FileUtils.moveFile(PATH_TEMP + "GBK.txt", PATH_FILE + "GBK.txt", mListener))
        FileUtils.deleteDir(PATH_TEMP)
    }

    @Test
    @Throws(Exception::class)
    fun listFilesInDir() {
        println(FileUtils.listFilesInDir(PATH_FILE, false)!!.toString())
        println(FileUtils.listFilesInDir(PATH_FILE, true)!!.toString())
    }

    @Test
    @Throws(Exception::class)
    fun listFilesInDirWithFilter() {
        println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, false)!!.toString())
        println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, true)!!.toString())
    }

    @Test
    @Throws(Exception::class)
    fun getFileLastModified() {
        System.out.println(TimeUtils.millis2String(FileUtils.getFileLastModified(PATH_FILE)))
    }

    @Test
    @Throws(Exception::class)
    fun getFileCharsetSimple() {
        assertEquals("GBK", FileUtils.getFileCharsetSimple(PATH_FILE + "GBK.txt"))
        assertEquals("Unicode", FileUtils.getFileCharsetSimple(PATH_FILE + "Unicode.txt"))
        assertEquals("UTF-8", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF8.txt"))
        assertEquals("UTF-16BE", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF16BE.txt"))
    }

    @Test
    @Throws(Exception::class)
    fun getFileLines() {
        assertEquals(7, FileUtils.getFileLines(PATH_FILE + "UTF8.txt").toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getDirSize() {
        println(FileUtils.getDirSize(PATH_FILE))
    }

    @Test
    @Throws(Exception::class)
    fun getFileSize() {
        println(FileUtils.getFileSize(PATH_FILE + "UTF8.txt"))
    }

    @Test
    @Throws(Exception::class)
    fun getDirLength() {
        println(FileUtils.getDirLength(PATH_FILE))
    }

    @Test
    @Throws(Exception::class)
    fun getFileLength() {
        println(FileUtils.getFileLength(PATH_FILE + "UTF8.txt"))
        println(FileUtils.getFileLength("https://raw.githubusercontent.com/Blankj/AndroidUtilCode/85bc44d1c8adb31027870ea4cb7a931700c80cad/LICENSE"))
    }

    @Test
    @Throws(Exception::class)
    fun getFileMD5ToString() {
        assertEquals("249D3E76851DCC56C945994DE9DAC406", FileUtils.getFileMD5ToString(PATH_FILE + "UTF8.txt"))
    }

    @Test
    @Throws(Exception::class)
    fun getDirName() {
        assertEquals(PATH_FILE, FileUtils.getDirName(File(PATH_FILE + "UTF8.txt")))
        assertEquals(PATH_FILE, FileUtils.getDirName(PATH_FILE + "UTF8.txt"))
    }

    @Test
    @Throws(Exception::class)
    fun getFileName() {
        assertEquals("UTF8.txt", FileUtils.getFileName(PATH_FILE + "UTF8.txt"))
        assertEquals("UTF8.txt", FileUtils.getFileName(File(PATH_FILE + "UTF8.txt")))
    }

    @Test
    @Throws(Exception::class)
    fun getFileNameNoExtension() {
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(PATH_FILE + "UTF8.txt"))
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(File(PATH_FILE + "UTF8.txt")))
    }

    @Test
    @Throws(Exception::class)
    fun getFileExtension() {
        assertEquals("txt", FileUtils.getFileExtension(File(PATH_FILE + "UTF8.txt")))
        assertEquals("txt", FileUtils.getFileExtension(PATH_FILE + "UTF8.txt"))
    }
}