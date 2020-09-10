package enumerations;

/**
 * The enum contains all relevant fields which an be invalid during the validation in this application.
 */
public enum ValidatorErrorFields {

    CORRECT_DATA(""),

    ADDRESS("address"),
    ANGLE_FACTOR("angleFactor"),
    COMMENT("comment"),
    CONTENTS("contents"),
    CUSHIONING("cushioning"),
    DESCRIPTION("description"),
    FOREIGN_KEY_BALL("foreignKeyBall"),
    FOREIGN_KEY_SUIT_CASE("foreignKeySuitCase"),
    HARDNESS("hardness"),
    IDENTIFIER("identifier"),
    MARKING("marking"),
    OWNER("owner"),
    POSITIONING("positioning"),
    POST_CODE("postCode"),
    PRIMARY_KEY("primaryKey"),
    REMARK("remark"),
    SITE_NAME("siteName"),
    TOWN("town"),
    UP_THROW("upThrow"),
    WEIGHT("weight"),
    ;

    private final String fieldName;

    ValidatorErrorFields(String fieldName) {
        this.fieldName = fieldName;
    }
}
