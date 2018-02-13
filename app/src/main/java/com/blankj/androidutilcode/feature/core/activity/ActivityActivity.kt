package com.blankj.androidutilcode.feature.core.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.blankj.androidutilcode.Config
import com.blankj.androidutilcode.MainActivity
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.feature.core.CoreUtilActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Activity 工具类 Demo
</pre> *
 */
class ActivityActivity : BaseBackActivity() {

    internal lateinit var viewSharedElement: ImageView
    internal var random = Random()
    private var bitmap: Bitmap? = null
    private var mIntent: Intent? = null
    private val intents = arrayOfNulls<Intent>(2)

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        return R.layout.activity_activity
    }


    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_activity)
        viewSharedElement = findViewById(R.id.view_shared_element)
        findViewById<View>(R.id.btn_clz).setOnClickListener(this)
        findViewById<View>(R.id.btn_clz_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_clz_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_clz).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_clz_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_clz_shared_element).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_clz_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_pkg_cls).setOnClickListener(this)
        findViewById<View>(R.id.btn_pkg_cls_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_pkg_cls_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_pkg_cls).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_pkg_cls_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_pkg_cls_shared_element).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_pkg_cls_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_intent).setOnClickListener(this)
        findViewById<View>(R.id.btn_intent_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_intent_shared_element).setOnClickListener(this)
        findViewById<View>(R.id.btn_intent_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_intents).setOnClickListener(this)
        findViewById<View>(R.id.btn_intents_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_intents_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_intents).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_intents_opt).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_intents_anim).setOnClickListener(this)
        findViewById<View>(R.id.btn_act_clz_shared_element).setOnClickListener(this)
        findViewById<View>(R.id.btn_start_home_activity).setOnClickListener(this)
        findViewById<View>(R.id.btn_finish_activity).setOnClickListener(this)
        findViewById<View>(R.id.btn_finish_to_activity).setOnClickListener(this)
        findViewById<View>(R.id.btn_finish_all_activities).setOnClickListener(this)
        val tvAboutActivity = findViewById<TextView>(R.id.tv_about_activity)
        tvAboutActivity.text = SpanUtils()
                .appendLine("Is SubActivityActivity Exists: " + ActivityUtils.isActivityExists(Config.PKG, SubActivityActivity::class.java.name))
                .appendLine("getLauncherActivity: " + ActivityUtils.getLauncherActivity(Config.PKG))
                .appendLine("getTopActivity: " + ActivityUtils.topActivity!!)
                .appendLine("getTopActivity: " + ActivityUtils.topActivity!!)
                .appendLine("Is CoreUtilActivity Exists In Stack: " + ActivityUtils.isActivityExistsInStack(CoreUtilActivity::class.java))
                .append("getActivityIcon: ")
                .appendImage(ActivityUtils.getActivityIcon(ActivityActivity::class.java)!!, SpanUtils.ALIGN_CENTER)
                .appendLine()
                .append("getActivityLogo: ")
                .appendImage(ActivityUtils.getActivityLogo(ActivityActivity::class.java)!!, SpanUtils.ALIGN_CENTER)
                .create()
        bitmap = (viewSharedElement.drawable as BitmapDrawable).bitmap

        mIntent = Intent(this, SubActivityActivity::class.java)
        intents[0] = mIntent
        intents[1] = Intent(this, SubActivityActivity::class.java)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_clz -> ActivityUtils.startActivity(SubActivityActivity::class.java)
            R.id.btn_clz_opt -> ActivityUtils.startActivity(SubActivityActivity::class.java,
                    getOption(random.nextInt(5)))
            R.id.btn_clz_anim -> ActivityUtils.startActivity(SubActivityActivity::class.java,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_act_clz -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java)
            R.id.btn_act_clz_opt -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    getOption(random.nextInt(5)))

            R.id.btn_act_clz_shared_element -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    viewSharedElement)
            R.id.btn_act_clz_anim -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_pkg_cls -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name)
            R.id.btn_pkg_cls_opt -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name,
                    getOption(random.nextInt(5)))
            R.id.btn_pkg_cls_anim -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_act_pkg_cls -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name)
            R.id.btn_act_pkg_cls_opt -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    getOption(random.nextInt(5)))
            R.id.btn_act_pkg_cls_shared_element -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    viewSharedElement)
            R.id.btn_act_pkg_cls_anim -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_intent -> ActivityUtils.startActivity(this,
                    mIntent!!)
            R.id.btn_intent_opt -> ActivityUtils.startActivity(this,
                    mIntent!!,
                    getOption(random.nextInt(5)))
            R.id.btn_intent_shared_element -> ActivityUtils.startActivity(this,
                    mIntent!!,
                    viewSharedElement)
            R.id.btn_intent_anim -> ActivityUtils.startActivity(this,
                    mIntent!!,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_intents -> ActivityUtils.startActivities(intents as Array<Intent>)
            R.id.btn_intents_opt -> ActivityUtils.startActivities(intents as Array<Intent>,
                    getOption(random.nextInt(5)))
            R.id.btn_intents_anim -> ActivityUtils.startActivities(intents as Array<Intent>,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_act_intents -> ActivityUtils.startActivities(this,
                    intents as Array<Intent>,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_act_intents_opt -> ActivityUtils.startActivities(this,
                    intents as Array<Intent>,
                    getOption(random.nextInt(5)))
            R.id.btn_act_intents_anim -> ActivityUtils.startActivities(this,
                    intents as Array<Intent>,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.btn_start_home_activity -> ActivityUtils.startHomeActivity()
            R.id.btn_finish_activity -> ActivityUtils.finishActivity(MainActivity::class.java)
            R.id.btn_finish_to_activity -> ActivityUtils.finishToActivity(MainActivity::class.java, false, true)
            R.id.btn_finish_all_activities -> ActivityUtils.finishAllActivities()
        }
    }

    private fun getOption(type: Int): Bundle? {
        LogUtils.d(type)
        when (type) {
            0 -> return ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.slide_in_right_1000,
                    R.anim.slide_out_left_1000)
                    .toBundle()
            1 -> return ActivityOptionsCompat.makeScaleUpAnimation(viewSharedElement,
                    viewSharedElement.width / 2,
                    viewSharedElement.height / 2,
                    0, 0)
                    .toBundle()
            2 -> return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(viewSharedElement,
                    bitmap,
                    0, 0)
                    .toBundle()
            3 -> return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    viewSharedElement,
                    getString(R.string.activity_shared_element))
                    .toBundle()
            4 -> return ActivityOptionsCompat.makeClipRevealAnimation(viewSharedElement,
                    viewSharedElement.width / 2,
                    viewSharedElement.height / 2,
                    0, 0)
                    .toBundle()
            else -> return null
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ActivityActivity::class.java)
            context.startActivity(starter)
        }
    }
}
