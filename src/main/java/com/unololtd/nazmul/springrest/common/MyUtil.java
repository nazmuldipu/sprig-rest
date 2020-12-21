package com.unololtd.nazmul.springrest.common;

import org.springframework.hateoas.PagedModel;

public class MyUtil {

    public static String createLinkHeader(PagedModel<?> pr) {
        final StringBuilder linkHeader = new StringBuilder();
        if(pr.getLinks("first").size() > 0) {
            linkHeader.append(buildLinkHeader(pr.getLinks("first").get(0).getHref(), "first"));
            linkHeader.append(", ");
        }
        if(pr.getLinks("last").size() > 0) {
            linkHeader.append(buildLinkHeader(pr.getLinks("last").get(0).getHref(), "last"));
        }
        return linkHeader.toString();
    }

    private static String buildLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }
}
