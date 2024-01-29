package com.example.quizapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.local.UiState
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.repo.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {

    private val _question: MutableStateFlow<UiState<Question>> = MutableStateFlow(UiState.Idle)
    val question = _question.asStateFlow()

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {

        _question.value = UiState.Loading

        viewModelScope.launch {
            try {
                _question.value = repository.getAllQuestions()
            } catch (e: Exception) {
                // TODO :-
            }
        }
    }
}