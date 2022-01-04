package com.zinoview.translatorapp.data.words.sync

interface SyncWords {

    class Base(
        private val words: List<SyncWord>
    ) : SyncWords
}