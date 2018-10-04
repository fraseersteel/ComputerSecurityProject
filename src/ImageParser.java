import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageParser {
    private final int payloadSizeInBytes = 83;
    private final int payloadSizeInBits = payloadSizeInBytes * 8;

    private BufferedImage img = null;
    private byte[] bytes = new byte[230400];
    private String stringRepresent = "";

    public void loadImage() {
        try {
            img = ImageIO.read(new File("stego_cover17.bmp"));
        } catch (IOException e) {
            System.out.println("Failed to load image!");
        }
    }

    public void processUsingLSB() {
        extractPixels();
        breakIntoBytes();
        storeIntoString();
        writeToFile("LSB.txt");
    }

    private void extractPixels() {
        int w = img.getWidth();
        int h = img.getHeight();
        int count = 0;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = img.getRGB(j, i);
                System.out.println(Integer.toBinaryString(pixel));
                System.out.println("y:" + i + "x:" + j);
                extractAllZeroBits(pixel);
            }
        }
    }

    private void extractAllZeroBits(int pixel) {
        String zeroBits = "";
        zeroBits += (pixel >> 16) & 0x1;
        zeroBits += (pixel >> 8) & 0x1;
        zeroBits += pixel & 0x1;
        System.out.println(zeroBits);
        stringRepresent = stringRepresent.concat(zeroBits);
    }

    private void breakIntoBytes() {
        int byteCount = 0;
        for (int i = 0; i+8 <= stringRepresent.length(); i += 8) {
            populateByteArray(stringRepresent.substring(i, i+8), byteCount);
            byteCount++;
        }
    }

    private void storeIntoString() {
        String s = new String(bytes);
        System.out.println(s);
    }

    private void populateByteArray(String bytecode, int pos) {
        int intRepresentation = Integer.parseInt(bytecode);
        byte byteRepresentation = (byte) intRepresentation;
        bytes[pos] = byteRepresentation;
        String s1 = String.format("%8s", Integer.toBinaryString(bytes[pos] & 0xFF)).replace(' ', '0');
        System.out.println(s1);
    }

    private void writeToFile(String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
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
}
