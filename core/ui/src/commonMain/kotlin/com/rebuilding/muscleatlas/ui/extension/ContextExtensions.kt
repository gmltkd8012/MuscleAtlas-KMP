package com.rebuilding.muscleatlas.ui.extension

import android.content.Context
import android.content.Intent

inline fun <reified T> Context.startActivity(vararg argument: Pair<String, Any> = arrayOf()) {
    val intent = Intent(this, T::class.java)
    if (argument.isNotEmpty()) {
        argument.forEach { (key, value) ->
            when (value) {
                is String -> intent.putExtra(key, value)
                is Boolean -> intent.putExtra(key, value)
                is Int -> intent.putExtra(key, value)
            }
        }
    }
    startActivity(intent)
}