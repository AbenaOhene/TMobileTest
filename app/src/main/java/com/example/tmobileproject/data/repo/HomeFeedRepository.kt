package com.example.tmobileproject.data.repo

import com.example.tmobileproject.data.remote.model.HomeFeedPage
import com.example.tmobileproject.util.Resource

interface HomeFeedRepository {
    suspend fun getHomeFeed(): Resource<HomeFeedPage?>
}