package com.microservice.eureca.my_practice_springboot.common.utili;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PictureHandler {
    /**
     * This methode received the file and form him take the (OriginalFilename).
     * After concat the FileName to the absolute path and then save the file like a InputString
     *
     * @param file:         The picture you want save
     * @param absolutePath: The path can be relative or absolute
     * @throws IOException
     */
    public static void saveFile(MultipartFile file, String absolutePath) throws IOException {
        String fileName = file.getOriginalFilename();
        Path fileRoute = Paths.get(absolutePath).resolve(fileName).toAbsolutePath();

        LoggerMessage.showMessage(PictureHandler.class, fileRoute.toString());
        //Save the file in the route
        Files.copy(file.getInputStream(), fileRoute);
    }
    /**
     * This methode received the file but not get from him the (OriginalFilename) because are in the param.
     * After concat the FileName to the absolute path and then save the file like a InputString
     * @param fileName: The file name(Optional because you can get from the file)
     * @param file: The picture you want save
     * @param path: The path can be relative or absolute
     * @throws IOException
     */
    public static void saveFile(MultipartFile file, String fileName, String path) throws IOException {
        Path fileRoute = Paths.get(path).resolve(fileName).toAbsolutePath();

        LoggerMessage.showMessage(PictureHandler.class, fileRoute.toAbsolutePath().toString());
        //Save the file in the route
        Files.copy(file.getInputStream(), fileRoute);
    }

    /**
     * This methode received the path and the fileName  and check if coincided with any file of the rout if that the case delete
     * @param path
     * @param fileName
     * @return true if was deleted, false if not was found
     */
    public static boolean deleteFile(String path, String fileName) {
        //Of this {path} search one file with this {fileName}
        Path beforeRoute = Paths.get(path).resolve(fileName).toAbsolutePath();
        LoggerMessage.showMessage(PictureHandler.class, beforeRoute.toString());

        File beforeFile = beforeRoute.toFile();
        if (beforeFile.exists() && beforeFile.canRead()) {
            return beforeFile.delete();
        }
        return false;
    }

}
