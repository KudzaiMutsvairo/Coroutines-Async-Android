package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UniqueWordItem(word: String, count: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.heightIn(40.dp).padding(1.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(1.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = "$word: $count",
                modifier = Modifier.padding(all = 2.dp),
            )
        }
    }
}

@Preview(heightDp = 50, widthDp = 200)
@Composable
fun UniqueWordItemPreview() {
    UniqueWordItem(word = "Unique-Word ", 3)
}
