package com.chowculator.etcd.restclient

import com.chowculator.etcd.client.EtcdWatcher

class JsonRestClient<T> {
    private final EtcdWatcher etcdWatcher
    private final RestTemplate restTemplate = new RestTemplate()
    private final String resource
    private final Class<T> c

    JsonRestClient(EtcdWatcher etcdWatcher, String resource, Class<T> c) {
        this.etcdWatcher = etcdWatcher
        this.resource = resource
        this.c = c
    }

    private String getBaseUrl() {
        etcdWatcher.get() + "/" + resource + "/"
    }

    T getObject(String url) {
        restTemplate.getForObject(getBaseUrl() + url, c)
    }

    List<T> getObjects(String url) {
        restTemplate.getForObjects(getBaseUrl() + url, c)
    }

    T postObject(String url, Object entity) {
        restTemplate.postForObject(getBaseUrl() + url, c, entity)
    }

    T putObject(String url, Object entity) {
        restTemplate.putForObject(getBaseUrl() + url, c, entity)
    }

    T deleteObject(String url) {
        restTemplate.deleteForObject(getBaseUrl() + url, c)
    }
}
