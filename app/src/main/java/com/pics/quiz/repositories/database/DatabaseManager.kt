package com.pics.quiz.repositories.database

import android.content.Context
import com.pics.quiz.model.database.PackagePlayed
import com.pics.quiz.model.database.PackageWithLevelsPlayed
import java.util.*

object DatabaseManager {

//    fun addPackagesPlayed(context: Context, packagesPlayed: ArrayList<PackagePlayed>): LongArray {
//        return AppDatabase.getInstance(context).packagePlayedDao().insertAll(*packagesPlayed.toTypedArray())
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    fun getPackagesPlayed(context: Context): ArrayList<PackagePlayed> {
//        val packagePlayeds = ArrayList<PackagePlayed>()
//        AppDatabase.getInstance(context).packagePlayedDao().getPackagesPlayed().takeIf { it.isNotEmpty() }?.let {
//            packagePlayeds.addAll(it)
//        }
//
//        return packagePlayeds
//    }
//
//    fun updatePackagesPlayed(context: Context, packagePlayeds: ArrayList<PackagePlayed>): Int {
//        return AppDatabase.getInstance(context).packagePlayedDao().updateAll(*packagePlayeds.toTypedArray())
//    }
//
//    fun deletePackagesPlayed(context: Context, packagePlayed: PackagePlayed): Int {
//        return AppDatabase.getInstance(context).packagePlayedDao().delete(packagePlayed)
//    }

    fun getPackagesWithLevelsPlayed(context: Context): ArrayList<PackageWithLevelsPlayed> {
        val list = ArrayList<PackageWithLevelsPlayed>()
        AppDatabase.getInstance(context).packageWithLevelPlayedDao().getPackagesWithLevelsPlayed().takeIf { it?.isNotEmpty() == true }?.let {
            list.addAll(ArrayList(it))
        }

        return list
    }

    fun getPackageWithLevelsPlayed(context: Context, id: String): PackageWithLevelsPlayed? {
        return AppDatabase.getInstance(context).packageWithLevelPlayedDao().getPackageWithLevelsPlayed(id)
    }
}