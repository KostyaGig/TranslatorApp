package com.zinoview.translatorapp.data.cache

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cacheWordRoom: CacheWord)

    @Update
    fun update(cacheWord: CacheWord)

    @Query("SELECT * FROM words_table")
    fun words() : List<CacheWord>

    @Query("SELECT * FROM words_table WHERE translated = :translatedWord")
    fun word(translatedWord: String) : List<CacheWord>
}