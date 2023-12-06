package com.digitalcomanetwork.kosterme.fileuploadservice.service;

import com.digitalcomanetwork.kosterme.fileuploadservice.config.GatewayProperties;
import com.digitalcomanetwork.kosterme.fileuploadservice.data.FileEntity;
import com.digitalcomanetwork.kosterme.fileuploadservice.data.FileRepository;
import com.digitalcomanetwork.kosterme.fileuploadservice.mapper.FileMapper;
import com.digitalcomanetwork.kosterme.fileuploadservice.shared.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final GatewayProperties gatewayProperties;

    @Override
    public FileDto saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileId = UUID.randomUUID().toString();
        try {
            if(fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            if (file.getBytes().length > (1024 * 1024)) {
                throw new Exception("File size exceeds maximum limit");
            }
            FileEntity attachment = new FileEntity(fileId,
                    fileName, file.getContentType(), file.getSize(), file.getBytes());
            fileRepository.save(attachment);
            return fileMapper.fileEntityToFileDto(attachment);
        } catch (MaxUploadSizeExceededException e) {
            throw new MaxUploadSizeExceededException(file.getSize());
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public List<FileDto> saveFiles(MultipartFile[] files) {
        List<FileDto> fileList = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            try {
                fileList.add(saveAttachment(file));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return fileList;
    }

    @Override
    public List<FileDto> getAllFiles() {
        List<FileEntity> files = fileRepository.findAll();
        return files.stream().map(fileMapper::fileEntityToFileDto).toList();
    }

    @Override
    public FileDto getFile(String fileId) {
        FileEntity file = fileRepository.findByFileId(fileId);
        return fileMapper.fileEntityToFileDto(file);
    }
}
