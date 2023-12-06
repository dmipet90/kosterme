package com.digitalcomanetwork.kosterme.fileuploadservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FileUploadResponseModel {
    private String fileId;
    private String fileName;
    private String fileType;
    private long fileSize;
}
