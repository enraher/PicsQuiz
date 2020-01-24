package com.pics.quiz.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * Created by enraher on 23/01/20.
 */

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        var clazz: Class<BaseActivity<VM>> = javaClass
        while (clazz.genericSuperclass !is ParameterizedType) {
            clazz = clazz.superclass as Class<BaseActivity<VM>>
        }
        val parameterizedType = clazz.genericSuperclass as ParameterizedType

        viewModel = ViewModelProvider(this).get(parameterizedType.actualTypeArguments[0] as Class<VM>)
        intent.extras?.let { viewModel.initialData(it) }

        setViews()
    }

    protected abstract val layoutResId: Int

    protected abstract fun setViews()

    open fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    open fun showToast(resource: Int) {
        Toast.makeText(this, resource, Toast.LENGTH_LONG).show()
    }
}