import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;

public class ImageParser {
    private BufferedImage img = null;
    private String stringRepresent = "";

    private void loadImage() {
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File("stego_cover17.bmp"));
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Failed to load image!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    System.out.println("Error closing input stream!");
                    ex.printStackTrace();
                }
            }
        }
    }

    private void extractPixels() {
        int w = img.getWidth();
        int h = img.getHeight();

        for (int i = h - 1; i >= 0; i--) {
            for (int j = w - 1; j >= 0; j--) {
                int pixel = img.getRGB(j, i);
                System.out.println("x:" + j + "y:" + i);
                extractAllZeroBits(pixel);
            }
        }
    }

    private void extractAllZeroBits(int pixel) {
        String zeroBits = "";
        zeroBits += (pixel >> 16) & 0x1;
        zeroBits += (pixel >> 8) & 0x1;
        zeroBits += pixel & 0x1;
        stringRepresent = stringRepresent.concat(zeroBits);
    }

    private void writeToFile(byte[] bytes) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("processed.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getStringRepresent() {
        return stringRepresent;
    }

    public static void main(String[] args) {
        ImageParser parser = new ImageParser();
        parser.loadImage();
        parser.extractPixels();
        parser.writeToFile(new BigInteger(parser.getStringRepresent(), 2).toByteArray());
    }
}
