package com.uziro.katalogwarung.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.uziro.katalogwarung.ui.theme.KatalogWarungTheme

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Scaffold { contentPadding ->
        ConstraintLayout(modifier = Modifier.padding(contentPadding)) {
//            TextField()
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    KatalogWarungTheme {
        HomeContent()
    }
}