package com.example.repository.retrofitextension


import com.example.util.DataState
import com.example.util.MessageType
import com.example.util.Response
import com.example.util.UIComponentType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.SocketTimeoutException


fun <T> handleUseCaseException(e: Throwable): DataState<T>{
    e.printStackTrace()
    when(e){
        is HttpException ->{
            val errorResponse = convertErrorBody(e)
            return DataState.error<T>(
                response = Response(
                    message = errorResponse,
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            )
        }
        else ->{
            return DataState.error<T>(
                response = Response(
                    message = e.message,
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            )
        }
    }
}



private fun convertErrorBody(throwable: HttpException) : String?{
    return try{
        throwable.response()?.message()
    }catch (exception: Exception){
        "Something went wrong"
    }
}




