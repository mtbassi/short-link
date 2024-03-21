package br.com.mtbassi.shortlink.service;

import br.com.mtbassi.shortlink.infra.exceptionhandler.ErrorCustomException;
import br.com.mtbassi.shortlink.infra.exceptionhandler.TypeExceptionEnum;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    public byte[] generateQrCode(String originalLink) {
        var outputStream = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(originalLink, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ErrorCustomException(TypeExceptionEnum.GENERATE_QR_CODE_EXCEPTION, e.getMessage());
        }
    }
}
