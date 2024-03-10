package com.example.quizapp.view.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.quizapp.data.local.UiState
import com.example.quizapp.vm.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val question = viewModel.question.collectAsState().value

    val questionIndex = remember {
        mutableStateOf(0)
    }

    when (question) {

        is UiState.Idle -> {


        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            val questions = try {
                question.data.results[questionIndex.value]
            } catch (ex: Exception){
                null
            }

            val questionsCount = try {
                question.data.results.size
            } catch (e : Exception) {
                null
            }

            QuestionDisplay(question = questions!!,
                questionSize = questionsCount!!,
                questionIndex = questionIndex) {
                questionIndex.value += 1
            }
        }

        else -> {

        }

    }
}