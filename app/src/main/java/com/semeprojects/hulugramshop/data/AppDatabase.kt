package com.semeprojects.hulugramshop.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semeprojects.hulugramshop.data.local.dao.CartDao
import com.semeprojects.hulugramshop.data.local.model.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
