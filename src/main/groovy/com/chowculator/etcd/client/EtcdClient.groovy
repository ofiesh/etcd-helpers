package com.chowculator.etcd.client

import groovy.json.JsonSlurper
import org.apache.http.client.fluent.Async
import org.apache.http.client.fluent.Content
import org.apache.http.client.fluent.Request
import org.apache.http.concurrent.FutureCallback
import org.apache.http.message.BasicNameValuePair

class EtcdClient {
    private final String uri

    EtcdClient(String uri) {
        this.uri = uri + "/v2/keys"
    }

    private static parseValue(InputStream val) {
        def parsed = new JsonSlurper().parse(val)
        parsed != null && parsed instanceof Map && parsed.action != "delete" && parsed.node != null ?
                parsed.node.value : null
    }

    def putValue(String key, String value) {
        Request.Put(uri + "/" + key).bodyForm(new BasicNameValuePair("value", value)).execute()
    }

    def getValue(String key) {
        def val = Request.Get(uri + "/" + key).execute().returnContent().asStream()
        parseValue(val)
    }

    void delete(String key) {
        Request.Delete(uri + "/" + key).execute();
    }

    def waitForChange(String key, FutureCallback<String> futureCallback) {
        Async.newInstance().execute(Request.Get(uri + "/" + key + "?wait=true"),
            new FutureCallback<Content>() {
                @Override
                void completed(Content result) {
                    futureCallback.completed(parseValue(result.asStream()))
                }

                @Override
                void failed(Exception ex) {
                    futureCallback.failed(ex)
                }

                @Override
                void cancelled() {
                    futureCallback.cancelled()
                }
            });
    }
}
