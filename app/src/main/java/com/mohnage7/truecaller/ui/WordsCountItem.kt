package com.mohnage7.truecaller.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mohnage7.truecaller.R

@Composable
fun WordsCountItem(wordsCountEntry: Map.Entry<String, Int>) {
    val iconModifier = Modifier
        .padding(4.dp)
        .size(24.dp)

    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_text),
            contentDescription = null,
            modifier = iconModifier,
            tint = Color.Gray
        )
        Text(
            text = wordsCountEntry.key,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(0.7f),
            maxLines = 1
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_tag),
            contentDescription = null,
            modifier = iconModifier,
            tint = Color.Gray,
        )
        Text(
            text = "${wordsCountEntry.value}",
            modifier = Modifier.padding(4.dp)
        )
    }
}