package com.example.quizapp.data.local

import java.lang.Exception

//Wrapper class
data class DataOrException<T, Boolean, E: Exception> (
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)