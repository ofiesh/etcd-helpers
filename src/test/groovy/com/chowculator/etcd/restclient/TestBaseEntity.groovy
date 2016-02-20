package com.chowculator.etcd.restclient

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class TestBaseEntity {
    String str
    Integer integer
    SubEnity subEnity
}
