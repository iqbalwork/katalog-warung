package com.uziro.katalogwarung.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uziro.katalogwarung.data.ProductRepository
import com.uziro.katalogwarung.data.model.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var searchQuery = MutableStateFlow("")
    var sortOption = MutableStateFlow(ProductSortOption.NAME_ASC)

    val productList = repository.getProductList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val filteredProductList: StateFlow<List<ProductData>> =
        productList.combine(searchQuery) { products, query ->
            if (query.isBlank()) {
                products
            } else {
                products.filter {
                    it.nama?.contains(query, ignoreCase = true) == true ||
                            it.barcode?.contains(query, ignoreCase = true) == true
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val productListState: StateFlow<ProductListState> =
        productList.combine(searchQuery) { products, query ->
            val filteredProducts = if (query.isBlank()) {
                products
            } else {
                products.filter { product ->
                    product.nama?.contains(query, ignoreCase = true) == true ||
                            product.barcode?.contains(query, ignoreCase = true) == true
                }
            }
            ProductListState.Success(filteredProducts)
        }.combine(sortOption) { productState, sort ->
            val sortedProducts = when (sort) {
                ProductSortOption.NAME_ASC -> (productState as? ProductListState.Success)?.products?.sortedBy { it.nama }
                    ?: emptyList()

                ProductSortOption.PRICE_SELL_ASC -> (productState as? ProductListState.Success)?.products?.sortedBy { it.hargajual }
                    ?: emptyList()

                ProductSortOption.PRICE_BUY_ASC -> (productState as? ProductListState.Success)?.products?.sortedBy { it.hargabeli }
                    ?: emptyList()

                ProductSortOption.STOCK_ASC -> (productState as? ProductListState.Success)?.products?.sortedBy { it.stok }
                    ?: emptyList()
            }
            ProductListState.Success(sortedProducts)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductListState.Loading
        )

    fun onSortOptionChanged(option: ProductSortOption) {
        sortOption.value = option
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    // Your other functions remain the same...
    fun getProduct(key: String): Flow<ProductData?> {
        return productList.map { list ->
            list.find { it.key == key }
        }
    }

    fun addProduct(product: ProductData) {
        viewModelScope.launch {
            repository.addProduct(product)
        }
    }

    fun updateProduct(key: String, product: ProductData) {
        viewModelScope.launch {
            repository.updateProduct(key, product)
        }
    }

}

sealed class ProductListState {
    object Loading : ProductListState()
    data class Success(val products: List<ProductData>) : ProductListState()
    data class Error(val message: String) : ProductListState()
}