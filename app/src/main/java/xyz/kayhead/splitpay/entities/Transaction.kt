package xyz.kayhead.splitpay.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "transaction")

class Transaction(var name: String = "", items: ArrayList<Item> = arrayListOf(),
                  taxRate: Double = 0.0, tipRate: Double = 0.0, var total: Double = 0.0) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "tax_rate")
    var taxRate = taxRate
        set(value) {
            field = value
            calculateTotal()
        }

    @ColumnInfo(name = "tip_rate")
    var tipRate = tipRate
        set(value) {
            field = value
            calculateTotal()
        }

    @Ignore
    var items = items
        set(value) {
            field = value
            calculateTotal()
        }
    private fun calculateTotal() {
        total = items.map{ it.cost }.fold(0.0, { q, next -> q + next })
        total *= (1 + taxRate)
        total *= (1 + tipRate)
    }

    fun addItem(itemName: String, cost: Double) {
        items.add(Item(itemName, cost, id))
        calculateTotal()
    }

    fun addItem(item: Item) {
        items.add(item)
        calculateTotal()
    }
}