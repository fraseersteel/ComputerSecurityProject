public class Driver {
    public static void main(String[] args) {
        ImageParser parser = new ImageParser();
        parser.loadImage();
        parser.processUsingLSB();
    }
}
