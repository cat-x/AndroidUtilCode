package com.blankj.androidutilcode.base.rv.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blankj.androidutilcode.base.rv.BaseViewHolder
import com.blankj.androidutilcode.base.rv.listener.OnItemClickListener
import com.blankj.androidutilcode.base.rv.listener.OnItemLongClickListener

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/22
 * desc  :
</pre> *
 */
abstract class BaseAdapter<M> : RecyclerView.Adapter<BaseViewHolder>() {

    private val mViewArray = SparseArray<View>()

    protected var mData: List<M>? = null
    protected lateinit var mContext: Context
    protected var mParent: ViewGroup? = null

    protected lateinit var mInflater: LayoutInflater

    private var mClickListener: OnItemClickListener? = null
    private var mLongClickListener: OnItemLongClickListener? = null

    var emptyView: View
        get() = getView(VIEW_TYPE_EMPTY)
        set(emptyView) = setView(VIEW_TYPE_EMPTY, emptyView)

    var headerView: View
        get() = getView(VIEW_TYPE_HEADER)
        set(headerView) = setView(VIEW_TYPE_HEADER, headerView)

    var footerView: View
        get() = getView(VIEW_TYPE_FOOTER)
        set(footerView) = setView(VIEW_TYPE_FOOTER, footerView)

    private val extraViewCount: Int
        get() {
            var extraViewCount = 0
            if (mViewArray.get(VIEW_TYPE_HEADER) != null) {
                extraViewCount++
            }
            if (mViewArray.get(VIEW_TYPE_FOOTER) != null) {
                extraViewCount++
            }
            return extraViewCount
        }

    private val dataSize: Int
        get() = if (mData == null) 0 else mData!!.size

    fun setData(data: List<M>) {
        mData = data
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSize == 0 && mViewArray.get(VIEW_TYPE_EMPTY) != null) {
            VIEW_TYPE_EMPTY
        } else if (position == 0 && mViewArray.get(VIEW_TYPE_HEADER) != null) {
            VIEW_TYPE_HEADER
        } else if (position == itemCount - 1 && mViewArray.get(VIEW_TYPE_FOOTER) != null) {
            VIEW_TYPE_FOOTER
        } else {
            getCustomViewType(position)
        }
    }

    protected fun getCustomViewType(position: Int): Int {
        return VIEW_TYPE_DEFAULT
    }

    protected abstract fun bindLayout(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mParent == null) {
            mParent = parent
            mContext = parent.context
            mInflater = LayoutInflater.from(mContext)
        }
        var itemView: View? = mViewArray.get(viewType)
        if (itemView == null) {
            itemView = inflateLayout(bindLayout(viewType))
        }
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_EMPTY, VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER -> {
            }
            else -> bindCustomViewHolder(holder, position)
        }
    }

    protected fun bindCustomViewHolder(holder: BaseViewHolder, position: Int) {
        val dataPos = position - if (mViewArray.get(VIEW_TYPE_HEADER) == null) 0 else 1
        holder.itemView.setOnClickListener { v ->
            if (mClickListener != null) {
                mClickListener!!.onItemClick(v, dataPos)
            }
        }
        holder.itemView.setOnLongClickListener { v -> mLongClickListener != null && mLongClickListener!!.onItemLongClick(v, dataPos) }
        bind(holder, mData!![dataPos])
    }

    protected abstract fun bind(holder: BaseViewHolder, data: M)

    override fun getItemCount(): Int {
        return dataSize + extraViewCount
    }

    fun removeEmptyView() {
        removeView(VIEW_TYPE_EMPTY)
    }

    fun removeHeaderView() {
        removeView(VIEW_TYPE_HEADER)
    }

    fun removeFooterView() {
        removeView(VIEW_TYPE_FOOTER)
    }

    private fun setView(type: Int, view: View) {
        mViewArray.put(type, view)
        notifyDataSetChanged()
    }

    private fun getView(type: Int): View {
        return mViewArray.get(type)
    }

    private fun removeView(type: Int) {
        if (mViewArray.get(type) != null) {
            mViewArray.delete(type)
            notifyDataSetChanged()
        }
    }

    private fun inflateLayout(@LayoutRes layoutId: Int): View {
        return mInflater.inflate(layoutId, mParent, false)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mClickListener = clickListener
    }

    fun setOnItemLongClickListener(longClickListener: OnItemLongClickListener) {
        mLongClickListener = longClickListener
    }

    companion object {

        protected const val VIEW_TYPE_EMPTY = 0xfff0
        protected const val VIEW_TYPE_HEADER = 0xfff1
        protected const val VIEW_TYPE_FOOTER = 0xfff2
        protected const val VIEW_TYPE_DEFAULT = 0xfff3
    }
}
