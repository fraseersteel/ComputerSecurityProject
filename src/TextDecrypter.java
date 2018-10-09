import java.io.*;

public class TextDecrypter {

    String encryptedMessage = "";
    char[] decryptedMessage = new char[83];
    File f = new File("processed.txt");

    void writeToFile(byte[] message) {

    }


    public static void main(String[] args) {


        String encryptedMessage = "";
        char[] decryptedMessage = new char[83];
        File f = new File("processed.txt");
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                encryptedMessage = readLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String cipherAlphabet = "pzckifgbdakhjlmpqrstuvwxyz";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";


        for (int i = 0; i < encryptedMessage.length(); i++) {
            if (encryptedMessage.charAt(i) == ' ') {
                decryptedMessage[i] = ' ';
            } else {
                int index = cipherAlphabet.indexOf(encryptedMessage.charAt(i));
                decryptedMessage[i] = alphabet.charAt(index);
            }
        }


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("decrypted.txt"));
            writer.write(decryptedMessage);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
