import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class app {

    // main method


    public static void main(String[] args) throws IOException {

        imageInfo imageInfo = new imageInfo();

        BufferedImage image = imageInfo.getImage();

        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        

    }


}
