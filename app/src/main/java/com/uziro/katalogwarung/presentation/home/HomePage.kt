package com.uziro.katalogwarung.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.uziro.katalogwarung.data.model.ProductData
import com.uziro.katalogwarung.presentation.MainViewModel
import com.uziro.katalogwarung.presentation.ProductListState
import com.uziro.katalogwarung.presentation.ProductSortOption
import com.uziro.katalogwarung.presentation.product.ProductList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {

    val searchText by viewModel.searchQuery.collectAsStateWithLifecycle()
//    val productList by viewModel.filteredProductList.collectAsStateWithLifecycle()
    val productListState by viewModel.productListState.collectAsStateWithLifecycle()

    val sortOptions = listOf(
        "Name" to ProductSortOption.NAME_ASC,
        "Sell Price" to ProductSortOption.PRICE_SELL_ASC,
        "Purchase Price" to ProductSortOption.PRICE_BUY_ASC,
        "Stock" to ProductSortOption.STOCK_ASC
    )

    HomeContent(
        state = productListState,
        sortOptions = sortOptions,
        onClickSortItem = {
            viewModel.onSortOptionChanged(it)
        },
        searchText = searchText,
        onSearchText = {
            viewModel.onSearchQueryChanged(it)
        },
        onAddItem = {
            navController.navigate("product_form")
        }
    ) { productKey ->
        navController.navigate("product_form?key=$productKey")
    }
}

@Composable
fun HomeContent(
    state: ProductListState,
    sortOptions: List<Pair<String, ProductSortOption>>,
    onClickSortItem: (ProductSortOption) -> Unit,
    searchText: String,
    onSearchText: (String) -> Unit,
    onAddItem: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItem,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = onSearchText,
                    modifier = Modifier.weight(1f),
                    label = { Text("Search Products") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    singleLine = true,
                )
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort")
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        sortOptions.forEach { (text, sort) ->
                            DropdownMenuItem(
                                text = { Text(text) },
                                onClick = {
                                    onClickSortItem(sort)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            when (state) {
                is ProductListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ProductListState.Success -> {
                    ProductList(
                        productList = state.products,
                        onItemClick = { productKey ->
                            onItemClick(productKey)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                is ProductListState.Error -> {
                    // Display error message
                    Text("Error: ${state.message}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    val searchText = ""
    val productListState =
        ProductListState.Success(
            listOf(
                ProductData(nama = "Beng Beng", hargajual = 2500),
                ProductData(nama = "Chocolatos", hargajual = 500),
                ProductData(nama = "Energen", hargajual = 1000),
            )
        )

    val sortOptions = listOf(
        "Name" to ProductSortOption.NAME_ASC,
        "Sell Price" to ProductSortOption.PRICE_SELL_ASC,
        "Purchase Price" to ProductSortOption.PRICE_BUY_ASC,
        "Stock" to ProductSortOption.STOCK_ASC
    )

    HomeContent(
        state = productListState,
        sortOptions = sortOptions,
        onClickSortItem = {

        },
        searchText = searchText,
        onSearchText = {

        },
        onAddItem = {

        }
    ) { productKey ->

    }
}