package ru.chjunior.gridexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            Item(4),
            Item(3),
            Item(2),
            Item(5),
            Item(1),
            Item(2),
            Item(4),
            Item(1)
        )

        recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler.adapter = Adapter(reorderItems(items))
    }

    private fun reorderItems(items: List<Item>): List<Item> {
        val reorderedItems = mutableListOf<Item>()

        var sumEven = 0
        var sumOdd = 0

        var indexEven = 0
        var indexOdd = 1

        while (indexEven < items.size || indexOdd < items.size) {
            if (sumEven <= sumOdd && indexEven < items.size) {
                sumEven += items[indexEven].value
                reorderedItems.add(items[indexEven])
                indexEven += 2
            }

            if (sumOdd < sumEven && indexOdd < items.size) {
                sumOdd += items[indexOdd].value
                reorderedItems.add(items[indexOdd])
                indexOdd += 2
            }
        }

        return reorderedItems
    }

    class Item(val value: Int)

    class Adapter(private val items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.height = 300 * item.value
            itemView.text.text = item.value.toString()
        }
    }
}
