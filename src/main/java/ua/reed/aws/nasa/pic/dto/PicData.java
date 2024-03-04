package ua.reed.aws.nasa.pic.dto;

public record PicData(String url, long contentLength, byte[] photo) {
}
