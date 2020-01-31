package com.pics.quiz.repositories

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pics.quiz.model.Version
import com.pics.quiz.other.ReportManager
import java.io.File

object FirebaseRepository {

    private fun storageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    fun getVersions(listener: (version: Version?, error: String?) -> Unit) {
        val docRef = FirebaseFirestore.getInstance().collection("appdata").document("versions")
        docRef.get()
            .addOnSuccessListener { document ->
                document?.data?.let {
                    listener(document.toObject(Version::class.java), null)
                } ?: run {
                    listener(null,"Unknown error")
                }
            }
            .addOnFailureListener { exception ->
                listener(null, exception.localizedMessage)
            }

    }

    fun getFileFromFirebase(context: Context, directoryRoot: String, filePath: String, listener: (file: File?) -> Unit) {
            val pathReference = storageReference().child("$directoryRoot/$filePath")
            val file = File(context.filesDir, filePath)
            pathReference.getFile(file).addOnSuccessListener {
                 listener(file)
            }.addOnFailureListener { exception ->
                ReportManager.getInstance(context).crashReport(exception)
                 listener(null)
            }
    }
}