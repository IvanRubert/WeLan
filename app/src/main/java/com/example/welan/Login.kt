package com.example.welan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController,modifier: Modifier){
    var choose by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.welan_logo_text),
            contentDescription = "",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth(0.6f)) {
            SegmentedButton(
                checked = false,
                onCheckedChange = {
                    choose = true
                },
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                colors = SegmentedButtonColors(
                    activeBorderColor = Color.Unspecified,
                    activeContainerColor = Color.Unspecified,
                    activeContentColor =  Color.Unspecified,
                    inactiveContainerColor = if (choose) MaterialTheme.colorScheme.primary else Color.White,
                    inactiveContentColor =Color.Unspecified,
                    inactiveBorderColor = Color.Unspecified,
                    disabledActiveContainerColor = Color.Unspecified,
                    disabledActiveContentColor = Color.Unspecified,
                    disabledActiveBorderColor = Color.Unspecified,
                    disabledInactiveContainerColor = Color.Unspecified,
                    disabledInactiveContentColor = Color.Unspecified,
                    disabledInactiveBorderColor = Color.Unspecified
                ),
                modifier = Modifier.height(60.dp)
            ) {
                Text("Usuario")
            }

            SegmentedButton(
                checked = false,
                onCheckedChange = {
                    choose = false
                },
                shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                colors = SegmentedButtonColors(
                    activeBorderColor = Color.Unspecified,
                    activeContainerColor = Color.Unspecified,
                    activeContentColor =  Color.Unspecified,
                    inactiveContainerColor = if (!choose) MaterialTheme.colorScheme.primary  else Color.White,
                    inactiveContentColor =Color.Unspecified,
                    inactiveBorderColor = Color.Unspecified,
                    disabledActiveContainerColor = Color.Unspecified,
                    disabledActiveContentColor = Color.Unspecified,
                    disabledActiveBorderColor = Color(0xFF000000),
                    disabledInactiveContainerColor = Color.Unspecified,
                    disabledInactiveContentColor = Color.Unspecified,
                    disabledInactiveBorderColor = Color(0xFF000000)
                ),
                modifier = Modifier.height(60.dp)
            ) {
                Text("Empresa",

                    )
            }
        }
    }
}