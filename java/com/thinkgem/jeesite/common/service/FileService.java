package com.thinkgem.jeesite.common.service;


import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
@Component
public class FileService {

    //String curProjectPath = Global.getConfig("upLoadPath");
    public String[] upLoad( MultipartFile file) {
        String[] rt=new String[2];
        //String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;
        String filepath="/"+ DateUtils.getDate("yyyy-MM-dd")+"//"+ IdGen.uuid();
        String saveDirectoryPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL+filepath;
        FileUtils.createDirectory(saveDirectoryPath);
        File saveDirectory = new File(saveDirectoryPath);
        String fileName="";
        if (!file.isEmpty()) {
            fileName= file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            try {
                file.transferTo(new File(saveDirectory, fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        rt[0]="/"+filepath+"//"+fileName;
        rt[1]=fileName;
        return rt;
    }

}
