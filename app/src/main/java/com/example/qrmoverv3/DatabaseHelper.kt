package com.example.qrmoverv3
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
val DATABASENAME = "QRMoverDB"
val TABLENAME = "BoxContents"
val COL_ID = "ID"
val COL_NOTE= "Note"
val COL_PICTURE = "Picture"
val COL_CREATEDON = "CreatedOn"
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " GUID PRIMARY KEY," + COL_NOTE + " VARCHAR(256)," + COL_PICTURE + " BLOB," + COL_CREATEDON + " VARCHAR(256))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(note: Note) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID, note.id)
        contentValues.put(COL_NOTE, note.note)
        contentValues.put(COL_PICTURE, note.picture)
        contentValues.put(COL_CREATEDON, note.createdOn)

        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData(): MutableList<Note> {
        val list: MutableList<Note> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val note = Note(result.getString(result.getColumnIndex(COL_ID)).toString(), result.getString(result.getColumnIndex(COL_NOTE)).toString(), result.getString(result.getColumnIndex(COL_CREATEDON)).toString(), result.getString(result.getColumnIndex(COL_PICTURE)).toString())
                list.add(note)
            }
            while (result.moveToNext())
        }
        return list
    }
    fun deleteData(id: String){
        val db = this.readableDatabase
        val query = "Delete from $TABLENAME WHERE ID = $id"
        db.rawQuery(query, null)
        }
}