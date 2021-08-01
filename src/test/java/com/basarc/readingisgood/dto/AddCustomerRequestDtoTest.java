package com.basarc.readingisgood.dto;


import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AddCustomerRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testProperties() {

        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setName("Michael");
        toBeTested.setSurname("Jackson");
        toBeTested.setEmail("mj@mj.com");
        toBeTested.setAddress("USA");

        assertEquals("Michael", toBeTested.getName());
        assertEquals("Jackson", toBeTested.getSurname());
        assertEquals("mj@mj.com", toBeTested.getEmail());
        assertEquals("USA", toBeTested.getAddress());
    }


    @Test
    public void testNameWhenSizeLowerThanMin() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setName(RandomStringUtils.randomAlphabetic(1));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "name");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testNameWhenSizeHigherThanMax() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setName(RandomStringUtils.randomAlphabetic(51));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "name");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testNameWhenValueIsEqualNull() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "name");
        assertEquals(nameConstraintViolations.size(), 1);
    }


    @Test
    public void testSurnameWhenSizeLowerThanMin() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setSurname(RandomStringUtils.randomAlphabetic(1));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "surname");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testSurnameWhenSizeHigherThanMax() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setSurname(RandomStringUtils.randomAlphabetic(51));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "surname");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testSurnameWhenValueIsEqualNull() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "surname");
        assertEquals(nameConstraintViolations.size(), 1);
    }


    @Test
    public void testAddressWhenSizeLowerThanMin() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setAddress(RandomStringUtils.randomAlphabetic(1));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "address");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testAddressWhenSizeHigherThanMax() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setSurname(RandomStringUtils.randomAlphabetic(251));
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "address");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testAddressWhenValueIsEqualNull() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "address");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testEmailWhenValueIsEqualNull() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        Set<ConstraintViolation<AddCustomerRequestDto>> nameConstraintViolations = validator
                .validateProperty(toBeTested, "email");
        assertEquals(nameConstraintViolations.size(), 1);
    }

    @Test
    public void testEmailWhenFormatIsInvalid() {
        AddCustomerRequestDto toBeTested = new AddCustomerRequestDto();
        toBeTested.setEmail("sample@");
        assertEquals(validator
                .validateProperty(toBeTested, "email").size(), 1);

        toBeTested.setEmail("sample");
        assertEquals(validator
                .validateProperty(toBeTested, "email").size(), 1);
    }


}
