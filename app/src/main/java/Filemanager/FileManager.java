package Filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileManager {
    File file;
    Boolean existence = false;
    Exception error;

    public void lastError() {
        error.printStackTrace();
    }

    public Boolean accessFile(File dataDir, String name) {
        try {
            file = new File(dataDir, name);
            existence = true;
            if (file.exists()) {
                return true;
            } else {
                if (!file.createNewFile()) {
                    existence = false;
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            existence = false;
            error = e;
            return null;
        }
    }

    public String readPlainText() {
        try {
            if (existence) {
                if (file.canRead()) {
                    Scanner scanner = new Scanner(file);
                    StringBuilder build = new StringBuilder("");
                    while (scanner.hasNextLine()) {
                        build.append(scanner.nextLine());
                    }
                    return build.toString();
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (FileNotFoundException e) {
            error = e;
        }
        return "";
    }

    public Boolean writePlainText(String text) {
        if (existence) {
            try {
                FileWriter typer = new FileWriter(file);
                typer.write(text);
                typer.close();
                return Boolean.TRUE;
            } catch (Exception e) {
                error = e;
                return null;
            }
        } else
            return Boolean.FALSE;
    }

    public String readByteStream(){
        if(existence){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                StringBuilder builder = new StringBuilder("");
                int ch = 0;
                ch = fileInputStream.read();
                while (ch != -1){
                    builder.append((char) ch);
                    ch = fileInputStream.read();
                }
                fileInputStream.close();
                return builder.toString();
            } catch (Exception e) {
                error = e;
                return "";
            }
        }
        return "";
    }

    public Boolean writeByteStream(String text){
        if(existence){
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                return Boolean.TRUE;
            } catch (Exception e) {
                error = e;
                return null;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean deleteFile(){
        Boolean status = Boolean.FALSE;
        if(existence){
            try{
                 status = file.delete();
            }
            catch (Exception e){
                error = e;
                return null;
            }
        }
        if(status){
            existence = Boolean.FALSE;
        }
        return status;
    }
}
