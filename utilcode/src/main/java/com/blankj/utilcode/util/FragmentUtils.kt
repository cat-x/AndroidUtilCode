package com.blankj.utilcode.util

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View

import java.util.ArrayList
import java.util.Collections

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/01/17
 * desc  : Fragment 相关工具类
</pre> *
 */
class FragmentUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    private class Args private constructor(internal var id: Int, internal var isHide: Boolean, internal var isAddStack: Boolean)

    class FragmentNode(internal var fragment: Fragment, internal var next: List<FragmentNode>?) {

        override fun toString(): String {
            return (fragment.javaClass.getSimpleName()
                    + "->"
                    + if (next == null || next!!.isEmpty()) "no child" else next!!.toString())
        }
    }

    interface OnBackClickListener {
        fun onBackClick(): Boolean
    }

    companion object {

        private val TYPE_ADD_FRAGMENT = 0x01
        private val TYPE_SHOW_FRAGMENT = 0x01 shl 1
        private val TYPE_HIDE_FRAGMENT = 0x01 shl 2
        private val TYPE_SHOW_HIDE_FRAGMENT = 0x01 shl 3
        private val TYPE_REPLACE_FRAGMENT = 0x01 shl 4
        private val TYPE_REMOVE_FRAGMENT = 0x01 shl 5
        private val TYPE_REMOVE_TO_FRAGMENT = 0x01 shl 6

        private val ARGS_ID = "args_id"
        private val ARGS_IS_HIDE = "args_is_hide"
        private val ARGS_IS_ADD_STACK = "args_is_add_stack"

        /**
         * 新增 fragment
         *
         * @param fm          fragment 管理器
         * @param containerId 布局 Id
         * @param add         要新增的 fragment
         * @param isHide      是否隐藏
         * @param isAddStack  是否入回退栈
         */
        @JvmOverloads
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                isHide: Boolean = false,
                isAddStack: Boolean = false) {
            putArgs(add, Args(containerId, isHide, isAddStack))
            operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add)
        }

        /**
         * 新增 fragment
         *
         * @param fm          fragment 管理器
         * @param containerId 布局 Id
         * @param add         要新增的 fragment
         * @param enterAnim   入场动画
         * @param exitAnim    出场动画
         */
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                @AnimRes enterAnim: Int,
                @AnimRes exitAnim: Int) {
            add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0)
        }

        /**
         * 新增 fragment
         *
         * @param fm           fragment 管理器
         * @param containerId  布局 Id
         * @param add          要新增的 fragment
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                @AnimRes enterAnim: Int,
                @AnimRes exitAnim: Int,
                @AnimRes popEnterAnim: Int,
                @AnimRes popExitAnim: Int) {
            add(fm, add, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        /**
         * 新增 fragment
         *
         * @param fm           fragment 管理器
         * @param containerId  布局 Id
         * @param add          要新增的 fragment
         * @param isAddStack   是否入回退栈
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        @JvmOverloads
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                isAddStack: Boolean,
                @AnimRes enterAnim: Int,
                @AnimRes exitAnim: Int,
                @AnimRes popEnterAnim: Int = 0,
                @AnimRes popExitAnim: Int = 0) {
            val ft = fm.beginTransaction()
            putArgs(add, Args(containerId, false, isAddStack))
            addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim)
            operate(TYPE_ADD_FRAGMENT, fm, ft, null, add)
        }

        /**
         * 新增 fragment
         *
         * @param fm             fragment 管理器
         * @param add            新增的 fragment
         * @param containerId    布局 Id
         * @param sharedElements 共享元素
         */
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                vararg sharedElements: View) {
            add(fm, add, containerId, false, *sharedElements)
        }

        /**
         * 新增 fragment
         *
         * @param fm             fragment 管理器
         * @param add            新增的 fragment
         * @param containerId    布局 Id
         * @param isAddStack     是否入回退栈
         * @param sharedElements 共享元素
         */
        fun add(fm: FragmentManager,
                add: Fragment,
                @IdRes containerId: Int,
                isAddStack: Boolean,
                vararg sharedElements: View) {
            val ft = fm.beginTransaction()
            putArgs(add, Args(containerId, false, isAddStack))
            addSharedElement(ft, *sharedElements)
            operate(TYPE_ADD_FRAGMENT, fm, ft, null, add)
        }

        /**
         * 新增 fragment
         *
         * @param fm          fragment 管理器
         * @param add         新增的 fragment
         * @param containerId 布局 Id
         * @param showIndex   要显示的 fragment 索引
         */
        fun add(fm: FragmentManager,
                add: List<Fragment>,
                @IdRes containerId: Int,
                showIndex: Int) {
            add(fm, add.toTypedArray<Fragment>(), containerId, showIndex)
        }

        /**
         * 新增 fragment
         *
         * @param fm          fragment 管理器
         * @param add         新增的 fragment
         * @param containerId 布局 Id
         * @param showIndex   要显示的 fragment 索引
         */
        fun add(fm: FragmentManager,
                add: Array<Fragment>,
                @IdRes containerId: Int,
                showIndex: Int) {
            var i = 0
            val len = add.size
            while (i < len) {
                putArgs(add[i], Args(containerId, showIndex != i, false))
                ++i
            }
            operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, *add)
        }

        /**
         * 显示 fragment
         *
         * @param show 要显示的 fragment
         */
        fun show(show: Fragment) {
            putArgs(show, false)
            operateNoAnim(show.fragmentManager, TYPE_SHOW_FRAGMENT, null, show)
        }

        /**
         * 显示 fragment
         *
         * @param fm fragment 管理器
         */
        fun show(fm: FragmentManager) {
            val fragments = getFragments(fm)
            for (show in fragments) {
                putArgs(show, false)
            }
            operateNoAnim(fm,
                    TYPE_SHOW_FRAGMENT, null,
                    *fragments.toTypedArray<Fragment>()
            )
        }

        /**
         * 隐藏 fragment
         *
         * @param hide 要隐藏的 fragment
         */
        fun hide(hide: Fragment) {
            putArgs(hide, true)
            operateNoAnim(hide.fragmentManager, TYPE_HIDE_FRAGMENT, null, hide)
        }

        /**
         * 隐藏 fragment
         *
         * @param fm fragment 管理器
         */
        fun hide(fm: FragmentManager) {
            val fragments = getFragments(fm)
            for (hide in fragments) {
                putArgs(hide, true)
            }
            operateNoAnim(fm,
                    TYPE_HIDE_FRAGMENT, null,
                    *fragments.toTypedArray<Fragment>()
            )
        }

        /**
         * 先显示后隐藏 fragment
         *
         * @param showIndex 要显示的 fragment 索引
         * @param fragments 要隐藏的 fragments
         */
        fun showHide(showIndex: Int, fragments: List<Fragment>) {
            showHide(fragments[showIndex], fragments)
        }

        /**
         * 先显示后隐藏 fragment
         *
         * @param show 要显示的 fragment
         * @param hide 要隐藏的 fragment
         */
        fun showHide(show: Fragment, hide: List<Fragment>) {
            for (fragment in hide) {
                putArgs(fragment, fragment !== show)
            }
            operateNoAnim(show.fragmentManager, TYPE_SHOW_HIDE_FRAGMENT, show,
                    *hide.toTypedArray<Fragment>())
        }

        /**
         * 先显示后隐藏 fragment
         *
         * @param showIndex 要显示的 fragment 索引
         * @param fragments 要隐藏的 fragments
         */
        fun showHide(showIndex: Int, vararg fragments: Fragment) {
            showHide(fragments[showIndex], *fragments)
        }

        /**
         * 先显示后隐藏 fragment
         *
         * @param show 要显示的 fragment
         * @param hide 要隐藏的 fragment
         */
        fun showHide(show: Fragment, vararg hide: Fragment) {
            for (fragment in hide) {
                putArgs(fragment, fragment !== show)
            }
            operateNoAnim(show.fragmentManager, TYPE_SHOW_HIDE_FRAGMENT, show, *hide)
        }

        /**
         * 先显示后隐藏 fragment
         *
         * @param show 要显示的 fragment
         * @param hide 要隐藏的 fragment
         */
        fun showHide(show: Fragment,
                     hide: Fragment) {
            putArgs(show, false)
            putArgs(hide, true)
            operateNoAnim(show.fragmentManager, TYPE_SHOW_HIDE_FRAGMENT, show, hide)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment  源 fragment
         * @param destFragment 目标 fragment
         * @param isAddStack   是否入回退栈
         */
        @JvmOverloads
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    isAddStack: Boolean = false) {
            val args = getArgs(srcFragment)
            replace(srcFragment.fragmentManager, destFragment, args.id, isAddStack)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment  源 fragment
         * @param destFragment 目标 fragment
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         */
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int) {
            replace(srcFragment, destFragment, false, enterAnim, exitAnim, 0, 0)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment  源 fragment
         * @param destFragment 目标 fragment
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int,
                    @AnimRes popEnterAnim: Int,
                    @AnimRes popExitAnim: Int) {
            replace(srcFragment, destFragment, false, enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment  源 fragment
         * @param destFragment 目标 fragment
         * @param isAddStack   是否入回退栈
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        @JvmOverloads
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    isAddStack: Boolean,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int,
                    @AnimRes popEnterAnim: Int = 0,
                    @AnimRes popExitAnim: Int = 0) {
            val args = getArgs(srcFragment)
            replace(srcFragment.fragmentManager, destFragment, args.id, isAddStack,
                    enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment    源 fragment
         * @param destFragment   目标 fragment
         * @param sharedElements 共享元素
         */
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    vararg sharedElements: View) {
            replace(srcFragment, destFragment, false, *sharedElements)
        }

        /**
         * 替换 fragment
         *
         * @param srcFragment    源 fragment
         * @param destFragment   目标 fragment
         * @param isAddStack     是否入回退栈
         * @param sharedElements 共享元素
         */
        fun replace(srcFragment: Fragment,
                    destFragment: Fragment,
                    isAddStack: Boolean,
                    vararg sharedElements: View) {
            val args = getArgs(srcFragment)
            replace(srcFragment.fragmentManager,
                    destFragment,
                    args.id,
                    isAddStack,
                    *sharedElements
            )
        }

        /**
         * 替换 fragment
         *
         * @param fm          fragment 管理器
         * @param containerId 布局 Id
         * @param fragment    fragment
         * @param isAddStack  是否入回退栈
         */
        @JvmOverloads
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    isAddStack: Boolean = false) {
            val ft = fm.beginTransaction()
            putArgs(fragment, Args(containerId, false, isAddStack))
            operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment)
        }

        /**
         * 替换 fragment
         *
         * @param fm          fragment 管理器
         * @param containerId 布局 Id
         * @param fragment    fragment
         * @param enterAnim   入场动画
         * @param exitAnim    出场动画
         */
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int) {
            replace(fm, fragment, containerId, false, enterAnim, exitAnim, 0, 0)
        }

        /**
         * 替换 fragment
         *
         * @param fm           fragment 管理器
         * @param containerId  布局 Id
         * @param fragment     fragment
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int,
                    @AnimRes popEnterAnim: Int,
                    @AnimRes popExitAnim: Int) {
            replace(fm, fragment, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        /**
         * 替换 fragment
         *
         * @param fm           fragment 管理器
         * @param containerId  布局 Id
         * @param fragment     fragment
         * @param isAddStack   是否入回退栈
         * @param enterAnim    入场动画
         * @param exitAnim     出场动画
         * @param popEnterAnim 入栈动画
         * @param popExitAnim  出栈动画
         */
        @JvmOverloads
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    isAddStack: Boolean,
                    @AnimRes enterAnim: Int,
                    @AnimRes exitAnim: Int,
                    @AnimRes popEnterAnim: Int = 0,
                    @AnimRes popExitAnim: Int = 0) {
            val ft = fm.beginTransaction()
            putArgs(fragment, Args(containerId, false, isAddStack))
            addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim)
            operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment)
        }

        /**
         * 替换 fragment
         *
         * @param fm             fragment 管理器
         * @param containerId    布局 Id
         * @param fragment       fragment
         * @param sharedElements 共享元素
         */
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    vararg sharedElements: View) {
            replace(fm, fragment, containerId, false, *sharedElements)
        }

        /**
         * 替换 fragment
         *
         * @param fm             fragment 管理器
         * @param containerId    布局 Id
         * @param fragment       fragment
         * @param isAddStack     是否入回退栈
         * @param sharedElements 共享元素
         */
        fun replace(fm: FragmentManager,
                    fragment: Fragment,
                    @IdRes containerId: Int,
                    isAddStack: Boolean,
                    vararg sharedElements: View) {
            val ft = fm.beginTransaction()
            putArgs(fragment, Args(containerId, false, isAddStack))
            addSharedElement(ft, *sharedElements)
            operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment)
        }

        /**
         * 出栈 fragment
         *
         * @param fm fragment 管理器
         */
        @JvmOverloads
        fun pop(fm: FragmentManager,
                isImmediate: Boolean = true) {
            if (isImmediate) {
                fm.popBackStackImmediate()
            } else {
                fm.popBackStack()
            }
        }

        /**
         * 出栈到指定 fragment
         *
         * @param fm          fragment 管理器
         * @param popClz      出栈 fragment 的类型
         * @param isInclusive 是否出栈 popClz 的 fragment
         * @param isImmediate 是否立即出栈
         */
        @JvmOverloads
        fun popTo(fm: FragmentManager,
                  popClz: Class<out Fragment>,
                  isInclusive: Boolean,
                  isImmediate: Boolean = true) {
            if (isImmediate) {
                fm.popBackStackImmediate(popClz.name,
                        if (isInclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
            } else {
                fm.popBackStack(popClz.name,
                        if (isInclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
            }
        }

        /**
         * 出栈所有 fragment
         *
         * @param fm fragment 管理器
         */
        @JvmOverloads
        fun popAll(fm: FragmentManager, isImmediate: Boolean = true) {
            while (fm.backStackEntryCount > 0) {
                if (isImmediate) {
                    fm.popBackStackImmediate()
                } else {
                    fm.popBackStack()
                }
            }
        }

        /**
         * 移除 fragment
         *
         * @param remove 要移除的 fragment
         */
        fun remove(remove: Fragment) {
            operateNoAnim(remove.fragmentManager, TYPE_REMOVE_FRAGMENT, null, remove)
        }

        /**
         * 移除到指定 fragment
         *
         * @param removeTo    要移除到的 fragment
         * @param isInclusive 是否移除 removeTo
         */
        fun removeTo(removeTo: Fragment, isInclusive: Boolean) {
            operateNoAnim(removeTo.fragmentManager, TYPE_REMOVE_TO_FRAGMENT,
                    if (isInclusive) removeTo else null, removeTo)
        }

        /**
         * 移除所有 fragment
         *
         * @param fm fragment 管理器
         */
        fun removeAll(fm: FragmentManager) {
            val fragments = getFragments(fm)
            operateNoAnim(fm,
                    TYPE_REMOVE_FRAGMENT, null,
                    *fragments.toTypedArray<Fragment>()
            )
        }

        private fun putArgs(fragment: Fragment, args: Args) {
            var bundle: Bundle? = fragment.arguments
            if (bundle == null) {
                bundle = Bundle()
                fragment.arguments = bundle
            }
            bundle.putInt(ARGS_ID, args.id)
            bundle.putBoolean(ARGS_IS_HIDE, args.isHide)
            bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack)
        }

        private fun putArgs(fragment: Fragment, isHide: Boolean) {
            var bundle: Bundle? = fragment.arguments
            if (bundle == null) {
                bundle = Bundle()
                fragment.arguments = bundle
            }
            bundle.putBoolean(ARGS_IS_HIDE, isHide)
        }

        private fun getArgs(fragment: Fragment): Args {
            val bundle = fragment.arguments
            return Args(bundle.getInt(ARGS_ID, fragment.id),
                    bundle.getBoolean(ARGS_IS_HIDE),
                    bundle.getBoolean(ARGS_IS_ADD_STACK))
        }

        private fun operateNoAnim(fm: FragmentManager,
                                  type: Int,
                                  src: Fragment?,
                                  vararg dest: Fragment) {
            val ft = fm.beginTransaction()
            operate(type, fm, ft, src, *dest)
        }

        private fun operate(type: Int,
                            fm: FragmentManager,
                            ft: FragmentTransaction,
                            src: Fragment?,
                            vararg dest: Fragment) {
            if (src != null && src.isRemoving) {
                Log.e("FragmentUtils", src.javaClass.getName() + " is isRemoving")
                return
            }
            var name: String
            var args: Bundle
            when (type) {
                TYPE_ADD_FRAGMENT -> for (fragment in dest) {
                    name = fragment.javaClass.getName()
                    args = fragment.arguments
                    val fragmentByTag = fm.findFragmentByTag(name)
                    if (fragmentByTag != null && fragmentByTag.isAdded) {
                        ft.remove(fragmentByTag)
                    }
                    ft.add(args.getInt(ARGS_ID), fragment, name)
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(fragment)
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name)
                }
                TYPE_HIDE_FRAGMENT -> for (fragment in dest) {
                    ft.hide(fragment)
                }
                TYPE_SHOW_FRAGMENT -> for (fragment in dest) {
                    ft.show(fragment)
                }
                TYPE_SHOW_HIDE_FRAGMENT -> {
                    ft.show(src)
                    for (fragment in dest) {
                        if (fragment !== src) {
                            ft.hide(fragment)
                        }
                    }
                }
                TYPE_REPLACE_FRAGMENT -> {
                    name = dest[0].javaClass.getName()
                    args = dest[0].arguments
                    ft.replace(args.getInt(ARGS_ID), dest[0], name)
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name)
                }
                TYPE_REMOVE_FRAGMENT -> for (fragment in dest) {
                    if (fragment !== src) {
                        ft.remove(fragment)
                    }
                }
                TYPE_REMOVE_TO_FRAGMENT -> for (i in dest.indices.reversed()) {
                    val fragment = dest[i]
                    if (fragment === dest[0]) {
                        if (src != null) ft.remove(fragment)
                        break
                    }
                    ft.remove(fragment)
                }
            }
            ft.commitAllowingStateLoss()
        }

        private fun addAnim(ft: FragmentTransaction,
                            enter: Int,
                            exit: Int,
                            popEnter: Int,
                            popExit: Int) {
            ft.setCustomAnimations(enter, exit, popEnter, popExit)
        }

        private fun addSharedElement(ft: FragmentTransaction,
                                     vararg views: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (view in views) {
                    ft.addSharedElement(view, view.transitionName)
                }
            }
        }

        /**
         * 获取顶部 fragment
         *
         * @param fm fragment 管理器
         * @return 最后加入的 fragment
         */
        fun getTop(fm: FragmentManager): Fragment? {
            return getTopIsInStack(fm, false)
        }

        /**
         * 获取栈中顶部 fragment
         *
         * @param fm fragment 管理器
         * @return 最后加入的 fragment
         */
        fun getTopInStack(fm: FragmentManager): Fragment? {
            return getTopIsInStack(fm, true)
        }

        private fun getTopIsInStack(fm: FragmentManager,
                                    isInStack: Boolean): Fragment? {
            val fragments = getFragments(fm)
            for (i in fragments.indices.reversed()) {
                val fragment = fragments[i]
                if (fragment != null) {
                    if (isInStack) {
                        if (fragment.arguments.getBoolean(ARGS_IS_ADD_STACK)) {
                            return fragment
                        }
                    } else {
                        return fragment
                    }
                }
            }
            return null
        }

        /**
         * 获取顶部可见 fragment
         *
         * @param fm fragment 管理器
         * @return 顶层可见 fragment
         */
        fun getTopShow(fm: FragmentManager): Fragment? {
            return getTopShowIsInStack(fm, false)
        }

        /**
         * 获取栈中顶部可见 fragment
         *
         * @param fm fragment 管理器
         * @return 栈中顶层可见 fragment
         */
        fun getTopShowInStack(fm: FragmentManager): Fragment? {
            return getTopShowIsInStack(fm, true)
        }

        private fun getTopShowIsInStack(fm: FragmentManager,
                                        isInStack: Boolean): Fragment? {
            val fragments = getFragments(fm)
            for (i in fragments.indices.reversed()) {
                val fragment = fragments[i]
                if (fragment != null
                        && fragment.isResumed
                        && fragment.isVisible
                        && fragment.userVisibleHint) {
                    if (isInStack) {
                        if (fragment.arguments.getBoolean(ARGS_IS_ADD_STACK)) {
                            return fragment
                        }
                    } else {
                        return fragment
                    }
                }
            }
            return null
        }

        /**
         * 获取同级别的 fragment
         *
         * @param fm fragment 管理器
         * @return fragment 管理器中的 fragment
         */
        fun getFragments(fm: FragmentManager): List<Fragment> {
            val fragments = fm.fragments
            return if (fragments == null || fragments.isEmpty()) emptyList<Fragment>() else fragments
        }

        /**
         * 获取同级别栈中的 fragment
         *
         * @param fm fragment 管理器
         * @return fragment 管理器栈中的 fragment
         */
        fun getFragmentsInStack(fm: FragmentManager): List<Fragment> {
            val fragments = getFragments(fm)
            val result = ArrayList<Fragment>()
            for (fragment in fragments) {
                if (fragment != null && fragment.arguments.getBoolean(ARGS_IS_ADD_STACK)) {
                    result.add(fragment)
                }
            }
            return result
        }

        /**
         * 获取所有 fragment
         *
         * @param fm fragment 管理器
         * @return 所有 fragment
         */
        fun getAllFragments(fm: FragmentManager): List<FragmentNode> {
            return getAllFragments(fm, ArrayList())
        }

        private fun getAllFragments(fm: FragmentManager,
                                    result: MutableList<FragmentNode>): List<FragmentNode> {
            val fragments = getFragments(fm)
            for (i in fragments.indices.reversed()) {
                val fragment = fragments[i]
                if (fragment != null) {
                    result.add(FragmentNode(fragment,
                            getAllFragments(fragment.childFragmentManager,
                                    ArrayList())))
                }
            }
            return result
        }

        /**
         * 获取栈中所有 fragment
         *
         * @param fm fragment 管理器
         * @return 所有 fragment
         */
        fun getAllFragmentsInStack(fm: FragmentManager): List<FragmentNode> {
            return getAllFragmentsInStack(fm, ArrayList())
        }

        private fun getAllFragmentsInStack(fm: FragmentManager,
                                           result: MutableList<FragmentNode>): List<FragmentNode> {
            val fragments = getFragments(fm)
            for (i in fragments.indices.reversed()) {
                val fragment = fragments[i]
                if (fragment != null && fragment.arguments.getBoolean(ARGS_IS_ADD_STACK)) {
                    result.add(FragmentNode(fragment,
                            getAllFragmentsInStack(fragment.childFragmentManager,
                                    ArrayList())))
                }
            }
            return result
        }

        /**
         * 查找 fragment
         *
         * @param fm      fragment 管理器
         * @param findClz 要查找的 fragment 类型
         * @return 查找到的 fragment
         */
        fun findFragment(fm: FragmentManager,
                         findClz: Class<out Fragment>): Fragment {
            return fm.findFragmentByTag(findClz.name)
        }

        /**
         * 处理 fragment 回退键
         *
         * 如果 fragment 实现了 OnBackClickListener 接口，返回`true`: 表示已消费回退键事件，反之则没消费
         *
         * 具体示例见 FragmentActivity
         *
         * @param fragment fragment
         * @return 是否消费回退事件
         */
        fun dispatchBackPress(fragment: Fragment): Boolean {
            return (fragment.isResumed
                    && fragment.isVisible
                    && fragment.userVisibleHint
                    && fragment is OnBackClickListener
                    && (fragment as OnBackClickListener).onBackClick())
        }

        /**
         * 处理 fragment 回退键
         *
         * 如果 fragment 实现了 OnBackClickListener 接口，返回`true`: 表示已消费回退键事件，反之则没消费
         *
         * 具体示例见 FragmentActivity
         *
         * @param fm fragment 管理器
         * @return 是否消费回退事件
         */
        fun dispatchBackPress(fm: FragmentManager): Boolean {
            val fragments = getFragments(fm)
            if (fragments == null || fragments.isEmpty()) return false
            for (i in fragments.indices.reversed()) {
                val fragment = fragments[i]
                if (fragment != null
                        && fragment.isResumed
                        && fragment.isVisible
                        && fragment.userVisibleHint
                        && fragment is OnBackClickListener
                        && (fragment as OnBackClickListener).onBackClick()) {
                    return true
                }
            }
            return false
        }

        /**
         * 设置背景色
         *
         * @param fragment fragment
         * @param color    背景色
         */
        fun setBackgroundColor(fragment: Fragment,
                               @ColorInt color: Int) {
            val view = fragment.view
            view?.setBackgroundColor(color)
        }

        /**
         * 设置背景资源
         *
         * @param fragment fragment
         * @param resId    资源 Id
         */
        fun setBackgroundResource(fragment: Fragment,
                                  @DrawableRes resId: Int) {
            val view = fragment.view
            view?.setBackgroundResource(resId)
        }

        /**
         * 设置背景
         *
         * @param fragment   fragment
         * @param background 背景
         */
        fun setBackground(fragment: Fragment, background: Drawable) {
            ViewCompat.setBackground(fragment.view, background)
        }

        /**
         * 获取类名
         *
         * @param fragment fragment
         * @return 类名
         */
        fun getSimpleName(fragment: Fragment?): String {
            return fragment?.javaClass?.getSimpleName() ?: "null"
        }
    }
}
/**
 * 新增 fragment
 *
 * @param fm          fragment 管理器
 * @param containerId 布局 Id
 * @param add         要新增的 fragment
 */
