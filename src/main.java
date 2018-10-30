

public class main {
    public static void main(String[] args) {

        TextDecrypter tx = new TextDecrypter();

        tx.readFromFile();
        tx.decrypt();
        tx.writeToFile();
    }

}
