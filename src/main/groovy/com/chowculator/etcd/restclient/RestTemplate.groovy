package com.chowculator.etcd.restclient

import groovy.json.JsonSlurper
import org.apache.http.client.fluent.Request

class RestTemplate {
    private final HttpClient httpClient

    RestTemplate(HttpClient httpClient) {
        this.httpClient = httpClient
    }

    public <T> T getForObject(String url, Class<T> c) {
        def object = new JsonSlurper().parse(httpClient.get(url))
        if(object != null) {
            assert object instanceof Map: "Object returned is not a map, maybe getForObjects?"
            return c.newInstance(object)
        }
        null
    }

    public <T> List<T> getForObjects(String path, Class<T> c) {
        def object = new JsonSlurper().parse(httpClient.get(path))
        if(object != null) {
            assert object instanceof List: "Object returned is not a list, maybe getForObject?"
            def l = []
            object.forEach {
                if(it != null && it instanceof Map) {
                    l.add(c.newInstance(it))
                } else {
                    l.add(null)
                }
            }
            return l
        }
        null
    }
}
