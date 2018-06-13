package ru.saturn;

import java.io.IOException;

/**
 * Created by Admin on 04.06.2018.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FileConventor fileConventor = new FileToFileConventor(System.getProperty("user.dir") + "\\Proba.epi");
        try {
            fileConventor.convert();
            System.out.println("Обработка файла завершена!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
