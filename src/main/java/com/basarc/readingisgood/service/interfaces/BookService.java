package com.basarc.readingisgood.service.interfaces;

import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.AddBookResponseDto;

public interface BookService {

    AddBookResponseDto addBook(AddBookRequestDto addBookRequestDto);

}
