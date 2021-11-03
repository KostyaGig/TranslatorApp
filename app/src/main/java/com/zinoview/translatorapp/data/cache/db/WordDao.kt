package com.zinoview.translatorapp.data.cache.db

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cacheWordRoom: CacheWord.Base)

    @Update
    fun update(cacheWord: CacheWord.Base)

    @Query("SELECT * FROM words_table")
    fun words() : List<CacheWord.Base>

    @Query("SELECT * FROM words_table WHERE translated = :translatedWord")
    fun word(translatedWord: String) : List<CacheWord.Base>
}