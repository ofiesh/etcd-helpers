package com.chowculator.etcd.client

import org.junit.Before
import org.junit.Test

class EtcdWatcherTest {
    def client = new EtcdClient("http://etcd:4001")

    @Before
    void before() {
        client.delete("watcher")
    }

    @Test
    void testWatcherValNotSet() {
        def watcher = new EtcdWatcher(client, "watcher")
        assert null == watcher.get()
    }

    @Test
    void testWatcherValSet() {
        client.putValue("watcher", "isSet")
        def watcher = new EtcdWatcher(client, "watcher")
        assert "isSet" == watcher.get()
    }

    @Test
    void testWatcherWatch() {
        def watcher = new EtcdWatcher(client, "watcher")
        Thread.sleep(100)
        client.putValue("watcher", "set1")
        Thread.sleep(100)
        assert "set1" == watcher.get()

        client.putValue("watcher", "set2")
        Thread.sleep(100)
        assert "set2" == watcher.get()

        client.delete("watcher")
        assert null == watcher.get()
    }
}
