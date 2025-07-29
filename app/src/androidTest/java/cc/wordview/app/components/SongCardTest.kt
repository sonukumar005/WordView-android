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

package cc.wordview.app.components

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import cc.wordview.app.ComposeTest
import cc.wordview.app.ui.components.SongCard
import cc.wordview.gengolex.Language
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SongCardTest : ComposeTest() {
    private fun setup(
        artist: String,
        trackName: String,
        language: Language? = null,
        onClick: Runnable
    ) {
        composeTestRule.setContent {
            var textVisible by remember { mutableStateOf(false) }

            if (textVisible) Text("Text")

            SongCard(
                modifier = Modifier.testTag("song-card"),
                thumbnail = "",
                artist = artist,
                trackName = trackName,
                language = language
            ) {
                textVisible = true
                onClick.run()
            }
        }
    }

    @Test
    fun displaysArtistAndTrack() {
        setup(artist = "Test Artist", trackName = "Test Track", onClick = {})

        composeTestRule.onNodeWithText("Test Artist").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Track").assertIsDisplayed()
    }

    @Test
    fun displaysArtistBigString() {
        setup(
            artist = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut accumsan arcu ut fermentum viverra. Nulla et nulla ante. Donec vitae sem ac arcu maximus viverra vel vel neque",
            trackName = "Test Track",
            onClick = {})

        composeTestRule.onNodeWithText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut accumsan arcu ut fermentum viverra. Nulla et nulla ante. Donec vitae sem ac arcu maximus viverra vel vel neque")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("song-card").assertWidthIsEqualTo(140.dp)
    }

    @Test
    fun displaysTrackBigString() {
        setup(
            trackName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut accumsan arcu ut fermentum viverra. Nulla et nulla ante. Donec vitae sem ac arcu maximus viverra vel vel neque",
            artist = "Test Artist",
            onClick = {})

        composeTestRule.onNodeWithText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut accumsan arcu ut fermentum viverra. Nulla et nulla ante. Donec vitae sem ac arcu maximus viverra vel vel neque")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("song-card").assertWidthIsEqualTo(140.dp)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun triggersOnClick() {
        val onClickMock: Runnable = mock()
        setup(artist = "Test Artist", trackName = "Test Track", onClick = onClickMock)
        composeTestRule.onNode(hasClickAction()).performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasText("Text"), 2_000)
        verify(onClickMock).run()
    }

    @Test
    fun displaysPortugueseCountryFlag() {
        setup(artist = "Test Artist", trackName = "Test Track", language = Language.PORTUGUESE, onClick = {})

        composeTestRule.onNodeWithTag("${Language.PORTUGUESE} icon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun displaysJapaneseCountryFlag() {
        setup(artist = "Test Artist", trackName = "Test Track", language = Language.JAPANESE, onClick = {})

        composeTestRule.onNodeWithTag("${Language.JAPANESE} icon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun displaysEnglishCountryFlag() {
        setup(artist = "Test Artist", trackName = "Test Track", language = Language.ENGLISH, onClick = {})

        composeTestRule.onNodeWithTag("${Language.ENGLISH} icon", useUnmergedTree = true).assertIsDisplayed()
    }
}