package com.amonteiro.a2024_04_fidme.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amonteiro.a2024_04_fidme.ui.theme._2024_04_fidmeTheme

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyTopBarPreview() {
    _2024_04_fidmeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                MyTopBar(
                    title = "Recherche",
                    //Icônes sur la barre
                    topBarActions = listOf {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Rechercher")
                        }
                    },

                    //Menu déroulant
                    dropDownMenuItem = listOf(
                        Triple(Icons.Filled.Refresh, "Refresh", {}),
                        Triple(Icons.Filled.Clear, "Clear", {})
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title:String? = null,
    navHostController: NavHostController? = null,
    //Icône
    topBarActions: List<@Composable () -> Unit>? = null,
    //Icône, Texte, Action
    dropDownMenuItem: List<Triple<ImageVector, String, () -> Unit>>? = null
) {

    var openDropDownMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title ?: "") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onTertiary,
            actionIconContentColor = MaterialTheme.colorScheme.onTertiary
        ),

        //Icône retour
        navigationIcon = {
            if (navHostController?.previousBackStackEntry != null) {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        //Icône sur la barre
        actions = {
            topBarActions?.forEach { it() }

            //DropDownMenu
            if (dropDownMenuItem != null) {

                //Icône du menu
                IconButton(onClick = { openDropDownMenu = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }

                //menu caché qui doit s'ouvrir quand on clique sur l'icône
                DropdownMenu(
                    expanded = openDropDownMenu,
                    onDismissRequest = { openDropDownMenu = false }
                ) {
                    //les items sont constitués à partir de la liste
                    dropDownMenuItem.forEach {
                        DropdownMenuItem(text = {
                            Row {
                                Icon(it.first, contentDescription = "")
                                Text(it.second)
                            }
                        }, onClick = {
                            openDropDownMenu = false //ferme le menu
                            it.third() //action au clic
                        })
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "fr")
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _2024_04_fidmeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                Text("Vide : ")
                MyError(errorMessage = "")
                Text("Rempli  :")
                MyError(errorMessage = "Un message")
                Text("Null :")
                MyError(errorMessage = null)
                Text("-----")
            }
        }
    }
}

@Composable
fun MyError(modifier:Modifier = Modifier, errorMessage:String?) {
    AnimatedVisibility(visible = !errorMessage.isNullOrBlank()){
        Text(text = errorMessage ?: "",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onError,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error))
    }

}