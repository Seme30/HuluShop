package com.semeprojects.hulugramshop.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.semeprojects.hulugramshop.data.network.api.ProductApi
import com.semeprojects.hulugramshop.data.network.repository.FirebaseUserRepository
import com.semeprojects.hulugramshop.data.network.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideFirebaseUserRepository(
        firebaseAuth: FirebaseAuth
    ): FirebaseUserRepository = FirebaseUserRepository(firebaseAuth)

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApi): ProductRepository {
        return ProductRepository(productApi)
    }

}