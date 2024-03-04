package ua.reed.aws.nasa.pic.service;

import ua.reed.aws.nasa.pic.dto.CreatePicRequest;
import ua.reed.aws.nasa.pic.dto.CreatePicResponse;
import ua.reed.aws.nasa.pic.dto.PicData;

public interface MarsPictureService {

    PicData getLargest();

    CreatePicResponse save(final CreatePicRequest request);

}
