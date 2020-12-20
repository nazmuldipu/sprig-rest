package com.unololtd.nazmul.springrest.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageAttr {
    public static int PAGE_SIZE = 10;

    public static String SORT_BY_FIELD_ID = "id";

    public static PageRequest getPageRequest(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(PageAttr.SORT_BY_FIELD_ID).descending());
    }

    public static PageRequest getPageRequestAsc(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(PageAttr.SORT_BY_FIELD_ID).ascending());
    }

    public static PageRequest getPageRequest(int page, int size) {
        if (size <= 0) size = 10;
        return PageRequest.of(page, size, Sort.by(PageAttr.SORT_BY_FIELD_ID).descending());
    }
}
