package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.dto.LocationDto;
import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.Translation;
import com.afkl.travel.exercise.repository.LocationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@Import(LocationService.class)
public class LocationServiceTest {

    private static final String TYPE_COUNTRY = "country";
    private static final String TYPE_CITY = "city";

    private static final String COUNTRY_CODE_NL = "NL";
    private static final String CITY_CODE_AMS = "AMS";

    private static final String LANGUAGE_EN = "EN";
    private static final String LANGUAGE_NL = "NL";

    private static final String COUNTRY_NAME_EN = "Netherlands";
    private static final String COUNTRY_NAME_NL = "Nederland";

    private static final String COUNTRY_DESCRIPTION_EN = "Netherlands (NL)";
    private static final String COUNTRY_DESCRIPTION_NL = "Nederland (NL)";

    private static final String CITY_NAME_EN = "Amsterdam";
    private static final String CITY_NAME_NL = "Amsterdam";

    private static final String CITY_DESCRIPTION_EN = "Amsterdam (AMS)";
    private static final String CITY_DESCRIPTION_NL = "Amsterdam (AMS)";

    private static final Double COUNTRY_LONGITUDE = 5.45d;
    private static final Double COUNTRY_LATITUDE = 52.3d;

    private static final Double CITY_LONGITUDE = 4.78417d;
    private static final Double CITY_LATITUDE = 52.31667d;

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @Test
    public void whenGetLocationByCountryLanguageEN_thenReturnsCountryWithDataEN() {
        Location location = getLocationCountry();

        Mockito.when(locationRepository.findByTypeAndCode(anyString(), anyString())).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.getLocationByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL, LANGUAGE_EN);

        assertCountry(locationDto, LANGUAGE_EN);

        Mockito.verify(locationRepository).findByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL);

    }

    @Test
    public void whenGetLocationByCountryLanguageNL_thenReturnsCountryWithDataNL() {
        Location location = getLocationCountry();

        Mockito.when(locationRepository.findByTypeAndCode(anyString(), anyString())).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.getLocationByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL, LANGUAGE_NL);

        assertCountry(locationDto, LANGUAGE_NL);

        Mockito.verify(locationRepository).findByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL);

    }

    @Test
    public void whenGetLocationByCountryLanguageAnyLanguage_thenReturnsCountryWithDataEN() {
        String anyLanguage = "XX";

        Location location = getLocationCountry();

        Mockito.when(locationRepository.findByTypeAndCode(anyString(), anyString())).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.getLocationByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL, anyLanguage);

        assertCountry(locationDto, LANGUAGE_EN);

        Mockito.verify(locationRepository).findByTypeAndCode(TYPE_COUNTRY, COUNTRY_CODE_NL);

    }

    private static void assertCountry(LocationDto locationDto, String language) {
        assertNotNull(locationDto);
        assertEquals(COUNTRY_CODE_NL, locationDto.getCode());
        assertEquals(TYPE_COUNTRY, locationDto.getType());

        assertEquals(COUNTRY_LONGITUDE, locationDto.getLongitude());
        assertEquals(COUNTRY_LATITUDE, locationDto.getLatitude());

        if(LANGUAGE_EN.equals(language)) {
            assertEquals(COUNTRY_NAME_EN, locationDto.getName());
            assertEquals(COUNTRY_DESCRIPTION_EN, locationDto.getDescription());
        } else {
            assertEquals(COUNTRY_NAME_NL, locationDto.getName());
            assertEquals(COUNTRY_DESCRIPTION_NL, locationDto.getDescription());
        }

        assertNull(locationDto.getParentCode());
        assertNull(locationDto.getParentType());
    }

    @Test
    public void whenGetLocationByCity_thenReturnsCityAndCountryData() {
        Location location = getLocationCity();

        Mockito.when(locationRepository.findByTypeAndCode(anyString(), anyString())).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.getLocationByTypeAndCode(TYPE_CITY, CITY_CODE_AMS, LANGUAGE_EN);

        assertNotNull(locationDto);
        assertEquals(CITY_CODE_AMS, locationDto.getCode());
        assertEquals(TYPE_CITY, locationDto.getType());

        assertEquals(CITY_LONGITUDE, locationDto.getLongitude());
        assertEquals(CITY_LATITUDE, locationDto.getLatitude());
        assertEquals(CITY_NAME_EN, locationDto.getName());
        assertEquals(CITY_DESCRIPTION_EN, locationDto.getDescription());

        assertEquals(COUNTRY_CODE_NL, locationDto.getParentCode());
        assertEquals(TYPE_COUNTRY, locationDto.getParentType());

        Mockito.verify(locationRepository).findByTypeAndCode(TYPE_CITY, CITY_CODE_AMS);

    }

    private Location getLocationCountry() {
        Location location = new Location();
        location.setCode(COUNTRY_CODE_NL);
        location.setType(TYPE_COUNTRY);
        location.setLongitude(COUNTRY_LONGITUDE);
        location.setLatitude(COUNTRY_LATITUDE);

        location.setTranslations(List.of(
                getTranslation(LANGUAGE_EN, COUNTRY_NAME_EN, COUNTRY_DESCRIPTION_EN),
                getTranslation(LANGUAGE_NL, COUNTRY_NAME_NL, COUNTRY_DESCRIPTION_NL)
        ));

        location.setParent(null);

        return location;
    }

    private Location getLocationCity() {
        Location location = new Location();
        location.setCode(CITY_CODE_AMS);
        location.setType(TYPE_CITY);
        location.setLongitude(CITY_LONGITUDE);
        location.setLatitude(CITY_LATITUDE);

        location.setTranslations(List.of(
                getTranslation(LANGUAGE_EN, CITY_NAME_EN, CITY_DESCRIPTION_EN),
                getTranslation(LANGUAGE_NL, CITY_NAME_NL, CITY_DESCRIPTION_NL)
        ));

        location.setParent(getLocationCountry());

        return location;
    }

    private Translation getTranslation(String language, String name, String description) {
        Translation translation = new Translation();
        translation.setLanguage(language);
        translation.setName(name);
        translation.setDescription(description);

        return translation;
    }

}
