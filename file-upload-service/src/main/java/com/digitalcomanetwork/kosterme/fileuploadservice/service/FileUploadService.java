package com.digitalcomanetwork.kosterme.fileuploadservice.service;

import com.digitalcomanetwork.kosterme.fileuploadservice.shared.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileDto saveAttachment(MultipartFile file) throws Exception;
    List<FileDto> saveFiles(MultipartFile[] files) throws Exception;
    List<FileDto> getAllFiles();
    FileDto getFile(String fileId);
}
