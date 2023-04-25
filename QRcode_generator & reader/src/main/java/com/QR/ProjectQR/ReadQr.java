package com.QR.ProjectQR;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr")
public class ReadQr {

    @Value("${location.file-path}")
    private String fpath;
    private String rename = "QR_code.png";
    @GetMapping("/read")
    public String Read() throws IOException, NotFoundException, ChecksumException, FormatException {

        // Input image path
        String filePath = fpath + rename;

        // Read input image
        BufferedImage image = ImageIO.read(new File(filePath));

        // Initialize QR code reader
        Reader reader = new QRCodeReader();

        // Set decoding hints
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);

        // Create luminance source from image
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        HybridBinarizer binarizer = new HybridBinarizer(source);

        // Create binary bitmap from binarizer
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);

        // Decode the QR code using reader and hints
        Result result = reader.decode(bitmap, hints);

        // Print the decoded text
        System.out.println("QR Code Text: " + result.getText());
        return ("QR Code Text: " + result.getText());
    }
}
