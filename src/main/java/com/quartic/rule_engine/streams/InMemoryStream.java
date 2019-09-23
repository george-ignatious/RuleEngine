package com.quartic.rule_engine.streams;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMemoryStream<T> implements Stream<T> {
    Queue<T> queue = new LinkedBlockingQueue<>();

    @Override
    public void insert(T signal) {
        queue.add(signal);
    }

    @Override
    public T next() {
        return queue.remove();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public Integer count() {
        return queue.size();
    }

    @Override
    public void clear() {
        queue.clear();
    }
}
