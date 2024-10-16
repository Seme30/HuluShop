package com.semeprojects.hulugramshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.semeprojects.hulugramshop.data.local.model.CartItem
import com.semeprojects.hulugramshop.viewmodel.CartViewModel

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            EmptyCartMessage()
        } else {
            CartItemsList(
                cartItems = cartItems,
                onIncreaseQuantity = { cartViewModel.increaseQuantity(it.productId) },
                onDecreaseQuantity = { cartViewModel.decreaseQuantity(it.productId) },
                onRemoveItem = { cartViewModel.removeFromCart(it.productId) }
            )

            Spacer(modifier = Modifier.weight(1f))

            CartSummary(totalPrice = totalPrice)

            Button(
                onClick = { /* TODO: Implement checkout logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}

@Composable
fun EmptyCartMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CartItemsList(
    cartItems: List<CartItem>,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cartItems) { item ->
            CartItemCard(
                item = item,
                onIncreaseQuantity = { onIncreaseQuantity(item) },
                onDecreaseQuantity = { onDecreaseQuantity(item) },
                onRemoveItem = { onRemoveItem(item) }
            )
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.productName, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${item.productPrice}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease quantity")
                }
                Text(text = item.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(Icons.Default.Add, contentDescription = "Increase quantity")
                }
                IconButton(onClick = onRemoveItem) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove item")
                }
            }
        }
    }
}

@Composable
fun CartSummary(totalPrice: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Total:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$${String.format("%.2f", totalPrice)}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
