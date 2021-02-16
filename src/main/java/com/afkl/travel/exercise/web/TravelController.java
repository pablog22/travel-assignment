package com.afkl.travel.exercise.web;

import com.afkl.travel.exercise.dto.LocationDto;
import com.afkl.travel.exercise.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("travel")
public class TravelController {

    private static final Logger log = LoggerFactory.getLogger(TravelController.class);

    private final LocationService locationService;

    public TravelController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations(
            @RequestHeader(value = "accept-language", defaultValue = "EN") String language
    ) {
        log.info("About to get locations");
        return locationService.getLocations(language);
    }

    @GetMapping("/locations/{type}/{code}")
    public LocationDto getLocation(
            @PathVariable String type,
            @PathVariable String code,
            @RequestHeader(value = "accept-language", defaultValue = "EN") String language
    ) {
        log.info("About to get location for type {} and code {}", type, code);
        return locationService.getLocationByTypeAndCode(type, code, language);
    }
}
