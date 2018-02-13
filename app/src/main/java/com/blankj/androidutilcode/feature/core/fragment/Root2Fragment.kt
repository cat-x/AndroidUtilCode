package com.blankj.androidutilcode.feature.core.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.Fade
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseFragment
import com.blankj.utilcode.util.FragmentUtils
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  :
</pre> *
 */
class Root2Fragment : BaseFragment(), FragmentUtils.OnBackClickListener {

    internal lateinit var ivSharedElement: ImageView
    internal lateinit var tvAboutFragment: TextView

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.fragment_root
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        if (view != null) {
            val random = Random()
            FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
            view.findViewById<View>(R.id.btn_show_about_fragment).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add_hide).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add_hide_stack).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add_show).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_add_child).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_pop_to_root).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_pop_add).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_hide_show).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_replace).setOnClickListener(this)
            ivSharedElement = view.findViewById<View>(R.id.iv_shared_element) as ImageView
            tvAboutFragment = view.findViewById<View>(R.id.tv_about_fragment) as TextView
        }
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        tvAboutFragment.text = ""
        when (view.id) {
            R.id.btn_show_about_fragment -> tvAboutFragment.text = ("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fragmentManager))
                    + "\ntopInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fragmentManager))
                    + "\ntopShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fragmentManager))
                    + "\ntopShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fragmentManager))
                    + "\n---all of fragments---\n"
                    + FragmentUtils.getAllFragments(fragmentManager).toString()
                    + "\n----------------------\n\n"
                    + "---stack top---\n"
                    + FragmentUtils.getAllFragmentsInStack(fragmentManager).toString()
                    + "\n---stack bottom---\n\n")
            R.id.btn_add -> FragmentUtils.add(fragmentManager,
                    ChildFragment.newInstance(),
                    R.id.fragment_container)
            R.id.btn_add_hide -> FragmentUtils.add(fragmentManager,
                    ChildFragment.newInstance(),
                    R.id.fragment_container,
                    true)
            R.id.btn_add_hide_stack -> FragmentUtils.add(fragmentManager,
                    ChildFragment.newInstance(),
                    R.id.fragment_container,
                    true,
                    true)
        }//            case R.id.btn_add_show:
        //                FragmentUtils.add(getFragmentManager(),
        //                        addSharedElement(Demo1Fragment.newInstance()),
        //                        R.id.fragment_container,
        //                        false,
        //                        false,
        //                        sharedElement);
        //                break;
        //            case R.id.btn_add_child:
        //                FragmentUtils.add(getChildFragmentManager(),
        //                        ChildFragment.newInstance(),
        //                        R.id.child_fragment_container,
        //                        false,
        //                        true);
        //                break;
        //            case R.id.btn_pop_to_root:
        //                FragmentUtils.popToFragment(getFragmentManager(),
        //                        Demo1Fragment.class,
        //                        true);
        //                break;
        //            case R.id.btn_pop_add:
        //                FragmentUtils.popAddFragment(getFragmentManager(),
        //                        addSharedElement(ChildFragment.newInstance()),
        //                        R.id.fragment_container,
        //                        true,
        //                        sharedElement);
        //                break;
        //            case R.id.btn_hide_show:
        //                Fragment fragment1 = FragmentUtils.findFragment(getFragmentManager(), Demo1Fragment.class);
        //                if (fragment1 != null) {
        //                    FragmentUtils.showHideFragment(this, fragment1);
        //                } else {
        //                    ToastUtils.showLong("please add demo1 first!");
        //                }
        //                break;
        //            case R.id.btn_replace:
        //                ((FragmentActivity) getActivity()).rootFragment = FragmentUtils.replaceFragment(this, addSharedElement(Demo3Fragment.newInstance()), false, sharedElement);
        //                break;
    }

    private fun addSharedElement(fragment: Fragment): Fragment {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.sharedElementEnterTransition = DetailTransition()
            fragment.enterTransition = Fade()
            fragment.sharedElementReturnTransition = DetailTransition()
        }
        return fragment
    }

    override fun onBackClick(): Boolean {
        //        FragmentUtils.popToFragment(getFragmentManager(), Demo1Fragment.class, true);
        return false
    }

    companion object {

        fun newInstance(): Root2Fragment {
            val args = Bundle()
            val fragment = Root2Fragment()
            fragment.arguments = args
            return fragment
        }
    }
}
