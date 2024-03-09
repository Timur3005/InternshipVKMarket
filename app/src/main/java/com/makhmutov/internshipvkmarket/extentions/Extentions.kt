package com.makhmutov.internshipvkmarket.extentions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(another: Flow<T>) =
    merge(this, another)