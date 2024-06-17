package com.example.sundayout.screens.main.home


import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sundayout.SundayoutApplication
import com.example.sundayout.data.BusinessRepository
import com.example.sundayout.data.NetworkBusinessRepository
import com.example.sundayout.model.Business
import com.example.sundayout.screens.auth.register.SignInViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface BusinessesUiState {
    data class Success(val businesses: List<Business>): BusinessesUiState
    object Error: BusinessesUiState
    object Loading: BusinessesUiState
}

class HomeViewModel(private val businessesRepository: BusinessRepository): ViewModel() {
    var businessesUiState: BusinessesUiState by mutableStateOf(BusinessesUiState.Loading)
        private set
    var searchText by mutableStateOf("")
        private set

    init {
        getBusinesses()
    }

    fun updateSearchText(text: String) {
        searchText = text
    }

    fun getBusinesses() {
        viewModelScope.launch {
            businessesUiState = BusinessesUiState.Loading
            businessesUiState = try {
                BusinessesUiState.Success (
                    businessesRepository.getBusinesses()
                )
            } catch (e: IOException) {
                BusinessesUiState.Error
            } catch (e: HttpException) {
                BusinessesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SundayoutApplication)
                val businessesRepository = application.container.businessRepository
                HomeViewModel(businessesRepository = businessesRepository)
            }
        }
    }
}