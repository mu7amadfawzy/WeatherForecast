package com.tasks.data.remote.adapter

import com.google.gson.Gson
import com.tasks.domain.model.BaseApiResponse
import com.tasks.domain.model.FailureCodeParser
import com.tasks.domain.model.FailureModel
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * The ResultCall class wraps a Retrofit Call object and converts the response to a Result object.
 * The enqueue() method handles both success and error responses.
 * For success responses, it creates a Result.success() object with the response body.
 * For error responses, it parses the error body to extract the error code and message.
 * It then creates a FailureModel object and wraps it in a Result.failure() object.
 * The enqueue() method also handles HttpExceptions, which are thrown when the server returns an error response.
 * The other methods of the Call interface are implemented to delegate to the underlying delegate object.
 */
class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) = delegate.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) =
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            response.code(),
                            Result.success(response.body()!!)
                        )
                    )
                } else
                    parseError(response, callback)


            override fun onFailure(call: Call<T>, t: Throwable) =
                callback.onResponse(this@ResultCall, Response.success(Result.failure(t)))
        }
    )

    private fun parseError(
        response: Response<T>,
        callback: Callback<Result<T>>
    ) {
        val apiResponse: BaseApiResponse = Gson().fromJson(
            response.errorBody()?.charStream(),
            BaseApiResponse::class.java
        )
        val code = apiResponse.details?.getOrNull(0)?.errorCode
        val message = apiResponse.message
        callback.onResponse(
            this@ResultCall,
            Response.success(
                Result.failure(
                    FailureModel(
                        code = code ?: response.code().toString(),
                        message = message ?: response.message(),
                        localMessage = code?.let { FailureCodeParser.parse(it).description }
                    )
                )
            )
        )

        callback.onResponse(
            this@ResultCall,
            Response.success(
                Result.failure(
                    HttpException(response)
                )
            )
        )
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun execute(): Response<Result<T>> =
        Response.success(Result.success(delegate.execute().body()!!))

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun clone(): Call<Result<T>> = ResultCall(delegate.clone())

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}