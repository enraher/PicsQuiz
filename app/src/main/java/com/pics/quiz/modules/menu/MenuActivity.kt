package com.pics.quiz.modules.menu

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityOptionsCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.item_package.view.*
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pics.flubber.Flubber
import com.pics.quiz.R
import com.pics.quiz.base.BaseActivity
import com.pics.quiz.extensions.anim
import com.pics.quiz.extensions.loadImageFromMemory
import com.pics.quiz.extensions.loadName
import com.pics.quiz.model.AppContent
import com.pics.quiz.model.Package
import com.pics.quiz.model.database.PackageWithLevelsPlayed
import com.pics.quiz.modules.common.FlagsDialog
import com.pics.quiz.modules.common.GenericDialog
import com.pics.quiz.modules.common.ReportDialog
import com.pics.quiz.other.GridSpacingItemDecoration
import com.pics.quiz.other.MainController
import com.pics.quiz.other.getCurrentLanguage
import com.pics.quiz.other.getResId
import com.pics.quiz.repositories.PreferencesManager
import com.pics.quiz.repositories.database.DatabaseManager
import java.util.*
import kotlin.collections.ArrayList

class MenuActivity : BaseActivity<MenuViewModel>(),
    GenericDialog.GenericDialogListener,
    FlagsDialog.FlagsDialogListener,
    ReportDialog.ReportDialogListener {

    override val layoutResId: Int
        get() = R.layout.activity_menu

    override fun setViews() {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = GridLayoutManager(this, 3)
        recycler.addItemDecoration(GridSpacingItemDecoration(resources.getDimension(R.dimen.margin_cards).toInt()))

        viewModel.loadList.observe(this, androidx.lifecycle.Observer { shouldLoad ->
            if(shouldLoad) {
                loadRecyclerView()
            }
        })

        ly_bottom_menu.alpha = 0f
        ly_bottom_menu.anim(
            Flubber.Preset.SLIDE_UP,
            Flubber.Curve.BZR_EASE_IN_OUT_SINE,
            1000,
            500
        ) {
            viewModel.getPackagesWithLevels()
        }

        img_flag.setOnClickListener{
            img_flag.anim(
                Flubber.Preset.POP,
                Flubber.Curve.BZR_EASE_IN_OUT_SINE,
                200
            ) {
                FlagsDialog.newInstance(this, null)
            }
        }

        ly_coins.setOnClickListener {
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(ly_coins, "img_coins")).toBundle()
//            this.launchActivityForResult(Intent(this, ShopActivity::class.java), 124, bundle)
        }

        img_leaderboard.setOnClickListener {
            showToast("Not implemented for this purpose")
        }

        setFlag()

        img_sound.setOnClickListener {
            if(PreferencesManager(this).musicOn) {
                PreferencesManager(this).musicOn = false
                img_sound.setImageResource(R.drawable.ic_sound_off)
            } else {
                PreferencesManager(this).musicOn = true
                img_sound.setImageResource(R.drawable.ic_sound_on)
            }
        }

        img_music.setOnClickListener {
            if(PreferencesManager(this).musicOn) {
                PreferencesManager(this).musicOn = false
                img_music.setImageResource(R.drawable.ic_music_off)
                stopSoundService()
            } else {
                PreferencesManager(this).musicOn = true
                img_music.setImageResource(R.drawable.ic_music_on)
                startSoundService()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        PreferencesManager(this).user?.stringCoins()?.let {
            txt_stars_menu.text = it
        } ?: run {
            txt_stars_menu.text = "0"
        }
        recycler?.adapter?.notifyDataSetChanged()
        setAudio()
    }

    private fun setAudio(){

        if(PreferencesManager(this).musicOn) {
            img_music.setImageResource(R.drawable.ic_music_on)
        } else {
            img_music.setImageResource(R.drawable.ic_music_off)
        }

        if(PreferencesManager(this).musicOn) {
            img_sound.setImageResource(R.drawable.ic_sound_on)
        } else {
            img_sound.setImageResource(R.drawable.ic_sound_off)
        }

    }

    private fun setFlag() {
        val codeGot = getCurrentLanguage(this)

        val drawableRscId = getResId("ic_$codeGot", this)
        if(drawableRscId!= null && drawableRscId > 0) {
            img_flag.setImageResource(drawableRscId)
        } else {
            img_flag.setImageResource(R.drawable.ic_earth)
        }

    }

    private fun loadRecyclerView() {
        MainController.getInstance(this).getAppContent()?.let { appContent ->
            recycler.adapter = MenuAdapter(this@MenuActivity, appContent, viewModel.packagesPlayed) { pairs, packageId ->

                pairs.add(Pair(ly_coins, "img_coins"))
                val array = pairs.toTypedArray()
                val bundle =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, *array).toBundle()

//                val intent = Intent(this, PackageDetailActivity::class.java)
//                intent.putExtra(EXTRA_PACKAGE_SELECTED, packageId)
//                this.launchActivityForResult(intent, 237, bundle)
            }
        }
    }

    override fun onGenericDialogRightClicked(_tag: String) {
        super.onBackPressed()
    }

    override fun onGenericDialogLeftClicked(_tag: String) {}

    override fun onGenericDialogDismiss(_tag: String) {}

    override fun onFlagDialogChanged() {
        setFlag()
        recycler?.adapter?.notifyDataSetChanged()
    }


    override fun onReportDialogClose(reported: Boolean) {
        showToast(R.string.thanks)
    }

    class MenuAdapter(
        private val activity: Activity,
        private val appContent: AppContent,
        private val packagesWithLevelsPlayed: TreeMap<String, PackageWithLevelsPlayed>,
        private val listener: (pairs: ArrayList<Pair<View, String>>, packageSelected: Package) -> Unit
    ) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

        private var lastPosition = -1

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(appContent, packagesWithLevelsPlayed, position, activity)
            setAnimation(holder, position)
        }

        private fun setAnimation(holder: ViewHolder, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                holder.itemView.alpha = 0F
                holder.itemView.anim(
                        Flubber.Preset.ZOOM_CARD_IN,
                        Flubber.Curve.BZR_EASE_IN_OUT_CUBIC,
                        (400L..900L).random(),
                        (200L..1100L).random())
                lastPosition = position
            }
        }

        override fun onViewDetachedFromWindow(holder: ViewHolder) {
            super.onViewDetachedFromWindow(holder)
            holder.clearAnimation()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return  ViewHolder(layoutInflater.inflate(R.layout.item_package, parent, false)) { pairs, packageSelected ->
                listener(pairs, packageSelected)
            }
        }

        override fun getItemCount(): Int {
            return appContent.packages.size
        }

        class ViewHolder(
                view: View,
                private val listener: (pairs: ArrayList<Pair<View, String>>, packageSelected: Package) -> Unit
        ) : RecyclerView.ViewHolder(view) {

            private val thisView = view

            fun clearAnimation() {
                thisView.alpha = 1F
                thisView.clearAnimation()
            }

            fun bind(appContent: AppContent, packagesWithLevelsPlayedMap: TreeMap<String, PackageWithLevelsPlayed>, position: Int, activity: Activity){
                val thisPackage = appContent.packages[position]
                if (thisPackage.free) {
                        thisView.txt_package_free.visibility = View.VISIBLE
                        thisView.txt_package_free.setTextColor(ContextCompat.getColor(activity, R.color.white))
                        thisView.txt_package_free.setBackgroundColor(ContextCompat.getColor(activity, R.color.bg_txt_package_item_free))
                        thisView.txt_package_free.text = activity.resources.getString(R.string.free)
                    } else {
                        thisView.txt_package_free.visibility = View.GONE
                    }


                if (packagesWithLevelsPlayedMap.contains(thisPackage.id)) {
                    packagesWithLevelsPlayedMap[thisPackage.id]?.packagePlayed?.getProgress(thisPackage.countNumberLevels()).let { progress ->
                        if(progress != null && progress > 0) {
                            thisView.txt_package_free.visibility = View.VISIBLE
                            thisView.txt_package_free.setTextColor(ContextCompat.getColor(activity, R.color.txt_package_item))
                            thisView.txt_package_free.setBackgroundColor(ContextCompat.getColor(activity, R.color.bg_txt_package_item))
                            thisView.txt_package_free.text = "$progress%"
                        }
                    }
                }


                if(thisPackage.isNewLevel) {
                    thisView.img_new_levesl_mark.visibility = View.VISIBLE
                    thisView.txt_new_levesl_mark.visibility = View.VISIBLE
                } else {
                    thisView.img_new_levesl_mark.visibility = View.GONE
                    thisView.txt_new_levesl_mark.visibility = View.GONE
                }


                thisView.img_package.loadImageFromMemory(appContent.fileImages, thisPackage.id)
                thisView.txt_package_name.loadName(thisPackage.name)
                itemView.setOnClickListener {

                    val pairs = ArrayList<Pair<View, String>>()
                    pairs.add(Pair(itemView, "img_package"))
                    pairs.add(Pair(itemView.txt_package_name, "txt_package_name"))

                    listener(pairs, thisPackage)
                }
            }
        }
    }


    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }
}