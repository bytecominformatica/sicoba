package br.com.clairtonluz.sicoba.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static void downloadFile(String filepath, OutputStream outputStream) {
        try {
            Path path = Paths.get(filepath);
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao tentar baixar o arquivo " + filepath, e);
        }
    }

    public static File writeInDisk(InputStream in, String path) {
        try {
            File targetFile = new File(path);
            targetFile.mkdirs();
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(in);
            return targetFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] zipFiles(List<File> files) {
        try {
            //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            for (File file : files) {
                //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    IOUtils.copy(fileInputStream, zipOutputStream);
                }
                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
