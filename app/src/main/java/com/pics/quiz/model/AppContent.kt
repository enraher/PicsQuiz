package com.pics.quiz.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AppContent (
    @SerializedName("file_images_packages") val fileImages: String?,
    @SerializedName("languages_available") val languagesAvailable: ArrayList<Language>,
    val packages: ArrayList<Package>
    ) : Serializable {

        fun orderPackages(){
            packages.sortBy { it.order }
        }
    }
