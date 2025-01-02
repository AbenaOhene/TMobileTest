package com.example.tmobileproject.data.repo

import android.util.Log
import com.example.tmobileproject.data.local.CardDao
import com.example.tmobileproject.data.local.model.toCard
import com.example.tmobileproject.data.local.model.toCardDto
import com.example.tmobileproject.data.remote.model.Card
import com.example.tmobileproject.data.remote.model.HomeFeedPage
import com.example.tmobileproject.data.remote.network.HomeFeedApi
import com.example.tmobileproject.util.Resource
import javax.inject.Inject

class HomeFeedRepositoryImpl @Inject constructor(
    private val apiService: HomeFeedApi,
    private val dao: CardDao
) : HomeFeedRepository {

    private suspend fun getLocalListOfCards(): List<Card> {
       return dao.getCards().map { it.toCard() }
    }

    override suspend fun getHomeFeed(): Resource<HomeFeedPage?> {
        return try {
            //Api call
            val response = apiService.getHomeFeedData()
            if (response.isSuccessful) {
                // Process the body if the response is successful
                val remoteBody = response.body()?.let { body ->
                    // Map the cards and save them to the database
                    body.page?.cards?.map { it.toCardDto() }?.let { cards ->
                        dao.upsertCardList(cards)
                    }
                    // Return the page from the response as HomeFeedPage
                    body.page
                }
                // If the response is valid, return the data in Resource.Success
                Log.d("HomeFeedRepositoryImpl", "Data fetched successfully: ${response.body()?.page}")
                Resource.Success(remoteBody) // Returning HomeFeedPage or null if no data
            } else {
                // If the response is not successful and data has been cached returned data else return error
                val localCards = getLocalListOfCards()
                if(localCards.isNotEmpty()){
                    Resource.Success(HomeFeedPage(localCards))

                } else {
                    Resource.Error("Failed to fetch data")
                }
            }
        } catch (exception: Exception) {
            // Catching any exceptions (e.g., network error) and returning an error
            Resource.Error(exception.message ?: "An unexpected error occurred")
        }
    }

}