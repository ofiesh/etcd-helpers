package com.chowculator.etcd.restclient

import groovy.json.JsonSlurper

class RestTemplate {
    private final JsonHttpClient client

    RestTemplate() {
        this.client  = new JsonHttpClient()
    }

    RestTemplate(JsonHttpClient client) {
        this.client = client
    }

    private static <T> T parseJsonResponseObject(InputStream resp, Class<T> c){
        def o = new JsonSlurper().parse(resp)
        if(o != null) {
            assert o instanceof Map : "Response is not a valid object"
            return c.newInstance(o)
        }
        null
    }

    private static <T> List<T> parseJsonResponseArray(InputStream resp, Class<T> c) {
        def o = new JsonSlurper().parse(resp)
        if(o != null) {
            assert o instanceof Map : "Response is not a valid array"
            def l =[]
            o.forEach {
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

    public <T> T getForObject(String url, Class<T> c) {
        parseJsonResponseObject(client.get(url), c)
    }

    public <T> List<T> getForObjects(String url, Class<T> c) {
        parseJsonResponseArray(client.get(url), c)
    }

    public <T> T postForObject(String url, Class<T> c, Object entity) {
        parseJsonResponseObject(client.post(url, entity), c)
    }

    public <T> T putForObject(String url, Class<T> c, Object entity) {
        parseJsonResponseObject(client.put(url, entity), c)
    }

    public <T> T deleteForObject(String url, Class<T> c) {
        parseJsonResponseObject(client.delete(url), c)
    }
}
