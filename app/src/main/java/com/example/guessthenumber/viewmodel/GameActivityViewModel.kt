package com.example.guessthenumber.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guessthenumber.model.GameStatus
import com.example.guessthenumber.model.RandomOrgRequest
import com.example.guessthenumber.model.RequestParam
import com.example.guessthenumber.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameActivityViewModel : ViewModel() {
    private val _number = MutableLiveData<Int>() // Живий даний для зберігання числа
    val number: LiveData<Int> get() = _number // Зовнішній LiveData для спостереження
    var randomNumberGenerated = false
    //-------------

    private val _attemptsLeft = MutableLiveData<Int>()
    val attemptsLeft: LiveData<Int> get() = _attemptsLeft

    private val _gameStatus = MutableLiveData<GameStatus>()
    val gameStatus: LiveData<GameStatus> get() = _gameStatus

    private var attempts = 5

    init {
        _attemptsLeft.value = attempts
        _gameStatus.value = GameStatus.IN_PROGRESS
        generateRandomNumber()
    }

    fun makeGuess(userGuess: Int) {
        viewModelScope.launch {
            if (_gameStatus.value == GameStatus.IN_PROGRESS) {
                attempts--
                _attemptsLeft.value = attempts

                if (userGuess == number.value) {
                    _gameStatus.value = GameStatus.WON
                } else if (attempts == 0) {
                    _gameStatus.value = GameStatus.LOST
                }
            }
        }
    }

    fun generateRandomNumber() {
        if (!randomNumberGenerated) {
            CoroutineScope(Dispatchers.IO).launch {
                //hardcode start
                val requestParam = RequestParam(
                    "0bab98af-5798-4c1b-a59d-304507830c39",
                    1,
                    1,
                    100,
                    true
                )
                val request = RandomOrgRequest(
                    "2.0",
                    "generateIntegers",
                    requestParam,
                    1
                )
                //hardcode end

                val randomNumberResponse = RetrofitInstance.randomIntApi.getRandomNumber(request)
                val number = randomNumberResponse.result.random.data[0]

                // Оновлюємо дані за допомогою LiveData
                _number.postValue(number)
                randomNumberGenerated = true
            }
        }
    }

    fun resetRandomNumberGenerated() {
        randomNumberGenerated = false
    }
}

