package com.blankj.androidutilcode.feature.core.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.Window
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Activity 工具类 Demo
</pre> *
 */
class SubActivityActivity : BaseBackActivity() {

    internal var random = Random()

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        return R.layout.activity_activity_sub
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        (flActivityContainer.parent as View).setBackgroundColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAfterTransition(this)
    }
}
