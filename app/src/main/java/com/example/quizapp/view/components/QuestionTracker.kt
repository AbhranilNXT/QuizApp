package com.example.quizapp.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionTracker( counter : Int = 10, outOf : Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){

            withStyle(style = SpanStyle(color = AppColors._LightGrey,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp)
            ){
                append("Question $counter/")
                withStyle(style = SpanStyle(color = AppColors._LightGrey,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp)
                ) {
                    append("$outOf")
                }
            }
        }
    },
        modifier = Modifier.padding(20.dp))
}