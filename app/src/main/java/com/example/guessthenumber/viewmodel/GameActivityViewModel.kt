package com.example.guessthenumber.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guessthenumber.config.RandomOrgConfig
import com.example.guessthenumber.model.GameStatus
import com.example.guessthenumber.model.RandomOrgRequest
import com.example.guessthenumber.model.RequestParam
import com.example.guessthenumber.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameActivityViewModel : ViewModel() {
    companion object {
        private const val API_RECEIVED_DATA_ERROR_MESSAGE = "Отримано некоректні данні з API"
    }

    private val _number = MutableLiveData<Int>()
    val number: LiveData<Int> get() = _number
    var randomNumberGenerated = false

    private val _attemptsLeft = MutableLiveData<Int>()
    val attemptsLeft: LiveData<Int> get() = _attemptsLeft

    private val _gameStatus = MutableLiveData<GameStatus>()
    val gameStatus: LiveData<GameStatus> get() = _gameStatus

    private var attempts = RandomOrgConfig.MAX_ATTEMPTS

    init {
        _attemptsLeft.value = attempts
        _gameStatus.value = GameStatus.IN_PROGRESS
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

    fun generateRandomNumber(context: Context) {
        if (!randomNumberGenerated && isInternetAvailable(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                // Forming a request on Random.org
                val requestParam = RequestParam(
                    RandomOrgConfig.RANDOM_ORG_API_KEY,
                    RandomOrgConfig.NUMBER_OF_DIGITS,
                    RandomOrgConfig.MIN_NUMBER,
                    RandomOrgConfig.MAX_NUMBER,
                    true
                )
                val request = RandomOrgRequest(
                    RandomOrgConfig.JSONRPC,
                    RandomOrgConfig.RANDOM_ORG_METHOD,
                    requestParam,
                    RandomOrgConfig.RANDOM_ORG_ID
                )
                // use Retrofit
                val randomNumberResponse = RetrofitInstance.randomIntApi.getRandomNumber(request)

                // Check if the response has data
                if (randomNumberResponse.result != null && randomNumberResponse.result.random.data.isNotEmpty()) {
                    val number = randomNumberResponse.result.random.data[0]
                    // update the data using LiveData
                    _number.postValue(number)
                    randomNumberGenerated = true
                } else {
                    viewModelScope.launch(Dispatchers.Main) {
                        Toast.makeText(
                            context, API_RECEIVED_DATA_ERROR_MESSAGE, Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else if (!isInternetAvailable(context)){
            _number.postValue(-1)
            randomNumberGenerated = true
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
