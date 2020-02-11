package com.cr.o.cdc.mlchallenge.di

import androidx.room.Room
import com.cr.o.cdc.mlchallenge.MLChallengeApp
import com.cr.o.cdc.mlchallenge.R
import com.cr.o.cdc.mlchallenge.db.MLChallengeDB
import com.cr.o.cdc.mlchallenge.retrofit.AppExecutors
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDB(app: MLChallengeApp): MLChallengeDB = Room
        .databaseBuilder(app, MLChallengeDB::class.java, MLChallengeDB.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(app: MLChallengeApp): Retrofit = Retrofit.Builder()
        .baseUrl(app.resources.getString(R.string.api_ml_url))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideAppExecutors() = AppExecutors()
}