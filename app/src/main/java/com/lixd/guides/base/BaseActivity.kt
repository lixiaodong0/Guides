package com.lixd.guides.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.Exception
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType


/**
 *  Created by lixiaodong
 *  Created bt date 2021/4/28 5:37 PM
 *  @description
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    var viewBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val inflate: Method = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java
            )
            viewBinding = inflate.invoke(null, layoutInflater) as VB
            setContentView(viewBinding?.root)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    abstract fun init()
}