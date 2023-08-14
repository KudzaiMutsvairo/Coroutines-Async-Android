package com.kudzai.mutswairo.stringsappv2.di

import com.kudzai.mutswairo.stringsappv2.data.repository.PageRepositoryImpl
import com.kudzai.mutswairo.stringsappv2.domain.repository.PageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideURL(): URL {
        return URL("https://truecaller.blog/2018/01/22/life-as-an-android-engineer/")
    }

    @Singleton
    @Provides
    fun providePageRepository(url: URL): PageRepository {
        return PageRepositoryImpl(url)
    }
}
