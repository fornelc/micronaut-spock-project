package com.micronaute.controller


import com.micronaute.SpeedCommand
import com.micronaute.StatusCommand
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class DefaultControllerSpec extends Specification {

    @Shared @AutoCleanup @Inject @Client("/")
    HttpClient client

    void "Root path should return index view"() {
        given:
        HttpResponse response = client.toBlocking().exchange("/", String.class)

        expect:
        response.body().contains("html")
    }

    void "/status should return status"() {
        given:
        HttpResponse response = client.toBlocking().exchange("/status", Map.class)

        expect:
        response.status == HttpStatus.OK
        response.body()["status"] == "green"
    }

    void "/statusAndSpeed should return status and speed"() {
        given:
        HttpResponse response = client.toBlocking().exchange("/statusAndSpeed", Map.class)

        expect:
        response.status == HttpStatus.OK
        response.body()["status"] == "green"
        response.body()["speed"] == 0
    }

    void "/speed should update speed"() {
        given:
        SpeedCommand speedCommand = new SpeedCommand(3)
        client.toBlocking().exchange(HttpRequest.POST("/speed", speedCommand))
        HttpResponse response = client.toBlocking().exchange("/statusAndSpeed", Map.class)

        expect:
        response.status() == HttpStatus.OK
        response.body()["speed"] == 3
    }

    void "/status should update status"() {
        given:
        StatusCommand statusCommand = new StatusCommand("red")
        client.toBlocking().exchange(HttpRequest.POST("/status", statusCommand))
        HttpResponse response = client.toBlocking().exchange("/statusAndSpeed", Map.class)

        expect:
        response.status() == HttpStatus.OK
        response.body()["status"] == "red"
    }
}