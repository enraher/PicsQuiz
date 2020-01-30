package com.pics.quiz.repositories.database

import android.content.Context
import com.pics.quiz.model.database.PackagePlayed
import java.util.*

object DatabaseManager {

    fun addPackagesPlayed(context: Context, packagesPlayed: ArrayList<PackagePlayed>): LongArray {
        return AppDatabase.getInstance(context).packagePlayedDao().insertAll(*packagesPlayed.toTypedArray())
    }

    @Suppress("UNCHECKED_CAST")
    fun getPackagePlayeds(context: Context): ArrayList<PackagePlayed> {
        val packagePlayeds = ArrayList<PackagePlayed>()
        AppDatabase.getInstance(context).packagePlayedDao().getPackagesPlayed().takeIf { it.isNotEmpty() }?.let {
            packagePlayeds.addAll(it)
        }

        return packagePlayeds
    }

    fun updatePackagePlayeds(context: Context, packagePlayeds: ArrayList<PackagePlayed>): Int {
        return AppDatabase.getInstance(context).packagePlayedDao().updateAll(*packagePlayeds.toTypedArray())
    }

    fun deletePackagePlayed(context: Context, packagePlayed: PackagePlayed): Int {
        return AppDatabase.getInstance(context).packagePlayedDao().delete(packagePlayed)
    }
}