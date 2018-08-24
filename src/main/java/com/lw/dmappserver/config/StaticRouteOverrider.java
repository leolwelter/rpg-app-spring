package com.lw.dmappserver.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* quick hack to enable Angular static path location strategy */
@Controller
public class StaticRouteOverrider {

    @RequestMapping(method=RequestMethod.GET , value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }
}
