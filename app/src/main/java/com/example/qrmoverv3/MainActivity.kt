package com.example.qrmoverv3
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.plus
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var context = this
        val db = DataBaseHandler(context)

        val savedItems = db.readData()
        // Initializing the array lists and the adapter
        var itemlist = if (savedItems != null) savedItems as ArrayList<String> else arrayListOf<String>()
        var adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_multiple_choice
            , itemlist)
        listView.adapter =  adapter

        // Adding the items to the list when the add button is pressed
        // Creating a random UUID (Universally unique identifier). This will be replaced with the UUID from the QR code eventually
        //TODO: replace generated UUID with QR code value
        val uuid: UUID = UUID.randomUUID()
        val randomUUIDString: String = uuid.toString()
        add.setOnClickListener {
            if (editText.text.toString().isNotEmpty()){
                var listItem = Note(randomUUIDString, editText.text.toString(), "", Calendar.getInstance().toString())
                db.insertData(listItem)
                itemlist.add(editText.text.toString())
                listView.adapter =  adapter
                adapter.notifyDataSetChanged()
                // This is because every time when you add the item the input space or the eidt text space will be cleared
                editText.text.clear()
            }
            else{
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }

        }

        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {
            db.bulkDeleteData()
            itemlist.clear()
            adapter.notifyDataSetChanged()
        }

        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item) && itemlist.count() > 0)
                {
                    adapter.remove(itemlist[item])
                    db.deleteData(itemlist[item])
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

    }
    private fun clearField() {
        editText.text.clear()
    }

}
