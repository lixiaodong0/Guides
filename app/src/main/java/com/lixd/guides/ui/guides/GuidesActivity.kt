package com.lixd.guides.ui.guides

import android.content.Context
import android.content.Intent
import coil.load
import com.lixd.guides.base.BaseActivity
import com.lixd.guides.dao.entities.Image
import com.lixd.guides.databinding.ActivityGuidesBinding
import java.io.File

/**
 * Created by lixiaodong
 * Created bt date 2021/4/29 2:35 PM
 *
 * @description
 */
class GuidesActivity : BaseActivity<ActivityGuidesBinding>() {

    private lateinit var image: Image

    companion object {
        const val PATH_KEY: String = "data"
        fun launch(context: Context, image: Image) {
            val intent = Intent()
            intent.setClass(context, GuidesActivity::class.java)
            intent.putExtra(PATH_KEY, image)
            context.startActivity(intent)
        }
    }

    private fun loadImage(image: Image) {
        viewBinding?.apply {
            if (image.source == Image.LOCAL_IMAGE) {
                //本地图片加载
                File(image.path).exists()
                guidesView.load(File(image.path))
            } else {
                //网络图片加载
                guidesView.load(image.path)
            }
        }
    }

    override fun init() {
        if (intent.getSerializableExtra(PATH_KEY) == null) {
            finish()
            return
        }
        image = intent.getSerializableExtra(PATH_KEY) as Image
        loadImage(image)
    }
}