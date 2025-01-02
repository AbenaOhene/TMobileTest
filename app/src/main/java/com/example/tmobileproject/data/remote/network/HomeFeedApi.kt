package com.example.tmobileproject.data.remote.network

import com.example.tmobileproject.data.remote.model.HomeFeedData
import retrofit2.Response
import retrofit2.http.GET

interface HomeFeedApi {

    @GET(HOME_FEED_PATH)
    suspend fun getHomeFeedData(): Response<HomeFeedData>

    companion object{
        const val BASE_URL = "https://private-8ce77c-tmobiletest.apiary-mock.com/test/"
        private const val HOME_FEED_PATH = "home"
    }
}