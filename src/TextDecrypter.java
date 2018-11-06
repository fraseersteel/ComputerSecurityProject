import java.io.*;
import java.util.*;

public class TextDecrypter {
    private String encryptedMessage;
    private char[] decryptedMessage;
    private HashMap<Character, Integer> frequencyMap;
    private List<String> commonWords;

    private String frequency = "etaoinshrdlcumwfgypbvkjxqz";

    public TextDecrypter() {
        encryptedMessage = "";
        decryptedMessage = new char[83];
        commonWords = new ArrayList<>();
        frequencyMap = new HashMap<>();
    }

    /*
        b = h
        c = c
        d = i
        h = l
        i = e
        j = m
        k = d
        l = n
        m = o
        p = a
        r = r
        s = s
        t = t
        u = u
        w = w
        x = x
        y = y
    */

    public void loadCommonWords() {
        String fileName = "common_words.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                commonWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        File f = new File("processed.txt");
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            encryptedMessage = b.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getFrequencyMap(String sentence) {
        String withoutSpaces = sentence.replaceAll("\\s+","");

        for (int i = 0; i < withoutSpaces.length(); i++) {
            char c = withoutSpaces.charAt(i);
            Integer value = frequencyMap.get(c);
                if (value != null) {
                    frequencyMap.put(c, new Integer(value + 1));
                } else {
                    frequencyMap.put(c, 1);
                }
            }
        return frequencyMap;
    }

    public void decrypt() {
        OrderMap valueComparator = new OrderMap(getFrequencyMap(encryptedMessage));
        TreeMap<Character, Integer> sorted = new TreeMap<>(valueComparator);
        sorted.putAll(frequencyMap);
        Set<Character> cipherAlpha = sorted.keySet();

        StringBuilder sb = new StringBuilder();
        for(Character ch: cipherAlpha){
            sb.append(ch);
        }

        String cipherAlphabet = sb.toString();
        System.out.println(cipherAlphabet);

        for (int i = 0; i < encryptedMessage.length(); i++) {
            if (encryptedMessage.charAt(i) == ' ') {
                decryptedMessage[i] = ' ';
            } else {
                int index = cipherAlphabet.indexOf(encryptedMessage.charAt(i));
                decryptedMessage[i] = frequency.charAt(index);
            }
        }

        String decryptedMessageString = String.valueOf(decryptedMessage);
        System.out.println(decryptedMessageString);

        StringTokenizer tokeniser = new StringTokenizer(decryptedMessageString, " ");
        int maxScore = tokeniser.countTokens();
        int score = 0;

        while (tokeniser.hasMoreElements()) {
            String token = (String) tokeniser.nextElement();
            if(commonWords.contains(token)) {
                score++;
            }
        }
        System.out.println(score);

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

    public static void main(String[] args) {
        TextDecrypter tx = new TextDecrypter();
        tx.loadCommonWords();
        tx.readFromFile();
        tx.decrypt();
        tx.writeToFile();
    }

}


class OrderMap implements Comparator<Character> {
    Map<Character, Integer> base;

    public OrderMap(Map<Character, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(Character a, Character b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}








