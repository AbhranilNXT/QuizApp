package com.example.quizapp.data.remote

import com.example.quizapp.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("api.php")
    suspend fun getAllQuestions(
        @Query("amount") amount:String,
        @Query("category") category:String,
        @Query("difficulty") difficulty:String
    ): Response<Question>
}