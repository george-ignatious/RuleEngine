package com.quartic.rule_engine.streams;

public interface Stream<T> {
    void insert(T signal);

    T next();

    boolean hasNext();

    Integer count();

    void clear();
}
