package com.kantin.e_ticket.util

import android.Manifest
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.kantin.e_ticket.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object Helper {

    /**
     * Permissions needed for the app
     */
    var PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun requestBody(file: File?): MultipartBody.Part? {
        if (file == null) {
            return null
        }
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    fun requestBody(field: String): RequestBody {
        return field.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun requestBody(field: Int): RequestBody {
        return field.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    }

}