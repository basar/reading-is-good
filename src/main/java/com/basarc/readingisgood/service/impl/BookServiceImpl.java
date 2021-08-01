package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.AddBookResponseDto;
import com.basarc.readingisgood.repository.BookRepository;
import com.basarc.readingisgood.service.interfaces.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public AddBookResponseDto addBook(AddBookRequestDto addBookRequestDto) {




        return null;
    }
}
