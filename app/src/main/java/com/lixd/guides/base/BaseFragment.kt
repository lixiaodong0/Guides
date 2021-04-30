package com.lixd.guides.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.Exception
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * Created by lixiaodong
 * Created bt date 2021/4/28 6:10 PM
 *
 * @description
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    var viewBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val inflate: Method = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            viewBinding = inflate.invoke(null, inflater, container, false) as VB
            return viewBinding!!.root
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    abstract fun init()

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}