package com.digitalcomanetwork.kosterme.fileuploadservice.shared;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class FileDto implements Serializable {
    private String fileId;
    private String fileName;
    private String fileType;
    private byte[] data;
}
