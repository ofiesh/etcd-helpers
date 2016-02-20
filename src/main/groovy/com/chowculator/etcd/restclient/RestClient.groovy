package com.chowculator.etcd.restclient

import com.chowculator.etcd.client.EtcdClient
import com.chowculator.etcd.client.EtcdWatcher
import groovy.json.JsonSlurper
import org.apache.http.client.fluent.Request

class RestClient {
    private final EtcdWatcher etcdWatcher

    RestClient(EtcdClient etcdClient, String resource) {
        etcdWatcher = new EtcdWatcher(etcdClient, resource)
    }
}
