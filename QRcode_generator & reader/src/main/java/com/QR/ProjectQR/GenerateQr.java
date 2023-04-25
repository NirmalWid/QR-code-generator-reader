package com.QR.ProjectQR;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr")

public class GenerateQr {

    @Value("${location.file-path}")
    private String fpath;

    @Value("${location.image-path}")
    private String img_path;
    private String rename = "QR_code.png";

    @GetMapping("/gen")
    public String Generate() {
        int width = 500;
        int height = 500;
        String qrCodeData = "Ceylon Government Railways - E ticketing service";
        String topText = "Millennium IT ESP";
        String bottomText = "Java SpringBoot";
        String fileType = "png";
        String path = fpath + rename;
        String imagePath = img_path; // image path

        try {
            // Generate the QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, getQRCodeParams());
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            qrCodeImage.createGraphics();

            // Draw the QR code image
            Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // Add the image to the center of the QR code
            BufferedImage image = ImageIO.read(new File(imagePath));
            int imageSize = Math.min(width, height) / 4; // adjust this as needed
            int x = (width - imageSize) / 2;
            int y = (height - imageSize) / 2;
            graphics.drawImage(image, x, y, imageSize, imageSize, null);

            // Draw the top and bottom text
            Font font = new Font("Times New Roman", Font.BOLD, 28);
            graphics.setFont(font);
            int topTextWidth = graphics.getFontMetrics().stringWidth(topText);
            graphics.drawString(topText, (width - topTextWidth) / 2, 35);
            int bottomTextWidth = graphics.getFontMetrics().stringWidth(bottomText);
            graphics.drawString(bottomText, (width - bottomTextWidth) / 2, height - 20);

            // Save the QR code image to a file
            File qrCodeFile = new File(path);
            ImageIO.write(qrCodeImage, fileType, qrCodeFile);

            System.out.println("QR Code generated successfully!");
            return "QR Code generated successfully!";

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
        private Map<EncodeHintType, Object> getQRCodeParams() {
             Map<EncodeHintType, Object> hintType = new HashMap<>();
             hintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        return hintType;
    }
}