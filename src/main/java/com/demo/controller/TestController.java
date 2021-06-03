package com.demo.controller;

import com.demo.util.ImageRemarkUtil;
import com.demo.util.WordRemarkUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: ZhengTianLiang
 * @date: 2021/6/3  16:31
 * @desc: 测试word水印的控制器
 */

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/6/3  16:31
     * @desc: 测试word加水印
     */
    @PostMapping(value = "/word")
    public String remarkWord(MultipartFile file, HttpServletResponse response,String remark) {
        InputStream inputStream = null;
        Document document = new Document();
//        document.loadFromFile("测试.doc");

        try {
            inputStream = file.getInputStream();

            document.loadFromStream(inputStream, FileFormat.Doc);
            WordRemarkUtil.InsertTextWatermark(document.getSections().get(0),remark);
            response.reset();
//            response.setContentType("bin");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + "123originalFilename.docx" + "\"");
            ServletOutputStream outputStream = response.getOutputStream();

            //保存文档
//            document.saveToFile("test1.docx",FileFormat.Docx );
            document.saveToStream(outputStream,FileFormat.Docx);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/6/3  17:38
     * @desc: 测试图片加水印
     */
    @PostMapping(value = "/image")
    public String remarkImage(MultipartFile file, HttpServletResponse response,String remark) throws IOException {
        MultipartFile multipartFile = ImageRemarkUtil.addWorkMarkToMutipartFile(file, remark);
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        // 设置输出的格式
        response.reset();
//        response.setContentType("bin");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inputStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

}
