package com.blankj.utilcode.util

import android.annotation.TargetApi
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.v4.content.ContextCompat
import android.view.View

import com.blankj.utilcode.constant.MemoryConstants

import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/12
 * desc  : 图片相关工具类
</pre> *
 */
class ImageUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * bitmap 转 byteArr
         *
         * @param bitmap bitmap 对象
         * @param format 格式
         * @return 字节数组
         */
        fun bitmap2Bytes(bitmap: Bitmap?, format: CompressFormat): ByteArray? {
            if (bitmap == null) return null
            val baos = ByteArrayOutputStream()
            bitmap.compress(format, 100, baos)
            return baos.toByteArray()
        }

        /**
         * byteArr 转 bitmap
         *
         * @param bytes 字节数组
         * @return bitmap
         */
        fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
            return if (bytes == null || bytes.size == 0)
                null
            else
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        /**
         * drawable 转 bitmap
         *
         * @param drawable drawable 对象
         * @return bitmap
         */
        fun drawable2Bitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                if (drawable.bitmap != null) {
                    return drawable.bitmap
                }
            }
            val bitmap: Bitmap
            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(1, 1,
                        if (drawable.opacity != PixelFormat.OPAQUE)
                            Bitmap.Config.ARGB_8888
                        else
                            Bitmap.Config.RGB_565)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        if (drawable.opacity != PixelFormat.OPAQUE)
                            Bitmap.Config.ARGB_8888
                        else
                            Bitmap.Config.RGB_565)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * bitmap 转 drawable
         *
         * @param bitmap bitmap 对象
         * @return drawable
         */
        fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
            return if (bitmap == null) null else BitmapDrawable(Utils.app.getResources(), bitmap)
        }

        /**
         * drawable 转 byteArr
         *
         * @param drawable drawable 对象
         * @param format   格式
         * @return 字节数组
         */
        fun drawable2Bytes(drawable: Drawable?, format: CompressFormat): ByteArray? {
            return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable), format)
        }

        /**
         * byteArr 转 drawable
         *
         * @param bytes 字节数组
         * @return drawable
         */
        fun bytes2Drawable(bytes: ByteArray): Drawable? {
            return bitmap2Drawable(bytes2Bitmap(bytes))
        }

        /**
         * view 转 bitmap
         *
         * @param view 视图
         * @return bitmap
         */
        fun view2Bitmap(view: View?): Bitmap? {
            if (view == null) return null
            val ret = Bitmap.createBitmap(view.width,
                    view.height,
                    Bitmap.Config.ARGB_8888)
            val canvas = Canvas(ret)
            val bgDrawable = view.background
            if (bgDrawable != null) {
                bgDrawable.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            view.draw(canvas)
            return ret
        }

        /**
         * 获取 bitmap
         *
         * @param file 文件
         * @return bitmap
         */
        fun getBitmap(file: File?): Bitmap? {
            return if (file == null) null else BitmapFactory.decodeFile(file.absolutePath)
        }

        /**
         * 获取 bitmap
         *
         * @param file      文件
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(file: File?, maxWidth: Int, maxHeight: Int): Bitmap? {
            if (file == null) return null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(file.absolutePath, options)
        }

        /**
         * 获取 bitmap
         *
         * @param filePath 文件路径
         * @return bitmap
         */
        fun getBitmap(filePath: String): Bitmap? {
            return if (isSpace(filePath)) null else BitmapFactory.decodeFile(filePath)
        }

        /**
         * 获取 bitmap
         *
         * @param filePath  文件路径
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(filePath: String, maxWidth: Int, maxHeight: Int): Bitmap? {
            if (isSpace(filePath)) return null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(filePath, options)
        }

        /**
         * 获取 bitmap
         *
         * @param is 输入流
         * @return bitmap
         */
        fun getBitmap(`is`: InputStream?): Bitmap? {
            return if (`is` == null) null else BitmapFactory.decodeStream(`is`)
        }

        /**
         * 获取 bitmap
         *
         * @param is        输入流
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(`is`: InputStream?, maxWidth: Int, maxHeight: Int): Bitmap? {
            if (`is` == null) return null
            val bytes = input2Byte(`is`)
            return getBitmap(bytes, 0, maxWidth, maxHeight)
        }

        /**
         * 获取 bitmap
         *
         * @param data   数据
         * @param offset 偏移量
         * @return bitmap
         */
        fun getBitmap(data: ByteArray, offset: Int): Bitmap? {
            return if (data.size == 0) null else BitmapFactory.decodeByteArray(data, offset, data.size)
        }

        /**
         * 获取 bitmap
         *
         * @param data      数据
         * @param offset    偏移量
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(data: ByteArray?,
                      offset: Int,
                      maxWidth: Int,
                      maxHeight: Int): Bitmap? {
            if (data!!.size == 0) return null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(data, offset, data.size, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeByteArray(data, offset, data.size, options)
        }

        /**
         * 获取 bitmap
         *
         * @param resId 资源 id
         * @return bitmap
         */
        fun getBitmap(@DrawableRes resId: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(Utils.app, resId)
            val canvas = Canvas()
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * 获取 bitmap
         *
         * @param resId     资源 id
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(@DrawableRes resId: Int,
                      maxWidth: Int,
                      maxHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            val resources = Utils.app.getResources()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, resId, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeResource(resources, resId, options)
        }

        /**
         * 获取 bitmap
         *
         * @param fd 文件描述
         * @return bitmap
         */
        fun getBitmap(fd: FileDescriptor?): Bitmap? {
            return if (fd == null) null else BitmapFactory.decodeFileDescriptor(fd)
        }

        /**
         * 获取 bitmap
         *
         * @param fd        文件描述
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return bitmap
         */
        fun getBitmap(fd: FileDescriptor?,
                      maxWidth: Int,
                      maxHeight: Int): Bitmap? {
            if (fd == null) return null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFileDescriptor(fd, null, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFileDescriptor(fd, null, options)
        }

        /**
         * 缩放图片
         *
         * @param src       源图片
         * @param newWidth  新宽度
         * @param newHeight 新高度
         * @param recycle   是否回收
         * @return 缩放后的图片
         */
        @JvmOverloads
        fun scale(src: Bitmap,
                  newWidth: Int,
                  newHeight: Int,
                  recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 缩放图片
         *
         * @param src         源图片
         * @param scaleWidth  缩放宽度倍数
         * @param scaleHeight 缩放高度倍数
         * @param recycle     是否回收
         * @return 缩放后的图片
         */
        @JvmOverloads
        fun scale(src: Bitmap,
                  scaleWidth: Float,
                  scaleHeight: Float,
                  recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val matrix = Matrix()
            matrix.setScale(scaleWidth, scaleHeight)
            val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 裁剪图片
         *
         * @param src     源图片
         * @param x       开始坐标 x
         * @param y       开始坐标 y
         * @param width   裁剪宽度
         * @param height  裁剪高度
         * @param recycle 是否回收
         * @return 裁剪后的图片
         */
        @JvmOverloads
        fun clip(src: Bitmap,
                 x: Int,
                 y: Int,
                 width: Int,
                 height: Int,
                 recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = Bitmap.createBitmap(src, x, y, width, height)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 倾斜图片
         *
         * @param src     源图片
         * @param kx      倾斜因子 x
         * @param ky      倾斜因子 y
         * @param recycle 是否回收
         * @return 倾斜后的图片
         */
        fun skew(src: Bitmap,
                 kx: Float,
                 ky: Float,
                 recycle: Boolean): Bitmap? {
            return skew(src, kx, ky, 0f, 0f, recycle)
        }

        /**
         * 倾斜图片
         *
         * @param src     源图片
         * @param kx      倾斜因子 x
         * @param ky      倾斜因子 y
         * @param px      平移因子 x
         * @param py      平移因子 y
         * @param recycle 是否回收
         * @return 倾斜后的图片
         */
        @JvmOverloads
        fun skew(src: Bitmap,
                 kx: Float,
                 ky: Float,
                 px: Float = 0f,
                 py: Float = 0f,
                 recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val matrix = Matrix()
            matrix.setSkew(kx, ky, px, py)
            val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 旋转图片
         *
         * @param src     源图片
         * @param degrees 旋转角度
         * @param px      旋转点横坐标
         * @param py      旋转点纵坐标
         * @param recycle 是否回收
         * @return 旋转后的图片
         */
        @JvmOverloads
        fun rotate(src: Bitmap,
                   degrees: Int,
                   px: Float,
                   py: Float,
                   recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            if (degrees == 0) return src
            val matrix = Matrix()
            matrix.setRotate(degrees.toFloat(), px, py)
            val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 获取图片旋转角度
         *
         * 返回 -1 表示异常
         *
         * @param filePath 文件路径
         * @return 旋转角度
         */
        fun getRotateDegree(filePath: String): Int {
            try {
                val exifInterface = ExifInterface(filePath)
                val orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> return 270
                    else -> return 0
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return -1
            }

        }

        /**
         * 转为圆形图片
         *
         * @param src     源图片
         * @param recycle 是否回收
         * @return 圆形图片
         */
        fun toRound(src: Bitmap, recycle: Boolean): Bitmap? {
            return toRound(src, 0, 0, recycle)
        }

        /**
         * 转为圆形图片
         *
         * @param src         源图片
         * @param recycle     是否回收
         * @param borderSize  边框尺寸
         * @param borderColor 边框颜色
         * @return 圆形图片
         */
        @JvmOverloads
        fun toRound(src: Bitmap,
                    @IntRange(from = 0) borderSize: Int = 0,
                    @ColorInt borderColor: Int = 0,
                    recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val width = src.width
            val height = src.height
            val size = Math.min(width, height)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val ret = Bitmap.createBitmap(width, height, src.config)
            val center = size / 2f
            val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
            rectF.inset((width - size) / 2f, (height - size) / 2f)
            val matrix = Matrix()
            matrix.setTranslate(rectF.left, rectF.top)
            matrix.preScale(size.toFloat() / width, size.toFloat() / height)
            val shader = BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            shader.setLocalMatrix(matrix)
            paint.shader = shader
            val canvas = Canvas(ret)
            canvas.drawRoundRect(rectF, center, center, paint)
            if (borderSize > 0) {
                paint.shader = null
                paint.color = borderColor
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = borderSize.toFloat()
                val radius = center - borderSize / 2f
                canvas.drawCircle(width / 2f, height / 2f, radius, paint)
            }
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 转为圆角图片
         *
         * @param src     源图片
         * @param radius  圆角的度数
         * @param recycle 是否回收
         * @return 圆角图片
         */
        fun toRoundCorner(src: Bitmap,
                          radius: Float,
                          recycle: Boolean): Bitmap? {
            return toRoundCorner(src, radius, 0, 0, recycle)
        }

        /**
         * 转为圆角图片
         *
         * @param src         源图片
         * @param radius      圆角的度数
         * @param borderSize  边框尺寸
         * @param borderColor 边框颜色
         * @param recycle     是否回收
         * @return 圆角图片
         */
        @JvmOverloads
        fun toRoundCorner(src: Bitmap,
                          radius: Float,
                          @IntRange(from = 0) borderSize: Int = 0,
                          @ColorInt borderColor: Int = 0,
                          recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val width = src.width
            val height = src.height
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val ret = Bitmap.createBitmap(width, height, src.config)
            val shader = BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader
            val canvas = Canvas(ret)
            val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
            val halfBorderSize = borderSize / 2f
            rectF.inset(halfBorderSize, halfBorderSize)
            canvas.drawRoundRect(rectF, radius, radius, paint)
            if (borderSize > 0) {
                paint.shader = null
                paint.color = borderColor
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = borderSize.toFloat()
                paint.strokeCap = Paint.Cap.ROUND
                canvas.drawRoundRect(rectF, radius, radius, paint)
            }
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 添加圆角边框
         *
         * @param src          源图片
         * @param borderSize   边框尺寸
         * @param color        边框颜色
         * @param cornerRadius 圆角半径
         * @return 圆角边框图
         */
        fun addCornerBorder(src: Bitmap,
                            @IntRange(from = 1) borderSize: Int,
                            @ColorInt color: Int,
                            @FloatRange(from = 0.0) cornerRadius: Float): Bitmap? {
            return addBorder(src, borderSize, color, false, cornerRadius, false)
        }

        /**
         * 添加圆角边框
         *
         * @param src          源图片
         * @param borderSize   边框尺寸
         * @param color        边框颜色
         * @param cornerRadius 圆角半径
         * @param recycle      是否回收
         * @return 圆角边框图
         */
        fun addCornerBorder(src: Bitmap,
                            @IntRange(from = 1) borderSize: Int,
                            @ColorInt color: Int,
                            @FloatRange(from = 0.0) cornerRadius: Float,
                            recycle: Boolean): Bitmap? {
            return addBorder(src, borderSize, color, false, cornerRadius, recycle)
        }

        /**
         * 添加圆形边框
         *
         * @param src        源图片
         * @param borderSize 边框尺寸
         * @param color      边框颜色
         * @return 圆形边框图
         */
        fun addCircleBorder(src: Bitmap,
                            @IntRange(from = 1) borderSize: Int,
                            @ColorInt color: Int): Bitmap? {
            return addBorder(src, borderSize, color, true, 0f, false)
        }

        /**
         * 添加圆形边框
         *
         * @param src        源图片
         * @param borderSize 边框尺寸
         * @param color      边框颜色
         * @param recycle    是否回收
         * @return 圆形边框图
         */
        fun addCircleBorder(src: Bitmap,
                            @IntRange(from = 1) borderSize: Int,
                            @ColorInt color: Int,
                            recycle: Boolean): Bitmap? {
            return addBorder(src, borderSize, color, true, 0f, recycle)
        }

        /**
         * 添加边框
         *
         * @param src          源图片
         * @param borderSize   边框尺寸
         * @param color        边框颜色
         * @param isCircle     是否画圆
         * @param cornerRadius 圆角半径
         * @param recycle      是否回收
         * @return 边框图
         */
        private fun addBorder(src: Bitmap,
                              @IntRange(from = 1) borderSize: Int,
                              @ColorInt color: Int,
                              isCircle: Boolean,
                              cornerRadius: Float,
                              recycle: Boolean): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = if (recycle) src else src.copy(src.config, true)
            val width = ret.width
            val height = ret.height
            val canvas = Canvas(ret)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = color
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderSize.toFloat()
            if (isCircle) {
                val radius = Math.min(width, height) / 2f - borderSize / 2f
                canvas.drawCircle(width / 2f, height / 2f, radius, paint)
            } else {
                val halfBorderSize = borderSize shr 1
                val rectF = RectF(halfBorderSize.toFloat(), halfBorderSize.toFloat(),
                        (width - halfBorderSize).toFloat(), (height - halfBorderSize).toFloat())
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            }
            return ret
        }

        /**
         * 添加倒影
         *
         * @param src              源图片的
         * @param reflectionHeight 倒影高度
         * @param recycle          是否回收
         * @return 带倒影图片
         */
        @JvmOverloads
        fun addReflection(src: Bitmap,
                          reflectionHeight: Int,
                          recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            // 原图与倒影之间的间距
            val REFLECTION_GAP = 0
            val srcWidth = src.width
            val srcHeight = src.height
            val matrix = Matrix()
            matrix.preScale(1f, -1f)
            val reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight - reflectionHeight,
                    srcWidth, reflectionHeight, matrix, false)
            val ret = Bitmap.createBitmap(srcWidth, srcHeight + reflectionHeight, src.config)
            val canvas = Canvas(ret)
            canvas.drawBitmap(src, 0f, 0f, null)
            canvas.drawBitmap(reflectionBitmap, 0f, (srcHeight + REFLECTION_GAP).toFloat(), null)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val shader = LinearGradient(
                    0f, srcHeight.toFloat(),
                    0f, (ret.height + REFLECTION_GAP).toFloat(),
                    0x70FFFFFF,
                    0x00FFFFFF,
                    Shader.TileMode.MIRROR)
            paint.shader = shader
            paint.xfermode = PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN)
            canvas.drawRect(0f, (srcHeight + REFLECTION_GAP).toFloat(), srcWidth.toFloat(), ret.height.toFloat(), paint)
            if (!reflectionBitmap.isRecycled) reflectionBitmap.recycle()
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 添加文字水印
         *
         * @param src      源图片
         * @param content  水印文本
         * @param textSize 水印字体大小
         * @param color    水印字体颜色
         * @param x        起始坐标 x
         * @param y        起始坐标 y
         * @return 带有文字水印的图片
         */
        fun addTextWatermark(src: Bitmap,
                             content: String,
                             textSize: Int,
                             @ColorInt color: Int,
                             x: Float,
                             y: Float): Bitmap? {
            return addTextWatermark(src, content, textSize.toFloat(), color, x, y, false)
        }

        /**
         * 添加文字水印
         *
         * @param src      源图片
         * @param content  水印文本
         * @param textSize 水印字体大小
         * @param color    水印字体颜色
         * @param x        起始坐标 x
         * @param y        起始坐标 y
         * @param recycle  是否回收
         * @return 带有文字水印的图片
         */
        fun addTextWatermark(src: Bitmap,
                             content: String?,
                             textSize: Float,
                             @ColorInt color: Int,
                             x: Float,
                             y: Float,
                             recycle: Boolean): Bitmap? {
            if (isEmptyBitmap(src) || content == null) return null
            val ret = src.copy(src.config, true)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val canvas = Canvas(ret)
            paint.color = color
            paint.textSize = textSize
            val bounds = Rect()
            paint.getTextBounds(content, 0, content.length, bounds)
            canvas.drawText(content, x, y + textSize, paint)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 添加图片水印
         *
         * @param src       源图片
         * @param watermark 图片水印
         * @param x         起始坐标 x
         * @param y         起始坐标 y
         * @param alpha     透明度
         * @param recycle   是否回收
         * @return 带有图片水印的图片
         */
        @JvmOverloads
        fun addImageWatermark(src: Bitmap,
                              watermark: Bitmap,
                              x: Int,
                              y: Int,
                              alpha: Int,
                              recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = src.copy(src.config, true)
            if (!isEmptyBitmap(watermark)) {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                val canvas = Canvas(ret)
                paint.alpha = alpha
                canvas.drawBitmap(watermark, x.toFloat(), y.toFloat(), paint)
            }
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 转为 alpha 位图
         *
         * @param src     源图片
         * @param recycle 是否回收
         * @return alpha 位图
         */
        @JvmOverloads
        fun toAlpha(src: Bitmap, recycle: Boolean? = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = src.extractAlpha()
            if (recycle!! && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 转为灰度图片
         *
         * @param src     源图片
         * @param recycle 是否回收
         * @return 灰度图
         */
        @JvmOverloads
        fun toGray(src: Bitmap, recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val ret = Bitmap.createBitmap(src.width, src.height, src.config)
            val canvas = Canvas(ret)
            val paint = Paint()
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)
            val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = colorMatrixColorFilter
            canvas.drawBitmap(src, 0f, 0f, paint)
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * 快速模糊图片
         *
         * 先缩小原图，对小图进行模糊，再放大回原先尺寸
         *
         * @param src     源图片
         * @param scale   缩放比例(0...1)
         * @param radius  模糊半径(0...25)
         * @param recycle 是否回收
         * @return 模糊后的图片
         */
        @JvmOverloads
        fun fastBlur(src: Bitmap,
                     @FloatRange(from = 0.0, to = 1.0, fromInclusive = false)
                     scale: Float,
                     @FloatRange(from = 0.0, to = 25.0, fromInclusive = false)
                     radius: Float,
                     recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val width = src.width
            val height = src.height
            val matrix = Matrix()
            matrix.setScale(scale, scale)
            var scaleBitmap = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
            val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
            val canvas = Canvas()
            val filter = PorterDuffColorFilter(
                    Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
            paint.colorFilter = filter
            canvas.scale(scale, scale)
            canvas.drawBitmap(scaleBitmap, 0f, 0f, paint)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                scaleBitmap = renderScriptBlur(scaleBitmap, radius, recycle)
            } else {
                scaleBitmap = stackBlur(scaleBitmap, radius.toInt(), recycle)
            }
            if (scale == 1f) {
                if (recycle && !src.isRecycled) src.recycle()
                return scaleBitmap
            }
            val ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true)
            if (!scaleBitmap.isRecycled) scaleBitmap.recycle()
            if (recycle && !src.isRecycled) src.recycle()
            return ret
        }

        /**
         * renderScript 模糊图片
         *
         * API 大于 17
         *
         * @param src     源图片
         * @param radius  模糊半径(0...25)
         * @param recycle 是否回收
         * @return 模糊后的图片
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @JvmOverloads
        fun renderScriptBlur(src: Bitmap,
                             @FloatRange(from = 0.0, to = 25.0, fromInclusive = false)
                             radius: Float,
                             recycle: Boolean = false): Bitmap {
            var rs: RenderScript? = null
            val ret = if (recycle) src else src.copy(src.config, true)
            try {
                rs = RenderScript.create(Utils.app)
                rs!!.messageHandler = RenderScript.RSMessageHandler()
                val input = Allocation.createFromBitmap(rs,
                        ret,
                        Allocation.MipmapControl.MIPMAP_NONE,
                        Allocation.USAGE_SCRIPT)
                val output = Allocation.createTyped(rs, input.type)
                val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
                blurScript.setInput(input)
                blurScript.setRadius(radius)
                blurScript.forEach(output)
                output.copyTo(ret)
            } finally {
                if (rs != null) {
                    rs.destroy()
                }
            }
            return ret
        }

        /**
         * stack 模糊图片
         *
         * @param src     源图片
         * @param radius  模糊半径
         * @param recycle 是否回收
         * @return stack 模糊后的图片
         */
        @JvmOverloads
        fun stackBlur(src: Bitmap, radius: Int, recycle: Boolean = false): Bitmap {
            var radius = radius
            val ret = if (recycle) src else src.copy(src.config, true)
            if (radius < 1) {
                radius = 1
            }
            val w = ret.width
            val h = ret.height

            val pix = IntArray(w * h)
            ret.getPixels(pix, 0, w, 0, 0, w, h)

            val wm = w - 1
            val hm = h - 1
            val wh = w * h
            val div = radius + radius + 1

            val r = IntArray(wh)
            val g = IntArray(wh)
            val b = IntArray(wh)
            var rsum: Int
            var gsum: Int
            var bsum: Int
            var x: Int
            var y: Int
            var i: Int
            var p: Int
            var yp: Int
            var yi: Int
            var yw: Int
            val vmin = IntArray(Math.max(w, h))

            var divsum = div + 1 shr 1
            divsum *= divsum
            val dv = IntArray(256 * divsum)
            i = 0
            while (i < 256 * divsum) {
                dv[i] = i / divsum
                i++
            }

            yi = 0
            yw = yi

            val stack = Array(div) { IntArray(3) }
            var stackpointer: Int
            var stackstart: Int
            var sir: IntArray
            var rbs: Int
            val r1 = radius + 1
            var routsum: Int
            var goutsum: Int
            var boutsum: Int
            var rinsum: Int
            var ginsum: Int
            var binsum: Int

            y = 0
            while (y < h) {
                bsum = 0
                gsum = bsum
                rsum = gsum
                boutsum = rsum
                goutsum = boutsum
                routsum = goutsum
                binsum = routsum
                ginsum = binsum
                rinsum = ginsum
                i = -radius
                while (i <= radius) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))]
                    sir = stack[i + radius]
                    sir[0] = p and 0xff0000 shr 16
                    sir[1] = p and 0x00ff00 shr 8
                    sir[2] = p and 0x0000ff
                    rbs = r1 - Math.abs(i)
                    rsum += sir[0] * rbs
                    gsum += sir[1] * rbs
                    bsum += sir[2] * rbs
                    if (i > 0) {
                        rinsum += sir[0]
                        ginsum += sir[1]
                        binsum += sir[2]
                    } else {
                        routsum += sir[0]
                        goutsum += sir[1]
                        boutsum += sir[2]
                    }
                    i++
                }
                stackpointer = radius

                x = 0
                while (x < w) {

                    r[yi] = dv[rsum]
                    g[yi] = dv[gsum]
                    b[yi] = dv[bsum]

                    rsum -= routsum
                    gsum -= goutsum
                    bsum -= boutsum

                    stackstart = stackpointer - radius + div
                    sir = stack[stackstart % div]

                    routsum -= sir[0]
                    goutsum -= sir[1]
                    boutsum -= sir[2]

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm)
                    }
                    p = pix[yw + vmin[x]]

                    sir[0] = p and 0xff0000 shr 16
                    sir[1] = p and 0x00ff00 shr 8
                    sir[2] = p and 0x0000ff

                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]

                    rsum += rinsum
                    gsum += ginsum
                    bsum += binsum

                    stackpointer = (stackpointer + 1) % div
                    sir = stack[stackpointer % div]

                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]

                    rinsum -= sir[0]
                    ginsum -= sir[1]
                    binsum -= sir[2]

                    yi++
                    x++
                }
                yw += w
                y++
            }
            x = 0
            while (x < w) {
                bsum = 0
                gsum = bsum
                rsum = gsum
                boutsum = rsum
                goutsum = boutsum
                routsum = goutsum
                binsum = routsum
                ginsum = binsum
                rinsum = ginsum
                yp = -radius * w
                i = -radius
                while (i <= radius) {
                    yi = Math.max(0, yp) + x

                    sir = stack[i + radius]

                    sir[0] = r[yi]
                    sir[1] = g[yi]
                    sir[2] = b[yi]

                    rbs = r1 - Math.abs(i)

                    rsum += r[yi] * rbs
                    gsum += g[yi] * rbs
                    bsum += b[yi] * rbs

                    if (i > 0) {
                        rinsum += sir[0]
                        ginsum += sir[1]
                        binsum += sir[2]
                    } else {
                        routsum += sir[0]
                        goutsum += sir[1]
                        boutsum += sir[2]
                    }

                    if (i < hm) {
                        yp += w
                    }
                    i++
                }
                yi = x
                stackpointer = radius
                y = 0
                while (y < h) {
                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] = -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                    rsum -= routsum
                    gsum -= goutsum
                    bsum -= boutsum

                    stackstart = stackpointer - radius + div
                    sir = stack[stackstart % div]

                    routsum -= sir[0]
                    goutsum -= sir[1]
                    boutsum -= sir[2]

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w
                    }
                    p = x + vmin[y]

                    sir[0] = r[p]
                    sir[1] = g[p]
                    sir[2] = b[p]

                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]

                    rsum += rinsum
                    gsum += ginsum
                    bsum += binsum

                    stackpointer = (stackpointer + 1) % div
                    sir = stack[stackpointer]

                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]

                    rinsum -= sir[0]
                    ginsum -= sir[1]
                    binsum -= sir[2]

                    yi += w
                    y++
                }
                x++
            }
            ret.setPixels(pix, 0, w, 0, 0, w, h)
            return ret
        }

        /**
         * 保存图片
         *
         * @param src      源图片
         * @param filePath 要保存到的文件路径
         * @param format   格式
         * @return `true`: 成功<br></br>`false`: 失败
         */
        fun save(src: Bitmap,
                 filePath: String,
                 format: CompressFormat): Boolean {
            return save(src, getFileByPath(filePath), format, false)
        }

        /**
         * 保存图片
         *
         * @param src      源图片
         * @param filePath 要保存到的文件路径
         * @param format   格式
         * @param recycle  是否回收
         * @return `true`: 成功<br></br>`false`: 失败
         */
        fun save(src: Bitmap,
                 filePath: String,
                 format: CompressFormat,
                 recycle: Boolean): Boolean {
            return save(src, getFileByPath(filePath), format, recycle)
        }

        /**
         * 保存图片
         *
         * @param src     源图片
         * @param file    要保存到的文件
         * @param format  格式
         * @param recycle 是否回收
         * @return `true`: 成功<br></br>`false`: 失败
         */
        @JvmOverloads
        fun save(src: Bitmap,
                 file: File?,
                 format: CompressFormat,
                 recycle: Boolean = false): Boolean {
            if (isEmptyBitmap(src) || !createFileByDeleteOldFile(file)) return false
            var os: OutputStream? = null
            var ret = false
            try {
                os = BufferedOutputStream(FileOutputStream(file!!))
                ret = src.compress(format, 100, os)
                if (recycle && !src.isRecycled) src.recycle()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                CloseUtils.closeIO(os)
            }
            return ret
        }

        /**
         * 根据文件名判断文件是否为图片
         *
         * @param file 　文件
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isImage(file: File?): Boolean {
            return file != null && isImage(file.path)
        }

        /**
         * 根据文件名判断文件是否为图片
         *
         * @param filePath 　文件路径
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isImage(filePath: String): Boolean {
            val path = filePath.toUpperCase()
            return (path.endsWith(".PNG") || path.endsWith(".JPG")
                    || path.endsWith(".JPEG") || path.endsWith(".BMP")
                    || path.endsWith(".GIF"))
        }

        /**
         * 获取图片类型
         *
         * @param filePath 文件路径
         * @return 图片类型
         */
        fun getImageType(filePath: String): String? {
            return getImageType(getFileByPath(filePath))
        }

        /**
         * 获取图片类型
         *
         * @param file 文件
         * @return 图片类型
         */
        fun getImageType(file: File?): String? {
            if (file == null) return null
            var `is`: InputStream? = null
            try {
                `is` = FileInputStream(file)
                return getImageType(`is`)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                CloseUtils.closeIO(`is`)
            }
        }

        /**
         * 流获取图片类型
         *
         * @param is 图片输入流
         * @return 图片类型
         */
        fun getImageType(`is`: InputStream?): String? {
            if (`is` == null) return null
            try {
                val bytes = ByteArray(8)
                return if (`is`.read(bytes, 0, 8) != -1) getImageType(bytes) else null
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * 获取图片类型
         *
         * @param bytes bitmap 的前 8 字节
         * @return 图片类型
         */
        fun getImageType(bytes: ByteArray): String? {
            if (isJPEG(bytes)) return "JPEG"
            if (isGIF(bytes)) return "GIF"
            if (isPNG(bytes)) return "PNG"
            return if (isBMP(bytes)) "BMP" else null
        }

        private fun isJPEG(b: ByteArray): Boolean {
            return (b.size >= 2
                    && b[0] == 0xFF.toByte() && b[1] == 0xD8.toByte())
        }

        private fun isGIF(b: ByteArray): Boolean {
            return (b.size >= 6
                    && b[0] == 'G'.toByte() && b[1] == 'I'.toByte()
                    && b[2] == 'F'.toByte() && b[3] == '8'.toByte()
                    && (b[4] == '7'.toByte() || b[4] == '9'.toByte()) && b[5] == 'a'.toByte())
        }

        private fun isPNG(b: ByteArray): Boolean {
            return b.size >= 8 && (b[0] == 137.toByte() && b[1] == 80.toByte()
                    && b[2] == 78.toByte() && b[3] == 71.toByte()
                    && b[4] == 13.toByte() && b[5] == 10.toByte()
                    && b[6] == 26.toByte() && b[7] == 10.toByte())
        }

        private fun isBMP(b: ByteArray): Boolean {
            return (b.size >= 2
                    && b[0].toInt() == 0x42 && b[1].toInt() == 0x4d)
        }

        /**
         * 判断 bitmap 对象是否为空
         *
         * @param src 源图片
         * @return `true`: 是<br></br>`false`: 否
         */
        private fun isEmptyBitmap(src: Bitmap?): Boolean {
            return src == null || src.width == 0 || src.height == 0
        }

        ///////////////////////////////////////////////////////////////////////////
        // 下方和压缩有关
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 按缩放压缩
         *
         * @param src       源图片
         * @param newWidth  新宽度
         * @param newHeight 新高度
         * @return 缩放压缩后的图片
         */
        fun compressByScale(src: Bitmap,
                            newWidth: Int,
                            newHeight: Int): Bitmap? {
            return scale(src, newWidth, newHeight, false)
        }

        /**
         * 按缩放压缩
         *
         * @param src       源图片
         * @param newWidth  新宽度
         * @param newHeight 新高度
         * @param recycle   是否回收
         * @return 缩放压缩后的图片
         */
        fun compressByScale(src: Bitmap,
                            newWidth: Int,
                            newHeight: Int,
                            recycle: Boolean): Bitmap? {
            return scale(src, newWidth, newHeight, recycle)
        }

        /**
         * 按缩放压缩
         *
         * @param src         源图片
         * @param scaleWidth  缩放宽度倍数
         * @param scaleHeight 缩放高度倍数
         * @return 缩放压缩后的图片
         */
        fun compressByScale(src: Bitmap,
                            scaleWidth: Float,
                            scaleHeight: Float): Bitmap? {
            return scale(src, scaleWidth, scaleHeight, false)
        }

        /**
         * 按缩放压缩
         *
         * @param src         源图片
         * @param scaleWidth  缩放宽度倍数
         * @param scaleHeight 缩放高度倍数
         * @param recycle     是否回收
         * @return 缩放压缩后的图片
         */
        fun compressByScale(src: Bitmap,
                            scaleWidth: Float,
                            scaleHeight: Float,
                            recycle: Boolean): Bitmap? {
            return scale(src, scaleWidth, scaleHeight, recycle)
        }

        /**
         * 按质量压缩
         *
         * @param src     源图片
         * @param quality 质量
         * @param recycle 是否回收
         * @return 质量压缩后的图片
         */
        @JvmOverloads
        fun compressByQuality(src: Bitmap,
                              @IntRange(from = 0, to = 100) quality: Int,
                              recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val baos = ByteArrayOutputStream()
            src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            val bytes = baos.toByteArray()
            if (recycle && !src.isRecycled) src.recycle()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        /**
         * 按质量压缩
         *
         * @param src         源图片
         * @param maxByteSize 允许最大值字节数
         * @param recycle     是否回收
         * @return 质量压缩压缩过的图片
         */
        @JvmOverloads
        fun compressByQuality(src: Bitmap,
                              maxByteSize: Long,
                              recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src) || maxByteSize <= 0) return null
            val baos = ByteArrayOutputStream()
            src.compress(CompressFormat.JPEG, 100, baos)
            val bytes: ByteArray
            if (baos.size() <= maxByteSize) {// 最好质量的不大于最大字节，则返回最佳质量
                bytes = baos.toByteArray()
            } else {
                baos.reset()
                src.compress(CompressFormat.JPEG, 0, baos)
                if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节，则返回最差质量
                    bytes = baos.toByteArray()
                } else {
                    // 二分法寻找最佳质量
                    var st = 0
                    var end = 100
                    var mid = 0
                    while (st < end) {
                        mid = (st + end) / 2
                        baos.reset()
                        src.compress(CompressFormat.JPEG, mid, baos)
                        val len = baos.size()
                        if (len.toLong() == maxByteSize) {
                            break
                        } else if (len > maxByteSize) {
                            end = mid - 1
                        } else {
                            st = mid + 1
                        }
                    }
                    if (end == mid - 1) {
                        baos.reset()
                        src.compress(CompressFormat.JPEG, st, baos)
                    }
                    bytes = baos.toByteArray()
                }
            }
            if (recycle && !src.isRecycled) src.recycle()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        /**
         * 按采样大小压缩
         *
         * @param src        源图片
         * @param sampleSize 采样率大小
         * @param recycle    是否回收
         * @return 按采样率压缩后的图片
         */
        @JvmOverloads
        fun compressBySampleSize(src: Bitmap,
                                 sampleSize: Int,
                                 recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize
            val baos = ByteArrayOutputStream()
            src.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()
            if (recycle && !src.isRecycled) src.recycle()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        }

        /**
         * 按采样大小压缩
         *
         * @param src       源图片
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @param recycle   是否回收
         * @return 按采样率压缩后的图片
         */
        @JvmOverloads
        fun compressBySampleSize(src: Bitmap,
                                 maxWidth: Int,
                                 maxHeight: Int,
                                 recycle: Boolean = false): Bitmap? {
            if (isEmptyBitmap(src)) return null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val baos = ByteArrayOutputStream()
            src.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val bytes = baos.toByteArray()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            if (recycle && !src.isRecycled) src.recycle()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        }

        private fun getFileByPath(filePath: String): File? {
            return if (isSpace(filePath)) null else File(filePath)
        }

        private fun createFileByDeleteOldFile(file: File?): Boolean {
            if (file == null) return false
            if (file.exists() && !file.delete()) return false
            if (!createOrExistsDir(file.parentFile)) return false
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        }

        private fun createOrExistsDir(file: File?): Boolean {
            // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
            return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
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

        /**
         * 计算采样大小
         *
         * @param options   选项
         * @param maxWidth  最大宽度
         * @param maxHeight 最大高度
         * @return 采样大小
         */
        private fun calculateInSampleSize(options: BitmapFactory.Options,
                                          maxWidth: Int,
                                          maxHeight: Int): Int {
            var height = options.outHeight
            var width = options.outWidth
            var inSampleSize = 1
            while ((width = width shr 1) >= maxWidth && (height = height shr 1) >= maxHeight) {
                inSampleSize = inSampleSize shl 1
            }
            return inSampleSize
        }

        private fun input2Byte(`is`: InputStream?): ByteArray? {
            if (`is` == null) return null
            try {
                val os = ByteArrayOutputStream()
                val b = ByteArray(MemoryConstants.KB)
                var len: Int
                while ((len = `is`.read(b, 0, MemoryConstants.KB)) != -1) {
                    os.write(b, 0, len)
                }
                return os.toByteArray()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                CloseUtils.closeIO(`is`)
            }
        }
    }
}
/**
 * 缩放图片
 *
 * @param src       源图片
 * @param newWidth  新宽度
 * @param newHeight 新高度
 * @return 缩放后的图片
 */