/**
 * 新增 fragment
 *
 * @param fm          fragment 管理器
 * @param containerId 布局 Id
 * @param add         要新增的 fragment
 * @param isHide      是否隐藏
 */
/**
 * 新增 fragment
 *
 * @param fm          fragment 管理器
 * @param containerId 布局 Id
 * @param add         要新增的 fragment
 * @param isAddStack  是否入回退栈
 * @param enterAnim   入场动画
 * @param exitAnim    出场动画
 */
/**
 * 替换 fragment
 *
 * @param srcFragment  源 fragment
 * @param destFragment 目标 fragment
 */
/**
 * 替换 fragment
 *
 * @param srcFragment  源 fragment
 * @param destFragment 目标 fragment
 * @param isAddStack   是否入回退栈
 * @param enterAnim    入场动画
 * @param exitAnim     出场动画
 */
/**
 * 替换 fragment
 *
 * @param fm          fragment 管理器
 * @param containerId 布局 Id
 * @param fragment    fragment
 */
/**
 * 替换 fragment
 *
 * @param fm          fragment 管理器
 * @param containerId 布局 Id
 * @param fragment    fragment
 * @param isAddStack  是否入回退栈
 * @param enterAnim   入场动画
 * @param exitAnim    出场动画
 */
/**
 * 出栈 fragment
 *
 * @param fm fragment 管理器
 */
/**
 * 出栈到指定 fragment
 *
 * @param fm          fragment 管理器
 * @param popClz      出栈 fragment 的类型
 * @param isInclusive 是否出栈 popClz 的 fragment
 */
/**
 * 出栈所有 fragment
 *
 * @param fm fragment 管理器
 */
