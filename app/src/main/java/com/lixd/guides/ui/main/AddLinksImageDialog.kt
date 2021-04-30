package com.lixd.guides.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import coil.Coil
import coil.request.ImageRequest
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lixd.guides.databinding.DialogAddLinksImageBinding
import com.lixd.guides.showToast
import kotlinx.coroutines.*

/**
 * Created by lixiaodong
 * Created bt date 2021/4/30 9:53 AM
 *
 * @description 添加链接图片的弹窗
 */
class AddLinksImageDialog : BottomSheetDialogFragment() {

    private lateinit var viewbinding: DialogAddLinksImageBinding

    var onConfirmListener: OnConfirmListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = DialogAddLinksImageBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbinding.editLinks.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val links = viewbinding.editLinks.text!!.trim().toString()
                if (!TextUtils.isEmpty(links)) {
                    CoroutineScope(Dispatchers.Main)
                        .launch(Dispatchers.Main) {//主线程开启携程
                            val drawable = withContext(Dispatchers.IO) {
                                checkImageLinksValid(links) //在子线程中调用
                            }
                            if (drawable != null) {
                                onConfirmListener?.onConfirm(links)
                                dismiss()
                            } else {
                                showToast(context!!, "链接错误")
                            }
                        }
                }
            }
            true
        }
    }

    /**
     * 校验图片链接有效性
     */
    private suspend fun checkImageLinksValid(links: String): Drawable? {
        val request = ImageRequest.Builder(context!!)
            .data(links)
            .build()
        return Coil.execute(request).drawable
    }

    interface OnConfirmListener {
        fun onConfirm(links: String)
    }

}