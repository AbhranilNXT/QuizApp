package com.example.quizapp.data.local

import java.lang.Exception

//Wrapper class
sealed interface UiState<out T> {

    object Idle : UiState<Nothing>

    object Loading : UiState<Nothing>

    class Success<T>(val data: T) : UiState<T>

    class Error(val message: String) : UiState<Nothing>
}