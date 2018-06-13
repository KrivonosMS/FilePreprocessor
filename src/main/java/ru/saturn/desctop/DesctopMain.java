package ru.saturn.desctop;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Admin on 09.06.2018.
 */
public class DesctopMain {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> new ChooseFile());
    }
}
