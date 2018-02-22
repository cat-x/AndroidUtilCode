package com.blankj.androidutilcode.feature.core.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blankj.androidutilcode.Config
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/26
 * desc  : Image 工具类 Demo
</pre> *
 */
class ImageActivity : BaseBackActivity() {

    internal lateinit var src: Bitmap
    internal var mList: MutableList<ImageBean> = ArrayList()

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_image
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_image)

        val rvImages = findViewById<RecyclerView>(R.id.rv_images)
        findViewById<View>(R.id.btn_save).setOnClickListener(this)

        src = ImageUtils.getBitmap(R.drawable.img_lena)
        val round = ImageUtils.getBitmap(R.drawable.avatar_round)
        val watermark = ImageUtils.getBitmap(R.mipmap.ic_launcher)

        val width = src.width
        val height = src.height

        mList.add(ImageBean(R.string.image_src, src))
        mList.add(ImageBean(R.string.image_scale, ImageUtils.scale(src, width / 2, height / 2)))
        mList.add(ImageBean(R.string.image_clip, ImageUtils.clip(src, 0, 0, width / 2, height / 2)))
        mList.add(ImageBean(R.string.image_skew, ImageUtils.skew(src, 0.2f, 0.1f)))
        mList.add(ImageBean(R.string.image_rotate, ImageUtils.rotate(src, 90, (width / 2).toFloat(), (height / 2).toFloat())))
        mList.add(ImageBean(R.string.image_to_round, ImageUtils.toRound(src)))
        mList.add(ImageBean(R.string.image_to_round_border, ImageUtils.toRound(src, 16, Color.GREEN)))
        mList.add(ImageBean(R.string.image_to_round_corner, ImageUtils.toRoundCorner(src, 80F)))
        mList.add(ImageBean(R.string.image_to_round_corner_border, ImageUtils.toRoundCorner(src, 80F, 16, Color.GREEN)))
        mList.add(ImageBean(R.string.image_add_corner_border, ImageUtils.addCornerBorder(src, 16, Color.GREEN, 0f)))
        mList.add(ImageBean(R.string.image_add_circle_border, ImageUtils.addCircleBorder(round, 16, Color.GREEN)))
        mList.add(ImageBean(R.string.image_add_reflection, ImageUtils.addReflection(src, 80)))
        mList.add(ImageBean(R.string.image_add_text_watermark, ImageUtils.addTextWatermark(src, "blankj", 40, Color.GREEN, 0f, 0f)))
        mList.add(ImageBean(R.string.image_add_image_watermark, ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88)))
        mList.add(ImageBean(R.string.image_to_gray, ImageUtils.toGray(src)))
        mList.add(ImageBean(R.string.image_fast_blur, ImageUtils.fastBlur(src, 0.1f, 5F)))
        mList.add(ImageBean(R.string.image_render_script_blur, ImageUtils.renderScriptBlur(src, 10F)))
        mList.add(ImageBean(R.string.image_stack_blur, ImageUtils.stackBlur(src, 10)))
        mList.add(ImageBean(R.string.image_compress_by_scale, ImageUtils.compressByScale(src, 0.5f, 0.5f)))
        mList.add(ImageBean(R.string.image_compress_by_quality_half, ImageUtils.compressByQuality(src, 50)))
        mList.add(ImageBean(R.string.image_compress_by_quality_max_size, ImageUtils.compressByQuality(src, 10L * 1024)))// 10Kb
        mList.add(ImageBean(R.string.image_compress_by_sample_size, ImageUtils.compressBySampleSize(src, 2)))

        rvImages.adapter = ImageAdapter(mList, R.layout.item_image)
        rvImages.layoutManager = LinearLayoutManager(this)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_save -> {
                val save = ImageUtils.save(src, Config.CACHE_PATH + "lena.jpg", Bitmap.CompressFormat.JPEG)
                ToastUtils.showLong(if (save) "successful" else "failed")
            }
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ImageActivity::class.java)
            context.startActivity(starter)
        }
    }
}
