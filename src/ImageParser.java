import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class ImageParser {
    private final int messageLength = 83;
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

        long startTime = System.currentTimeMillis();
        System.out.println("started processing...");

        for (int i = h - 1; i >= 0; i--) {
            for (int j = w - 1; j >= 0; j--) {
                int pixel = img.getRGB(j, i);
                extractZeroBits(pixel);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("finished processing. Time it took: ");
        System.out.print(TimeUnit.MILLISECONDS.toSeconds(endTime - startTime));
        System.out.print(" seconds");
        System.out.println();
    }

    private void extractZeroBits(int pixel) {
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

    private void findMatch(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if(isMatch(bytes, i)) {
                System.out.println("Found match");
                extractArtefact(bytes, i);
                return;
            }
        }
    }

    private void extractArtefact(byte[] bytes, int i) {
        byte[] message = new byte[messageLength];
        for (int j = 1; j <= message.length ; j++) {
            message[j-1] = bytes[i+messageLength-j];
        }
        writeToFile(message);
    }

    private boolean isMatch(byte[] bytes, int index) {
        for (int j = 0; j < messageLength; j++) {
            int symbol = bytes[index+j] & 0xFF;
            if(!(symbol >= 32 && symbol <= 122)) {
                return false;
            }
        }
        return true;
    }

    private String getStringRepresent() {
        return stringRepresent;
    }

    public static void main(String[] args) {
        ImageParser parser = new ImageParser();
        parser.loadImage();
        parser.extractPixels();
        parser.findMatch(new BigInteger(parser.getStringRepresent(), 2).toByteArray());
    }
}
