import java.io.*;
import java.util.*;

public class TextDecrypter {

    String encryptedMessage;
    char[] decryptedMessage;
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    HashMap<Character,Integer> map;

    public void readFromFile(){
        encryptedMessage = "";
        decryptedMessage = new char[83];
        File f = new File("processedFinal.txt");
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            encryptedMessage = b.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getFrequencyMap(String sentence){

        map = new HashMap<Character,Integer>();

        for(int i = 0; i<sentence.length();i++){
            char c = sentence.charAt(i);
            Integer value = map.get(c);
            if(value != null){
                map.put(c, new Integer(value+1));
            }
            else {
                map.put(c,1);
            }
        }
        System.out.println("unsorted " + map);
        return map;
    }

    public void decrypt() {

        OrderMap valueComparator = new OrderMap(getFrequencyMap(encryptedMessage));
        TreeMap<Character,Integer> sorted = new TreeMap<Character,Integer>(valueComparator);
        sorted.putAll(map);
        System.out.println("sorted " + sorted);
//        String cipherAlphabet = getFrequencyMap(encryptedMessage);
        String cipherAlphabet = "pzckifgbdakhjlmpqrstuvwxyz";

        for (int i = 0; i < encryptedMessage.length(); i++) {
            if (encryptedMessage.charAt(i) == ' ') {
                decryptedMessage[i] = ' ';
            } else {
                int index = cipherAlphabet.indexOf(encryptedMessage.charAt(i));
                decryptedMessage[i] = alphabet.charAt(index);
            }
        }

    }

    public void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("decrypted.txt"));
            writer.write(decryptedMessage);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


class OrderMap implements Comparator<Character>{
Map<Character,Integer> base;

    public OrderMap(Map<Character,Integer> base){
        this.base = base;
    }

    @Override
    public int compare(Character a, Character b) {
        if(base.get(a) >= base.get(b)){
            return -1;
        }else{
            return 1;
        }
    }
}








