package com.companyname.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfoRequestLoggingFilter implements Filter {

    /*
     * Normally I would implement similar behavior as in RequestLoggingFilter.
     * But due lack of time there will be simple implementation
     * */
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        System.out.printf("Request Method: %s%n", requestSpec.getMethod());
        System.out.printf("Request URL: %s%n", requestSpec.getURI());
        if (requestSpec.getBody() != null) {
            System.out.printf("Request Body: %s%n", (Object) requestSpec.getBody());
        } else {
            System.out.println("Request Body: [no body]");
        }

        return ctx.next(requestSpec, responseSpec);
    }
}
