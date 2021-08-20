package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.UpdateBookStockRequestDto;
import com.basarc.readingisgood.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.Path.BOOK)
public class BookController extends AbstractApiController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(@RequestBody @Valid AddBookRequestDto request) {
        return ok(bookService.addBook(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBookStock(@RequestBody @Valid UpdateBookStockRequestDto request) {
        return ok(bookService.updateBookStock(request));
    }

}
