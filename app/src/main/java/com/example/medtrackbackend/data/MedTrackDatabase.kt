package com.example.medtrackbackend.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.medtrackbackend.data.DateConverter
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.MedicineDao
import com.example.medtrackbackend.data.ProgramDao
import com.example.medtrackbackend.data.TimeDao

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Medicine::class, IntakeProgram::class, IntakeTime::class],
    version = 1,
    exportSchema = false
)
abstract class MedTrackDatabase:RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun programDao(): ProgramDao
    abstract fun timeDao(): TimeDao

    //Creating the database in a thread safe manner
    companion object{
        @Volatile
        var INSTANCE:MedTrackDatabase? = null
        fun getDatabase(context:Context):MedTrackDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    MedTrackDatabase::class.java,
                    "medtrack_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("MedTrackDatabase", "Database created")
        }
    }

}