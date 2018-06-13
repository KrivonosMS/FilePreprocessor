package ru.saturn.desctop;

import ru.saturn.FileToFileConventor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Admin on 09.06.2018.
 */
public class ChooseFile extends JFrame {
    public ChooseFile() throws HeadlessException {
        super("Укажите файл для преобразования!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel();
        label.setAlignmentX(CENTER_ALIGNMENT);

        JButton button = new JButton("Выбрать файл для преобразования");
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.addActionListener((ActionEvent event) -> {
            JFileChooser fileChooser = new JFileChooser( System.getProperty("user.dir"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setDialogTitle("Выбрать файл");
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            fileChooser.setApproveButtonText("Выбрать");
            fileChooser.setFileFilter(new EpiFileFilter(".epi", ""));
            fileChooser.showOpenDialog(this);
            File file = fileChooser.getSelectedFile();

            try {
                new FileToFileConventor(file).convert();
                label.setText("Файл преобразован");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                label.setText("Возникла непредвиденная ошибка, обратитесь к разработчику.");
                e.printStackTrace();
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(button);
        panel.add(label);
        panel.add(Box.createVerticalGlue());

        add(panel);
        setPreferredSize(new Dimension(400, 200));
        pack();
        setVisible(true);
    }
}
