package com.rebuilding.muscleatlas.ui.extension

import android.content.Context
import android.content.Intent

inline fun <reified T> Context.startActivity(vararg argument: Pair<String, String> = arrayOf()) {
    val intent = Intent(this, T::class.java)
    if (argument.isNotEmpty()) {
        argument.forEach { arg ->
            intent.putExtra(arg.first, arg.second)
        }
    }
    startActivity(intent)
}