package com.pics.quiz.modules.menu

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pics.quiz.base.BaseViewModel
import com.pics.quiz.model.database.PackagePlayed
import com.pics.quiz.model.database.PackageWithLevelsPlayed
import com.pics.quiz.other.MainController
import com.pics.quiz.repositories.database.DatabaseManager
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by enrasoft on 24/1/20.
 */

class MenuViewModel(application: Application) : BaseViewModel(application) {
    val packagesPlayed : TreeMap<String, PackageWithLevelsPlayed> = TreeMap()

    private val _loadList = MutableLiveData<Boolean>()
    val loadList: LiveData<Boolean> get() = _loadList

    fun getPackagesWithLevels(){
        dataBackgroundProcess({ DatabaseManager.getPackagesWithLevelsPlayed(context) }) {
            if(it is ArrayList<*>) {
                for (packageWithLevels in it.filterIsInstance<PackageWithLevelsPlayed>()) {
                    packagesPlayed[packageWithLevels.packagePlayed.id] = packageWithLevels
                }
                _loadList.value = true
            }
        }
    }
}