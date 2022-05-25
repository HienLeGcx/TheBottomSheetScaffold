package com.hien.mybottomsheetcompose.ui.home

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hien.mybottomsheetcompose.ui.bottom.BottomSheetFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    fragmentManager: FragmentManager,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            BottomSheetFragmentCompose(
                fragmentManager = fragmentManager,
                modifier = Modifier
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MainFragmentCompose(
                fragmentManager = fragmentManager,
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
            )
            Button(onClick = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }) {
                Text(color = Color.Black, text = "Expand/Collapse Bottom Sheet")
            }
        }

    }
}

@Composable
fun MainFragmentCompose(
    modifier: Modifier,
    fragmentManager: FragmentManager,
) {
    Column(modifier = modifier.background(Color.Blue)) {
        FragmentContainer(
            modifier = Modifier
                .fillMaxSize(),
            fragmentManager = fragmentManager,
            commitFragment = {
                add(it, HomeFragment()).commit()
            })
    }
}

@Composable
fun BottomSheetFragmentCompose(
    modifier: Modifier,
    fragmentManager: FragmentManager,
) {
    FragmentContainer(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.Gray),
        fragmentManager = fragmentManager,
        commitFragment = {
            add(it, BottomSheetFragment()).commit()
        })
}


@Composable
fun FragmentContainer(
    modifier: Modifier,
    fragmentManager: FragmentManager,
    commitFragment: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by remember { mutableStateOf(View.generateViewId()) }
    var initialized by remember { mutableStateOf(false) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            if (!initialized) {
                fragmentManager.beginTransaction().apply {
                    commitFragment(view.id)
                }
                initialized = true
            } else {
                fragmentManager.onContainerAvailable(view)
            }
        }
    )
}

/** Access to package-private method in FragmentManager through reflection */
private fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
    val method = FragmentManager::class.java.getDeclaredMethod(
        "onContainerAvailable",
        FragmentContainerView::class.java
    )
    method.isAccessible = true
    method.invoke(this, view)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun HomeDemoScreen() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    HomeScreen(
        fragmentManager = object : FragmentManager() {},
        coroutineScope,
        bottomSheetScaffoldState
    )
}