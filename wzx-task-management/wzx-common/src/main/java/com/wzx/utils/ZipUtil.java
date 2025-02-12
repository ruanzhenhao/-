package com.wzx.utils;

import com.wzx.dto.ZipFileDTO;
import com.wzx.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ZipUtil {

    public void downloadZipFile(HttpServletResponse response, ZipFileDTO fileDTO) {
        List<String> names = fileDTO.getFileNms();
        List<ByteArrayOutputStream> streams = fileDTO.getStreams();

        try {
            // 设置响应头
            String filename = URLEncoder.encode(fileDTO.getZipFileNm(), StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 创建 ZIP 输出流
            try (OutputStream output = response.getOutputStream();
                 ZipOutputStream zipStream = new ZipOutputStream(output)) {

                for (int i = 0; i < names.size(); i++) {
                    ByteArrayOutputStream stream = streams.get(i);
                    log.info("准备写入 ZIP 文件: {}", names.get(i));
                    log.info("流大小: {}", stream.size());

                    if (stream.size() > 0) {
                        ZipEntry zipEntry = new ZipEntry(names.get(i));
                        zipStream.putNextEntry(zipEntry);
                        stream.writeTo(zipStream);
                        zipStream.closeEntry();
                    } else {
                        log.warn("跳过空流: {}", names.get(i));
                    }
                }
                zipStream.finish();
            }
        } catch (IOException e) {
            log.error("I/O error while downloading zip file: {}", e.getMessage());
            throw new TaskException("文件下载失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while downloading zip file: {}", e.getMessage());
            throw new TaskException("文件下载失败: " + e.getMessage());
        }
    }
    // 读取文件到 ByteArrayOutputStream
    public ByteArrayOutputStream getByteArrayOutputStream(String filePath) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream inputStream = new FileInputStream(new File(filePath))) {
            byte[] buffer = new byte[8 * 1024]; // 8KB buffer
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            log.error("Error reading file {}: {}", filePath, e.getMessage());
            throw new TaskException("文件读取错误: " + e.getMessage());
        }
        return outputStream;
    }
}