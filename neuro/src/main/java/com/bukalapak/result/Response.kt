package com.bukalapak.result

class Response<T> private constructor(
        body: T? = null,
        val success: Boolean,
        error: String? = null
){
    private val mBody:T? = body
    val body: T
    get() = if (success) mBody!! else throw IllegalArgumentException("unsuccessful response have not body")
    private val mError:String? = error
    val error: String
        get() = if (!success) mError!! else throw IllegalArgumentException("unsuccessful response have not body")


    companion object {
        fun <T> success(body:T):Response<T>{
            return Response(body = body, success = true)
        }

        fun <T> error(error: String):Response<T>{
            return Response(error = error, success = false)
        }
    }
}