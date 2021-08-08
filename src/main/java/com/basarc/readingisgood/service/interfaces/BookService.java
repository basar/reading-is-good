package com.basarc.readingisgood.service.interfaces;

import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.AddBookResponseDto;
import com.basarc.readingisgood.dto.UpdateBookStockRequestDto;
import com.basarc.readingisgood.dto.UpdateBookStockResponseDto;

import java.util.Optional;

public interface BookService {

    AddBookResponseDto addBook(AddBookRequestDto addBookRequestDto);

    UpdateBookStockResponseDto updateBookStock(UpdateBookStockRequestDto updateBookStockRequestDto);

    boolean decreaseStockIfEnoughBookAvailable(String bookId, int quantity);

    Optional<Book> findById(String bookId);

}
