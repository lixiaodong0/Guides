package com.lixd.guides.ui.history

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.lixd.guides.base.BaseViewBindingAdapter
import com.lixd.guides.dao.entities.Image
import com.lixd.guides.databinding.ItemHistoryImagesBinding
import com.lixd.guides.ui.guides.GuidesActivity
import java.io.File
import java.text.FieldPosition

/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/28 5:16 PM
 *  @description
 */
class HistoryImagesAdapter(context: Context) :
    BaseViewBindingAdapter<ItemHistoryImagesBinding, Image>(context) {

    override fun convert(
        holder: BaseViewBindingHolder<ItemHistoryImagesBinding>,
        data: Image,
        position: Int
    ) {
        if (data.source == Image.LOCAL_IMAGE) {
            holder.viewBinding.iamge.load(File(data.path))
        } else {
            holder.viewBinding.iamge.load(data.path)
        }
    }

    override fun onItemClicked(data: Image, position: Int) {
        GuidesActivity.launch(context, data)
    }

    override fun getViewBinding(parent: ViewGroup): ItemHistoryImagesBinding =
        ItemHistoryImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
}