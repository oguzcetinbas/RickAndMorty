package com.example.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.db.RickMortyDao
import com.example.rickandmorty.db.RickMortyPropertyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickMortyPropertyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RickMortyPropertyDatabase::class.java,
            "rickmorty_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(rickMortyPropertyDatabase: RickMortyPropertyDatabase) : RickMortyDao {
        return rickMortyPropertyDatabase.rickmortyDao()
    }
}