import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by boxfishedu on 17/1/5.
 */
public class MyServer {

    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream din = null;
        DataOutputStream dout = null;
        try {
//            InetAddress addr = InetAddress.getLocalHost();
//            ss = new ServerSocket(8888, 50, addr);
            ss = new ServerSocket(8888);
            System.out.println("listening to 8888");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (ss != null) {
            try {
                s = ss.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                String msg = din.readUTF();
                System.out.println("ip: " + s.getInetAddress());
                System.out.println("msg: " + msg);
                System.out.println("===============");
                dout.writeUTF("Hello Client");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
