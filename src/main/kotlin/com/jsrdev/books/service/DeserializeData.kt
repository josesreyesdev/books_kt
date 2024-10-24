package com.jsrdev.books.service

interface DeserializeData {
    fun <T> getData(json: String, genericClass: Class<T>): T
}