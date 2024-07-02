package com.epikron.objectstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epikron.objectstore.ui.composables.EditScreen
import com.epikron.objectstore.ui.composables.MainScreen
import com.epikron.objectstore.ui.models.Screens
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val editObjectId = remember { mutableIntStateOf(-1) }
            ObjectStoreTheme {
                NavHost(navController = navController, startDestination = Screens.Main.name) {
                    composable(Screens.Main.name) {
                        MainScreen(
                            onAddButtonClick = {
                                editObjectId.intValue = -1
                                navController.navigate(Screens.Edit.name)
                            }, onEditObjectClick = { objectId ->
                                editObjectId.intValue = objectId
                                navController.navigate(Screens.Edit.name)
                            }
                        )
                    }
                    composable(Screens.Edit.name) {
                        EditScreen(
                            editObjectId = editObjectId.intValue,
                            onBackPressed = { navController.navigate(Screens.Main.name) },
                            onEditRelation = { editObjectId.intValue = it }
                        )
                    }
                }
            }
        }
    }
}
