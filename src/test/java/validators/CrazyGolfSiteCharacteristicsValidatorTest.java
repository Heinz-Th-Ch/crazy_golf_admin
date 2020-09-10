package validators;

import abstracts.AbstractPlainJava;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link CrazyGolfSiteCharacteristicsValidator}.
 */
public class CrazyGolfSiteCharacteristicsValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_PRIMARY_KEY = 1;
    private static final String CORRECT_SITE_NAME = "value";
    private static final String CORRECT_ADDRESS = "value";
    private static final String CORRECT_POST_CODE = "8200";
    private static final String CORRECT_TOWN = "value";

    private final CrazyGolfSiteCharacteristicsValidator validator = new CrazyGolfSiteCharacteristicsValidator();

    private List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    private CrazyGolfSiteCharacteristicsImpl characteristics;

    @Test
    public void validateAllFieldAreOkay() {
        characteristics = new CrazyGolfSiteCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_SITE_NAME,
                CORRECT_ADDRESS,
                CORRECT_POST_CODE,
                CORRECT_TOWN);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkay() {
        characteristics = new CrazyGolfSiteCharacteristicsImpl(-1,
                "",
                "",
                "",
                "");
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                5,
                results.size());
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
        assertEquals("wrong error code received for site name",
                Pair.of(EMPTY_FIELD, SITE_NAME),
                results.get(1));
        assertEquals("wrong error code received for address",
                Pair.of(EMPTY_FIELD, ADDRESS),
                results.get(2));
        assertEquals("wrong error code received for post code",
                Pair.of(EMPTY_FIELD, POST_CODE),
                results.get(3));
        assertEquals("wrong error code received for town",
                Pair.of(EMPTY_FIELD, TOWN),
                results.get(4));
    }

    @Test
    public void validatePostCodeIsNull() {
        validator.validatePostCode(results,
                null);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, POST_CODE),
                results.get(0));
    }

    @Test
    public void validatePostCodeIsEmpty() {
        validator.validatePostCode(results,
                "");
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(EMPTY_FIELD, POST_CODE),
                results.get(0));
    }

    @Test
    public void validatePostCodeIsOkayAsNumeric() {
        validator.validatePostCode(results,
                "8200");
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validatePostCodeIsOkayAsFullyQualified() {
        validator.validatePostCode(results,
                "CH-8200");
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validatePostCodeIsNotOkayAsFullyQualifiedCountryCodeFalseNumberOfParts() {
        validator.validatePostCode(results,
                "CH-8200-01");
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_POST_CODE_SYNTAX, POST_CODE),
                results.get(0));
    }

    @Test
    public void validatePostCodeIsNotOkayAsFullyQualifiedCountryCodeFalseCountryCode() {
        validator.validatePostCode(results,
                "??-8200");
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_COUNTRY_CODE, POST_CODE),
                results.get(0));
    }

    @Test
    public void validatePostCodeIsNotOkayAsFullyQualifiedCountryCodeFalsePostCode() {
        validator.validatePostCode(results,
                "CH-82A0");
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_POST_CODE, POST_CODE),
                results.get(0));
    }

}