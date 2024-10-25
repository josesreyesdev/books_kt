package com.jsrdev.books.main

import com.jsrdev.books.models.BookData
import com.jsrdev.books.models.ResultData
import com.jsrdev.books.models.Statistics
import com.jsrdev.books.service.DeserializeBookData
import com.jsrdev.books.service.GetBookData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val BASE_URL = "https://gutendex.com/"

class Menu(
    private val apiService: GetBookData = GetBookData(),
    private val deserializeData: DeserializeBookData = DeserializeBookData()
) {

    fun showMenu() {
        val json: String = getStringData()
        println("Json Data: $json")

        val resultData: List<BookData> = resultData(json).results

        // show randoms books
        val booksToShow = 5
        showBooks(booksToShow, resultData)

        // top 10 most downloaded books
        showTop10MostDownloadedBooks(resultData)

        // search by title book
        showByTitleBook()

        // statistics
        getStatistics(resultData)

    }

    private fun getStatistics(data: List<BookData>) {
        val stats: Statistics = data.asSequence()
            .filter { it.downloadCount > 0 }
            .map { it.downloadCount.toDouble() } // extraemos todas las descargas
            .toList()
            .let { downloadedCount ->
                Statistics(
                    count = downloadedCount.size,
                    sum = downloadedCount.sum(),
                    average = downloadedCount.average(),
                    min = downloadedCount.minOrNull() ?: 0.0,
                    max = downloadedCount.maxOrNull() ?: 0.0
                )
            }
        println()
        println("Statistics")
        println("Count: ${stats.count}, Sum: ${stats.sum}, Average: ${"%.2f".format(stats.average)}, Min: ${stats.min}, Max: ${stats.max}")
    }

    private fun showByTitleBook() {
        var titleBook = getTitleBook()
        while (titleBook.isNullOrEmpty()) {
            println("Invalid entry, please, try again")
            titleBook = getTitleBook()
        }

        titleBook = titleBook.trim().lowercase()
        titleBook = encodedAndFormatTitleBook(titleBook)

        val json: String = getStringData(buildUrl(title = titleBook))
        val resultDataByTitle: List<BookData> = resultData(json).results

        val book: BookData? = resultDataByTitle.asSequence()
            .filter { it.languages.any { lang -> lang.equals("ES", ignoreCase = true)  }}
            .filter { it.title.contains(titleBook, ignoreCase = true) }
            .firstOrNull()

        println()
        return book?.let {
            println("Libro encontrado")
            println("$book")
        } ?: println("No encontre ninguna coincidencia con: $titleBook")
    }

    private fun encodedAndFormatTitleBook(title: String): String {
        // encodedSeriesName.replace("+", "%20")
        return URLEncoder.encode(title, StandardCharsets.UTF_8)
    }

    private fun getTitleBook(): String? {
        println()
        println("Excribe el nombre del libro a consultar: ")
        return readlnOrNull()
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
            .take(booksToShow)
            .forEachIndexed { ind, book ->
                println("${ind + 1}: $book")
            }
    }

    private fun resultData(json: String): ResultData =
        deserializeData.getData(json , ResultData::class.java)

    private fun getStringData(url: String = buildUrl(title = null)): String =
        apiService.getData(url)

    private fun buildUrl(title: String?): String {

        val urlBuilder: StringBuilder = StringBuilder(BASE_URL)
        urlBuilder.append("books/")
        title?.let { urlBuilder.append("?search=${title}") }

        return urlBuilder.toString()
    }
}