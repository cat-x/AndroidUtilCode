package com.blankj.subutil.util

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/09/10
 * desc  :
</pre> *
 */
object TestConfig {

    internal val FILE_SEP = System.getProperty("file.separator")

    internal val LINE_SEP = System.getProperty("line.separator")

    internal val TEST_PATH: String

    init {
        var projectPath = System.getProperty("user.dir")
        if (!projectPath.contains("subutil")) {
            projectPath += FILE_SEP + "subutil"
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res"
    }
}
