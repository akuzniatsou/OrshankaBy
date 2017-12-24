package com.studio.mpak.orshankanews.validators;

public interface Validator<T> {

    boolean isValid(T domain);
}
