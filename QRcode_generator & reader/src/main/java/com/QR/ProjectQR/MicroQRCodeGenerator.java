package com.QR.ProjectQR;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MicroQRCodeGenerator {
    public static void main(String[] args) {
        String text = "Hello, World!"; // text to encode
        int size = 17; // size of the QR code (17x17 for micro QR code)
        String fileType = "png"; // file type of the generated QR code image
        String filePath = "microqrcode.png"; // file path of the generated QR code image

        // set encoding hints for micro QR code
        EncodeHintType hintType = EncodeHintType.MARGIN;
        int margin = 0;
        ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.L;

        // create QR code matrix
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size);


            // create buffered image from QR code matrix
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                    image.setRGB(x, y, pixel);
                }
            }

            // save QR code image to file
            File qrCodeFile = new File(filePath);
            ImageIO.write(image, fileType, qrCodeFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
