import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageParserProper {
    private List<Integer> lsbBits = new ArrayList<>();
    ArrayList<Integer> bytes = new ArrayList<>();
    private final int byteLength = 8;

    public static void main(String[] args) throws Exception {
        ImageParserProper parser = new ImageParserProper();
        parser.loadImage();
        parser.makeBytes();
        parser.writeToFile();
    }

    private void loadImage() {
        InputStream in = null;

        try {
            in = new FileInputStream(new File("stego_cover17.bmp"));
            int lsb, byt, count = 0;

            while (in.available() > 0) {
                byt = in.read();
                if(count > 53) {
                    lsb = byt & 0x1;
                    lsbBits.add(lsb);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Integer> makeBytes() {
        Iterator<Integer> it=lsbBits.iterator();
        int myByte = 0;

        for(int j = 0; j < lsbBits.size(); j+=byteLength) {
            myByte=0;

            for(int i = 0; i < byteLength; i++) {
                if(it.hasNext()) {
                    int bit=it.next();
                    myByte=(bit << i) ^ myByte; // shift it by the count i and ^ with myByte
                } else {
                    myByte= ((0 << i) ^ myByte); // pad with 0's
                }
            }
            bytes.add(myByte);
        }
        return bytes;
    }

    private void writeToFile() throws Exception {
        FileOutputStream out = new FileOutputStream(new File("processed.txt"));

        for (int i = 0; i < bytes.size(); i++) {
            out.write(bytes.get(i));
        }
    }
}
