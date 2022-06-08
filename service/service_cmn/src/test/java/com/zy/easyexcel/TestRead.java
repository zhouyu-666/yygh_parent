package com.zy.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //读取的路径
        String fileName="D:\\yygh_log\\excel\\01.xlsx";
        //调用方法
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
