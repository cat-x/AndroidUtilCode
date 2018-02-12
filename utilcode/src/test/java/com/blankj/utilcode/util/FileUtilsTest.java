package com.blankj.utilcode.util;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static com.blankj.utilcode.util.TestConfig.PATH_FILE;
import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.PATH_TEMP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/19
 *     desc  : FileUtils 单元测试
 * </pre>
 */
public class FileUtilsTest {

    private FileFilter mFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith("8.txt");
        }
    };

    private FileUtils.OnReplaceListener mListener = new FileUtils.OnReplaceListener() {
        @Override
        public boolean onReplace() {
            return true;
        }
    };


    @Test
    public void getFileByPath() throws Exception {
        assertNull(FileUtils.Companion.getFileByPath(" "));
        assertNotNull(FileUtils.Companion.getFileByPath(PATH_FILE));
    }

    @Test
    public void isFileExists() throws Exception {
        assertTrue(FileUtils.Companion.isFileExists(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.Companion.isFileExists(PATH_FILE + "UTF8"));
    }

    @Test
    public void rename() throws Exception {
        assertTrue(FileUtils.Companion.rename(PATH_FILE + "GBK.txt", "GBK1.txt"));
        assertTrue(FileUtils.Companion.rename(PATH_FILE + "GBK1.txt", "GBK.txt"));
    }

    @Test
    public void isDir() throws Exception {
        assertFalse(FileUtils.Companion.isDir(PATH_FILE + "UTF8.txt"));
        assertTrue(FileUtils.Companion.isDir(PATH_FILE));
    }

    @Test
    public void isFile() throws Exception {
        assertTrue(FileUtils.Companion.isFile(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.Companion.isFile(PATH_FILE));
    }

    @Test
    public void createOrExistsDir() throws Exception {
        assertTrue(FileUtils.Companion.createOrExistsDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.Companion.createOrExistsDir(PATH_FILE));
        assertTrue(FileUtils.Companion.deleteDir(PATH_FILE + "new Dir"));
    }

    @Test
    public void createOrExistsFile() throws Exception {
        assertTrue(FileUtils.Companion.createOrExistsFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.Companion.createOrExistsFile(PATH_FILE));
        assertTrue(FileUtils.Companion.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void createFileByDeleteOldFile() throws Exception {
        assertTrue(FileUtils.Companion.createFileByDeleteOldFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.Companion.createFileByDeleteOldFile(PATH_FILE));
        assertTrue(FileUtils.Companion.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void copyDir() throws Exception {
        assertFalse(FileUtils.Companion.copyDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.Companion.copyDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.Companion.copyDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.Companion.deleteDir(PATH_TEMP));
    }

    @Test
    public void copyFile() throws Exception {
        assertFalse(FileUtils.Companion.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.Companion.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "new Dir" + FILE_SEP + "GBK.txt", mListener));
        assertTrue(FileUtils.Companion.copyFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.Companion.deleteDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.Companion.deleteDir(PATH_TEMP));
    }

    @Test
    public void moveDir() throws Exception {
        assertFalse(FileUtils.Companion.moveDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.Companion.moveDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.Companion.moveDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.Companion.moveDir(PATH_TEMP, PATH_FILE, mListener));
    }

    @Test
    public void moveFile() throws Exception {
        assertFalse(FileUtils.Companion.moveFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.Companion.moveFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.Companion.moveFile(PATH_TEMP + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        FileUtils.Companion.deleteDir(PATH_TEMP);
    }

    @Test
    public void listFilesInDir() throws Exception {
        System.out.println(FileUtils.Companion.listFilesInDir(PATH_FILE, false).toString());
        System.out.println(FileUtils.Companion.listFilesInDir(PATH_FILE, true).toString());
    }

    @Test
    public void listFilesInDirWithFilter() throws Exception {
        System.out.println(FileUtils.Companion.listFilesInDirWithFilter(PATH_FILE, mFilter, false).toString());
        System.out.println(FileUtils.Companion.listFilesInDirWithFilter(PATH_FILE, mFilter, true).toString());
    }

    @Test
    public void getFileLastModified() throws Exception {
        System.out.println(TimeUtils.millis2String(FileUtils.Companion.getFileLastModified(PATH_FILE)));
    }

    @Test
    public void getFileCharsetSimple() throws Exception {
        assertEquals("GBK", FileUtils.Companion.getFileCharsetSimple(PATH_FILE + "GBK.txt"));
        assertEquals("Unicode", FileUtils.Companion.getFileCharsetSimple(PATH_FILE + "Unicode.txt"));
        assertEquals("UTF-8", FileUtils.Companion.getFileCharsetSimple(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF-16BE", FileUtils.Companion.getFileCharsetSimple(PATH_FILE + "UTF16BE.txt"));
    }

    @Test
    public void getFileLines() throws Exception {
        assertEquals(7, FileUtils.Companion.getFileLines(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirSize() throws Exception {
        System.out.println(FileUtils.Companion.getDirSize(PATH_FILE));
    }

    @Test
    public void getFileSize() throws Exception {
        System.out.println(FileUtils.Companion.getFileSize(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirLength() throws Exception {
        System.out.println(FileUtils.Companion.getDirLength(PATH_FILE));
    }

    @Test
    public void getFileLength() throws Exception {
        System.out.println(FileUtils.Companion.getFileLength(PATH_FILE + "UTF8.txt"));
        System.out.println(FileUtils.Companion.getFileLength("https://raw.githubusercontent.com/Blankj/AndroidUtilCode/85bc44d1c8adb31027870ea4cb7a931700c80cad/LICENSE"));
    }

    @Test
    public void getFileMD5ToString() throws Exception {
        assertEquals("249D3E76851DCC56C945994DE9DAC406", FileUtils.Companion.getFileMD5ToString(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirName() throws Exception {
        assertEquals(PATH_FILE, FileUtils.Companion.getDirName(new File(PATH_FILE + "UTF8.txt")));
        assertEquals(PATH_FILE, FileUtils.Companion.getDirName(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getFileName() throws Exception {
        assertEquals("UTF8.txt", FileUtils.Companion.getFileName(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8.txt", FileUtils.Companion.getFileName(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileNameNoExtension() throws Exception {
        assertEquals("UTF8", FileUtils.Companion.getFileNameNoExtension(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8", FileUtils.Companion.getFileNameNoExtension(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileExtension() throws Exception {
        assertEquals("txt", FileUtils.Companion.getFileExtension(new File(PATH_FILE + "UTF8.txt")));
        assertEquals("txt", FileUtils.Companion.getFileExtension(PATH_FILE + "UTF8.txt"));
    }
}