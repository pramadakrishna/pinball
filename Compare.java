import java.io.*;

public class Compare {
    public static void main(String[] args) throws IOException {

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            long startTime = System.currentTimeMillis();

            in = new BufferedInputStream(new FileInputStream("C:/Users/prama/OneDrive/Desktop/iot.zip"));
            out = new BufferedOutputStream(new FileOutputStream("C:/Users/prama/OneDrive/Desktop/intro-file.txt"));

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Time taken to copy file: " + duration + " milliseconds");

        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
