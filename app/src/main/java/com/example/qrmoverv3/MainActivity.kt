package com.example.qrmoverv3
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        val context = this
        val db = DataBaseHandler(context)
        btnInsert.setOnClickListener {
            if (editTextName.text.toString().isNotEmpty() &&
                editTextAge.text.toString().isNotEmpty()
            ) {
                val user = User(editTextName.text.toString(), editTextAge.text.toString().toInt())
                db.insertData(user)
                clearField()
            }
            else {
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }
        }
        btnRead.setOnClickListener {
            val data = db.readData()
            tvResult.text = ""
            for (i in 0 until data.size) {
                tvResult.append(
                    data[i].id.toString() + " " + data[i].name + " " + data[i].age + "\n"
                )
            }
        }
    }
    private fun clearField() {
        editTextName.text.clear()
        editTextAge.text.clear()
    }
}