package Chat2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        my_client mc = new my_client();
        mc.clientGUI();
    }
}

class my_client {
    public static ServerSocket serversocket;
    public static Socket socket;
    JLabel ip_label = new JLabel("IP地址");
    JTextField ip_field = new JTextField(10);
    JLabel port_label   = new JLabel("端口号");
    JTextField port_field = new JTextField(10);
    JButton start_button = new JButton("启动服务器");
    JTextArea main_area = new JTextArea(23,40);
    JLabel say_label = new JLabel("聊天框");
    JTextField say_field = new JTextField(30);
    JButton send_button = new JButton("发送");

    public void clientGUI (){
        JFrame clientUI = new JFrame("客户端");
        clientUI.setBounds(600,100,500,500);
        clientUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel north_panel = new JPanel();
        JPanel center_panel = new JPanel();
        JPanel south_panel = new JPanel();

        north_panel.add(ip_label);
        north_panel.add(ip_field);
        north_panel.add(port_label);
        north_panel.add(port_field);
        north_panel.add(start_button);
        clientUI.add(north_panel, BorderLayout.NORTH);

        JScrollPane jp = new JScrollPane(main_area);
        center_panel.add(jp);
        clientUI.add(center_panel,BorderLayout.CENTER);

        south_panel.add(say_label);
        south_panel.add(say_field);
        south_panel.add(send_button);
        clientUI.add(south_panel,BorderLayout.SOUTH);

        clientUI.setVisible(true);

        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new Socket(ip_field.getText(),Integer.parseInt(port_field.getText()));
                    main_area.append("Connected to server...\n");
                    my_thread mt = new my_thread();
                    mt.start();
                }catch(IOException e1){
                    JOptionPane.showMessageDialog(null, "客户端启动失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String out_message = say_field.getText();
                    out.writeUTF(out_message);
                    main_area.append("Client :" + out_message + "\n");
                    say_field.setText("");
                }catch (IOException e1){
                    JOptionPane.showMessageDialog(null, "客户端发送失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    class my_thread extends Thread {
        public void run () {
            try{
                while(true){
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    String in_message = in.readUTF();
                    main_area.append("Server :" + in_message + "\n");
                }
            }catch(IOException e2){
                JOptionPane.showMessageDialog(null, "客户端接受错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
