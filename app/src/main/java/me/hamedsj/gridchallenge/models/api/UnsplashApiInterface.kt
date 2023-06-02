package me.hamedsj.gridchallenge.models.api

import me.hamedsj.gridchallenge.BuildConfig
import me.hamedsj.gridchallenge.models.entities.PhotoResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashApiInterface {

    @Headers("Authorization: ${BuildConfig.ACCESS_KEY}")
    @GET("photos")
    suspend fun fetchPhotos(
        page: Int = 1,
        per_page: Int = 30
    ): Response<List<PhotoResponseModel>>

}