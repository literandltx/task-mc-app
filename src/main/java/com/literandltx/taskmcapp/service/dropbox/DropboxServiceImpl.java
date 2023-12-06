package com.literandltx.taskmcapp.service.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CreateFolderResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.literandltx.taskmcapp.exception.custom.DropboxException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class DropboxServiceImpl implements DropboxService {
    private final DbxClientV2 client;

    @Override
    public InputStream downloadFile(String filePath) {
        try {
            ListFolderResult result = client.files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    System.out.println(metadata.getPathLower());
                }

                if (!result.getHasMore()) {
                    break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException e) {
            throw new DropboxException("Cannot download file: " + filePath);
        }

        return handleDropboxAction(() -> client.files().download(filePath).getInputStream(),
                String.format("Error downloading file: %s", filePath));
    }

    @Override
    public FileMetadata uploadFile(String filePath, InputStream fileStream) {
        return handleDropboxAction(() -> client.files().uploadBuilder(filePath)
                        .uploadAndFinish(fileStream),
                String.format("Error uploading file: %s", filePath));
    }

    @Override
    public CreateFolderResult createFolder(String folderPath) {
        return handleDropboxAction(() -> client.files().createFolderV2(folderPath),
                "Error creating folder");
    }

    @Override
    public FolderMetadata getFolderDetails(String folderPath) {
        return getMetadata(folderPath, FolderMetadata.class,
                String.format("Error getting folder details: %s", folderPath));
    }

    @Override
    public FileMetadata getFileDetails(String filePath) {
        return getMetadata(filePath, FileMetadata.class,
                String.format("Error getting file details: %s", filePath));
    }

    @Override
    public ListFolderResult listFolderContinue(String cursor) {
        return handleDropboxAction(() -> client.files().listFolderContinue(cursor),
                "Error listing folder");
    }

    @Override
    public void deleteFile(String filePath) {
        handleDropboxAction(() -> client.files().deleteV2(filePath),
                String.format("Error deleting file: %s", filePath));
    }

    @Override
    public void deleteFolder(String folderPath) {
        handleDropboxAction(() -> client.files().deleteV2(folderPath),
                String.format("Error deleting folder: %s", folderPath));
    }

    @SuppressWarnings("unchecked")
    private <T> T getMetadata(String path, Class<T> type, String message) {
        Metadata metadata = handleDropboxAction(() -> client.files().getMetadata(path),
                String.format("Error accessing details of: %s", path));

        checkIfMetadataIsInstanceOfGivenType(metadata, type, message);
        return (T) metadata;
    }

    private <T> void checkIfMetadataIsInstanceOfGivenType(
            Metadata metadata,
            Class<T> validType,
            String exceptionMessage
    ) {
        boolean isValidType = validType.isInstance(metadata);
        if (!isValidType) {
            throw new DropboxException(exceptionMessage);
        }
    }

    private <T> T handleDropboxAction(DropboxActionResolver<T> action, String exceptionMessage) {
        try {
            return action.perform();
        } catch (Exception e) {
            throw new DropboxException(String
                    .format("%s with cause: %s", exceptionMessage, e.getMessage()), e);
        }
    }
}
