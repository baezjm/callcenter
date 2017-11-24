package com.examen.callcenter.entity;

import net.jcip.annotations.Immutable;

import java.util.Objects;

@Immutable
public final class Call {

    public Long getNumber() {
        return number;
    }

    private final Long number;

    public Call(final Long number) {
        this.number = number;
    }

    public String closeCall(){
        Thread.interrupted();
        return "close_call";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return Objects.equals(number, call.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
