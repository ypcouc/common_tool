package com.ypc.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * 获取文件夹下的文件名
     * @param path
     * @return
     */
    public static List<String> getLogFileAbsolutePath(String path) {
        if(StringUtils.isEmpty(path)){
            return null;
        }
        File file = new File(path);
        List<String> files = new ArrayList<String>();
        getFilePath(files,file);
        return files;
    }

    private static void getFilePath(List<String> list,File file){
        if(list == null || file == null){
            return;
        }
        try {
            File[] tempList = file.listFiles();
            if (tempList != null && tempList.length > 0) {
                for (int i = 0; i < tempList.length; i++) {
                    if (tempList[i].isFile()) {
                        list.add(new String(tempList[i].getAbsolutePath().getBytes("UTF-8")));
                    }else if (tempList[i].isDirectory()) {
                        getFilePath(list,tempList[i]);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按行读取文件内容 实现分页
     * @return
     */
    public static List<String> readFileLine(String filePath,Long startLine,Long size) {
        if(StringUtils.isEmpty(filePath)){
            return null;
        }
        if(startLine == null){
            startLine = 0L;
        }
        if(size == null){
            size = 30L;
        }
        File file = new File(filePath);
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int n = 1;
            int readNum = 1;
            String tempStr = null;
            while((tempStr = reader.readLine()) != null) {
                if (n++ >= startLine && readNum <= size) {
                    list.add(tempStr);
                    readNum++;
                }
                if(readNum > size){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
