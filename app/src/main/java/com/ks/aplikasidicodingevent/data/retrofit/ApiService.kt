package com.ks.aplikasidicodingevent.data.retrofit

import com.ks.aplikasidicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("/events")
    fun getEvents(
        @Query("active") active: Int,

    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<EventResponse>

    @GET("/events")
    fun search(
        @Query("active") active: Int,
        @Query("q") query: String
    ): Call<EventResponse>
}
