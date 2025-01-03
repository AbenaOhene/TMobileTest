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

    // This function gets the local list of cards stored in the database.
    private suspend fun getLocalListOfCards(): List<Card> {
        //Get cards  from local database and map data
       return dao.getCards().map { it.toCard() }
    }

    // This function gets the data either from local or remote.
    override suspend fun getHomeFeed(): Resource<HomeFeedPage?> {
        return try {
            //Api call
            val response = apiService.getHomeFeedData()
            if (response.isSuccessful) {
                // Process the body if the response is successful
                val remoteBody = response.body()?.let { body ->
                    // Map the cards and save them to the database
                    body.page?.cards?.map { it.toCardDto() }?.let { cards ->
                        dao.clearCardDatabase()
                        dao.upsertCardList(cards)
                    }
                    // Return the page from the response as HomeFeedPage
                    body.page
                }
                // If remote body is not null, return remote data
                if (remoteBody != null) {
                    Log.d("HomeFeedRepositoryImpl", "Remote data fetched successfully.")
                    Resource.Success(remoteBody)
                } else {
                    // If remote data is null, return local data
                    val localCards = getLocalListOfCards()
                    Log.d("HomeFeedRepositoryImpl", "Local data fetched successfully. $localCards")
                    if (localCards.isNotEmpty()) {
                        Resource.Success(HomeFeedPage(localCards))
                    } else {
                        Resource.Error("No data found.")
                    }
                }
            } else {
                // If the response is not successful and data has been cached returned data else return error
                val localCards = getLocalListOfCards()
                Log.d("HomeFeedRepositoryImpl", "Local data fetched successfully. $localCards")
                if(localCards.isNotEmpty()){
                    Resource.Success(HomeFeedPage(localCards))

                } else {
                    Resource.Error("Failed to fetch data")
                }
            }
        } catch (exception: Exception) {
            // Get local data if is not empty else return error
            val localCards = getLocalListOfCards()
            if (localCards.isNotEmpty()) {
                Resource.Success(HomeFeedPage(localCards))
            } else {
                // Catching any exceptions (e.g., network error) and returning an error
                Resource.Error(exception.message ?: "An unexpected error occurred")
            }

        }
    }

}