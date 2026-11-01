package com.example.warehouseinventoryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

class InventoryFragment : Fragment() {
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    InventoryScreen(
                        viewModel = viewModel,
                        onAddProductClick = {
                            parentFragmentManager.commit {
                                replace(android.R.id.content, AddProductFragment())
                                addToBackStack(null)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InventoryScreen(viewModel: InventoryViewModel, onAddProductClick: () -> Unit) {
    val products = viewModel.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("WAREHOUSE INVENTORY", fontSize = 26.sp, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard("Products", products.size.toString())
            SummaryCard("Stock", products.sumOf { it.quantity }.toString())
            SummaryCard("Low Stock", products.count { it.quantity <= it.minimumStock }.toString())
        }

        Spacer(Modifier.height(25.dp))
        Text("Inventory Status", fontSize = 22.sp)
        Spacer(Modifier.height(10.dp))

        products.forEach { product ->
            ProductCard(product = product)
        }

        Spacer(Modifier.height(20.dp))
        Button(onClick = onAddProductClick, modifier = Modifier.fillMaxWidth()) {
            Text("ADD NEW PRODUCT")
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 22.sp)
            Text(title, fontSize = 13.sp)
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(product.name, fontSize = 19.sp)
            Spacer(Modifier.height(5.dp))
            Text("Product ID: ${product.id}")
            Text("Available Quantity: ${product.quantity}")
            Text("Minimum Stock: ${product.minimumStock}")
            Spacer(Modifier.height(5.dp))
            if (product.quantity <= product.minimumStock) {
                Text("⚠ LOW STOCK")
            } else {
                Text("✓ IN STOCK")
            }
        }
    }
}
