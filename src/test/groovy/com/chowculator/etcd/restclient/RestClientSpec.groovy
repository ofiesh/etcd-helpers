package com.chowculator.etcd.restclient

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.junit.Test
import spock.lang.Specification


class RestClientSpec extends Specification {
    TestBaseEntity testBaseEntity1
    TestBaseEntity testBaseEntity2

    def setup() {
        testBaseEntity1 = new TestBaseEntity()
        testBaseEntity1.str = "bar"
        testBaseEntity1.integer = 1

        testBaseEntity1.subEnity = new SubEnity()
        testBaseEntity1.subEnity.sub = "sub"

        testBaseEntity2 = new TestBaseEntity()
        testBaseEntity2.str = "bar2"
        testBaseEntity2.integer = 2

        testBaseEntity2.subEnity = new SubEnity()
        testBaseEntity2.subEnity.sub = "sub2"
    }

    def "should get object"() {
        setup:
        def httpClient = Mock(HttpClient)
        httpClient.get("testurl") >> new ByteArrayInputStream(new JsonBuilder(testBaseEntity1).toString().bytes)
        def restTemplate = new RestTemplate(httpClient)

        when:
        TestBaseEntity result = restTemplate.getForObject("testurl", TestBaseEntity)

        then:
        assert testBaseEntity1 == result
    }

    def "should get objects"() {
        setup:
        def httpClient = Mock(HttpClient)
        def bytes = new JsonBuilder([testBaseEntity1, testBaseEntity2]).toString().bytes
        httpClient.get("testurl") >> new ByteArrayInputStream(bytes)
        def restTemplate = new RestTemplate(httpClient)

        when:
        List<TestBaseEntity> results = restTemplate.getForObjects("testurl", TestBaseEntity)

        then:
        assert [testBaseEntity1, testBaseEntity2] == results
    }
}
