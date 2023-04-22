package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        my_serve ms1 = new my_serve();
        ms1.my_serveGUI();
    }
}

class my_serve {

    public static ServerSocket serversocket;
    public static Socket socket;
    //这里定义GUI所用的所有组件
    JTextField port_field = new JTextField(20);
    JLabel port_label   = new JLabel("端口号");
    JButton start_button = new JButton("启动服务器");
    JTextArea main_area = new JTextArea(23,40);
    JLabel say_label = new JLabel("聊天框");
    JTextField say_field = new JTextField(30);
    JButton send_button = new JButton("发送");

    public void my_serveGUI(){
        // 将Frame分为上中下三个部分
        JFrame serverUI = new JFrame("Server");
        serverUI.setBounds(600,100,500,500);
        serverUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel north_panel = new JPanel();
        JPanel center_panel = new JPanel();
        JPanel south_panel = new JPanel();
        //上方

        north_panel.add(port_label);
        north_panel.add(port_field);
        north_panel.add(start_button);
        serverUI.add(north_panel, BorderLayout.NORTH);
        //界面中间

        JScrollPane jp = new JScrollPane(main_area);
        center_panel.add(jp);
        serverUI.add(center_panel,BorderLayout.CENTER);
        //界面下方

        south_panel.add(say_label);
        south_panel.add(say_field);
        south_panel.add(send_button);
        serverUI.add(south_panel,BorderLayout.SOUTH);

        serverUI.setVisible(true);

        //按钮监听
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    my_thread mt = new my_thread();
                    mt.start();
            }
        });

        send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String out_message = say_field.getText();
                    out.writeUTF(out_message);
                    main_area.append("Server :" + out_message +"\n");
                    say_field.setText("");
                }catch(IOException e1) {
                    JOptionPane.showMessageDialog(null, "发送错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    class my_thread extends Thread {
        public void run () {
            try{
                main_area.append("服务器已启动等待连接...\n");
                serversocket = new ServerSocket(Integer.parseInt(port_field.getText()));
                socket = serversocket.accept();
                main_area.append("客户端已连接成功...\n");
            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                while(true){
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    String in_message = in.readUTF();
                    main_area.append("Client :" + in_message + "\n");
                }
            }catch(IOException e2){
                JOptionPane.showMessageDialog(null, "接受错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//    class my_thread1 extends Thread {
//        public void run () {
//            try{
//                main_area.append("服务器已启动等待连接...\n");
//                serversocket = new ServerSocket(Integer.parseInt(port_field.getText()));
//                socket = serversocket.accept();
//                main_area.append("客户端已连接成功...\n");
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
}


