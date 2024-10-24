package com.ks.aplikasidicodingevent.data.remote.retrofit

import com.ks.aplikasidicodingevent.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("/events")
    fun getEvents(
        @Query("active") active: Int,
    ): Call<EventResponse>

    @GET("/events")
    fun search(
        @Query("active") active: Int,
        @Query("q") query: String
    ): Call<EventResponse>

    @GET("/events")
    fun newEvent(
        @Query("active") active: Int,
        @Query("limit") limit: Int
    ): Call<EventResponse>
}
