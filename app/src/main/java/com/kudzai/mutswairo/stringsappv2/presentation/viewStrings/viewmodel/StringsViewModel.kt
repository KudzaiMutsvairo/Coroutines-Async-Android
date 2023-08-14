package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudzai.mutswairo.stringsappv2.domain.repository.PageRepository
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.events.StringsScreenEvent
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.model.StringsScreenStateData
import com.kudzai.mutswairo.stringsappv2.util.Resource
import com.kudzai.mutswairo.stringsappv2.util.StringUtils.escapeSpecialCharacter
import com.kudzai.mutswairo.stringsappv2.util.StringUtils.replaceSpecialCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for processing data, uiState and uiEvents for the Application
 */
@HiltViewModel
class StringsViewModel @Inject constructor(
    private val repository: PageRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<StringsScreenStateData>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<StringsScreenStateData>> = _uiState

    fun onEvent(event: StringsScreenEvent) {
        when (event) {
            is StringsScreenEvent.OnGetStrings -> {
                getStrings()
            }
        }
    }

    private fun getStrings() {
        viewModelScope.launch {
            repository.getWebPageFromUrl().let { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { page ->
                            try {
                                // Use coroutineScope to run multiple coroutines concurrently and handle errors
                                coroutineScope {
                                    val taskTenthCharacter = async { getTenthCharacter(page) }
                                    val taskEveryTenthCharacter =
                                        async { getEveryTenthCharacter(page) }
                                    val taskUniqueWordsAndCount =
                                        async { getUniqueWordsAndCount(page) }

                                    val tenthCharacter = taskTenthCharacter.await()
                                    val everyTenthCharacter = taskEveryTenthCharacter.await()
                                    val uniqueWordsAndCount = taskUniqueWordsAndCount.await()

                                    _uiState.emit(
                                        UiState.Success(
                                            StringsScreenStateData(
                                                tenthChar = tenthCharacter,
                                                everyTenthChar = everyTenthCharacter,
                                                uniqueWordsAndCount = uniqueWordsAndCount,
                                            ),
                                        ),
                                    )
                                }
                            } catch (e: Exception) {
                                _uiState.emit(
                                    UiState.Error(e.message ?: "An unknown error occurred"),
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.emit(
                            UiState.Error(result.message ?: "An unknown error occurred"),
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getTenthCharacter(page: String): String {
        return if (page.length > 10) page[9].toString().replaceSpecialCharacters() else ""
    }

    private suspend fun getEveryTenthCharacter(page: String): List<String> =
        withContext(Dispatchers.Default) {
            val everyTenthCharacterList = mutableListOf<String>()
            for (i in 9..page.length step 10) {
                everyTenthCharacterList.add(page[i].escapeSpecialCharacter())
            }
            everyTenthCharacterList
        }

    private suspend fun getUniqueWordsAndCount(page: String): Map<String, Int> =
        withContext(Dispatchers.Default) {
            val words = page.split(" ")
            val uniqueWords = mutableMapOf<String, Int>()
            for (word in words) {
                if (uniqueWords.containsKey(word)) {
                    uniqueWords[word] = uniqueWords[word]!! + 1
                } else {
                    uniqueWords[word] = 1
                }
            }
            uniqueWords
        }
}
