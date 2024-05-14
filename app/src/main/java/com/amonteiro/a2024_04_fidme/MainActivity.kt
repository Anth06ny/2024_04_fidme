package com.amonteiro.a2024_04_fidme

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.amonteiro.a2024_04_fidme.ui.AppNavigation
import com.amonteiro.a2024_04_fidme.ui.theme._2024_04_fidmeTheme
import com.amonteiro.a2024_04_fidme.utils.LocationUtils.getLastKnowLocationEconomyMode
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2024_04_fidmeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen() {

    // Créez un état de la permission pour ACCESS_FINE_LOCATION
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var myLocation by remember { mutableStateOf<Location?>(null) }
    //Pour obtenir le context de l'application
    val context  = LocalContext.current

    Column {
        //Etat des permissions
        Text("isGranted : ${locationPermissionState.status.isGranted}")
        Text("shouldShowRationale:${locationPermissionState.status.shouldShowRationale}")

        //Si on a la permission on récupère la localisation
        if (locationPermissionState.status.isGranted) {
            //Attention c'est un callback donc asynchrone
            getLastKnowLocationEconomyMode(context)
                //Callback appelé quand la localisation sera trouvé
                ?.addOnSuccessListener {
                    myLocation = it
                }
                //Callback appelé si la localisation n'est pas trouvé
                ?.addOnFailureListener {
                    it.printStackTrace()
                    println("Localisation non trouvé")
                }
        }

        Button(onClick = {
            if (!locationPermissionState.status.isGranted) {
                //Demande de permission
                locationPermissionState.launchPermissionRequest()
            }
        }) {
            //Affichage de ma position sur le bouton
            Text("My Location : ${myLocation?.latitude ?: "-"}, ${myLocation?.longitude ?: "-"}")
        }
    }


}

