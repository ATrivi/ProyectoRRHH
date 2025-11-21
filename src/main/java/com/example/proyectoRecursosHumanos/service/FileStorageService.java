package com.example.proyectoRecursosHumanos.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    // Directorio base donde se guardarán los recibos
    private final String STORAGE_ROOT = "C:\\Users\\atrivinh\\OneDrive - NTT DATA EMEAL\\Desktop";

    public String savePaySlip(Integer payrollId, byte[] pdfContent, String employeeName) {

        //nombre del archivo
        String fileName = employeeName.replaceAll(" ", "_") + "_" + payrollId + ".pdf";
        Path targetPath = Paths.get(STORAGE_ROOT, fileName);

        try {
            //Asegurarse de que el directorio exista, sino, lo crea
            File directory = new File(STORAGE_ROOT);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            //Escribir los bytes al disco
            try (FileOutputStream fos = new FileOutputStream(targetPath.toFile())) {
                fos.write(pdfContent);
            }

            //Devolver la ruta completa para guardarla en la base de datos
            return targetPath.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo PDF en el disco.", e);
        }
    }
}