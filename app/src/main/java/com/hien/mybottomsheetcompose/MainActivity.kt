package com.hien.mybottomsheetcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import com.hien.mybottomsheetcompose.ui.home.HomeScreen

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
            )
            val coroutineScope = rememberCoroutineScope()
            // A surface container using the 'background' color from the theme
            HomeScreen(
                fragmentManager = supportFragmentManager,
                coroutineScope,
                bottomSheetScaffoldState
            )
        }
    }
}