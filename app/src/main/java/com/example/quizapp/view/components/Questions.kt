package com.example.quizapp.view.components

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.quizapp.data.local.UiState
import com.example.quizapp.vm.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val question = viewModel.question.collectAsState().value

    when (question) {

        is UiState.Idle -> {


        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            Log.d("Success Value", question.data.results.toString())
            Log.d("size","${question.data.results.size}")
            question.data.results.forEach { questionItem ->

                Log.d("resultqqqq", questionItem.question.toString()
                    .replace("&quot;","'")
                    .replace("&#039;","'")
                )
            }
        }

        else -> {

        }

    }
}