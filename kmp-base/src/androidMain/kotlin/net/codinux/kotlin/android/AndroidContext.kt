package net.codinux.kotlin.android

import android.content.Context

object AndroidContext {
    lateinit var applicationContext: Context

    fun getApplicationContextIfInitialized(): Context? =
        if (AndroidContext::applicationContext.isInitialized) {
            applicationContext
        } else {
            null
        }

}