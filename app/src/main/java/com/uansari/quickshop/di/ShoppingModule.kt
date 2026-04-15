package com.uansari.quickshop.di

import android.content.Context
import androidx.room.Room
import com.uansari.quickshop.data.local.ShoppingDao
import com.uansari.quickshop.data.local.ShoppingDatabase
import com.uansari.quickshop.data.repository.ShoppingRepositoryImpl
import com.uansari.quickshop.domain.repository.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShoppingModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        shoppingRepositoryImpl: ShoppingRepositoryImpl
    ): ShoppingRepository

    companion object {

        @Provides
        @Singleton
        fun provideShoppingDatabase(
            @ApplicationContext context: Context
        ): ShoppingDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ShoppingDatabase::class.java,
                name = "shopping_list_db",
            ).build()
        }

        @Provides
        @Singleton
        fun provideShoppingDao(database: ShoppingDatabase): ShoppingDao {
            return database.shoppingDao()
        }
    }
}