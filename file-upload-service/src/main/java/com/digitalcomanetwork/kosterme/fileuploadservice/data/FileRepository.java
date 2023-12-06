package com.digitalcomanetwork.kosterme.fileuploadservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFileId(String fileId);
}
