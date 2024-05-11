/*
 * This is just a sample custom actuator for future reference
 */

package com.northcastle.spring.jobs.monitor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom")
public class SampleActuator {

	// .../custom?text=SomeText
    @ReadOperation
    public Map<String, String> customEndpoint(String text){
        Map<String, String> map = new HashMap<>();
        map.put("text", text);
        return map;
    }

}
