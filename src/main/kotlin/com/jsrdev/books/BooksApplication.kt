package com.jsrdev.books

import com.jsrdev.books.main.Menu
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooksApplication : CommandLineRunner {
    override fun run(vararg args: String?) {
        Menu().showMenu()
    }
}

fun main(args: Array<String>) {
    runApplication<BooksApplication>(*args)
}
