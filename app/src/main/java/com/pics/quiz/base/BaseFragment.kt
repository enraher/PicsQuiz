package com.pics.quiz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * Created by enraher on 23/01/20.
 */

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    private lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var clazz: Class<BaseFragment<VM>> = javaClass
        while (clazz.genericSuperclass !is ParameterizedType) {
            clazz = clazz.superclass as Class<BaseFragment<VM>>
        }
        val parameterizedType = clazz.genericSuperclass as ParameterizedType
        viewModel = ViewModelProvider(this).get(parameterizedType.actualTypeArguments[0] as Class<VM>)
        arguments?.let { viewModel.initialData(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRscId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
    }

    protected abstract val layoutRscId: Int

    protected abstract fun setViews()

    open fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    open fun showToast(resource: Int) {
        Toast.makeText(context, resource, Toast.LENGTH_LONG).show()
    }
}
