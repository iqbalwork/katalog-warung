package com.uziro.katalogwarung.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshots
import com.uziro.katalogwarung.data.model.ProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) {

    private val database = firebaseDatabase.reference

    fun getProductList(): Flow<List<ProductData>> {
        return database
            .child("product")
            .snapshots
            .map {
                it.children.mapNotNull { snapshot ->
                    val data = snapshot.getValue(ProductData::class.java)?.apply {
                        // Set the key from the snapshot
                        key = snapshot.key.orEmpty()
                    }
                    data
                }
            }
    }

    fun addProduct(product: ProductData) {
        val newProductRef = database.child("product").push()
        newProductRef.setValue(product)
    }

    fun updateProduct(key: String, product: ProductData) {
        database.child("product").child(key).setValue(product)
    }

}