package com.basarc.readingisgood.service;

import com.basarc.readingisgood.domain.Book;
import com.basarc.readingisgood.dto.AddBookRequestDto;
import com.basarc.readingisgood.dto.UpdateBookStockRequestDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.repository.BookRepository;
import com.basarc.readingisgood.service.impl.BookServiceImpl;
import com.basarc.readingisgood.service.interfaces.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookServiceImpl.class, ModelMapper.class})
@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void testAddBookWhenSameBookNameIsExist() {

        Book book = new Book();
        final String bookName = "Sample Book";
        book.setName(bookName);

        when(bookRepository.findByName(bookName)).thenReturn(Optional.of(book));

        AddBookRequestDto bookRequestDto = new AddBookRequestDto();
        bookRequestDto.setName(bookName);

        assertThrows(ReadingException.class, () -> bookService.addBook(bookRequestDto));

        verify(bookRepository).findByName(bookName);
    }

    @Test
    public void testAddBookWhenSameBookNameIsNotExist() {

        Book book = new Book();
        book.setName("Sample Name");

        when(bookRepository.findByName(book.getName())).thenReturn(Optional.empty());
        when(bookRepository.save(any())).thenReturn(book);

        AddBookRequestDto addBookRequestDto = new AddBookRequestDto();
        addBookRequestDto.setName(book.getName());

        assertEquals(bookService.addBook(addBookRequestDto).getName(), book.getName());

        verify(bookRepository).findByName(book.getName());
        verify(bookRepository).save(any());
    }

    @Test
    public void testUpdateBookStockWhenBookNotFound() {

        Book book = new Book();
        book.setId("12345");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        UpdateBookStockRequestDto requestDto = new UpdateBookStockRequestDto();
        requestDto.setBookId("12345");

        assertThrows(ReadingException.class, () -> bookService.updateBookStock(requestDto));

        verify(bookRepository).findById(book.getId());
    }

    @Test
    public void testUpdateBookStockWhenBookFound() {

        Book book = new Book();
        book.setId("12345");
        book.setStock(10);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        UpdateBookStockRequestDto requestDto = new UpdateBookStockRequestDto();
        requestDto.setBookId("12345");
        requestDto.setStockAmount(5L);

        assertEquals(bookService.updateBookStock(requestDto).getTotalStock(), 15);

        verify(bookRepository).findById(book.getId());
        verify(bookRepository).save(book);
    }

    @Test
    public void testFindByIdWhenBookIdNotValid() {
        assertThrows(IllegalArgumentException.class, () -> bookService.findById(null));
        assertThrows(IllegalArgumentException.class, () -> bookService.findById(""));
        assertThrows(IllegalArgumentException.class, () -> bookService.findById("  "));
    }

    @Test
    public void testFindByIdWhenBookIdValid() {

        Book book = new Book();
        book.setId("12345");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        assertEquals(bookService.findById(book.getId()), Optional.of(book));

        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());
        assertEquals(bookService.findById(book.getId()), Optional.empty());

        verify(bookRepository, times(2)).findById(book.getId());
    }


    @Test
    public void testDecreaseStockWhenStockAvailable() {

        Book book = new Book();
        book.setId("12345");
        final int quantity = 1;

        final Query query = new Query(Criteria.where("id")
                .is(book.getId()).and("stock").gte(quantity));
        final Update update = new Update().inc("stock", -quantity);

        when(mongoTemplate.findAndModify(query, update, Book.class))
                .thenReturn(book);
        assertTrue(bookService.decreaseStockIfEnoughBookAvailable(book.getId(), quantity));

        verify(mongoTemplate).findAndModify(query, update, Book.class);
    }

    @Test
    public void testDecreaseStockWhenStockNotAvailable() {

        Book book = new Book();
        book.setId("12345");
        final int quantity = 1;

        final Query query = new Query(Criteria.where("id")
                .is(book.getId()).and("stock").gte(quantity));
        final Update update = new Update().inc("stock", -quantity);

        when(mongoTemplate.findAndModify(query, update, Book.class))
                .thenReturn(null);
        assertFalse(bookService.decreaseStockIfEnoughBookAvailable(book.getId(), quantity));

        verify(mongoTemplate).findAndModify(query, update, Book.class);
    }

}
