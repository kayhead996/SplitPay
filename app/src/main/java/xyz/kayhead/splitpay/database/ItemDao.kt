package xyz.kayhead.splitpay.database

import android.arch.persistence.room.*
import xyz.kayhead.splitpay.entities.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM `items` WHERE item_id IN")
    fun getItemsById(vararg itemIds: Int): Array<Item>

    @Query("SELECT * FROM `items` WHERE transaction_id = :transactionId")
    fun getItemById(transactionId: Int): Item

    @Update
    fun updateItems(vararg items: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg items: Item)

    @Delete
    fun deleteItems(vararg items: Item)
}