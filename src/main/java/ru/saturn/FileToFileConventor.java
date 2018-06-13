package ru.saturn;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Admin on 07.06.2018.
 */
public class FileToFileConventor implements FileConventor {
    private final String fileInPath;
    private final String fileOutPath;

    public FileToFileConventor(File file) {
        this(file.getPath());
    }

    public FileToFileConventor(String path) {
        this.fileInPath = path;
        this.fileOutPath = createPath();
        checkInputFile(path);
    }

    private String createPath() {
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_YYYY HH_mm_ss");
        String dir = System.getProperty("user.dir");
        return dir + "/" + "proba" + " " + format.format(new Date()) + ".epi";
    }

    private void checkInputFile(String path) {
        if (Files.notExists(Paths.get(path))) throw new IllegalArgumentException("Файл " + path + " не найден!");
    }

    @Override
    public void convert() throws Exception {
        List<String> lineList = Files.lines(Paths.get(fileInPath), StandardCharsets.UTF_8).collect(Collectors.toList());

        String in1_HighFlow = getConstValue(lineList, "In1_HighFlow");
        String in2_HighFlow = getConstValue(lineList, "In2_HighFlow");
        String dT = getConstValue(lineList, "dT");

        List<String> lineListNew = convert(lineList, in1_HighFlow, in2_HighFlow, dT);

        Files.write(Paths.get(fileOutPath), lineListNew, StandardCharsets.UTF_8);
    }

    private List<String> convert(List<String> lineList, String in1_HighFlow, String in2_HighFlow, String dT) {
        return lineList.stream()
                    .map(line -> line.trim())
                    .map(line -> line.replaceAll("(?<=[+-]\\s{0,10})In1_HighFlow", in1_HighFlow))
                    .map(line -> line.replaceAll("(?<=[+-])\\s{0,10}In2_HighFlow", in2_HighFlow))
                    .map(line -> line.replaceAll("(?<=[+-])\\s{0,10}dT", dT))
                    .map(line -> line.replaceAll("(?<=DETe_1\\.press\\s{0,10}to\\s{0,10}\\d{0,10},)", " DETe_1.inject follow  DETe_1.source, DETe_1.dilute to 0,"))
                    .map(line -> line.replaceAll("(?<=CBr4_1\\.press\\s{0,10}to\\s{0,10}\\d{0,10},)", " CBr4_1.inject follow  CBr4_1.source, CBr4_1.dilute to 0,"))
                    .map(line -> line.replaceAll("RunHydBot\\.feed1", "RunHydBot.source"))
                    .collect(Collectors.toList());

    }

    private String getConstValue(List<String> lineList, String constName) {
        return lineList.stream().parallel()
                    .filter(line -> line.contains(constName))
                    .map(line -> {Pattern p = Pattern.compile("(?<="+constName+"\\s{0,10}=\\s{0,10})\\d+");
                        Matcher m = p.matcher(line);
                        return (m.find() ? m.group(): "");})
                    .findFirst().get();
    }
}
