package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A composable item that displays the 10th character of a string
 * @param character the 10th character of a string
 * @param modifier Modifier
 */
@Composable
fun TenthCharItem(character: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(80.dp).height(40.dp).padding(1.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = character)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TenthCharItemPreview() {
    TenthCharItem(character = "K")
}
