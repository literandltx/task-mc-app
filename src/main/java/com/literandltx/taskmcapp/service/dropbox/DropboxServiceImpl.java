package com.literandltx.taskmcapp.service.dropbox;

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
    public InputStream downloadFile(final String filePath) {
        return handleDropboxAction(() -> client.files().download(filePath).getInputStream(),
                String.format("Error downloading file: %s", filePath));
    }

    @Override
    public FileMetadata uploadFile(final String filePath, final InputStream fileStream) {
        return handleDropboxAction(() -> client.files().uploadBuilder(filePath)
                        .uploadAndFinish(fileStream),
                String.format("Error uploading file: %s", filePath));
    }

    @Override
    public CreateFolderResult createFolder(final String folderPath) {
        return handleDropboxAction(() -> client.files().createFolderV2(folderPath),
                "Error creating folder");
    }

    @Override
    public FolderMetadata getFolderDetails(final String folderPath) {
        return getMetadata(folderPath, FolderMetadata.class,
                String.format("Error getting folder details: %s", folderPath));
    }

    @Override
    public FileMetadata getFileDetails(final String filePath) {
        return getMetadata(filePath, FileMetadata.class,
                String.format("Error getting file details: %s", filePath));
    }

    @Override
    public ListFolderResult listFolderContinue(final String cursor) {
        return handleDropboxAction(() -> client.files().listFolderContinue(cursor),
                "Error listing folder");
    }

    @Override
    public void deleteFile(final String filePath) {
        handleDropboxAction(() -> client.files().deleteV2(filePath),
                String.format("Error deleting file: %s", filePath));
    }

    @Override
    public void deleteFolder(final String folderPath) {
        handleDropboxAction(() -> client.files().deleteV2(folderPath),
                String.format("Error deleting folder: %s", folderPath));
    }

    @SuppressWarnings("unchecked")
    private <T> T getMetadata(final String path, final Class<T> type, final String message) {
        final Metadata metadata = handleDropboxAction(() -> client.files().getMetadata(path),
                String.format("Error accessing details of: %s", path));

        checkIfMetadataIsInstanceOfGivenType(metadata, type, message);
        return (T) metadata;
    }

    private <T> void checkIfMetadataIsInstanceOfGivenType(
            final Metadata metadata,
            final Class<T> validType,
            final String exceptionMessage
    ) {
        if (!validType.isInstance(metadata)) {
            throw new DropboxException(exceptionMessage);
        }
    }

    private <T> T handleDropboxAction(
            final DropboxActionResolver<T> action,
            final String exceptionMessage
    ) {
        try {
            return action.perform();
        } catch (Exception e) {
            throw new DropboxException(String
                    .format("%s with cause: %s", exceptionMessage, e.getMessage()), e);
        }
    }
}
