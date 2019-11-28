package com.es.phoneshop.validator.implementation;

import com.es.phoneshop.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductQuantityValidator implements Validator {
    private static final String ERROR_FIELD = "quantity";
    private static final String VALUE_BELOW_OR_EQUALS_ZERO = "Invalid input. You're entered invalid quantity.";
    private static final String CANNOT_PARSE_TO_INT = "Invalid input. Not a number.";

    @Override
    public Map validate(HttpServletRequest request, Map errorMap) {
        String quantity = request.getParameter(ERROR_FIELD);
        try {
            if (Integer.valueOf(quantity) <= 0) {
                addErrorMessage(errorMap, VALUE_BELOW_OR_EQUALS_ZERO);
            }
        } catch (NumberFormatException e) {
            addErrorMessage(errorMap, CANNOT_PARSE_TO_INT);
        }
        return errorMap;
    }

    @Override
    public void addErrorMessage(Map errorMap, String message) {
        boolean containsError = errorMap.containsKey(ERROR_FIELD);
        List errorList = containsError ?
                (List) errorMap.get(ERROR_FIELD) : new ArrayList<>();
        errorList.add(0, message);
        if (containsError) {
            errorMap.replace(ERROR_FIELD, errorList);
        } else {
            errorMap.put(ERROR_FIELD, errorList);
        }
    }
}
