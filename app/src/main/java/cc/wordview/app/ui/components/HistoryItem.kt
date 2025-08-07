/*
 * Copyright (c) 2025 Arthur Araujo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package cc.wordview.app.ui.components

import android.annotation.SuppressLint
import cc.wordview.app.ui.activities.home.composables.history.HistoryEntry
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cc.wordview.app.R
import cc.wordview.app.ui.theme.Typography
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SimpleDateFormat")
@Composable
fun HistoryItem(modifier: Modifier = Modifier, result: HistoryEntry, onClick: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    fun onClickResult() = coroutineScope.launch {
        // delay so that the animation can be seen
        delay(120)
        onClick()
    }

    Card(
        modifier = modifier
            .testTag("history-item")
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(0.dp),
        onClick = { onClickResult() }
    ) {
        Row {
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Box(
                    Modifier
                        .zIndex(-1f)
                        .alpha(0.1f)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
                AsyncImage(
                    model = result.thumbnailUrl,
                    error = painterResource(id = if (isSystemInDarkTheme()) R.drawable.nonet else R.drawable.nonet_dark),
                    contentDescription = "${result.title} cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight,
                )
            }
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = result.title,
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    softWrap = false,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = result.artist,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Light,
                        softWrap = false,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
                val sdf = java.text.SimpleDateFormat("yyyy/MM/dd")
                val date = java.util.Date(result.unixWatchedAt)

                Text(
                    text = "Watched at ${sdf.format(date)}",
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                    fontWeight = FontWeight.Light,
                    softWrap = false,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}