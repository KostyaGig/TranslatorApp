package com.zinoview.translatorapp.data.words.sync

interface SyncWord {

    object Empty : SyncWord

    class Base(
        private val src: String,
        private val translated: String
    ) : SyncWord
}