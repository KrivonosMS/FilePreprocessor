package ru.saturn.desctop;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Admin on 11.06.2018.
 */
public class EpiFileFilter extends FileFilter {
    private final String extension;
    private final String description;

    public EpiFileFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            } else {
                return file.getName().endsWith(extension);
            }
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}
