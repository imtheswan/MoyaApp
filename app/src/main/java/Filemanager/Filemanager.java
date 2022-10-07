package Filemanager;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Scanner;

public class Filemanager {
    File file;
    Boolean existance = false;
    Exception error;

    public void lastError() {
        error.printStackTrace();
    }

    public Boolean accesFile(File dataDir, String name) {
        try {
            file = new File(dataDir, name);
            existance = true;
            if (file.exists()) {
                return true;
            } else {
                if (!file.createNewFile()) {
                    existance = false;
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            existance = false;
            error = e;
            return null;
        }
    }

    public String readPlainText() {
        try {
            if (existance) {
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
        if (existance) {
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
        return "";
    }

    public Boolean writeByteStream(){
        return null;
    }
}
