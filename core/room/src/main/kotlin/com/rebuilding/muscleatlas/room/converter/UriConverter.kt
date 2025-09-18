package com.rebuilding.muscleatlas.room.converter

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.core.net.toUri

@ProvidedTypeConverter
class UriConverter {
    @TypeConverter
    fun convertStringToUri(value: String?): Uri? {
        return value?.let { it.toUri() }
    }

    @TypeConverter
    fun convertUriToString(uri: Uri?): String? {
        return uri?.toString()
    }
}