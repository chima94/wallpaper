package com.example.searchui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.searchdata.SearchViewModel
import com.example.searchresultdestination.SearchResultDestination
import com.example.toaster.ToasterViewModel

@Composable
fun SearchUI(){
    val viewModel: SearchViewModel = hiltViewModel()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)) {
            SearchInput(){query ->
                viewModel.navigate(SearchResultDestination.createSearchRoute(query))
            }
            SearchInputExplained()
        }
    }
}


@Composable
fun SearchInputExplained() {
    Text(
        text = stringResource(com.example.strings.R.string.search_input_explain) ,
        fontSize = 12.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, end = 24.dp)
            .animateContentSize()
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInput(
    onInputText: (inputText: String) -> Unit = {}
){
    val viewModel = hiltViewModel<ToasterViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current
    var inputText by rememberSaveable{ mutableStateOf("")}
    val invalidInput = inputText.isBlank() || inputText.length < 3
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        isError = invalidInput,
        label = { Text(text = stringResource(id = com.example.strings.R.string.search)) },
        value = inputText,
        onValueChange = { inputText = it },
        keyboardOptions = KeyboardOptions(
            KeyboardCapitalization.Words, autoCorrect = false,
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            if (invalidInput) {
                viewModel.shortToast(com.example.strings.R.string.search_error)
                return@KeyboardActions
            }
            keyboardController?.hide()
            onInputText(inputText)
        }),

    )
}