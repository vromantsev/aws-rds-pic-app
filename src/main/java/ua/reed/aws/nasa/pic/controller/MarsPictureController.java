package ua.reed.aws.nasa.pic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.reed.aws.nasa.pic.dto.CreatePicRequest;
import ua.reed.aws.nasa.pic.dto.CreatePicResponse;
import ua.reed.aws.nasa.pic.service.MarsPictureService;

@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class MarsPictureController {

    private final MarsPictureService marsPictureService;

    @GetMapping(value = "/largest", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getLargestPicture() {
        var picture = marsPictureService.getLargest();
        return ResponseEntity.ok()
                .body(picture.photo());
    }

    @PostMapping
    public ResponseEntity<CreatePicResponse> savePictures(@RequestBody final CreatePicRequest request) {
        var result = marsPictureService.save(request);
        return ResponseEntity.ok()
                .body(result);
    }
}
