package com.duoc.educloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3ResumenService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3ResumenService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String subirResumen(Long numeroResumen, Path rutaArchivo) {
        String key = crearKey(numeroResumen);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("text/plain; charset=UTF-8")
                .build();

        s3Client.putObject(request, rutaArchivo);

        return key;
    }

    public String modificarResumen(Long numeroResumen, String nuevoContenido) {
        String key = crearKey(numeroResumen);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("text/plain; charset=UTF-8")
                .build();

        s3Client.putObject(request, RequestBody.fromString(nuevoContenido, StandardCharsets.UTF_8));

        return key;
    }

    public byte[] descargarResumen(Long numeroResumen) throws IOException {
        String key = crearKey(numeroResumen);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (ResponseInputStream<GetObjectResponse> respuesta = s3Client.getObject(request)) {
            return respuesta.readAllBytes();
        }
    }

    public void borrarResumen(Long numeroResumen) {
        String key = crearKey(numeroResumen);

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    public String crearKey(Long numeroResumen) {
        return numeroResumen + "/resumen-inscripcion-" + numeroResumen + ".txt";
    }

    public String crearNombreArchivo(Long numeroResumen) {
        return "resumen-inscripcion-" + numeroResumen + ".txt";
    }
}
