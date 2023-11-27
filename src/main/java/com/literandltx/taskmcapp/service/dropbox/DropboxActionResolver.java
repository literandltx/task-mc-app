package com.literandltx.taskmcapp.service.dropbox;

@FunctionalInterface
interface DropboxActionResolver<T> {

    T perform() throws Exception;

}
