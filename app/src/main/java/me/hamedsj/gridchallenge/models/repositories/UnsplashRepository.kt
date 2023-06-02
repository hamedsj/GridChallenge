package me.hamedsj.gridchallenge.models.repositories

import me.hamedsj.gridchallenge.models.entities.PhotoResponseModel
import me.hamedsj.gridchallenge.utils.Response

interface UnsplashRepository {

    suspend fun fetchPhotos() : Response<List<PhotoResponseModel>, Throwable>

}