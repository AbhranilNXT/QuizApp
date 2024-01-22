package com.example.quizapp.data.remote

import com.example.quizapp.data.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("api.php?amount=20&category=31&difficulty=easy")
    suspend fun getAllQuestions(): Question
}