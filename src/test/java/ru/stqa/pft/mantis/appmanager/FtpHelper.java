package ru.stqa.pft.mantis.appmanager;


import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс для работы с протоколом FTP. Будет использоваться для передачи конфигов на удаленную машину, чтобы отключить капчу.
 */
public class FtpHelper {

    private final ApplicationManager app;
    private FTPClient ftp;


    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    /**
     * Метод загружает новый конфигурационный файл с отключенной капчей.
     * Старый конфигурационный файл на время теста переименовывается
     */


    /**
     *
     * @param file Локальный файл, который должен быть загружен на удаленную машину (файл с удаленным конфигом)
     * @param targetFileName Имя конфигарационного файла на удаленном сервере
     * @param backup Имя резервной копии
     * @throws IOException
     */
    public void upload(File file, String targetFileName, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(backup);
        ftp.rename(targetFileName, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(targetFileName, new FileInputStream(file));
        ftp.disconnect();
    }

    /**
     * Метод удаляет новый конфигурационный файл с отключенной капчей.
     * Меняет название старого конфигурационного файла с включенной капчей на актуальное
     */

    /**
     *
     * @param backup Имя резервной копии
     * @param targetFileName Имя конфигарационного файла на удаленном сервере
     * @throws IOException
     */
    public void restore(String backup, String targetFileName) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(targetFileName);
        ftp.rename(backup, targetFileName);
        ftp.disconnect();
    }
}
