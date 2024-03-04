package ua.reed.aws.nasa.pic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Photo(@JsonProperty("img_src") String imgSrc, Camera camera) {
}
