package com.uziro.katalogwarung.presentation.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.uziro.katalogwarung.data.model.ProductData
import com.uziro.katalogwarung.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavHostController,
    productKey: String?,
    viewModel: MainViewModel = hiltViewModel()
) {
    val isEditing = productKey != null
    var product by remember { mutableStateOf(ProductData()) }

    // Fetch product if editing
    LaunchedEffect(productKey) {
        if (isEditing) {
            viewModel.getProduct(productKey).collect { fetchedProduct ->
                fetchedProduct?.let { product = it }
            }
        }
    }

    ProductContent(
        isEditing = isEditing,
        product = product,
        onProductChange = {},
        onUpdateProduct = { key, product ->
            viewModel.updateProduct(product.key, product)
            navController.popBackStack()
        },
        onAddProduct = {
            viewModel.addProduct(product)
            navController.popBackStack()
        },
        onBackPress = {
            navController.popBackStack()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductContent(
    isEditing: Boolean,
    product: ProductData,
    onProductChange: (ProductData) -> Unit,
    onUpdateProduct: (String, ProductData) -> Unit,
    onAddProduct: (ProductData) -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Product" else "Add Product") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Text fields for each product property
            OutlinedTextField(
                value = product.nama.orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(nama = it))
                },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.barcode.orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(barcode = it))
                },
                label = { Text("Barcode") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.hargabeli?.toString().orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(hargabeli = it.toIntOrNull()))
                },
                label = { Text("Purchase Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.hargajual?.toString().orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(hargajual = it.toIntOrNull()))
                },
                label = { Text("Sell Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.satuan.orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(satuan = it))
                },
                label = { Text("Unit") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.stok?.toString().orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(stok = it.toIntOrNull()))
                },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = product.kategori.orEmpty(),
                onValueChange = {
                    onProductChange(product.copy(kategori = it))
                },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    if (isEditing) {
                        onUpdateProduct(product.key, product)
                    } else {
                        onAddProduct(product)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isEditing) "Update Product" else "Add Product")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductFormEditPreview() {
    MaterialTheme {
        // Simulating the edit state by passing a key.
        // The LaunchedEffect would then fetch the data.
        ProductContent(
            isEditing = false,
            product = ProductData(),
            onProductChange = {},
            onUpdateProduct = { key, product ->

            },
            onAddProduct = {

            },
            onBackPress = {

            })
    }
}