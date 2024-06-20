package com.semeprojects.hulugramshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.semeprojects.hulugramshop.ui.nav_utils.Screens
import com.semeprojects.hulugramshop.ui.nav_utils.SetUpNavGraph
import com.semeprojects.hulugramshop.viewmodel.AuthState
import com.semeprojects.hulugramshop.viewmodel.AuthViewModel

@Composable
fun HulugramShop(
    authViewModel: AuthViewModel = hiltViewModel()
){

    Surface(
        modifier = Modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.background
        ),
    ) {

        val authState by authViewModel.authState.collectAsState()
        var startDestination = Screens.SignIn.route
        if(authState is AuthState.Authenticated){
            startDestination = Screens.Main.route
        }

        val navController = rememberNavController()
        SetUpNavGraph(
            startDestination = startDestination,
            navController = navController,
        )

    }

}