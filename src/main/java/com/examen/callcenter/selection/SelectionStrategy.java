package com.examen.callcenter.selection;


import java.util.Optional;

public interface SelectionStrategy<T> {

    Optional<T> getNextSelectable();

    void addSelectable(final T t);
}
