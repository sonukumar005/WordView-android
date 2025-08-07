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

package cc.wordview.app.ui.activities.home.composables.history

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.navigation.NavHostController
import cc.wordview.app.R
import cc.wordview.app.SongViewModel
import cc.wordview.app.extensions.goBack
import cc.wordview.app.extensions.openActivity
import cc.wordview.app.ui.activities.home.composables.search.dataStore
import cc.wordview.app.ui.activities.player.PlayerActivity
import cc.wordview.app.ui.components.HistoryItem
import cc.wordview.app.ui.theme.poppinsFamily
import com.gigamole.composefadingedges.verticalFadingEdges
import kotlinx.coroutines.flow.map
import kotlin.uuid.Uuid
import com.google.gson.Gson
import kotlin.uuid.ExperimentalUuidApi

val PLAY_HISTORY = stringSetPreferencesKey("play_history")

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
fun History(navController: NavHostController) {
    val listState = rememberLazyListState()

    val context = LocalContext.current

    val playHistoryFlow = remember { context.dataStore.data.map { it[PLAY_HISTORY] ?: emptySet() } }
    val playHistory by playHistoryFlow.collectAsState(initial = emptySet())

    val gson = remember { Gson() }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = LocalContentColor.current
            ),
            title = {
                Text(
                    stringResource(R.string.history),
                    fontFamily = poppinsFamily,
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.goBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        )
    }) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalFadingEdges(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            var i = 0

            items(playHistory.toList().reversed(), key = { Uuid.random() }) {
                val entry = gson.fromJson(it, HistoryEntry::class.java)

                i += 1
                HistoryItem(
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(durationMillis = i * 250),
                        placementSpec = spring(
                            stiffness = Spring.StiffnessLow,
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        )
                    ),
                    result = entry
                ) {
                    SongViewModel.setVideo(entry.id)
                    context.openActivity<PlayerActivity>()
                }
                Spacer(Modifier.size(16.dp))
            }
        }
    }
}