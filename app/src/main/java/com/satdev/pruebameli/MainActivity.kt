package com.satdev.pruebameli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satdev.pruebameli.feature_search_product.presentation.SearchProductRoute
import com.satdev.pruebameli.feature_search_product.presentation.SearchProductViewModel
import com.satdev.pruebameli.ui.navigation.Route
import com.satdev.pruebameli.ui.navigation.Route.HOME
import com.satdev.pruebameli.ui.theme.PruebaMeliTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaMeliTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = HOME) {
                        composable(Route.HOME) {
                            val viewModel = hiltViewModel<SearchProductViewModel>()
                            SearchProductRoute(viewModel = viewModel, onShowProductDetail = {

                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebaMeliTheme {
    }
}