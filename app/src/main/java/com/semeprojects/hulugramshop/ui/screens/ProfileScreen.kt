package com.semeprojects.hulugramshop.ui.screens

import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.semeprojects.hulugramshop.R
import com.semeprojects.hulugramshop.data.preferences.Theme
import com.semeprojects.hulugramshop.ui.components.HAlertDialog
import com.semeprojects.hulugramshop.ui.nav_utils.Screens
import com.semeprojects.hulugramshop.viewmodel.ProfileUIState
import com.semeprojects.hulugramshop.viewmodel.ProfileViewModel
import com.semeprojects.hulugramshop.viewmodel.ThemeViewModel


@Composable
fun ProfileScreen(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState = profileViewModel.profileUiState.collectAsState().value

    when(uiState){
        is ProfileUIState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        ProfileUIState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ProfileUIState.Success -> {
            val user = uiState.user
            ProfileScreenContent(
                displayName = user.displayName,
                email = user.email,
                themeViewModel = themeViewModel,
                navController,
                profileViewModel
            )
        }
    }

}


@Composable
fun ProfileScreenContent(
    displayName: String?,
    email: String?,
    themeViewModel: ThemeViewModel,
    navController: NavHostController,
    profileViewModel: ProfileViewModel
){
    var showDialog by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(R.drawable.placeholder),
                contentDescription = "Profile Icon",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (displayName != null) {
            Text(
                text = displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        if (email != null) {
            Text(
                text = email,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        val currentTheme = themeViewModel.theme.collectAsState().value

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { themeViewModel.setTheme(
                    if (currentTheme == Theme.DARK) Theme.LIGHT else Theme.DARK
                ) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = currentTheme == Theme.DARK,
                onCheckedChange = {
                    themeViewModel.setTheme(
                        if (currentTheme == Theme.DARK) Theme.LIGHT else Theme.DARK
                    )
                },
                modifier = Modifier.scale(0.8f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (currentTheme == Theme.DARK) "Dark Mode" else "Light Mode")

        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
        if(showDialog){
            HAlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                onConfirmation = {
                    profileViewModel.logout()
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                    showDialog = false
                },
                dialogTitle = "Sign Out",
                dialogText = "Are you sure you want to sign out your account?",
                icon = Icons.AutoMirrored.Filled.Logout
            )
        }
        SettingItem(icon = Icons.Filled.Person, text = "Edit Profile")
        Spacer(modifier = Modifier.height(16.dp))
        SettingItem(icon = Icons.Filled.Notifications, text = "Notifications")
        Spacer(modifier = Modifier.height(16.dp))
        SettingItem(icon = Icons.AutoMirrored.Filled.Logout, text = "Logout", onClick = {
            showDialog = true
        })
    }
}
@Composable
fun SettingItem(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}