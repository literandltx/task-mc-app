package com.literandltx.taskmcapp.service.dropbox;

import com.dropbox.core.v2.files.CreateFolderResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public interface DropboxService {
    InputStream downloadFile(final String filePath);

    FileMetadata uploadFile(final String filePath, final InputStream fileStream);

    CreateFolderResult createFolder(final String folderPath);

    FolderMetadata getFolderDetails(final String folderPath);

    FileMetadata getFileDetails(final String filePath);

    ListFolderResult listFolderContinue(final String cursor);

    void deleteFile(final String filePath);

    void deleteFolder(final String folderPath);
}
