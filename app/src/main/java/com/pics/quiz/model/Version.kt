package com.pics.quiz.model

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Version (
    @get:PropertyName("app_version")
    @set:PropertyName("app_version")
    var appVersion: Int = 0,

    @get:PropertyName("packages_json_version")
    @set:PropertyName("packages_json_version")
    var  packagesJsonVersion: Int = 0
) : Serializable