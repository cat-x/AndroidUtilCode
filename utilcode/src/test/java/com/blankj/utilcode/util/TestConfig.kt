package com.blankj.utilcode.util

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/09/10
 * desc  : 单元测试配置文件
</pre> *
 */
object TestConfig {

    internal val FILE_SEP = System.getProperty("file.separator")

    internal val LINE_SEP = System.getProperty("line.separator")

    internal lateinit var TEST_PATH: String

    internal var PATH_TEMP = TEST_PATH + "temp" + FILE_SEP

    internal var PATH_CACHE = TEST_PATH + "cache" + FILE_SEP

    internal var PATH_ENCRYPT = TEST_PATH + "encrypt" + FILE_SEP

    internal var PATH_FILE = TEST_PATH + "file" + FILE_SEP

    internal var PATH_ZIP = TEST_PATH + "zip" + FILE_SEP

    init {
        var projectPath = System.getProperty("user.dir")
        if (!projectPath.contains("utilcode")) {
            projectPath += FILE_SEP + "utilcode"
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res" + FILE_SEP
    }
}
