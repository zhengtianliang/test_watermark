package com.demo.util;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.TextWatermark;
import com.spire.doc.documents.WatermarkLayout;

import java.awt.*;

/**
 * @author: ZhengTianLiang
 * @date: 2021/6/3  16:26
 * @desc: 文档的加水印  工具类
 */

public class WordRemarkUtil {

    public static void main(String[] args) {
        Document document = new Document();
        document.loadFromFile("测试.doc");

        //插入文本水印
        InsertTextWatermark(document.getSections().get(0),"要加的水印字体");

        //保存文档
        document.saveToFile("test1.docx",FileFormat.Docx );
    }

    //自定义方法指定文本水印字样，并设置成水印
    public static void InsertTextWatermark(Section section,String text){
        TextWatermark txtWatermark = new TextWatermark();
//        txtWatermark.setText("咔咔测试");
        txtWatermark.setText(text);
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);
        section.getDocument().setWatermark(txtWatermark);
    }
}
