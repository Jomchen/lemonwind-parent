package com.lemonwind.test.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Jomkie
 * @since 2021-05-22 15:24:6
 * 二维码生成工具
 */
public class QrcodeImageTool {

    private static final String QR_CODE_IMAGE_PATH = "/Users/gisboy/Desktop/MyQRCode.png";

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateQRCodeImage(String text, int width, int height, OutputStream outputStream) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            /*log.error("Could not generate QR Code, WriterException: {}", e.getMessage(), e);*/
        } catch (IOException e) {
            /*log.error("Could not generate QR Code, WriterException: {}", e.getMessage(), e);*/
        }
    }

}
