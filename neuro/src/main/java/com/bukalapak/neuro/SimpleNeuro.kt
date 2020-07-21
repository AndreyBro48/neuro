package com.bukalapak.neuro

import android.content.Context
import android.net.Uri
import com.bukalapak.result.Response

class SimpleNeuro<T> {

    private val neuro = Neuro<T>()
    private var soma: Soma<T>? = null

    fun setBase(uri: Uri) {
        soma = object : Soma<T>(ID) {
            override fun onProcessNoBranch(signal: Signal): Response<T> = Response.error("Error")
            override fun onProcessOtherBranch(signal: Signal): Response<T> = Response.error("Error")
            override val schemes = uri.scheme?.let { listOf(it) } ?: emptyList()
            override val hosts = uri.host?.let { listOf(it) } ?: emptyList()
            override val ports = if (uri.port == -1) emptyList() else listOf(uri.port)

        }
    }

    fun addPath(expression: String, action: SignalAction<T>) {
        val soma = soma ?: throw IllegalStateException("You must call SimpleNeuro.setBase(Uri) first.")
        neuro.connect(soma, AxonBranch(expression, action))
    }

    fun proceed(url: String, context: Context? = null):Response<T> {
        return neuro.proceed(url, context)
    }

    fun clearPaths() {
        neuro.clearConnection()
    }

    companion object {
        private const val ID = "simple"
    }
}