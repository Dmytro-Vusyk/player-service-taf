package com.companyname.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class InfoRequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(InfoRequestLoggingFilter.class);

    /*
     * Normally I would implement similar behavior as in RequestLoggingFilter.
     * But due lack of time there will be simple implementation
     * */
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        StringBuffer logBuffer = new StringBuffer();

        logBuffer.append(System.lineSeparator())
                .append("Request Method: ")
                .append(requestSpec.getMethod())
                .append(System.lineSeparator())
                .append("Request URL: ")
                .append(requestSpec.getURI())
                .append(System.lineSeparator());

        if (requestSpec.getBody() != null) {
            logBuffer.append("Request Body: ")
                    .append(Optional.ofNullable(requestSpec.getBody()))
                    .append(System.lineSeparator());
        } else {
            logBuffer.append("Request Body: [no body]")
                    .append(System.lineSeparator());
        }

        logger.info(logBuffer.toString());

        return ctx.next(requestSpec, responseSpec);
    }
}
