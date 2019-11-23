package com.es.phoneshop.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface Validator <F, E> {
    Map<F, List<E>> validate(HttpServletRequest request, Map<F, List<E>> errorMap);
    void addErrorMessage(Map<F, List<E>> errorMap, String errorMessage);
}
