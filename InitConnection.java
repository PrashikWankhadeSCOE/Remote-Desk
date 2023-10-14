import java.io.DataInputStream;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import javax.xml.crypto.Data;

import javafx.scene.shape.Rectangle;

public class InitConnection {
    ServerSocket socket = null;
    DataInputStream password = null;
    DataOutputStream verify = null;
    String width = "";
    String height = "";

    InitConnection(int port,String value1){

        Robot robot = null;
        Rectangle rectangle = null;

        try{
            System.out.println("Establising Connection waiting for client");
            socket = new ServerSocket(port);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String width = ""+dim.getWidth();
            String height = ""+dim.getHeight();
            rectangle = new Rectangle();
            robot = new Robot(gDev);

            drawGUI();

            while(true){
                Socket sc = socket.accept();
                password = new DataInputStream(sc.getInputStream());
                verify = new DataOutputStream(sc.getOutputStream());

                String password = password.readUTF();

                if(password.equals(value1)){

                    verify.writeUTF("Valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);
                    new SendScreen(sc,robot,rectangle);
                    new ReceiveEvents(sc,robot);
                }
                else{
                    verify.writeUTF("Invalid");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void drawGUI(){

    }
}
