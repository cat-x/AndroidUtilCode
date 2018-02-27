package com.blankj.utilcode.util

import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 尺寸相关工具类
</pre> *
 */
object SizeUtils {


    /**
     * 获取到 View 尺寸的监听
     */
    interface OnGetSizeListener {
        fun onGetSize(view: View)
    }


    /**
     * dp 转 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    fun dp2px(dpValue: Float): Int {
        val scale = Utils.app.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px 转 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    fun px2dp(pxValue: Float): Int {
        val scale = Utils.app.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp 转 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = Utils.app.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px 转 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = Utils.app.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 各种单位转换
     *
     * 该方法存在于 TypedValue
     *
     * @param unit    单位
     * @param value   值
     * @param metrics DisplayMetrics
     * @return 转换结果
     */
    fun applyDimension(unit: Int,
                       value: Float,
                       metrics: DisplayMetrics): Float {
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0f
    }

    /**
     * 在 onCreate 中获取视图的尺寸
     *
     * 需回调 OnGetSizeListener 接口，在 onGetSize 中获取 view 宽高
     *
     * 用法示例如下所示
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.OnGetSizeListener() {
     * Override
     * public void onGetSize(final View view) {
     * view.getWidth();
     * }
     * });
    </pre> *
     *
     * @param view     视图
     * @param listener 监听器
     */
    fun forceGetViewSize(view: View, listener: OnGetSizeListener?) {
        view.post {
            listener?.onGetSize(view)
        }
    }

    /**
     * 测量视图尺寸
     *
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    fun measureView(view: View): IntArray {
        var lp: ViewGroup.LayoutParams? = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
        val lpHeight = lp.height
        val heightSpec: Int
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        view.measure(widthSpec, heightSpec)
        return intArrayOf(view.measuredWidth, view.measuredHeight)
    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     * @return 视图宽度
     */
    fun getMeasuredWidth(view: View): Int {
        return measureView(view)[0]
    }

    /**
     * 获取测量视图高度
     *
     * @param view 视图
     * @return 视图高度
     */
    fun getMeasuredHeight(view: View): Int {
        return measureView(view)[1]
    }
}

