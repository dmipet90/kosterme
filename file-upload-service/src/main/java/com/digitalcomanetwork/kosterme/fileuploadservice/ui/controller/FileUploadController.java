package com.digitalcomanetwork.kosterme.fileuploadservice.ui.controller;

import com.digitalcomanetwork.kosterme.fileuploadservice.data.FileEntity;
import com.digitalcomanetwork.kosterme.fileuploadservice.mapper.FileMapper;
import com.digitalcomanetwork.kosterme.fileuploadservice.service.FileUploadService;
import com.digitalcomanetwork.kosterme.fileuploadservice.shared.FileDto;
import com.digitalcomanetwork.kosterme.fileuploadservice.ui.model.FileUploadResponseModel;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    @Autowired
    private Environment env;
    private final FileUploadService fileUploadService;
    private final FileMapper fileMapper;

    @GetMapping("/status/check")
    public String getStatus() {
        return "Working on port " + env.getProperty("local.server.port");
    }


    @PostMapping("/single/base")
    public ResponseEntity<FileUploadResponseModel> uploadFileToBase(@RequestParam("file") MultipartFile file) throws Exception {
        FileDto fileDetails = fileUploadService.saveAttachment(file);
        FileUploadResponseModel response = fileMapper.fileDtoToFileResponse(fileDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/multiple/base")
    public ResponseEntity<List<FileUploadResponseModel>> uploadMultipleFilesToBase(@RequestParam("files") MultipartFile[] files) throws Exception {
        List<FileDto> fileDetails = fileUploadService.saveFiles(files);
        List<FileUploadResponseModel> response = fileDetails.stream().map(fileMapper::fileDtoToFileResponse).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileUploadResponseModel>> getAllFiles() {
        List<FileDto> fileDetails = fileUploadService.getAllFiles();
        List<FileUploadResponseModel> response = fileDetails.stream().map(fileMapper::fileDtoToFileResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("id") String id) {
        FileDto fileDetails = fileUploadService.getFile(id);
        MediaType mediaType = MediaType.parseMediaType(fileDetails.getFileType());
        ByteArrayResource resource = new ByteArrayResource(fileDetails.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileDetails.getFileName())
                .contentType(mediaType) //
                .contentLength(fileDetails.getData().length)
                .body(resource);
    }
}
