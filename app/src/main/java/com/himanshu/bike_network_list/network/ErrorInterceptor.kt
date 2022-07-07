package com.himanshu.bike_network_list.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

const val TAG = "BikeNetworksApp"
const val RESPONSE =
    "\nAPI Response ====================================================================================================\n"
const val BAD_REQUEST = "BAD REQUEST==========>\n"
const val ERROR_CODE = "\nError Code:"
const val MESSAGE = "\nMessage:"
const val ERROR = "\nERROR ==========>"
const val SERVER_ERROR = "\nSERVER ERROR ==========>"
const val REQUEST =
    "=\n********************************************* API REQUEST *****************************************************\n"

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        when (response.code()) {
            in 200..299 -> {
                val rawJson = response.body()?.string()
                response = response.newBuilder()
                    .body(ResponseBody.create(response.body()?.contentType(), rawJson!!)).build();
                Log.i(
                    TAG,
                    REQUEST + request.toString() + RESPONSE + rawJson + RESPONSE
                )
            }
            in 300..399 -> Log.e(
                TAG,
                REQUEST + request.toString() + ERROR + ERROR_CODE + response.code() + MESSAGE + response.message()
            )
            400 -> Log.e(
                TAG,
                REQUEST + request.toString() + BAD_REQUEST + ERROR_CODE + response.code() + MESSAGE + response.message()
            )
            in 401..499 -> Log.e(
                TAG,
                REQUEST + request.toString() + ERROR + ERROR_CODE + response.code() + MESSAGE + response.message()
            )
            in 500..599 -> Log.e(
                TAG,
                REQUEST + request.toString() + SERVER_ERROR + ERROR_CODE + response.code() + MESSAGE + response.message()
            )
        }
        return response
    }
}