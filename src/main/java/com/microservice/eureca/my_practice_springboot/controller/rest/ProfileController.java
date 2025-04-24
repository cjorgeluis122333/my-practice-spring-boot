package com.microservice.eureca.my_practice_springboot.controller.rest;

import com.microservice.eureca.my_practice_springboot.common.utili.PictureHandler;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;
import com.microservice.eureca.my_practice_springboot.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    UserService userService;


    @PostMapping("/picture")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        if (!file.isEmpty()) {

            try {
                //Name of the picture to save
                String fileName = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename().replace(" ", ""));

                var getUser = userService.findByUsername(authentication.getName());

                //Save the picture in the path
                PictureHandler.saveFile(file, fileName, "profile_img");

                //If the user have a picture delete his before
                if (getUser.getPicture() != null && getUser.getPicture().length() > 2)
                    PictureHandler.deleteFile("profile_img", getUser.getPicture());

                //Insert a new picture
                getUser.setPicture(fileName);
                var afterUpdate = userService.updateUser(getUser);

                response.put("user", afterUpdate);
                response.put("message", "The picture was upload correctly");

            } catch (UserNotFoundException e) {
                response.put("message", "The user was not fount");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            } catch (IOException e) {
                response.put("message", "Error to upload the client img");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "The image is null");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/picture/{pictureName:.+}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Resource> downloadPicture(@PathVariable String pictureName) {
        Path path = Paths.get("profile_img").resolve(pictureName).toAbsolutePath();
        Resource resource = null;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Error we can not load the image " + pictureName);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(  //attachment: make you download the file
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\""
        );

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
