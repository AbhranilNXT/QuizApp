package com.example.quizapp.data.repo

import com.example.quizapp.data.local.UiState
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.remote.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {


    suspend fun getAllQuestions(): UiState<Question> {
        val response = api.getAllQuestions(amount = "20", category = "31", difficulty = "easy")
        if (response.isSuccessful)
            return UiState.Success(data = response.body()!!)
        else
            return UiState.Error(message = "Error")
    }
}