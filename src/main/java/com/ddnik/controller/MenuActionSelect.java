package com.ddnik.controller;

import java.util.Optional;

@FunctionalInterface
public interface MenuActionSelect<T> {
    Optional<T> execute() throws Exception;
}
