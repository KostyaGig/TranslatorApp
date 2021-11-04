package com.zinoview.translatorapp.core

import com.zinoview.translatorapp.core.words.Abstract

interface FactoryMapper<S,R> : Abstract.Mapper {
    fun map(src: S) : R
}