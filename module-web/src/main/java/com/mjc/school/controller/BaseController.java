package com.mjc.school.controller;

import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;

import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll();

    R readById(K id) throws NotFoundException;

    R create(T createRequest) throws ValidatorException, NotFoundException;

    R update(T updateRequest) throws ValidatorException, NotFoundException;

    boolean deleteById(K id) throws NotFoundException;
}
