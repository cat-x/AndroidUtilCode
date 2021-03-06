package com.blankj.utilcode.util

import com.blankj.utilcode.util.TestConfig.PATH_FILE
import com.blankj.utilcode.util.TestConfig.PATH_TEMP
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.FileInputStream

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/24
 * desc  : FileIOUtils 单元测试
</pre> *
 */
class FileIOUtilsTest {

    @Test
    @Throws(Exception::class)
    fun writeFileFromIS() {
        Assert.assertTrue(FileIOUtils.writeFileFromIS(PATH_TEMP + "UTF8.txt", FileInputStream(PATH_FILE + "UTF8.txt"), false))
        Assert.assertTrue(FileIOUtils.writeFileFromIS(PATH_TEMP + "UTF8.txt", FileInputStream(PATH_FILE + "UTF8.txt"), true))
    }

    @Test
    @Throws(Exception::class)
    fun writeFileFromBytes() {
        //        String p = path + "test.txt";
        //        String p1 = path + "copy.zip";
        //        byte[] data = new byte[(1 << 20) * 100];
        //        long st, end;
        //        FileUtils.deleteFile(p);
        //
        //        st = System.currentTimeMillis();
        //        for (int i = 0; i < 100; i++) {
        //            FileIOUtils.writeFileFromBytesByStream(p, data, true);
        //        }
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
        //        FileUtils.deleteFile(p);
        //
        //        st = System.currentTimeMillis();
        //        for (int i = 0; i < 100; i++) {
        //            FileIOUtils.writeFileFromBytesByChannel(p, data, true);
        //        }
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
        //        FileUtils.deleteFile(p);
        //
        //        st = System.currentTimeMillis();
        //        for (int i = 0; i < 100; i++) {
        //            FileIOUtils.writeFileFromBytesByMap(p, data, true, false);
        //        }
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
        //        FileUtils.deleteFile(p);
    }

    @Test
    @Throws(Exception::class)
    fun writeFileFromString() {

    }

    @Test
    @Throws(Exception::class)
    fun readFile2List() {

    }

    @Test
    @Throws(Exception::class)
    fun readFile2String() {

    }

    @Test
    @Throws(Exception::class)
    fun readFile2Bytes() {
        //        String p = path + "test.app.zip";
        //        long st, end;
        //        st = System.currentTimeMillis();
        //        FileIOUtils.readFile2BytesByStream(p);
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
        //        st = System.currentTimeMillis();
        //        FileIOUtils.readFile2BytesByChannel(p);
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
        //        st = System.currentTimeMillis();
        //        FileIOUtils.readFile2BytesByMap(p);
        //        end = System.currentTimeMillis();
        //        System.out.println(end - st);
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        FileUtils.deleteAllInDir(PATH_TEMP)
    }

}