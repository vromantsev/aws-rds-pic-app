package ua.reed.aws.nasa.pic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.reed.aws.nasa.pic.config.AppProperties;
import ua.reed.aws.nasa.pic.dto.CreatePicRequest;
import ua.reed.aws.nasa.pic.dto.CreatePicResponse;
import ua.reed.aws.nasa.pic.dto.Photo;
import ua.reed.aws.nasa.pic.dto.Photos;
import ua.reed.aws.nasa.pic.dto.PicData;
import ua.reed.aws.nasa.pic.entity.Camera;
import ua.reed.aws.nasa.pic.entity.Picture;
import ua.reed.aws.nasa.pic.repository.CameraRepository;
import ua.reed.aws.nasa.pic.repository.PictureRepository;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleMarsPictureService implements MarsPictureService {

    private final AppProperties appProperties;
    private final PictureRepository pictureRepository;
    private final CameraRepository cameraRepository;
    private final RestTemplate restTemplate;

    @Transactional(readOnly = true)
    @Override
    public PicData getLargest() {
        Picture picture = this.pictureRepository.findByMaxSize();
        return new PicData(picture.getUrl(), picture.getSize(), restTemplate.getForObject(picture.getUrl(), byte[].class));
    }

    @Override
    public CreatePicResponse save(final CreatePicRequest request) {
        String url = buildUrl(request);
        Photos photos = restTemplate.getForObject(url, Photos.class);
        List<Picture> pictures = Objects.requireNonNull(photos).photos().parallelStream()
                .map(mapToPicture())
                .toList();
        Camera camera = cameraRepository.save(
                Camera.builder()
                        .name(request.camera())
                        .pictures(pictures)
                        .build()
        );
        return CreatePicResponse.of(
                HttpStatus.OK.value(),
                "Pictures successfully stored for sol=%d, camera=%s".formatted(request.sol(), camera.getName()),
                pictures.size()
        );
    }

    private Function<Photo, Picture> mapToPicture() {
        return photo -> {
            String imgSrc = photo.imgSrc();
            HttpHeaders headers = restTemplate.headForHeaders(imgSrc);
            URI location = Objects.requireNonNull(headers.getLocation());
            HttpHeaders picHeaders = restTemplate.headForHeaders(location);
            return Picture.builder()
                    .url(location.toString())
                    .size(picHeaders.getContentLength())
                    .build();
        };
    }

    private String buildUrl(final CreatePicRequest request) {
        return UriComponentsBuilder.fromHttpUrl(appProperties.getUrl())
                .queryParam(appProperties.getApiKeyHeader(), appProperties.getApiKey())
                .queryParam(appProperties.getSolHeader(), request.sol())
                .queryParamIfPresent(appProperties.getCameraHeader(), Optional.ofNullable(request.camera()))
                .build()
                .toUriString();
    }
}
