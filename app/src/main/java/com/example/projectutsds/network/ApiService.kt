package com.example.projectutsds.network
import com.example.projectutsds.models.DoaResponse


import com.example.projectutsds.models.DoaItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // UBAH BARIS INI
    @GET("api")
    suspend fun getAllDoa(): Response<List<DoaItem>>

    @GET("api/id/{id}")
    suspend fun getDoaById(@Path("id") id: String): Response<DoaItem>

    @GET("api/doa/{name}")
    suspend fun getDoaByName(@Path("name") name: String): Response<DoaItem>
}