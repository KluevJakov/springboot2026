package ru.jafix.springproject.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.jafix.springproject.dto.common.Status;
import ru.jafix.springproject.dto.common.StatusDto;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {

    public List<StatusDto> multiUpload(MultipartFile[] files) {
        List<StatusDto> statuses = new ArrayList<>();
        for(MultipartFile mf : files) {
            statuses.add(upload(mf));
        }
        return statuses;
    }

    public StatusDto upload(byte[] data, String filename) {
        try {
            Path path = Paths.get("uploads");

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            filename = URLDecoder.decode(filename, StandardCharsets.UTF_8);
            path = path.resolve(filename);

            Files.write(path, data);
        } catch (Exception e) {
            return StatusDto.builder()
                    .status(Status.ERROR)
                    .message(e.getMessage())
                    .build();
        }

        return StatusDto.builder()
                .status(Status.SUCCESS)
                .build();
    }

    public StatusDto upload(MultipartFile file) {

        try {
            Path path = Paths.get("uploads");

            if (file.getSize() > 1_048_576L) {
                throw new IllegalArgumentException("Размер файла превышает 1 МБ");
            }

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            path = path.resolve(file.getOriginalFilename());

            //Files.copy(file.getInputStream(), path);
            Files.write(path, file.getBytes());
        } catch (Exception e) {
            return StatusDto.builder()
                    .status(Status.ERROR)
                    .message(e.getMessage())
                    .build();
        }

        return StatusDto.builder()
                .status(Status.SUCCESS)
                .build();
    }

    public Resource download(String filename) throws MalformedURLException {
        Path path = Paths.get("uploads").resolve(filename);

        return new UrlResource(path.toUri());
    }

}
