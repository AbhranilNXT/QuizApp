package com.example.quizapp.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.data.local.UiState
import com.example.quizapp.data.model.Result
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


@Composable
fun QuestionDisplay(question : Result,
                    questionSize : Int,
                    questionIndex: MutableState<Int>,
                    onNextClicked: (Int) -> Unit = {}
) {

    val choicesState = remember(question) {
        question.incorrect_answers.toMutableList()
    }

    choicesState.add(question.correct_answer)
    choicesState.shuffle()

    val answerState = remember(question) {
        mutableStateOf<Int?>(value = null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer : (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.correct_answer
        }
    }
    val pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = AppColors._DarkPurple) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            if(questionIndex.value >=1) ShowProgress(score = questionIndex.value+1)

            QuestionTracker(counter = questionIndex.value+1, questionSize )
            DrawDottedLine(pathEffect = pathEffect)
            
            Column {
                Text(text = question.question.replace("&quot;","'")
                    .replace("&#039;","'"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColors._OffWhite2,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f))

                choicesState.forEachIndexed { index, answerText ->
                    Row(modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors._OffDarkPurple,
                                    AppColors._OffDarkPurple
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        )
                        .background(color = Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                    if(correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green.copy(alpha = 0.2f)
                                    } else {
                                        Color.Red.copy(alpha = 0.2f)
                                    }
                            ))

                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color = if(correctAnswerState.value == true && index == answerState.value)
                                    Color.Green
                                else if(correctAnswerState.value == false && index == answerState.value)
                                    Color.Red
                                else AppColors._OffWhite,
                                fontSize = 17.sp)) {

                                append(answerText.replace("&quot;","'")
                                    .replace("&#039;","'"))

                            }
                        }
                        
                        Text(text = annotatedString, modifier = Modifier.padding(8.dp))
                    }
                }
                Button(onClick = { onNextClicked(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = buttonColors(
                        containerColor = AppColors._LightBlue
                    )
                ) {
                    Text(text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors._OffWhite,
                        fontSize = 17.sp)
                }
            }
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

@Composable
fun DrawDottedLine(pathEffect : androidx.compose.ui.graphics.PathEffect) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(color = AppColors._LightGrey,
            start = Offset(0f,0f),
            end = Offset(size.width,0f),
            pathEffect = pathEffect)

    }
}

@Preview
@Composable
fun ShowProgress(score: Int = 12) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)
    ))

    val progressFactor = remember(score) {
        mutableStateOf(score*0.05f)
    }

    Row(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .height(48.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    AppColors._LightPurple, AppColors._LightPurple
                )
            ),
            shape = RoundedCornerShape(32.dp)
        )
        .clip(
            shape = RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {

        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )) {
            Text(text = (score*10).toString(),
                modifier = Modifier.clip(shape = RoundedCornerShape(24.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(8.dp),
                color = AppColors._OffWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}