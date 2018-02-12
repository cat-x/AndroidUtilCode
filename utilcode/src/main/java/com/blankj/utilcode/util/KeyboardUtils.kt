package com.blankj.utilcode.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 键盘相关工具类
</pre> *
 */
class KeyboardUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    interface OnSoftInputChangedListener {
        fun onSoftInputChanged(height: Int)
    }

    companion object {

        private var sContentViewInvisibleHeightPre: Int = 0

        /*
      避免输入法面板遮挡
      <p>在 manifest.xml 中 activity 中设置</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

        /**
         * 动态显示软键盘
         *
         * @param activity activity
         */
        fun showSoftInput(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            var view = activity.currentFocus
            if (view == null) view = View(activity)
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        /**
         * 动态显示软键盘
         *
         * @param view 视图
         */
        fun showSoftInput(view: View) {
            val imm = Utils.app.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        /**
         * 动态隐藏软键盘
         *
         * @param activity activity
         */
        fun hideSoftInput(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            var view = activity.currentFocus
            if (view == null) view = View(activity)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * 动态隐藏软键盘
         *
         * @param view 视图
         */
        fun hideSoftInput(view: View) {
            val imm = Utils.app.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * 切换软键盘显示与否状态
         */
        fun toggleSoftInput() {
            val imm = Utils.app.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        /**
         * 判断软键盘是否可见
         *
         * @param activity             activity
         * @param minHeightOfSoftInput 软键盘最小高度
         * @return `true`: 可见<br></br>`false`: 不可见
         */
        @JvmOverloads
        fun isSoftInputVisible(activity: Activity,
                               minHeightOfSoftInput: Int = 200): Boolean {
            return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput
        }

        private fun getContentViewInvisibleHeight(activity: Activity): Int {
            val contentView = activity.findViewById<View>(android.R.id.content)
            val r = Rect()
            contentView.getWindowVisibleDisplayFrame(r)
            return contentView.rootView.height - r.height()
        }

        /**
         * 注册软键盘改变监听器
         *
         * @param activity activity
         * @param listener listener
         */
        fun registerSoftInputChangedListener(activity: Activity,
                                             listener: OnSoftInputChangedListener?) {
            val contentView = activity.findViewById<View>(android.R.id.content)
            sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity)
            contentView.viewTreeObserver.addOnGlobalLayoutListener {
                if (listener != null) {
                    val height = getContentViewInvisibleHeight(activity)
                    if (sContentViewInvisibleHeightPre != height) {
                        listener.onSoftInputChanged(height)
                        sContentViewInvisibleHeightPre = height
                    }
                }
            }
        }

        /**
         * 修复软键盘内存泄漏
         *
         * 在[Activity.onDestroy]中使用
         *
         * @param context context
         */
        fun fixSoftInputLeaks(context: Context?) {
            if (context == null) return
            val imm = Utils.app.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    ?: return
            val strArr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
            for (i in 0..2) {
                try {
                    val declaredField = imm.javaClass.getDeclaredField(strArr[i]) ?: continue
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true)
                    }
                    val obj = declaredField.get(imm)
                    if (obj == null || obj !is View) continue
                    val view = obj as View
                    if (view.context === context) {
                        declaredField.set(imm, null)
                    } else {
                        return
                    }
                } catch (th: Throwable) {
                    th.printStackTrace()
                }

            }
        }

        /**
         * 点击屏幕空白区域隐藏软键盘
         *
         * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
         *
         * 需重写 dispatchTouchEvent
         *
         * 参照以下注释代码
         */
        fun clickBlankArea2HideSoftInput() {
            Log.i("KeyboardUtils", "Please refer to the following code.")
            /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
        }
    }
}
/**
 * 判断软键盘是否可见
 *
 * 默认软键盘最小高度为 200
 *
 * @param activity activity
 * @return `true`: 可见<br></br>`false`: 不可见
 */
