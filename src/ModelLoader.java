

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;

public class ModelLoader {
    private final File userDirectory;

    public ModelLoader() {
        userDirectory = Config.getConfig("mainConfig").getProperty(File.class, "userDirectory");
        if (!userDirectory.exists()) userDirectory.mkdirs();
    }

    public void savePlayer(){

    }

    private File getUserFile(String name) {
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        if (d != null) {
            for (File dd : d) {
                if (dd.getName().equals(name)) {
                    return dd;
                }
            }
            return null;
        } else {
            return null;

        }
    }

}
