package com.pics.quiz.base

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pics.quiz.other.BackgroundSoundService
import com.pics.quiz.repositories.PreferencesManager
import java.lang.reflect.ParameterizedType

/**
 * Created by enraher on 23/01/20.
 */

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var viewModel: VM

    private var isMusicServiceBounded = false

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

    private var mBoundService: BackgroundSoundService? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mBoundService = (service as BackgroundSoundService.LocalBinder).service
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mBoundService = null

        }
    }

    protected fun stopSoundService() {
        mBoundService?.stopPlayer()
    }

    protected fun startSoundService() {
        PreferencesManager(this).musicOn = true
        bindSoundService()
        mBoundService.let { it?.startPlayer() }
    }

    private fun bindSoundService() {
        if(PreferencesManager(this).musicOn) {
            val svc = Intent(this, BackgroundSoundService::class.java)
            bindService(svc, mConnection, Context.BIND_AUTO_CREATE)
            isMusicServiceBounded = true
        }
    }

    fun unbindSoundService() {
        if(PreferencesManager(this).musicOn) {
            if (isMusicServiceBounded) {
                unbindService(mConnection)
                isMusicServiceBounded = false
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unbindSoundService()
    }

    override fun onResume() {
        super.onResume()
        bindSoundService()
    }
}