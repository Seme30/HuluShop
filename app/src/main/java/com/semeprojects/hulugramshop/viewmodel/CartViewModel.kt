package com.semeprojects.hulugramshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semeprojects.hulugramshop.data.local.model.CartItem
import com.semeprojects.hulugramshop.data.local.repository.CartRepository
import com.semeprojects.hulugramshop.data.network.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.getAllCartItems()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val totalPrice: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.productPrice * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)


    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.incrementQuantity(productId)
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.decrementQuantity(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}
