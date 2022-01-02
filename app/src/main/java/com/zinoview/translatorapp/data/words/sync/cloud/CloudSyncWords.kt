package com.zinoview.translatorapp.data.words.sync.cloud

import com.google.gson.annotations.SerializedName

interface CloudSyncWords {

    class Base(
        @SerializedName("mark")
        private val mark: String = FAILURE_MARK
    ) : CloudSyncWords {


        private companion object {
            private const val FAILURE_MARK = "Failure"
        }
    }

}