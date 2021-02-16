package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.dto.LocationDto;
import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.Translation;
import com.afkl.travel.exercise.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDto> getLocations(String language) {
        log.info("Retrieving locations from repository");
        List<Location> locations = locationRepository.findAll();
        log.info("{} locations retrieved", locations.size());
        return locations
                .stream()
                .map(entity -> convertToDto(entity, language))
                .collect(Collectors.toList());
    }

    public LocationDto getLocationByTypeAndCode(String type, String code, String language) {
        log.info("Trying to get location for type {} and code {}.", type, code);
        Optional<Location> location = this.locationRepository.findByTypeAndCode(type, code);

        return location
                .map(entity -> convertToDto(entity, language))
                .orElseThrow(LocationNotFoundException::new);
    }

    private LocationDto convertToDto(Location entity, String language) {
        LocationDto dto = new LocationDto();
        dto.setCode(entity.getCode());
        dto.setType(entity.getType());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());

        Location parent = entity.getParent();
        if (parent != null) {
            dto.setParentCode(parent.getCode());
            dto.setParentType(parent.getType());
        }

        log.info("Looking for translation.");
        Optional<Translation> translation =
                Optional.ofNullable(TranslationHelper.getTranslationForLanguage(entity.getTranslations(), language));

        translation.ifPresent(t -> {
            dto.setName(t.getName());
            dto.setDescription(t.getDescription());
        });

        return dto;

    }
}
