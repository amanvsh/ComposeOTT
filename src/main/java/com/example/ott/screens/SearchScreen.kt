package com.example.ott.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ott.R
import com.example.ott.model.Content
import com.example.ott.utils.getScreenOrientation
import com.example.ott.viewmodel.ContentListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavHostController) {

    val viewModel: ContentListViewModel = hiltViewModel()
    val filteredContentList = remember { mutableStateOf(emptyList<Content>()) }
    val searchText: MutableState<String> = remember { mutableStateOf("") }
    val listState = rememberLazyGridState()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    //Hide keyboard on scroll start
    val firstVisibleIndex by derivedStateOf { listState.firstVisibleItemScrollOffset }
    LaunchedEffect(firstVisibleIndex) {
        if (filteredContentList.value.isNotEmpty()) {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(key1 = Unit) {
        // Cursor focus request
        focusRequester.requestFocus()
        // Launch keyboard when BasicTextField(EditText) gets the focus
        keyboardController?.show()

        //Fetch all the pages(1,2,3) data and store in ViewModel
        viewModel.getContentList()
        viewModel.filterListFlow.collect {
                filteredContentList.value = it
        }
    }

    SearchList(
        filteredContentList = filteredContentList,
        searchText = searchText,
        navController = navController,
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        viewModel = viewModel,
        listState = listState
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchList(
    filteredContentList: MutableState<List<Content>>,
    searchText: MutableState<String>,
    navController: NavHostController,
    focusRequester: FocusRequester,
    viewModel: ContentListViewModel,
    listState: LazyGridState,
    keyboardController: SoftwareKeyboardController?
) {
    Column {
        //Header View
        SearchHeaderView(searchText.value, onEmailChange = { newText ->
            if (newText.isEmpty() || newText.length >= 3) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.filterSearcherData(newText)
                }
            }
            searchText.value = newText
        }, navController, focusRequester, keyboardController)

        // Searched content list grid
        LazyVerticalGrid(
            state = listState,
            modifier = Modifier.background(Color.Black),
            columns = GridCells.Fixed(getScreenOrientation(LocalConfiguration.current)),
        ) {
            items(filteredContentList.value) { content ->
                ContentListItemCard(item = content, searchKeyword = searchText.value)
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchHeaderView(
    contentName: String,
    onEmailChange: (String) -> Unit,
    navController: NavHostController,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 12.dp)

    ) {

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)

        ) {

                BasicTextField(
                    value = contentName,
                    singleLine = true,
                    onValueChange = { onEmailChange(it) },
                    modifier = Modifier
                        .weight(0.9f)
                        .focusRequester(focusRequester)
                        .padding(start = 8.dp, bottom = 5.dp),
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    cursorBrush = SolidColor(Color.White)
                    )

            IconButton(
                modifier = Modifier
                    .weight(0.1f), // Adjust size as needed
                onClick = {
                    keyboardController?.hide()
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_cancel),
                    contentDescription = "Close",
                    Modifier.size(16.dp)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            color = Color.White,
            thickness = 1.5.dp
        )
    }

}