/**
 * 缩放图片
 *
 * @param src         源图片
 * @param scaleWidth  缩放宽度倍数
 * @param scaleHeight 缩放高度倍数
 * @return 缩放后的图片
 */
/**
 * 裁剪图片
 *
 * @param src    源图片
 * @param x      开始坐标 x
 * @param y      开始坐标 y
 * @param width  裁剪宽度
 * @param height 裁剪高度
 * @return 裁剪后的图片
 */
/**
 * 倾斜图片
 *
 * @param src 源图片
 * @param kx  倾斜因子 x
 * @param ky  倾斜因子 y
 * @return 倾斜后的图片
 */
/**
 * 倾斜图片
 *
 * @param src 源图片
 * @param kx  倾斜因子 x
 * @param ky  倾斜因子 y
 * @param px  平移因子 x
 * @param py  平移因子 y
 * @return 倾斜后的图片
 */
/**
 * 旋转图片
 *
 * @param src     源图片
 * @param degrees 旋转角度
 * @param px      旋转点横坐标
 * @param py      旋转点纵坐标
 * @return 旋转后的图片
 */
/**
 * 转为圆形图片
 *
 * @param src 源图片
 * @return 圆形图片
 */
/**
 * 转为圆形图片
 *
 * @param src         源图片
 * @param borderSize  边框尺寸
 * @param borderColor 边框颜色
 * @return 圆形图片
 */
