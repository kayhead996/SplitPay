package xyz.kayhead.splitpay.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items",
        foreignKeys = [
                ForeignKey(entity = Transaction::class,
                        parentColumns = ["id"],
                        childColumns = ["transaction_id"])])

class Item(var itemName: String = "", var cost: Double = 0.0,
           @ColumnInfo(name = "transaction_id") var transactionId: Int) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var itemId: Int = 0

}