package com.example.practicsing.ui.my.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.ui.diary.DiaryScreen
import com.example.practicsing.data.model.Diary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiaryViewModel : ViewModel() {

    private val repo = DiaryRepository()

    private val _diaryList = MutableStateFlow<List<Diary>>(emptyList())
    val diaryList: StateFlow<List<Diary>> = _diaryList

    init {
        loadDiaries()
    }

    fun loadDiaries() {
        viewModelScope.launch {
            _diaryList.value = repo.getAllDiary()
        }
    }

    fun addDiary(title: String, content: String) {
        viewModelScope.launch {
            repo.addDiary(
                Diary(
                    title = title,
                    content = content
                )
            )
            loadDiaries()
        }
    }

    fun updateDiary(diary: Diary) {
        viewModelScope.launch {
            repo.updateDiary(diary)
            loadDiaries()
        }
    }

    fun deleteDiary(id: String) {
        viewModelScope.launch {
            repo.deleteDiary(id)
            loadDiaries()
        }
    }
}