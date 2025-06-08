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

package cc.wordview.app.ui.activities.home.composables.search

import cc.wordview.app.extractor.VideoStream.Companion.YTService
import org.schabi.newpipe.extractor.Page
import org.schabi.newpipe.extractor.search.SearchInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    private var nextPage: Page? = null

    override fun search(query: String): List<StreamInfoItem> {
        val queryHandler = YTService.searchQHFactory.fromQuery(query, listOf("music_songs"), "")
        val search = SearchInfo.getInfo(YTService, queryHandler)
        nextPage = search.nextPage

        val items = ArrayList<StreamInfoItem>()

        for (item in search.relatedItems) {
            if (item is StreamInfoItem) items.add(item)
        }

        return items
    }

    override fun searchNextPage(query: String): List<StreamInfoItem> {
        if (nextPage == null) return emptyList()

        val queryHandler = YTService.searchQHFactory.fromQuery(query, listOf("music_songs"), "")
        val search = SearchInfo.getMoreItems(YTService, queryHandler, nextPage!!)
        nextPage = search.nextPage

        return search.items.filterIsInstance<StreamInfoItem>()
    }
}