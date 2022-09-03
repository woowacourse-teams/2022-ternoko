package com.woowacourse.ternoko.common.log;

import org.springframework.web.util.ContentCachingRequestWrapper;

public class RequestContext {

    private ContentCachingRequestWrapper request;

    public void setRequest(final ContentCachingRequestWrapper request) {
        this.request = request;
    }

    public ContentCachingRequestWrapper getRequest() {
        return request;
    }
}
