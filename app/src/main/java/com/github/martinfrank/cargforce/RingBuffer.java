package com.github.martinfrank.cargforce;

import java.util.function.ObjDoubleConsumer;

public class RingBuffer<T> {

    private final int ringBufferSize;
    private int ringIndex;

    private final T[] buffer;

    @SuppressWarnings("unchecked")
    public RingBuffer(int ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
        buffer = (T[]) new Object[ringBufferSize];
    }

    public void add(T t) {
        buffer[ringIndex] = t;
        moveRingIndex();
    }

    private void moveRingIndex() {
        ringIndex = ringIndex + 1;
        if (ringIndex == ringBufferSize) {
            ringIndex = 0;
        }
    }

    public T get(int index){
        return buffer[mapIndex(index)];
    }

    private int mapIndex(int i) {
        int index = i + ringIndex;
        if (index >= ringBufferSize) {
            index = index - ringBufferSize;
        }
        return index;
    }

}
