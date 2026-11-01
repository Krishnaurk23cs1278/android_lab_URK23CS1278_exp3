package com.example.warehouseinventoryapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Product(
    val name: String,
    val id: String,
    val quantity: Int,
    val minimumStock: Int
)

class InventoryViewModel : ViewModel() {
    val products = mutableStateListOf(
        Product("Laptop", "P101", 25, 5),
        Product("Keyboard", "P102", 8, 5),
        Product("Mouse", "P103", 3, 5)
    )

    fun addProduct(name: String, id: String, quantity: Int, minimumStock: Int) {
        products.add(Product(name, id, quantity, minimumStock))
    }
}
