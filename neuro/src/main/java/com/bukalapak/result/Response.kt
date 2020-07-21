package com.bukalapak.result

class Response<T> private constructor(
        _body: T? = null,
        val success: Boolean,
        _error: String? = null
){
    private val mBody:T? = _body
    val body: T
    get() = if (success) mBody!! else throw IllegalArgumentException("unsuccessful response have not body")
    private val mError:String? = _error
    val error: String
        get() = if (!success) mError!! else throw IllegalArgumentException("unsuccessful response have not body")


    companion object {
        fun <T> success(body:T):Response<T>{
            return Response(_body = body, success = true)
        }

        fun <T> error(error: String):Response<T>{
            return Response(_error = error, success = false)
        }
    }
}