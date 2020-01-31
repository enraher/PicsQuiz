package com.pics.quiz.model

import java.io.Serializable

data class Report(
    val packageId: String,
    val levelId: String = "",
    val sublevelId: String = "",
    var reportType: String = "",
    var comment: String = "",
    var languageGame: String = ""
) : Serializable