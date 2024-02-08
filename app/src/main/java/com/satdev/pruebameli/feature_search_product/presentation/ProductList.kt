package com.satdev.pruebameli.feature_search_product.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.satdev.pruebameli.feature_search_product.data.model.Product
import com.satdev.pruebameli.ui.Utils.formatPrice
import com.satdev.pruebameli.ui.theme.PruebaMeliTheme
import com.satdev.pruebameli.ui.theme.md_theme_light_outlineVariant

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 0.dp),
    ) {
        items(products){product ->
            ProductRow(productRecord = product)
            Divider(color = Color.Gray)
        }
    }
}


@Composable
fun ProductRow(productRecord : Product, modifier: Modifier = Modifier) {
    val formattedPrice = remember{ formatPrice((productRecord.price) ?: 0L) }
    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ), shape = RectangleShape
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(productRecord.thumbnail.toString()),
                contentDescription = "Product Image", modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(80.dp).offset(x = 5.dp), contentScale = ContentScale.Crop

            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 90.dp, top = 10.dp, bottom = 10.dp, end = 0.dp)
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                    Text(
                        text = productRecord.title?.trim().toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = productRecord.seller?.nickname?.toString() ?: "",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    PruebaMeliTheme {
        ProductList(products = listOf(Product(title = "celular", price = 12000, thumbnail = "http://http2.mlstatic.com/D_909396-MLA73320148334_122023-I.jpg"))) {

        }
    }
}