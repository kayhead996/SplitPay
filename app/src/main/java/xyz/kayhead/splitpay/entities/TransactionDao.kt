package xyz.kayhead.splitpay.entities

import android.arch.persistence.room.*

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions(): Array<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id IN :transactionIds")
    fun getTransactionById(vararg transactionIds: Int)

    @Update
    fun updateTransaction(vararg items: Transaction)

    @Insert
    fun insertTransaction(vararg items: Transaction)

    @Delete
    fun deleteTransaction(vararg items: Transaction)
}