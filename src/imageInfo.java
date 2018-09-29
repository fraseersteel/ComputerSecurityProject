import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageInfo {

        // main method

        int width = 960;
        int height = 640;
        BufferedImage image = null;
        File f = null;


        public BufferedImage getImage() {
            try {
                f = new File("src/stego_cover17.bmp");
                image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                image = ImageIO.read(f);
                System.out.println("Reading complete.");
            } catch (IOException e) {
                System.out.println("error reading image " + e);
            }
            return image;
        }


        public void outputImage() {
            try {
                f = new File("out.bmp");
                ImageIO.write(image, "bmp", f);
                System.out.println("writing complete");
            } catch (IOException e) {
                System.out.println("error" + e);
            }
        }

    }
