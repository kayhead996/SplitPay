package xyz.kayhead.splitpay.entities

import android.arch.persistence.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM `items` WHERE item_id IN :itemIds")
    fun getItemsById(vararg itemIds: Int): Array<Item>

    @Query("SELECT * FROM `items` WHERE transaction_id = :transactionId")
    fun getItemById(transactionId: Int): Item

    @Update
    fun updateItems(vararg items: Item)

    @Insert
    fun insertItems(vararg items: Item)

    @Delete
    fun deleteItems(vararg items: Item)
}