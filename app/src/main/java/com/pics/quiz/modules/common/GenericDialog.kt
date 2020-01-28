package com.pics.quiz.modules.common

/**
 * Created by enrasoft on 24/1/20.
 */

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pics.flubber.Flubber
import com.pics.quiz.R
import kotlinx.android.synthetic.main.dialog_generic.view.*


class GenericDialog : DialogFragment() {

    interface GenericDialogListener {
        fun onGenericDialogRightClicked(_tag: String)
        fun onGenericDialogLeftClicked(_tag: String)
        fun onGenericDialogDismiss(_tag: String)
    }

    private var listener: GenericDialogListener? = null

    private var _tag: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_generic, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialogs)


        arguments?.getString(KEY_TITLE_TEXT)?.let {
            view.txt_title_generic_dialog.text = it
        } ?: run {
            view.txt_title_generic_dialog.visibility = View.GONE
        }

        arguments?.getString(KEY_DESC_TEXT)?.let {
            view.txt_desc_generic_dialog.text = it
        } ?: run {
            view.txt_desc_generic_dialog.visibility = View.GONE
        }

        arguments?.getString(KEY_BTN_RIGHT_TEXT)?.let {
            view.btn_right.text = it
            view.btn_right.alpha = 0F
            Flubber.with()
                .animation(Flubber.Preset.ZOOM_IN)
                .interpolator(Flubber.Curve.BZR_EASE_IN_OUT_CUBIC)
                .duration(200).delay(600)
                .createFor(view.btn_right)
                .start()
        } ?: run {
            view.btn_right.visibility = View.GONE
        }

        arguments?.getString(KEY_BTN_LEFT_TEXT)?.let {
            view.btn_left.text = it
            view.btn_left.alpha = 0F
            Flubber.with()
                .animation(Flubber.Preset.ZOOM_IN)
                .interpolator(Flubber.Curve.BZR_EASE_IN_OUT_CUBIC)
                .duration(200).delay(500)
                .createFor(view.btn_left).start()
        } ?: run {
            view.btn_left.visibility = View.GONE
        }

        arguments?.getBoolean(KEY_CANCELABLE)?.let {
            isCancelable = it
        } ?: run {
            isCancelable = true
        }
        if(isCancelable) {
            view.btn_quit.visibility = View.VISIBLE
        } else {
            view.btn_quit.visibility = View.GONE
        }


        arguments?.getString(KEY_TOP_TEXT)?.let {
            view.txt_top_title_generic_dialog.visibility = View.VISIBLE
            view.txt_top_title_generic_dialog.text = it
        } ?: run {
            view.txt_top_title_generic_dialog.visibility = View.GONE
        }

        arguments?.getInt(KEY_IMAGE)?.let {
            view.imgIconDialog.setImageResource(it)
            Flubber.with()
                .animation(Flubber.Preset.WOBBLE)
                .interpolator(Flubber.Curve.BZR_EASE_IN_OUT_CUBIC)
                .duration(1000).repeatCount(3).repeatMode(ValueAnimator.REVERSE)
                .delay(500)
                .createFor(view.imgIconDialog).start()
        } ?: run {
            view.imgIconDialog.visibility = View.GONE
        }

        arguments?.getString(KEY_DIALOG_TAG)?.let {
            _tag = it
        }

        view.btn_right.setOnClickListener {
            listener?.onGenericDialogRightClicked(_tag)
            dismiss()
        }

        view.btn_left.setOnClickListener {
            listener?.onGenericDialogLeftClicked(_tag)
            dismiss()
        }

        view.btn_quit.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //If dialog parent is a fragment we attach the listener here.
        //If it is not null means parent is an context and listener is already attached onAttach
        if(listener == null || targetFragment != null) {
            try {
                listener = targetFragment as GenericDialogListener?
            } catch (e: Exception) {
                Log.d("GenericDialog", "Calling Fragment must implement listener")
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val root = RelativeLayout(activity)
//        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        val dialog = Dialog(requireActivity())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(root)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.dialog_bg.toHexColor(requireActivity())))
//        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        val windowParams = dialog.window?.attributes
//        windowParams?.dimAmount = 0.0f
//        dialog.window?.attributes = windowParams
//
//        return dialog
//
//
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GenericDialogListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onGenericDialogDismiss(_tag)
    }


    companion object {
        const val KEY_TITLE_TEXT = "key_title_text"
        const val KEY_DESC_TEXT = "key_desc_text"
        const val KEY_BTN_RIGHT_TEXT = "key_btn_right_text"
        const val KEY_BTN_LEFT_TEXT = "key_btn_left_text"
        const val KEY_TOP_TEXT = "key_top_text"
        const val KEY_IMAGE = "key_image"
        const val KEY_CANCELABLE = "key_cancelable"
        const val KEY_DIALOG_TAG = "key_dialog_tag"

        @JvmStatic
        fun newInstance(activity: FragmentActivity, fragment: Fragment?, title: String? = null, description: String? = null,  btnRightText: String? = null,
                        btnLeftText: String? = null, _tag: String? = null,  imageRsc: Int? = null, topText: String? = null, cancelable: Boolean = true) {
            val dialogFragment =  GenericDialog().apply {
                arguments = Bundle().apply {
                    title?.let {
                        this.putString(KEY_TITLE_TEXT, it)
                    }
                    description?.let {
                        this.putString(KEY_DESC_TEXT, it)
                    }
                    btnRightText?.let {
                        this.putString(KEY_BTN_RIGHT_TEXT, it)
                    }
                    btnLeftText?.let {
                        this.putString(KEY_BTN_LEFT_TEXT, it)
                    }
                    topText?.let {
                        this.putString(KEY_TOP_TEXT, it)
                    }
                    _tag?.let {
                        this.putString(KEY_DIALOG_TAG, it)
                    }
                    imageRsc?.let {
                        this.putInt(KEY_IMAGE, it)
                    }

                    this.putBoolean(KEY_CANCELABLE, cancelable)
                }
            }
            dialogFragment.setTargetFragment(fragment, 0)
            dialogFragment.show(activity.supportFragmentManager, "generic_dialog")
        }
    }
}