package me.hamedsj.gridchallenge.models.repositories

import me.hamedsj.gridchallenge.models.api.UnsplashApiInterface
import me.hamedsj.gridchallenge.models.entities.PhotoResponseModel
import me.hamedsj.gridchallenge.utils.Failure
import me.hamedsj.gridchallenge.utils.Response
import me.hamedsj.gridchallenge.utils.Success
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashRepositoryImpl : UnsplashRepository {

    companion object {
        const val UNSPLASH_API_BASE_URL = "https://api.unsplash.com/"
    }

    val unsplashApiInterface: UnsplashApiInterface

    init {
        unsplashApiInterface = Retrofit.Builder()
            .baseUrl(UNSPLASH_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApiInterface::class.java)
    }


    override suspend fun fetchPhotos(): Response<List<PhotoResponseModel>, Throwable> {
        try {
            val response = unsplashApiInterface.fetchPhotos()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    return Success(body)
                } ?: let {
                    return Failure(Throwable("fetch photos response body was null"))
                }
            } else {
                return Failure(Throwable("fetch photos request was unsuccessful! -> ${response.code()}"))
            }
        } catch (throwable: Throwable) {
            return Failure(throwable)
        }
    }


}