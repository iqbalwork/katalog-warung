package com.uziro.katalogwarung.presentation.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uziro.katalogwarung.data.model.ProductData

// ProductList.kt
@Composable
fun ProductList(
    productList: List<ProductData>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(productList) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onItemClick(product.key) },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = product.nama ?: "No Name",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Stok: ${product.stok ?: 0}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    // Add the purchase and sell prices here
                    Text(
                        text = "Harga Beli: Rp${product.hargabeli ?: 0}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Harga Jual: Rp${product.hargajual ?: 0}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val dummyProducts = listOf(
        ProductData("1", "12345", 10000, 12000, "Dummy Product 1", "Pcs", 50, "Food"),
        ProductData("2", "67890", 5000, 7500, "Dummy Product 2", "Kg", 20, "Beverage"),
        ProductData("3", "54321", 2000, 3000, "Dummy Product 3", "Pcs", 100, "Snack")
    )
    MaterialTheme {
        ProductList(productList = dummyProducts, onItemClick = {})
    }
}