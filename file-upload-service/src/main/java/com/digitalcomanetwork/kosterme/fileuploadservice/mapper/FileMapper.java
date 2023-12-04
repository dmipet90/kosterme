package com.digitalcomanetwork.kosterme.fileuploadservice.mapper;

import com.digitalcomanetwork.kosterme.fileuploadservice.data.FileEntity;
import com.digitalcomanetwork.kosterme.fileuploadservice.shared.FileDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {
    FileEntity fileDtoFileEntity(FileDto fileDetails);
    FileDto fileEntityToFileDto(FileEntity fileEntity);
}
