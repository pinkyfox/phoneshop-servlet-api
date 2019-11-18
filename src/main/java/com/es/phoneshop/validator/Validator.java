package com.es.phoneshop.validator;

import com.es.phoneshop.exceptions.CannotParseToIntException;
import com.es.phoneshop.exceptions.ValueBelowOrEqualsZeroException;

public class Validator {
    public static int parseToInteger(String string) throws ValueBelowOrEqualsZeroException, CannotParseToIntException {
        int intValue;
        try {
            intValue = Integer.valueOf(string);
            if (intValue <= 0) {
                throw new ValueBelowOrEqualsZeroException();
            }
        } catch (NumberFormatException e) {
            throw new CannotParseToIntException();
        }
        return intValue;
    }
}
