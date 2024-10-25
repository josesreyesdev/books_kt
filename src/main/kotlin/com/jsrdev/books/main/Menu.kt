package com.jsrdev.books.main

import com.jsrdev.books.models.BookData
import com.jsrdev.books.models.ResultData
import com.jsrdev.books.service.DeserializeBookData
import com.jsrdev.books.service.GetBookData

const val BASE_URL = "https://gutendex.com/"

class Menu(
    private val apiService: GetBookData = GetBookData(),
    private val deserializeData: DeserializeBookData = DeserializeBookData()
) {

    fun showMenu() {
        println("Data: ${getResultData()}")

        val resultData: List<BookData> = resultData().results

        val booksToShow = 5
        showBooks(booksToShow, resultData)

        showTop10MostDownloadedBooks(resultData)
    }

    private fun showTop10MostDownloadedBooks(bookList: List<BookData>) {
        println()
        println("Top 10 most downloaded books")

        return bookList.asSequence()
            .filter { it.downloadCount > 0 }
            .sortedByDescending { it.downloadCount }
            .map { it.copy(title = it.title.uppercase()) }
            .take(10)
            .forEachIndexed { ind, book ->
                println("${ind + 1}: ${book.title} ------- download count: ${book.downloadCount}")
            }
    }

    private fun showBooks(booksToShow: Int, bookList: List<BookData>) {
        val startIndex = (0..bookList.size).random().let { randomIndex ->
            if (randomIndex in (bookList.size - booksToShow until bookList.size)) {
                bookList.size - booksToShow
            } else {
                randomIndex
            }
        }

        println()
        println("$booksToShow random books:")

        return bookList.asSequence()
            .drop(startIndex) // Saltamos hasta el índice inicial calculado
            .take(booksToShow) // Tomamos los libros necesarios
            .forEachIndexed { ind, book ->
                println("${ind + 1}: $book")
            }
    }

    private fun resultData(): ResultData = deserializeData.getData(
        json = getResultData(), genericClass = ResultData::class.java
    )

    private fun getResultData(): String {
        val url = "${BASE_URL}books/"
        return getStringData(url = url)
    }

    private fun getStringData(url: String): String =
        apiService.getData(url)
}