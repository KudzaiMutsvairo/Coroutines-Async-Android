package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.events.StringsScreenEvent
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.model.StringsScreenStateData
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.viewmodel.StringsViewModel
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.viewmodel.UiState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun ViewStringsScreen(
    navigator: DestinationsNavigator,
    viewModel: StringsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Header() },
        floatingActionButton = {
            GetDataFABButton(onGetDataClicked = {
                isLoading = true
                viewModel.onEvent(StringsScreenEvent.OnGetStrings)
            })
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp),
                ) {
                    when (uiState) {
                        is UiState.Error -> {
                            val state = uiState as UiState.Error

                            LaunchedEffect(state.message) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = state.message,
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                            }
                        }

                        is UiState.Loading -> {
                            if (isLoading) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        CircularProgressIndicator()
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(text = "Loading...")
                                    }
                                }
                            }
                        }

                        is UiState.Success -> {
                            val state = uiState as UiState.Success<StringsScreenStateData>
                            TenthCharacterSection(character = state.data.tenthChar)
                            TabsSection(
                                tenthChars = state.data.everyTenthChar,
                                uniqueWords = state.data.uniqueWordsAndCount,
                            )
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header() {
    TopAppBar(
        title = { Text("Strings App") },
    )
}

@Composable
fun GetDataFABButton(modifier: Modifier = Modifier, onGetDataClicked: () -> Unit = { }) {
    FloatingActionButton(
        onClick = { onGetDataClicked() },
        modifier = Modifier
            .padding(16.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Get Data",
            tint = Color.White,
        )
    }
}

@Composable
fun TenthCharacterSection(character: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(40.dp).width(200.dp).padding(1.dp),
    ) {
        Box(
            modifier = Modifier.padding(1.dp).fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(text = "Tenth Character: $character", modifier = Modifier.padding(3.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsSection(
    tenthChars: List<String>,
    uniqueWords: Map<String, Int>,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = {
                    if (pagerState.currentPage != 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                0,
                            )
                        }
                    }
                },
                text = { Text("All Tenth Chars") },
            )
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = {
                    if (pagerState.currentPage != 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                1,
                            )
                        }
                    }
                },
                text = { Text("Unique Words") },
            )
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AllTenthCharsSection(tenthChars)
                1 -> UniqueWordsSection(uniqueWords)
            }
        }
    }
}

@Composable
fun AllTenthCharsSection(
    data: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(data) { dataItem ->
            TenthCharItem(character = dataItem)
        }
    }
}

@Composable
fun UniqueWordsSection(
    data: Map<String, Int>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(data.toList()) { (key, value) ->
            UniqueWordItem(word = key, count = value)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Preview
@Composable
fun PreviewTenthCharacterSection() {
    TenthCharacterSection(character = "A")
}
