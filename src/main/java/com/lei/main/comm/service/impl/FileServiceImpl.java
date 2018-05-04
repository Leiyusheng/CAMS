package com.lei.main.comm.service.impl;

import com.lei.main.comm.bean.Message;
import com.lei.main.comm.service.FileService;
import com.lei.main.comm.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ServletContext servletContext;

    private final String rootPath;//tomcat根目录名

    public FileServiceImpl() {
        this.rootPath = "WEB-INF";
    }//todo apache-tomcat
    public FileServiceImpl(String rootPath) {
        this.rootPath = rootPath;
    }

    public Message<String> saveFile(String method, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                String s = UUID.randomUUID().toString();//随机生成图片url名
                String url = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24) + ".jpg";
                String catalog = File.separator + method + File.separator;
                String path0 = servletContext.getRealPath("WEB-INF");
                int index = path0.indexOf(rootPath);//查找tomcat根目录
                String path = path0.substring(0, index) + "pic" + catalog + url;//替换为图片资源目录
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(path));
                url = "pic" + catalog + url;
                return Common.messageBox(Common.success, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Common.messageBox(Common.failed);
    }
}
