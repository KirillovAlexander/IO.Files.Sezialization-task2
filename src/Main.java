import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static final String SAVE_PATH = "F://Netology.Games//savegames";

    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 9, 80, 42.18);
        GameProgress progress2 = new GameProgress(50, 7, 90, 120.01);
        GameProgress progress3 = new GameProgress(10, 12, 110, 45.99);

        saveGame(progress1, "//save1.dat");
        saveGame(progress2, "//save2.dat");
        saveGame(progress3, "//save3.dat");

        List<String> savesPath = new ArrayList<>();
        savesPath.add(SAVE_PATH + "//save1.dat");
        savesPath.add(SAVE_PATH + "//save2.dat");
        savesPath.add(SAVE_PATH + "//save3.dat");

        zipFiles("F://Netology.Games//savegames", savesPath);
    }

    private static void saveGame(GameProgress progress, String path) {
        try (FileOutputStream fos = new FileOutputStream(SAVE_PATH + path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipPath, List<String> savesPath) {
        //Создадим zip-архив
        try (FileOutputStream fos = new FileOutputStream(zipPath + "//saves.zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String savePath : savesPath) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    ZipEntry entry = new ZipEntry(getFileName(savePath));
                    zos.putNextEntry(entry);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            for (String savePath : savesPath) {
                File file = new File(savePath);
                if (file.delete()) System.out.println("Файл '" + file.getName() + "' успешно удален.");
                else System.out.println(file.getName() + " не удален. Path: " + file.getPath());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
