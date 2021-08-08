package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.AddBookResponseDto;
import com.basarc.readingisgood.dto.UpdateBookStockRequestDto;
import com.basarc.readingisgood.dto.UpdateBookStockResponseDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.BookRepository;
import com.basarc.readingisgood.service.interfaces.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper,
                           MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    @Override
    public AddBookResponseDto addBook(AddBookRequestDto addBookRequestDto) {

        Optional<Book> existBook = bookRepository.findByName(addBookRequestDto.getName());
        if (existBook.isPresent()) {
            log.debug("Book is already exist with the name:{}", addBookRequestDto.getName());
            throw new ReadingException(ApiResponseCode.BOOK_ALREADY_DEFINED);
        }
        //Convert to book
        Book book = convertToBook(addBookRequestDto);
        //Save the book
        Book managed = bookRepository.save(book);
        //Convert to dto and return it
        return convertToAddBookResponseDto(managed);
    }

    @Transactional
    @Override
    public UpdateBookStockResponseDto updateBookStock(UpdateBookStockRequestDto requestDto) {

        Book bookToBeUpdated = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> {
                    log.debug("Book not found with id:{}", requestDto.getBookId());
                    return new ReadingException(ApiResponseCode.BOOK_NOT_FOUND);
                });

        long currentStock = bookToBeUpdated.getStock();
        bookToBeUpdated.setStock(currentStock + requestDto.getStockAmount());
        //update the book
        bookRepository.save(bookToBeUpdated);
        return new UpdateBookStockResponseDto(bookToBeUpdated.getId(), bookToBeUpdated.getStock());
    }

    @Transactional
    @Override
    public boolean decreaseStockIfEnoughBookAvailable(String bookId, int quantity) {

        Book book = mongoTemplate.findAndModify(
                new Query(Criteria.where("id").is(bookId).and("stock").gte(quantity)),
                new Update().inc("stock", -quantity),
                Book.class);

        return book!=null;
    }

    @Override
    public Optional<Book> findById(String bookId) {
        Assert.hasText(bookId, "Id must not be empty!");
        return bookRepository.findById(bookId);
    }


    private Book convertToBook(AddBookRequestDto addBookRequestDto) {
        return modelMapper.map(addBookRequestDto, Book.class);
    }

    private AddBookResponseDto convertToAddBookResponseDto(Book book) {
        return modelMapper.map(book, AddBookResponseDto.class);
    }

}
