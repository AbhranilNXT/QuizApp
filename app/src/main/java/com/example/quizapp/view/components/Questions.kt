package com.example.quizapp.view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Preview
@Composable
fun QuestionDisplay() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp),
        color = AppColors._DarkPurple) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            QuestionTracker()
        }
    }
}



@Composable
fun QuestionTracker( counter : Int = 10, outOf : Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){

            withStyle(style = SpanStyle(color = AppColors._LightGrey,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp)){
                append("Question $counter/")
                withStyle(style = SpanStyle(color = AppColors._LightGrey,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp)) {
                    append("$outOf")
                }
            }
        }
    },
        modifier = Modifier.padding(20.dp))
}