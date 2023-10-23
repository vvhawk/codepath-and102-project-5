package com.example.emojo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MoodDao {
    @Insert
    fun insert(moodEntry: MoodEntry): Long

    @Query("SELECT * FROM mood_entries")
    fun getAllEntries(): Flow<List<MoodEntry>>


}