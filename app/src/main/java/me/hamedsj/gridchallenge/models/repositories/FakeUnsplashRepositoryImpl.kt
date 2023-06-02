package me.hamedsj.gridchallenge.models.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.hamedsj.gridchallenge.GridChallenge
import me.hamedsj.gridchallenge.models.entities.PhotoResponseModel
import me.hamedsj.gridchallenge.utils.Failure
import me.hamedsj.gridchallenge.utils.Response
import me.hamedsj.gridchallenge.utils.Success
import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.Charset


class FakeUnsplashRepositoryImpl : UnsplashRepository {

    companion object {
        const val RESPONSE_CACHE_FILE_NAME = "response-cache-1-30.json"
    }


    override suspend fun fetchPhotos(): Response<List<PhotoResponseModel>, Throwable> {
        try {
            val cachedResponse = readJsonFromAssets(RESPONSE_CACHE_FILE_NAME)
            cachedResponse?.let { response ->
                val photoListType = object : TypeToken<List<PhotoResponseModel>>() {}.type
                return Success(Gson().fromJson(response, photoListType))
            } ?: let {
                return Failure(Throwable("fetch photos response body was null"))
            }
        } catch (throwable: Throwable) {
            return Failure(throwable)
        }
    }

    private fun readJsonFromAssets(filePath: String): String? {
        try {
            val source = GridChallenge.applicationContext.assets.open(filePath).source().buffer()
            return source.readByteString().string(Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


}