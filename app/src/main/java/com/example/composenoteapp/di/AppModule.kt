package com.example.composenoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.composenoteapp.model.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun makeDatabase(app:Application):NotesDatabase=
        Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notes_db"
        ).build()

}
