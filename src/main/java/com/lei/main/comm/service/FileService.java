package com.lei.main.comm.service;

import com.lei.main.comm.bean.Message;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Message<String> saveFile(String method, MultipartFile file) throws Exception;
}
