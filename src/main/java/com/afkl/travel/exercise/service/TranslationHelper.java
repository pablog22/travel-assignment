package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.model.Translation;

import java.util.List;

public class TranslationHelper {

    public static final String DEFAULT_LANGUAGE = "EN";

    ///////////////////////////////////////////////////
    // I know that the languages EN and NL are not the specified in
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language
    // I'm doing this this way due to time constraints

    /**
     *
     * @param translations the transactions of a location
     * @param language the language for the traslation
     * @return a transaction that match the language passed a param or the transaction of the default language
     */
    public static Translation getTranslationForLanguage(List<Translation> translations, String language) {
        Translation defaultTranslation = null;
        for (Translation translation : translations) {
            if (language.equals(translation.getLanguage())) {
                return translation;
            } else if (DEFAULT_LANGUAGE.equals(translation.getLanguage())) {
                defaultTranslation = translation;
            }
        }

        return defaultTranslation;
    }
}
