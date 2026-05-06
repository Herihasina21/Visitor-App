package com.example.visitor.network

import com.example.visitor.model.Stats
import com.example.visitor.model.Visitor
import retrofit2.http.*

interface VisitorApi {

    @GET("api/visitors")
    suspend fun getVisitors(): ApiResponse<List<Visitor>>

    @GET("api/visitors/stats")
    suspend fun getStats(): ApiResponse<Stats>

    @POST("api/visitors")
    suspend fun addVisitor(@Body visitor: Visitor): ApiResponse<Visitor>

    @PUT("api/visitors/{id}")
    suspend fun updateVisitor(
        @Path("id") id: Long,
        @Body visitor: Visitor
    ): ApiResponse<Visitor>

    @DELETE("api/visitors/{id}")
    suspend fun deleteVisitor(@Path("id") id: Long): ApiResponse<Void?>
}