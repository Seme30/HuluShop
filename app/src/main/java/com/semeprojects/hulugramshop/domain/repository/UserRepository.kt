package com.semeprojects.hulugramshop.domain.repository

import com.semeprojects.hulugramshop.data.network.model.User


interface UserRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User>
    suspend fun signOut(): Result<Unit>
    fun getCurrentUser(): User?
}