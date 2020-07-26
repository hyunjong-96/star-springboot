package com.hyunjong.book.springboot.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class UploadVO {
    private String id;
    private String signal;
    private MultipartFile file1;
}
