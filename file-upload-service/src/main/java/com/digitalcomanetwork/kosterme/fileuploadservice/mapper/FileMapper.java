package com.digitalcomanetwork.kosterme.fileuploadservice.mapper;

import com.digitalcomanetwork.kosterme.fileuploadservice.data.FileEntity;
import com.digitalcomanetwork.kosterme.fileuploadservice.shared.FileDto;
import com.digitalcomanetwork.kosterme.fileuploadservice.ui.model.FileUploadResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {
    FileUploadResponseModel fileDtoToFileResponse(FileDto fileDetails);
    FileDto fileEntityToFileDto(FileEntity fileEntity);
}
