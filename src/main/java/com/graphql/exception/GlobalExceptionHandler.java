package com.graphql.exception;

import graphql.GraphQLError;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handle(BindException ex) {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message("...").build();
    }

}
