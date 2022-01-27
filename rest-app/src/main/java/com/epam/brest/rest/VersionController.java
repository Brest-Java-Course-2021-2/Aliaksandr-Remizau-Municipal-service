package com.epam.brest.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * VersionController.
 */
@RestController
public class VersionController {
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(VersionController.class);
    /**
     * Field VERSION.
     */
    private final static String VERSION = "0.0.1";
    /**
     * Version.
     *
     * @return app version
     */
    @GetMapping(value = "/version")
    public String getVersion(){
        log.debug("getVersion()");
        return VERSION;
    }
}
