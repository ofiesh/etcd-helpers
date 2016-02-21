package com.chowculator.etcd.restclient

import static org.apache.http.entity.ContentType.APPLICATION_JSON

import groovy.json.JsonBuilder
import org.apache.http.client.fluent.Request

class JsonHttpClient {

    private static InputStream execute(Request request, Object entity) {
        execute(request.bodyString(new JsonBuilder(entity).toString(), APPLICATION_JSON))
    }

    private static InputStream execute(Request request) {
        request.addHeader("Accept", APPLICATION_JSON.mimeType).execute().returnContent().asStream()
    }

    def get(String url) {
        execute(Request.Get(url))
    }

    def post(String url, Object entity) {
        execute(Request.Post(url), entity)
    }

    def put(String url, Object entity) {
        execute(Request.Put(url), entity)
    }

    def delete(String url) {
        execute(Request.Delete(url))
    }
}
