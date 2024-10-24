package com.jsrdev.books.service

interface ConvertData {
    fun <T> getData(json: String, genericClass: Class<T>): T
}