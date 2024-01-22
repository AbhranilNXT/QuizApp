package com.example.quizapp.data.repo

import android.util.Log
import com.example.quizapp.data.local.DataOrException
import com.example.quizapp.data.model.QuestionItem
import com.example.quizapp.data.remote.QuestionApi
import java.util.ArrayList
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {

    private val dataOrException
    =DataOrException<ArrayList<QuestionItem>, Boolean, Exception> ()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        }catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exc","getAllQuestions: ${dataOrException.e!!.localizedMessage}")

        }
        return dataOrException
    }
}