/**
 * 转为圆角图片
 *
 * @param src    源图片
 * @param radius 圆角的度数
 * @return 圆角图片
 */
/**
 * 转为圆角图片
 *
 * @param src         源图片
 * @param radius      圆角的度数
 * @param borderSize  边框尺寸
 * @param borderColor 边框颜色
 * @return 圆角图片
 */
/**
 * 添加倒影
 *
 * @param src              源图片的
 * @param reflectionHeight 倒影高度
 * @return 带倒影图片
 */
/**
 * 添加图片水印
 *
 * @param src       源图片
 * @param watermark 图片水印
 * @param x         起始坐标 x
 * @param y         起始坐标 y
 * @param alpha     透明度
 * @return 带有图片水印的图片
 */
/**
 * 转为 alpha 位图
 *
 * @param src 源图片
 * @return alpha 位图
 */
/**
 * 转为灰度图片
 *
 * @param src 源图片
 * @return 灰度图
 */
/**
 * 快速模糊
 *
 * 先缩小原图，对小图进行模糊，再放大回原先尺寸
 *
 * @param src    源图片
 * @param scale  缩放比例(0...1)
 * @param radius 模糊半径
 * @return 模糊后的图片
 */
/**
 * renderScript 模糊图片
 *
 * API 大于 17
 *
 * @param src    源图片
 * @param radius 模糊半径(0...25)
 * @return 模糊后的图片
 */
/**
 * stack 模糊图片
 *
 * @param src    源图片
 * @param radius 模糊半径
 * @return stack 模糊后的图片
 */
/**
 * 保存图片
 *
 * @param src    源图片
 * @param file   要保存到的文件
 * @param format 格式
 * @return `true`: 成功<br></br>`false`: 失败
 */
/**
 * 按质量压缩
 *
 * @param src     源图片
 * @param quality 质量
 * @return 质量压缩后的图片
 */
/**
 * 按质量压缩
 *
 * @param src         源图片
 * @param maxByteSize 允许最大值字节数
 * @return 质量压缩压缩过的图片
 */
/**
 * 按采样大小压缩
 *
 * @param src        源图片
 * @param sampleSize 采样率大小
 * @return 按采样率压缩后的图片
 */
/**
 * 按采样大小压缩
 *
 * @param src       源图片
 * @param maxWidth  最大宽度
 * @param maxHeight 最大高度
 * @return 按采样率压缩后的图片
 */
