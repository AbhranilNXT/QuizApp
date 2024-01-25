package com.example.quizapp

import android.os.Bundle
import android.util.Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.local.UiState
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.vm.QuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    QuizHome()
                }
            }
        }
    }
}

@Composable
fun QuizHome(viewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(viewModel)
}

@Composable
fun Questions(viewModel: QuestionsViewModel) {
//    val questions = viewModel.data.value.data
    val question = viewModel.question.collectAsState().value

    when (question) {

        is UiState.Idle -> {

        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            d("Success Value", question.data.results.toString())
        }

        else -> {

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuizAppTheme {

    }
}