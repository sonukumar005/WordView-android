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

package cc.wordview.app.ui.activities

import cc.wordview.app.ui.activities.auth.viewmodel.login.LoginRepository
import cc.wordview.app.ui.activities.auth.viewmodel.login.LoginRepositoryImpl
import cc.wordview.app.ui.activities.auth.viewmodel.register.RegisterRepository
import cc.wordview.app.ui.activities.auth.viewmodel.register.RegisterRepositoryImpl
import cc.wordview.app.ui.activities.home.composables.home.HomeRepository
import cc.wordview.app.ui.activities.home.composables.home.HomeRepositoryImpl
import cc.wordview.app.ui.activities.player.viewmodel.PlayerRepository
import cc.wordview.app.ui.activities.player.viewmodel.PlayerRepositoryImpl
import cc.wordview.app.ui.activities.home.composables.search.SearchRepository
import cc.wordview.app.ui.activities.home.composables.search.SearchRepositoryImpl
import cc.wordview.app.ui.activities.player.viewmodel.KnownWordsRepository
import cc.wordview.app.ui.activities.player.viewmodel.KnownWordsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    internal abstract fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayerRepository

    @Singleton
    @Binds
    internal abstract fun bindKnownWordsRepository(knownWordsRepositoryImpl: KnownWordsRepositoryImpl): KnownWordsRepository

    @Singleton
    @Binds
    internal abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    internal abstract fun bindRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

    @Singleton
    @Binds
    internal abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}