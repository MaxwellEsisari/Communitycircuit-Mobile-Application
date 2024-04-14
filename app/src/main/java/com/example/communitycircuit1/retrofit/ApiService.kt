package com.example.communitycircuit1.retrofit
import com.example.communitycircuit1.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/pay")
    fun pay(
        @Query("amount") amount: String,
        @Query("phone") phone: String,
        @Query("campaignBeneficiary") campaignBeneficiary: String
    ): Call<ApiResponse>
}