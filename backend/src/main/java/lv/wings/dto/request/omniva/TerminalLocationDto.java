package lv.wings.dto.request.omniva;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerminalLocationDto {

    @JsonProperty("ZIP")
    private String zip;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("TYPE")
    private String type;

    @JsonProperty("A0_NAME")
    private String a0Name;

    @JsonProperty("A1_NAME")
    private String a1Name;

    @JsonProperty("A2_NAME")
    private String a2Name;

    @JsonProperty("A3_NAME")
    private String a3Name;

    @JsonProperty("A4_NAME")
    private String a4Name;

    @JsonProperty("A5_NAME")
    private String a5Name;

    @JsonProperty("A6_NAME")
    private String a6Name;

    @JsonProperty("A7_NAME")
    private String a7Name;

    @JsonProperty("A8_NAME")
    private String a8Name;

    @JsonProperty("X_COORDINATE")
    private String xCoordinate;

    @JsonProperty("Y_COORDINATE")
    private String yCoordinate;

    @JsonProperty("SERVICE_HOURS")
    private String serviceHours;

    @JsonProperty("TEMP_SERVICE_HOURS")
    private String tempServiceHours;

    @JsonProperty("TEMP_SERVICE_HOURS_UNTIL")
    private String tempServiceHoursUntil;

    @JsonProperty("TEMP_SERVICE_HOURS_2")
    private String tempServiceHours2;

    @JsonProperty("TEMP_SERVICE_HOURS_2_UNTIL")
    private String tempServiceHours2Until;

    @JsonProperty("comment_est")
    private String commentEst;

    @JsonProperty("comment_eng")
    private String commentEng;

    @JsonProperty("comment_rus")
    private String commentRus;

    @JsonProperty("comment_lav")
    private String commentLav;

    @JsonProperty("comment_lit")
    private String commentLit;

    @JsonProperty("MODIFIED")
    private String modified;
}

