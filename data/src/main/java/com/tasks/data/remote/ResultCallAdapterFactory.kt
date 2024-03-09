package com.tasks.data.remote

import com.google.gson.Gson
import com.tasks.domain.model.BaseApiResponse
import com.tasks.domain.model.FailureCodeParser
import com.tasks.domain.model.FailureModel
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }
        val upperBound = getParameterUpperBound(0, returnType)

        return if (upperBound is ParameterizedType && upperBound.rawType == Result::class.java) {
            object : CallAdapter<Any, Call<Result<*>>> {
                override fun responseType(): Type = getParameterUpperBound(0, upperBound)

                override fun adapt(call: Call<Any>): Call<Result<*>> =
                    ResultCall(call) as Call<Result<*>>
            }
        } else null
    }
}

class ResultCall<T>(private val delegate: Call<T>) :
    Call<Result<T>> {

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
                } else {
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

            override fun onFailure(call: Call<T>, t: Throwable) =
                callback.onResponse(this@ResultCall, Response.success(Result.failure(t)))
        }
    )

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
