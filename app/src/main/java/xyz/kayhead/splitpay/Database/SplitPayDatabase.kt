package xyz.kayhead.splitpay.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import xyz.kayhead.splitpay.entities.Item
import xyz.kayhead.splitpay.entities.ItemDao
import xyz.kayhead.splitpay.entities.Transaction
import xyz.kayhead.splitpay.entities.TransactionDao

@Database(version = 1, entities = [Item::class, Transaction::class])
abstract class SplitPayDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun transactionDao(): TransactionDao
}