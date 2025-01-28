
import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Socket c = new Socket(args[0], Integer.parseInt(args[1]));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String a, b, s;
        System.out.print("a = ");
        a = reader.readLine();
        System.out.print("b = ");
        b = reader.readLine();

        DataInputStream socketIn = new DataInputStream(c.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(c.getOutputStream());
        socketOut.writeInt(a.length());

        socketOut.write(a.getBytes());
        socketOut.writeInt(b.length());
        socketOut.write(b.getBytes());
        socketOut.flush();

        int length = socketIn.readInt(); // Read the length of 'a'
        byte[] bytes = new byte[length];
        socketIn.readFully(bytes); // Read 'a' based on its length
        s = new String(bytes);
        System.out.println("String concatenat:"+s);

        reader.close();
        c.close();

    }

}