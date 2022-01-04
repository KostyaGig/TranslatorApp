package com.zinoview.translatorapp.data.words.sync.cloud

import com.zinoview.translatorapp.core.words.Abstract

interface CloudAbstractResponseToCloudSyncWordsMapper : Abstract.ResponseMapper<CloudSyncWords> {

    class Base : CloudAbstractResponseToCloudSyncWordsMapper {

        override fun map(): CloudSyncWords = CloudSyncWords.Success

        override fun map(message: String): CloudSyncWords = CloudSyncWords.Failure(message)
    }
}