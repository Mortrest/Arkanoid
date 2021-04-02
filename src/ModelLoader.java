

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;

public class ModelLoader {
    private final File userDirectory;

    public ModelLoader() {
        userDirectory = Config.getConfig("mainConfig").getProperty(File.class, "userDirectory");
        if (!userDirectory.exists()) userDirectory.mkdirs();
    }

    public void savePlayer(String text, String name) {
        try {
            File file = getFile(name);
            if (file == null) {
                File player = new File(userDirectory.getPath() + "\\" + name + ".txt");
                PrintStream printStream = new PrintStream(player);
                printStream.println(text);
                printStream.close();
            } else {
                PrintStream printStream = new PrintStream(file);
                printStream.println(text);
                printStream.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String[] names(){
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        String[] str = new String[howManyFiles()];
        if (d != null) {
            for (int i = 0; i < str.length; i++) {
                str[i] = d[i].getName();
            }
        }
        return str;
    }

    public int howManyFiles() {
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        assert d != null;
        return d.length;
    }

    public String getUserFile(String name) throws IOException {
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        if (d != null) {
            for (File dd : d) {
                if (dd.getName().equals(name)) {
                    return Files.readString(dd.toPath());
                }
            }
            return null;
        }
        return null;
    }

    public File getFile(String name) throws IOException {
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        if (d != null) {
            for (File dd : d) {
                if (dd.getName().equals(name)) {
                    return dd;
                }
            }
            return null;
        }
        return null;
    }
}
