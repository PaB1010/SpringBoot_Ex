package org.koreait.file.controllers;

import lombok.extern.slf4j.Slf4j;
import org.koreait.etc.Greet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * File Upload 처리 Controller
 * @return
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private Greet greet;

    // application.yml 설정값을 주입
    @Value("${file.upload.path}")
    private String filePath;


    @GetMapping("/upload")
    public String upload() {

        System.out.println(greet.getName());

        return "file/upload";
    }

    @ResponseBody
    @PostMapping("/upload")
    public void uploadPs(@RequestPart("file") MultipartFile _file, @RequestPart("file2") MultipartFile file2) {

        // log.info(_file.toString());

        // <input .. name = '이름'
        log.info("getName() : {}", _file.getName());

        // Upload한 File 실제 이름
        log.info("getOriginalFilename() : {}", _file.getOriginalFilename());

        // File 용량(Byte 단위)
        log.info("getSize() : {}", _file.getSize());

        // File Upload할 경로
        File uploadPath = new File(filePath + _file.getOriginalFilename());

        try {
            // 임시 저장 공간에 있는 File -> 지정한 Server 경로로 이동
            _file.transferTo(uploadPath);
        } catch (IOException e) {}

        log.info("file2 : {}", file2.getOriginalFilename());
    }

    @ResponseBody
    @PostMapping("/upload2")
    public void uploadPs2(@RequestPart("file") MultipartFile[] files) {

        for (MultipartFile file : files) {
            try {
                file.transferTo(new File(filePath + file.getOriginalFilename()));
            } catch (IOException e) {}
        }
    }
}