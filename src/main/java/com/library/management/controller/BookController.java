package com.library.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.library.management.model.Book;
import com.library.management.service.BookService;

import jakarta.validation.Valid;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {

        model.addAttribute(
                "books",
                bookService.getAllBooks()
        );

        return "books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {

        model.addAttribute(
                "book",
                new Book()
        );

        return "add-book";
    }

    @PostMapping("/books/save")
    public String saveBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add-book";
        }

        bookService.saveBook(book);

        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute(
                "book",
                bookService.getBookById(id)
        );

        return "edit-book";
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(
            @PathVariable Integer id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result) {

        if (result.hasErrors()) {
            return "edit-book";
        }

        bookService.updateBook(id, book);

        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(
            @PathVariable Integer id) {

        bookService.deleteBook(id);

        return "redirect:/books";
    }

    @GetMapping("/books/search")
    public String searchBooks(
            @RequestParam(required = false) String title,
            Model model) {

        if (title == null || title.trim().isEmpty()) {

            model.addAttribute(
                    "books",
                    bookService.getAllBooks()
            );

        } else {

            model.addAttribute(
                    "books",
                    bookService.searchByTitle(title)
            );
        }

        return "books";
    }
}