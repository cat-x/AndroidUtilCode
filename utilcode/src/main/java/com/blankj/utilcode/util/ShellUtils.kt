package com.blankj.utilcode.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/07
 * desc  : Shell 相关工具类
</pre> *
 */
class ShellUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    /**
     * 返回的命令结果
     */
    class CommandResult(
            /**
             * 结果码
             */
            var result: Int,
            /**
             * 成功信息
             */
            var successMsg: String?,
            /**
             * 错误信息
             */
            var errorMsg: String?)

    companion object {

        private val LINE_SEP = System.getProperty("line.separator")

        /**
         * 是否是在 root 下执行命令
         *
         * @param command 命令
         * @param isRoot  是否需要 root 权限执行
         * @return CommandResult
         */
        fun execCmd(command: String, isRoot: Boolean): CommandResult {
            return execCmd(arrayOf(command), isRoot, true)
        }

        /**
         * 是否是在 root 下执行命令
         *
         * @param commands 多条命令链表
         * @param isRoot   是否需要 root 权限执行
         * @return CommandResult
         */
        fun execCmd(commands: List<String>?, isRoot: Boolean): CommandResult {
            return execCmd(commands?.toTypedArray<String>(), isRoot, true)
        }

        /**
         * 是否是在 root 下执行命令
         *
         * @param command         命令
         * @param isRoot          是否需要 root 权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        fun execCmd(command: String,
                    isRoot: Boolean,
                    isNeedResultMsg: Boolean): CommandResult {
            return execCmd(arrayOf(command), isRoot, isNeedResultMsg)
        }

        /**
         * 是否是在 root 下执行命令
         *
         * @param commands        命令链表
         * @param isRoot          是否需要 root 权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        fun execCmd(commands: List<String>?,
                    isRoot: Boolean,
                    isNeedResultMsg: Boolean): CommandResult {
            return execCmd(commands?.toTypedArray<String>(),
                    isRoot,
                    isNeedResultMsg)
        }

        /**
         * 是否是在 root 下执行命令
         *
         * @param commands        命令数组
         * @param isRoot          是否需要 root 权限执行
         * @param isNeedResultMsg 是否需要结果消息
         * @return CommandResult
         */
        @JvmOverloads
        fun execCmd(commands: Array<String>?,
                    isRoot: Boolean,
                    isNeedResultMsg: Boolean = true): CommandResult {
            var result = -1
            if (commands == null || commands.size == 0) {
                return CommandResult(result, null, null)
            }
            var process: Process? = null
            var successResult: BufferedReader? = null
            var errorResult: BufferedReader? = null
            var successMsg: StringBuilder? = null
            var errorMsg: StringBuilder? = null
            var os: DataOutputStream? = null
            try {
                process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh")
                os = DataOutputStream(process!!.outputStream)
                for (command in commands) {
                    if (command == null) continue
                    os.write(command.toByteArray())
                    os.writeBytes(LINE_SEP)
                    os.flush()
                }
                os.writeBytes("exit" + LINE_SEP)
                os.flush()
                result = process.waitFor()
                if (isNeedResultMsg) {
                    successMsg = StringBuilder()
                    errorMsg = StringBuilder()
                    successResult = BufferedReader(InputStreamReader(process.inputStream,
                            "UTF-8"))
                    errorResult = BufferedReader(InputStreamReader(process.errorStream,
                            "UTF-8"))
                    var line: String? = null
                    if ({ line = successResult.readLine();line }() != null) {
                        successMsg.append(line)
                        while ({ line = successResult.readLine();line }() != null) {
                            successMsg.append(LINE_SEP).append(line)
                        }
                    }
                    if ({ line = errorResult.readLine();line }() != null) {
                        errorMsg.append(line)
                        while ({ line = errorResult.readLine();line }() != null) {
                            errorMsg.append(LINE_SEP).append(line)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                CloseUtils.closeIO(os, successResult, errorResult)
                if (process != null) {
                    process.destroy()
                }
            }
            return CommandResult(
                    result,
                    if (successMsg == null) null else successMsg.toString(),
                    if (errorMsg == null) null else errorMsg.toString()
            )
        }
    }
}
/**
 * 是否是在 root 下执行命令
 *
 * @param commands 多条命令数组
 * @param isRoot   是否需要 root 权限执行
 * @return CommandResult
 */
