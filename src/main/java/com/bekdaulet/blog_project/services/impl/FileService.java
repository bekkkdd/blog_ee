package com.bekdaulet.blog_project.services.impl;

import com.bekdaulet.blog_project.models.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileService {

    @Value("${file.post.images.dir}")
    private String postImgsDir;

    public boolean uploadPost(MultipartFile file, Post post) {
        if(file != null && file.getContentType().startsWith("image")) {
            try {
                byte bytes[] = file.getBytes();
                File directory = new File(postImgsDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(postImgsDir + "post-" + post.getId() + ".jpg"));
                bos.write(bytes);
                bos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
