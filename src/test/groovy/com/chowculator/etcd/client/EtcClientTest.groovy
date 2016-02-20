package com.chowculator.etcd.client

import org.apache.http.client.HttpResponseException
import org.apache.http.concurrent.FutureCallback
import org.junit.Test

class EtcClientTest {
    def client = new EtcdClient("http://etcd:4001")
    @Test(expected = HttpResponseException)
    public void testPutGet() {
        client.putValue("abc", "foo!!obar!")
        assert "foo!!obar!" == client.getValue("abc")
        client.delete("abc")
        client.getValue("abc")
    }

    @Test
    public void testAsync() {
        client.putValue("foo", "bar")
        final foo = new MutableStringHolder();
        foo.value == "notBar"
        def change = client.waitForChange("foo", new FutureCallback<String>() {
            @Override
            void completed(String result) {
                foo.value = result
            }

            @Override
            void failed(Exception ex) {
                assert false
            }

            @Override
            void cancelled() {
                assert false
            }
        })
        Thread.sleep(100)
        client.putValue("foo", "bars")
        Thread.sleep(100)
        assert "bars" == foo.value
    }
}
