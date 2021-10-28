package com.zinoview.translatorapp.data.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.translatorapp.data.DataLanguage
import com.zinoview.translatorapp.data.DataWords

//SUCCESS_RESULT = "Success translator word"
//EMPTY_WORD_RESULT = "Empty field"
//FAILURE_RESULT = "Failure"
//
//SUCCESS_MARK = "Success"
//EMPTY_WORD_MARK = "Empty"
//FAILURE_MARK = "Failure"
//
//RU_LANGUAGE = "ru"
//EN_LANGUAGE = "en"

//"fromLanguage": "",
//"mark": "Empty",
//"result": "Empty field",
//"srcWord": "",
//"toLanguage": "",
//"translatorWord": ""

/**
 * Mark(метка) - Success or Failure model
 */

interface CloudWord {

    fun map(mapper: CloudResultMapper) : DataWords


    data class Base(
        @SerializedName("message")
        private val message: String = "",
        @SerializedName("mark")
        private val mark: String = "",
        @SerializedName("translatorWord")
        private val translatedWord: String = "",
        @SerializedName("srcWord")
        private val srcWord: String = "",
        @SerializedName("fromLanguage")
        private val fromLanguage: String = "",
        @SerializedName("toLanguage")
        private val toLanguage: String = ""
    ) : CloudWord {

        override fun map(mapper: CloudResultMapper) : DataWords {
            val stateWord = mapper.map(mark)
            val language = DataLanguage(fromLanguage, toLanguage)
            return when(stateWord) {
                CloudResultMapper.StateMark.SUCCESS -> DataWords.Success(
                    srcWord,
                    translatedWord,
                    language
                )
                CloudResultMapper.StateMark.FAILURE -> DataWords.Failure(
                    message
                )
            }
        }
    }
}
