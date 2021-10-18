package com.example.navigator

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigatorModule{

    @Binds
    abstract fun navigator(navigator: NavigatorImpl): Navigator
}