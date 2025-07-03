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

package cc.wordview.app

import cc.wordview.app.ui.activities.RepositoryModule
import cc.wordview.app.ui.activities.auth.viewmodel.login.LoginRepository
import cc.wordview.app.ui.activities.auth.viewmodel.register.RegisterRepository
import cc.wordview.app.ui.screens.player.MockPlayerRepositoryImpl
import cc.wordview.app.ui.activities.player.viewmodel.PlayerRepository
import cc.wordview.app.ui.screens.search.MockSearchRepositoryImpl
import cc.wordview.app.ui.activities.home.composables.search.SearchRepository
import cc.wordview.app.ui.activities.player.viewmodel.KnownWordsRepository
import cc.wordview.app.ui.screens.login.MockLoginRepositoryImpl
import cc.wordview.app.ui.screens.player.MockKnownWordsRepositoryImpl
import cc.wordview.app.ui.screens.register.MockRegisterRepositoryImpl
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import dagger.Module
import dagger.Binds

@TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
@Module
abstract class RepositoryTestModule {

    @Singleton
    @Binds
    abstract fun bindsMockSearchRepository(mockSearchRepositoryImpl: MockSearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    abstract fun bindsMockPlayerRepository(mockPlayerRepositoryImpl: MockPlayerRepositoryImpl): PlayerRepository

    @Singleton
    @Binds
    abstract fun bindsMockKnownWordsRepository(mockKnownWordsRepositoryImpl: MockKnownWordsRepositoryImpl): KnownWordsRepository


    @Singleton
    @Binds
    abstract fun bindsMockLoginRepository(mockLoginRepositoryImpl: MockLoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun bindsMockRegisterRepository(mockRegisterRepositoryImpl: MockRegisterRepositoryImpl): RegisterRepository
}