package com.chowculator.etcd.restclient

import org.apache.http.client.fluent.Request

class HttpClient {
    public InputStream get(String url) {
        Request.Get(url).execute().returnContent().asStream()
    }
}
