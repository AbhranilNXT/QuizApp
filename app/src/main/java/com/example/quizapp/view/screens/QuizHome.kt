package com.example.quizapp.view.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.view.components.Questions
import com.example.quizapp.vm.QuestionsViewModel


@Composable
fun QuizHome(viewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(viewModel)
}
