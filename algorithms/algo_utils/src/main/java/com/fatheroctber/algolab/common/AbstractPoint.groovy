package com.fatheroctber.algolab.common

public abstract class AbstractPoint<T> {
    protected T x
    protected T y

    public AbstractPoint(T x, T y) {
        this.x = x
        this.y = y
    }

    public T getX() {
        return x
    }

    public T getY() {
        return y
    }

    public T setX(T x) {
        this.x = x
    }

    public T setY(T y) {
        this.y = y
    }
}
