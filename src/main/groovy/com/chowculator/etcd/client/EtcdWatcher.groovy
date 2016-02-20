package com.chowculator.etcd.client

import org.apache.http.client.HttpResponseException
import org.apache.http.concurrent.FutureCallback

class EtcdWatcher {
    private final EtcdClient etcdClient;
    private String key;
    private final holder = new MutableStringHolder();

    EtcdWatcher(EtcdClient etcdClient, String key) {
        this.etcdClient = etcdClient
        this.key = key;
        try {
            holder.value = etcdClient.getValue(key)
        } catch (HttpResponseException e) {
            //not found
        }
        watchAndGet()
    }

    public String get() {
        return holder.value
    }

    private void watchAndGet() {
        etcdClient.waitForChange(key, new FutureCallback<String>() {
            @Override
            void completed(String result) {
                holder.value = result
                watchAndGet()
            }

            @Override
            void failed(Exception ex) {

            }

            @Override
            void cancelled() {

            }
        })
    }
}
