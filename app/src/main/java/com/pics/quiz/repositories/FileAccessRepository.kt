package com.pics.quiz.repositories

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.util.zip.ZipFile

object FileAccessRepository {

    fun getFileFromMemory(context: Context, filePath: String) : File {
        return File(context.filesDir, filePath)
    }

    fun readStringFromFile(f: File) : String {
        val bufferedReader: BufferedReader = f.bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    fun checkIfImageFilesExist(context: Context, dirToCheck: String): Boolean {
        val dir = File(context.filesDir, dirToCheck)
        return (dir.exists() && dir.listFiles().isNotEmpty())
    }

    fun unzip(context: Context, zipFile: File) {
        val zip = ZipFile(zipFile)
        val enumeration = zip.entries()
        while (enumeration.hasMoreElements()) {
            val entry = enumeration.nextElement()
            val destFilePath = File(context.filesDir, entry.name)
            destFilePath.parentFile.mkdirs()

            if (entry.isDirectory)
                continue

            val bufferedIs = BufferedInputStream(zip.getInputStream(entry))

            bufferedIs.use {
                destFilePath.outputStream().buffered(1024).use { bos ->
                    bufferedIs.copyTo(bos)
                }
            }
        }
        zipFile.delete()
    }
}