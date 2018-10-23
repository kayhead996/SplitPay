package xyz.kayhead.splitpay.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import xyz.kayhead.splitpay.entities.Item
import xyz.kayhead.splitpay.entities.Transaction

@Database(version = 1, entities = [Item::class, Transaction::class])
abstract class SplitPayDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        private var INSTANCE: SplitPayDatabase? = null

        fun getInstance(context: Context): SplitPayDatabase? {
            if (INSTANCE == null) {
                synchronized(SplitPayDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            SplitPayDatabase::class.java, "split_pay.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}