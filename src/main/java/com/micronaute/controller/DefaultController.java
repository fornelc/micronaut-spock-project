package com.micronaute.controller;

import com.micronaute.SpeedCommand;
import com.micronaute.StatusCommand;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;

import java.util.Map;

@Controller("/")
public class DefaultController {

    private String status = "green";
    private int speed = 0;
    private Boolean gimme = false;

    @Get(uri="/", produces= MediaType.TEXT_HTML)
    @View("index")
    public ModelAndView index() {
        return new ModelAndView("index", CollectionUtils.mapOf());
    }

    @Get(value = "/status", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> getStatus() {
        this.gimme = true;
        return HttpResponse.ok(
                CollectionUtils.mapOf(
                        "status", status
                )
        );
    }

    @Get(value = "/statusAndSpeed", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> getStatusAndSpeed() {
        this.gimme = true;
        return HttpResponse.ok(
                CollectionUtils.mapOf(
                        "status", status,
                        "speed", speed
                )
        );
    }

    @Post(value = "speed", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> auto(SpeedCommand autoCommand) {

        speed = autoCommand.getSpeed();
        return HttpResponse.ok(
                CollectionUtils.mapOf("status", true)
        );
    }

    @Post(value = "status", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> auto(StatusCommand whatToDoCommand) {
        status = whatToDoCommand.getStatus();
        Map gimmeMap = CollectionUtils.mapOf("gimme", this.gimme);
        gimme = false;
        return HttpResponse.ok(gimmeMap);
    }

}