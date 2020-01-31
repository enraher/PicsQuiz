package com.pics.quiz.modules.common

/**
 * Created by enrasoft on 8/3/18.
 */
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pics.flubber.Flubber
import com.pics.quiz.R
import com.pics.quiz.extensions.anim
import com.pics.quiz.model.Language
import com.pics.quiz.other.GridSpacingItemDecoration
import com.pics.quiz.other.MainController
import com.pics.quiz.other.getResId
import com.pics.quiz.repositories.PreferencesManager
import kotlinx.android.synthetic.main.dialog_flags.view.*
import kotlinx.android.synthetic.main.flag_placeholder.view.*


class FlagsDialog : DialogFragment() {

    interface FlagsDialogListener {
        fun onFlagDialogChanged()
    }

    private var listener: FlagsDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(listener == null || targetFragment != null) {
            try {
                listener = targetFragment as FlagsDialogListener?
            } catch (e: Exception) {
                Log.d("FlagsDialog", "Calling Fragment must implement listener")
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FlagsDialogListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_flags, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialogs)

        activity?.let { activity ->
            MainController.getInstance(activity).getAppContent()?.let {
                loadRecyclerView(view, it.languagesAvailable)
            }
        }


        view.btn_flags_quit.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun loadRecyclerView(view: View, languages: List<Language>) {
        view.recycler_flags.setHasFixedSize(true)
        view.recycler_flags.layoutManager = GridLayoutManager(activity, 4)
        view.recycler_flags.addItemDecoration(GridSpacingItemDecoration(resources.getDimension(R.dimen.margin_cards).toInt()))
        view.recycler_flags.adapter = FlagsAdapter(activity as Activity, languages) {
            listener?.onFlagDialogChanged()
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(activity: FragmentActivity, fragment: Fragment?) {
            val dialogFragment =  FlagsDialog()
            dialogFragment.setTargetFragment(fragment, 0)
            dialogFragment.show(activity.supportFragmentManager, "flag_dialog")
        }
    }
}

class FlagsAdapter(private val activity: Activity, private val languages: List<Language> = ArrayList(), private val listener: () -> Unit) : RecyclerView.Adapter<FlagsAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = languages[position]
        holder.bind(item)
        setAnimation(holder, position)
    }

    private fun setAnimation(holder: ViewHolder, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            holder.itemView.alpha = 0F
            holder.itemView.anim(Flubber.Preset.ZOOM_CARD_IN, Flubber.Curve.BZR_EASE_IN_OUT_CUBIC, (400L..900L).random(), (200L..1100L).random())
            lastPosition = position
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(activity, layoutInflater.inflate(R.layout.flag_placeholder, parent, false)) {
            listener()
        }
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    class ViewHolder(val activity: Activity, val view: View, val listener: () -> Unit) : RecyclerView.ViewHolder(view) {


        fun clearAnimation() {
            view.alpha = 1F
            view.clearAnimation()
        }

        fun bind(language: Language){
            val drawableRscId = getResId("ic_" + language.code, view.context)
            if(drawableRscId!= null && drawableRscId > 0) {
                view.img_flag.setImageResource(drawableRscId)
            } else {
                view.img_flag.setImageResource(R.drawable.ic_earth)
            }

            view.txt_flag_name.text = language.name
            view.img_flag.setOnClickListener{
                PreferencesManager(view.context).languageSelected = language.code
                this@ViewHolder.listener()
            }
        }
    }
}