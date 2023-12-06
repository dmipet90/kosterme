package com.digitalcomanetwork.kosterme.fileuploadservice.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String fileId;
    @Column(nullable = false, length = 50)
    private String fileName;
    @Column(nullable = false, length = 50)
    private String fileType;
    @Column(nullable = false)
    private long fileSize;
    @Lob
    private byte[] data;

    public FileEntity(String fileId, String fileName, String fileType, long fileSize, byte[] data) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.data = data;
    }
}
