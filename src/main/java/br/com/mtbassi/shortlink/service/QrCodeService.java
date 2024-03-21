package br.com.mtbassi.shortlink.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    @SneakyThrows
    public byte[] generateQrCode(String originalLink){
        BitMatrix bitMatrix = new QRCodeWriter().encode(originalLink, BarcodeFormat.QR_CODE, 200, 200);
        var outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }
}
