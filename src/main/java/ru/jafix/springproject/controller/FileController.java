package ru.jafix.springproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.jafix.springproject.dto.common.StatusDto;
import ru.jafix.springproject.service.StorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;

    @PostMapping(value = "/alt", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public StatusDto uploadAlt(@RequestBody byte[] data,
                               @RequestHeader("X-Filename") String filename) {
        return storageService.upload(data, filename);
    }

    @PostMapping
    public StatusDto upload(@RequestParam("file") MultipartFile file) {
        return storageService.upload(file);
    }

    @PostMapping ("/multi-upload")
    public List<StatusDto> multiUpload(@RequestParam("files") MultipartFile[] files) {
        return storageService.multiUpload(files);
    }

    @GetMapping
    public ResponseEntity<Resource> download(@RequestParam("filename") String filename) throws MalformedURLException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(storageService.download(filename));
    }

    @GetMapping("/alt")
    public ResponseEntity<byte[]> downloadAlt(@RequestParam("filename") String filename) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(storageService.download(filename).getContentAsByteArray());
    }

}
