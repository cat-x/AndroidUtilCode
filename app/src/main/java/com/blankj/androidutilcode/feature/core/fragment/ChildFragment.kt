package com.blankj.androidutilcode.feature.core.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseFragment
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  :
</pre> *
 */
class ChildFragment : BaseFragment(), FragmentUtils.OnBackClickListener {

    private var tvAboutFragment: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.fragment_child
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        val random = Random()
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
        if (view != null) {
            view.findViewById<View>(R.id.btn_show_about_fragment).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_pop).setOnClickListener(this)
            tvAboutFragment = view.findViewById<View>(R.id.tv_about_fragment) as TextView
        }
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        tvAboutFragment!!.text = ""
        when (view.id) {
            R.id.btn_show_about_fragment -> tvAboutFragment!!.text = ("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fragmentManager))
                    + "\ntopInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fragmentManager))
                    + "\ntopShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fragmentManager))
                    + "\ntopShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fragmentManager))
                    + "\n---all of fragments---\n"
                    + FragmentUtils.getAllFragments(fragmentManager).toString()
                    + "\n----------------------\n\n"
                    + "---stack top---\n"
                    + FragmentUtils.getAllFragmentsInStack(fragmentManager).toString()
                    + "\n---stack bottom---\n\n")
        }//            case R.id.btn_pop:
        //                FragmentUtils.popFragment(getFragmentManager());
        //                break;
    }

    override fun onBackClick(): Boolean {
        LogUtils.d("demo2 onBackClick")
        return false
    }

    companion object {

        fun newInstance(): ChildFragment {
            val args = Bundle()
            val fragment = ChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
