package com.blankj.utilcode.util

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider

import java.io.File

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/23
 * desc  : 意图相关工具类
</pre> *
 */
class IntentUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 获取安装 App（支持 8.0）的意图
         *
         * 8.0 需添加权限
         * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
         *
         * @param filePath  文件路径
         * @param authority 7.0 及以上安装需要传入清单文件中的`<provider>`的 authorities 属性
         * <br></br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
         * @return 安装 App（支持 8.0）的意图
         */
        fun getInstallAppIntent(filePath: String, authority: String): Intent? {
            return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority)
        }

        /**
         * 获取安装 App(支持 8.0)的意图
         *
         * 8.0 需添加权限
         * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
         *
         * @param file      文件
         * @param authority 7.0 及以上安装需要传入清单文件中的`<provider>`的 authorities 属性
         * <br></br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
         * @param isNewTask 是否开启新的任务栈
         * @return 安装 App(支持 8.0)的意图
         */
        @JvmOverloads
        fun getInstallAppIntent(file: File?,
                                authority: String,
                                isNewTask: Boolean = false): Intent? {
            if (file == null) return null
            val intent = Intent(Intent.ACTION_VIEW)
            val data: Uri
            val type = "application/vnd.android.package-archive"
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file)
            } else {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                data = FileProvider.getUriForFile(Utils.app, authority, file)
            }
            intent.setDataAndType(data, type)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取卸载 App 的意图
         *
         * @param packageName 包名
         * @param isNewTask   是否开启新的任务栈
         * @return 卸载 App 的意图
         */
        @JvmOverloads
        fun getUninstallAppIntent(packageName: String, isNewTask: Boolean = false): Intent {
            val intent = Intent(Intent.ACTION_DELETE)
            intent.data = Uri.parse("package:" + packageName)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取打开 App 的意图
         *
         * @param packageName 包名
         * @param isNewTask   是否开启新的任务栈
         * @return 打开 App 的意图
         */
        @JvmOverloads
        fun getLaunchAppIntent(packageName: String, isNewTask: Boolean = false): Intent? {
            val intent = Utils.app.packageManager.getLaunchIntentForPackage(packageName)
                    ?: return null
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取 App 具体设置的意图
         *
         * @param packageName 包名
         * @param isNewTask   是否开启新的任务栈
         * @return App 具体设置的意图
         */
        @JvmOverloads
        fun getAppDetailsSettingsIntent(packageName: String,
                                        isNewTask: Boolean = false): Intent {
            val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
            intent.data = Uri.parse("package:" + packageName)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取分享文本的意图
         *
         * @param content   分享文本
         * @param isNewTask 是否开启新的任务栈
         * @return 分享文本的意图
         */

        @JvmOverloads
        fun getShareTextIntent(content: String, isNewTask: Boolean = false): Intent {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, content)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取分享图片的意图
         *
         * @param content   文本
         * @param imagePath 图片文件路径
         * @param isNewTask 是否开启新的任务栈
         * @return 分享图片的意图
         */
        @JvmOverloads
        fun getShareImageIntent(content: String,
                                imagePath: String?,
                                isNewTask: Boolean = false): Intent? {
            return if (imagePath == null || imagePath.length == 0) null else getShareImageIntent(content, File(imagePath), isNewTask)
        }

        /**
         * 获取分享图片的意图
         *
         * @param content   文本
         * @param image     图片文件
         * @param isNewTask 是否开启新的任务栈
         * @return 分享图片的意图
         */
        @JvmOverloads
        fun getShareImageIntent(content: String,
                                image: File?,
                                isNewTask: Boolean = false): Intent? {
            return if (image != null && image.isFile) null else getShareImageIntent(content, Uri.fromFile(image), isNewTask)
        }

        /**
         * 获取分享图片的意图
         *
         * @param content   分享文本
         * @param uri       图片 uri
         * @param isNewTask 是否开启新的任务栈
         * @return 分享图片的意图
         */
        @JvmOverloads
        fun getShareImageIntent(content: String,
                                uri: Uri,
                                isNewTask: Boolean = false): Intent {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/*"
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取其他应用组件的意图
         *
         * @param packageName 包名
         * @param className   全类名
         * @param isNewTask   是否开启新的任务栈
         * @return 其他应用组件的意图
         */
        fun getComponentIntent(packageName: String,
                               className: String,
                               isNewTask: Boolean): Intent {
            return getComponentIntent(packageName, className, null, isNewTask)
        }

        /**
         * 获取其他应用组件的意图
         *
         * @param packageName 包名
         * @param className   全类名
         * @param bundle      bundle
         * @param isNewTask   是否开启新的任务栈
         * @return 其他应用组件的意图
         */
        @JvmOverloads
        fun getComponentIntent(packageName: String,
                               className: String,
                               bundle: Bundle? = null,
                               isNewTask: Boolean = false): Intent {
            val intent = Intent(Intent.ACTION_VIEW)
            if (bundle != null) intent.putExtras(bundle)
            val cn = ComponentName(packageName, className)
            intent.component = cn
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取关机的意图
         *
         * 需添加权限 `<uses-permission android:name="android.permission.SHUTDOWN" />`
         *
         * @return 关机的意图
         */
        val shutdownIntent: Intent
            get() = getShutdownIntent(false)

        /**
         * 获取关机的意图
         *
         * 需添加权限 `<uses-permission android:name="android.permission.SHUTDOWN" />`
         *
         * @param isNewTask 是否开启新的任务栈
         * @return 关机的意图
         */
        fun getShutdownIntent(isNewTask: Boolean): Intent {
            val intent = Intent(Intent.ACTION_SHUTDOWN)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取跳至拨号界面意图
         *
         * @param phoneNumber 电话号码
         * @param isNewTask   是否开启新的任务栈
         * @return 跳至拨号界面意图
         */
        @JvmOverloads
        fun getDialIntent(phoneNumber: String, isNewTask: Boolean = false): Intent {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取拨打电话意图
         *
         * 需添加权限 `<uses-permission android:name="android.permission.CALL_PHONE" />`
         *
         * @param phoneNumber 电话号码
         * @param isNewTask   是否开启新的任务栈
         * @return 拨打电话意图
         */
        @JvmOverloads
        fun getCallIntent(phoneNumber: String, isNewTask: Boolean = false): Intent {
            val intent = Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber))
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取跳至发送短信界面的意图
         *
         * @param phoneNumber 接收号码
         * @param content     短信内容
         * @param isNewTask   是否开启新的任务栈
         * @return 发送短信界面的意图
         */
        @JvmOverloads
        fun getSendSmsIntent(phoneNumber: String,
                             content: String,
                             isNewTask: Boolean = false): Intent {
            val uri = Uri.parse("smsto:" + phoneNumber)
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", content)
            return getIntent(intent, isNewTask)
        }

        /**
         * 获取拍照的意图
         *
         * @param outUri    输出的 uri
         * @param isNewTask 是否开启新的任务栈
         * @return 拍照的意图
         */
        @JvmOverloads
        fun getCaptureIntent(outUri: Uri, isNewTask: Boolean = false): Intent {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            return getIntent(intent, isNewTask)
        }

        private fun getIntent(intent: Intent, isNewTask: Boolean): Intent {
            return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
        }
    }

    //    /**
    //     * 获取选择照片的 Intent
    //     *
    //     * @return
    //     */
    //    public static Intent getPickIntentWithGallery() {
    //        Intent intent = new Intent(Intent.ACTION_PICK);
    //        return intent.setType("image*//*");
    //    }
    //
    //    /**
    //     * 获取从文件中选择照片的 Intent
    //     *
    //     * @return
    //     */
    //    public static Intent getPickIntentWithDocuments() {
    //        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    //        return intent.setType("image*//*");
    //    }
    //
    //
    //    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
    //        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
    //    }
    //
    //    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
    //                                             int outputX, int outputY, boolean returnData) {
    //        Intent intent = new Intent();
    //        if (Build.VERSION.SDK_INT < 19) {
    //            intent.setAction(Intent.ACTION_GET_CONTENT);
    //        } else {
    //            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    //            intent.addCategory(Intent.CATEGORY_OPENABLE);
    //        }
    //        intent.setType("image*//*");
    //        intent.putExtra("output", saveTo);
    //        intent.putExtra("aspectX", aspectX);
    //        intent.putExtra("aspectY", aspectY);
    //        intent.putExtra("outputX", outputX);
    //        intent.putExtra("outputY", outputY);
    //        intent.putExtra("scale", true);
    //        intent.putExtra("return-data", returnData);
    //        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
    //        return intent;
    //    }
    //
    //    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
    //        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
    //    }
    //
    //    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
    //                                              int outputX, int outputY, boolean returnData) {
    //        Intent intent = new Intent("com.android.camera.action.CROP");
    //        intent.setDataAndType(uriFrom, "image*//*");
    //        intent.putExtra("crop", "true");
    //        intent.putExtra("output", uriTo);
    //        intent.putExtra("aspectX", aspectX);
    //        intent.putExtra("aspectY", aspectY);
    //        intent.putExtra("outputX", outputX);
    //        intent.putExtra("outputY", outputY);
    //        intent.putExtra("scale", true);
    //        intent.putExtra("return-data", returnData);
    //        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
    //        return intent;
    //    }
    //
    //    public static Intent buildImageCaptureIntent(final Uri uri) {
    //        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    //        return intent;
    //    }
}
