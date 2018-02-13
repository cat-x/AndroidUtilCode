package com.blankj.androidutilcode.feature.sub.pinyin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.subutil.util.PinyinUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : Pinyin 工具类 Demo
</pre> *
 */
class PinyinActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_pinyin
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_pinyin)

        val tvAboutPinyin = findViewById<TextView>(R.id.tv_about_pinyin)

        val surnames = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑"
        val size = surnames.length
        val sb = StringBuilder("汉字转拼音: " + PinyinUtils.ccs2Pinyin("汉字转拼音", " ")
                + "\n获取首字母: " + PinyinUtils.getPinyinFirstLetters("获取首字母", " ")
                + "\n\n测试姓氏"
                + "\n澹台: " + PinyinUtils.getSurnamePinyin("澹台")
                + "\n尉迟: " + PinyinUtils.getSurnamePinyin("尉迟")
                + "\n万俟: " + PinyinUtils.getSurnamePinyin("万俟")
                + "\n单于: " + PinyinUtils.getSurnamePinyin("单于"))
        for (i in 0 until size) {
            val surname = surnames[i].toString()
            sb.append(String.format(
                    "\n%s 正确: %-6s 错误: %-6s",
                    surname,
                    PinyinUtils.getSurnamePinyin(surname),
                    PinyinUtils.ccs2Pinyin(surname)
            ))
        }
        tvAboutPinyin.text = sb.toString()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, PinyinActivity::class.java)
            context.startActivity(starter)
        }
    }
}
