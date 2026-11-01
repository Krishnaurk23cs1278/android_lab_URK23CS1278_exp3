package com.example.warehouseinventoryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AddProductFragment : Fragment() {
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    AddProductScreen(
                        onAddProduct = { name, id, qty, min ->
                            viewModel.addProduct(name, id, qty, min)
                            parentFragmentManager.popBackStack()
                        },
                        onBackClick = { parentFragmentManager.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun AddProductScreen(onAddProduct: (String, String, Int, Int) -> Unit, onBackClick: () -> Unit) {
    var productName by remember { mutableStateOf("") }
    var productId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var minimumStock by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("ADD NEW PRODUCT", fontSize = 26.sp)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(value = productName, onValueChange = { productName = it; message = "" }, label = { Text("Product Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = productId, onValueChange = { productId = it; message = "" }, label = { Text("Product ID") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = quantity, onValueChange = { quantity = it; message = "" }, label = { Text("Quantity") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = minimumStock, onValueChange = { minimumStock = it; message = "" }, label = { Text("Minimum Stock Level") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val qtyVal = quantity.toIntOrNull()
                val minVal = minimumStock.toIntOrNull()
                if (productName.isBlank() || productId.isBlank() || qtyVal == null || minVal == null) {
                    message = "Please enter valid product details."
                } else {
                    onAddProduct(productName, productId, qtyVal, minVal)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ADD PRODUCT")
        }

        if (message.isNotEmpty()) {
            Spacer(Modifier.height(15.dp))
            Text(message, fontSize = 16.sp)
        }

        Spacer(Modifier.height(15.dp))
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("BACK TO INVENTORY")
        }
    }
}
