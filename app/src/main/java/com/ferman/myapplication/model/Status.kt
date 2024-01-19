package com.ferman.myapplication.model

sealed class Status {
    data object Loading : Status()
    class Success <T> (val data: T) : Status()
    data object Failed : Status()
}