package com.challenge.meli.data.network

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error<T>(val message: String,val code: Int): NetworkState<T>()
}