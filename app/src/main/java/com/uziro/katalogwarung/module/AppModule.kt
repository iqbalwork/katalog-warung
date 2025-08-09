package com.uziro.katalogwarung.module

import com.google.firebase.database.FirebaseDatabase
import com.uziro.katalogwarung.data.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton // Provides a single instance throughout the app's lifecycle
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance("https://katalog-warung-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    @Provides
    @Singleton
    fun provideProductRepository(firebaseDatabase: FirebaseDatabase): ProductRepository {
        return ProductRepository(firebaseDatabase) // Hilt provides firebaseDatabase
    }
}