package com.example.practicsing.ui.pract

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.repository.PracticeRepository
import com.example.practicsing.data.repository.PracticeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PracticeViewModel(
    private val repository: PracticeRepository
) : ViewModel() {

    private val _currentDay = MutableStateFlow(1)
    val currentDay: StateFlow<Int> = _currentDay.asStateFlow()

    private var userId: String = ""

    fun loadUserData(context: Context) {
        val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        userId = sharedPref.getString("userid", "") ?: ""
        
        if (userId.isNotEmpty()) {
            viewModelScope.launch {
                val streak = repository.getCurrentStreak(userId)
                _currentDay.value = streak
            }
        }
    }

    fun finishPractice(onSuccess: (Int) -> Unit) {
        if (userId.isNotEmpty()) {
            viewModelScope.launch {
                val newStreak = repository.registerPracticeDone(userId)
                _currentDay.value = newStreak
                onSuccess(newStreak)
            }
        } else {
            // Fallback if no user logged in, just keep current day or increment locally in memory
            onSuccess(_currentDay.value)
        }
    }
}

class PracticeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PracticeViewModel(PracticeRepositoryImpl()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
