package ua.reed.aws.nasa.pic.dto;

public record CreatePicResponse(int status, String msg, long storedPicsCount) {

    public static CreatePicResponse of(int status, String msg, long storedPicsCount) {
        return new CreatePicResponse(status, msg, storedPicsCount);
    }
}
