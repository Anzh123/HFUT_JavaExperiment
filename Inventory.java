package JavaEx2;

import java.io.*;
import java.util.Vector;

class Goods {
    String Item_num;
    int Quantity;
    String supplier;
    String Description;
}
class Thing {
    String Item_num;
    int Quantity;
    String supplier;
    String Description;
}
public class Inventory {

    static Vector<Goods> v_goods=new Vector<Goods>(); //创建Goods对象数组
    static Vector<Thing> O_thing=new Vector<Thing>(); //创建Thing的到货（O）事件对象数组
    static Vector<Thing> R_thing=new Vector<Thing>(); //创建Thing的到货（R）事件对象数组
    static Vector<Thing> D_thing=new Vector<Thing>(); //创建Thing的到货（D）事件对象数组
    static Vector<Thing> A_thing=new Vector<Thing>(); //创建Thing的到货（A）事件对象数组

    static BufferedWriter out_Shipping=null;
    static BufferedWriter out_Errors=null;
    static BufferedReader in=null;
    static BufferedWriter out_NewInventory=null;

    //读取库存信息
    public static void read_Inventory () {
        try{
            in = new BufferedReader(new FileReader("E:\\JAVAExperiment2\\JavaExperiment2\\src\\JavaEx2\\Inventory.txt"));
            String str_temp1;
            // 将库存文件存入vector中
            while ((str_temp1 = in.readLine()) != null){
                String[] str2_temp2 = str_temp1.split("\\s+");
                Goods goods = new Goods();
                goods.Item_num = str2_temp2[0];
                goods.Quantity = Integer.parseInt(str2_temp2[1]);
                goods.supplier = str2_temp2[2];
                goods.Description = str2_temp2[3];
                v_goods.addElement(goods);
            }
            // 关闭这个输入流
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void read_Transactions () {
        //读取事件文件
        try{
            in = new BufferedReader(new FileReader("E:\\JAVAExperiment2\\JavaExperiment2\\src\\JavaEx2\\Transactions.txt"));
            String str_temp1;
            while ((str_temp1 = in.readLine()) != null) {
                String[] str_temp2 = str_temp1.split("\\s+");
                Thing thing = new Thing();
                // 如果是O事件则存入O数组中
                if (str_temp2[0].equals("O")) {
                    thing.Item_num = str_temp2[1];
                    thing.Quantity = Integer.parseInt(str_temp2[2]);
                    thing.supplier = str_temp2[3];
                    // 由于要先给需求数量少的客户
                    // 先把订单的按需求数量升序排好，后面就不用在排序了
                    // 按照数量大小有序插入
                    int O_thing_size = O_thing.size();
                    int O_thing_index = O_thing.size() - 1;

                    if (O_thing_size == 0) {
                        O_thing.addElement(thing);
                    } else {
                        while (O_thing_index > 0) {
                            if ((thing.Quantity) < (O_thing.elementAt(O_thing_index).Quantity)) {
                                O_thing_index--;
                            }
                        }
                        O_thing.insertElementAt(thing, O_thing_index);
                    }
                    // 如果是A事件
                } else if (str_temp2[0].equals("A")) {
                    thing.Item_num = str_temp2[1];
                    thing.supplier = str_temp2[2];
                    thing.Description = str_temp2[3];

                    A_thing.addElement(thing);
                    // 如果是R事件
                } else if (str_temp2[0].equals("R")) {
                    thing.Item_num = str_temp2[1];
                    thing.Quantity = Integer.parseInt(str_temp2[2]);

                    R_thing.addElement(thing);
                    // 如果是D事件
                } else if (str_temp2[0].equals("D")) {
                    thing.Item_num = str_temp2[1];

                    D_thing.addElement(thing);
                } else {
                    System.out.println("读入文件错误");
                }
            }
            // 输入流关闭
            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 先处理A事件加入库存种类
    public static void operate_A_thing (){
        for (int i = 0 ; i < A_thing.size(); i ++){
            Goods goods_temp = new Goods();
            goods_temp.Item_num = A_thing.elementAt(i).Item_num;
            goods_temp.Quantity = 0;
            goods_temp.supplier = A_thing.elementAt(i).supplier;
            goods_temp.Description = A_thing.elementAt(i).Description;
            //这个是按库存货物的编码大小的排序  可能多此一举
            for (int j = 0 ; j<v_goods.size();j ++){
                if (Integer.parseInt(goods_temp.Item_num) < Integer.parseInt(v_goods.elementAt(j).Item_num)){
                    v_goods.insertElementAt(goods_temp,j);
                    break;
                }
            }
        }
    }
    //再处理事件R 增加已有库存的数量
    public static void operate_R_thing(){
        for( int i = 0 ; i < R_thing.size() ; i ++ ){
            for ( int j = 0 ; j < v_goods.size(); j ++){
                if (R_thing.elementAt(i).Item_num.equals(v_goods.elementAt(j).Item_num)){
                    v_goods.elementAt(j).Quantity += R_thing.elementAt(i).Quantity;
                }
            }
        }
    }
    //在处理O事件发货
    public static void operate_O_thing(){
        // 用于存储发货单
        Vector<Goods> s_goods = new Vector<Goods>();
        try{
            out_Shipping = new BufferedWriter(new FileWriter("E:\\JAVAExperiment2\\JavaExperiment2\\src\\JavaEx2\\Shipping.txt"));
            out_Errors = new BufferedWriter(new FileWriter("E:\\JAVAExperiment2\\JavaExperiment2\\src\\JavaEx2\\Errors.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
        // 双重遍历 找到对应事件和库存
        for (int i = 0 ; i < O_thing.size() ; i ++){
            for (int j = 0;j< v_goods.size() ; j ++){
                if(O_thing.elementAt(i).Item_num.equals(v_goods.elementAt(j).Item_num)){
                    if(O_thing.elementAt(i).Quantity <= v_goods.elementAt(j).Quantity){
                        // 这里已经对应上了并且库存够
                        v_goods.elementAt(j).Quantity -= O_thing.elementAt(i).Quantity;
                        boolean flag = false;
                        // 遍历发货单 如果有相同的客户要相同的货物则直接在数量上相加
                        for (int k = 0 ; k < s_goods.size() ; k ++){
                            if (O_thing.elementAt(i).supplier.equals(s_goods.elementAt(k).supplier)
                            && O_thing.elementAt(i).Item_num.equals(s_goods.elementAt(k).Item_num)){
                                s_goods.elementAt(k).Quantity += O_thing.elementAt(i).Quantity;
                                flag = true;
                            }
                        }
                        // 如果没有相同的货单 则在发货单上加上新的货物
                        if (!flag){
                            Goods goods_temp = new Goods();
                            goods_temp.Item_num = O_thing.elementAt(i).Item_num;
                            goods_temp.Quantity = O_thing.elementAt(i).Quantity;
                            goods_temp.supplier = O_thing.elementAt(i).supplier;
                            s_goods.addElement(goods_temp);
                        }
                    }
                    else {
                        try {
                            // 注意这里的error流别关掉
                            out_Errors.write(O_thing.elementAt(i).supplier+"\t"+O_thing.elementAt(i).Item_num+"\t"+O_thing.elementAt(i).Quantity+"\n");
                            //out_Errors.flush();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        try{
            // 输出发货单
            for ( int m = 0 ; m < s_goods.size() ; m ++) {
                out_Shipping.write(s_goods.elementAt(m).supplier + "\t" + s_goods.elementAt(m).Item_num + "\t" + s_goods.elementAt(m).Quantity+"\n");
                out_Shipping.flush();
                //out_Shipping.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            out_Shipping.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // 处理D事件
    public static void operate_D_thing(){
        // 双重遍历 找到对应
        for (int i = 0 ; i < D_thing.size() ; i ++){
            for ( int j = 0 ; j < v_goods.size() ; j ++){
                if (D_thing.elementAt(i).Item_num.equals(v_goods.elementAt(j).Item_num)){
                    if(v_goods.elementAt(j).Quantity != 0){
                        try{
                            out_Errors.write(D_thing.elementAt(j).supplier+"\t"+D_thing.elementAt(j).Item_num+"\t"+D_thing.elementAt(j).Quantity+"\n");
                            //out_Errors.flush();
                            //out_Errors.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    v_goods.removeElement(v_goods.elementAt(j));
                }
            }
        }
        try{
            out_Errors.close();
        }catch ( IOException e){
            e.printStackTrace();
        }
    }
    // 输出新的货单
    public static void OUT_newInventory(){
        try{
            out_NewInventory = new BufferedWriter(new FileWriter("E:\\JAVAExperiment2\\JavaExperiment2\\src\\JavaEx2\\NewInventory.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
        // 将现在的库存 输出到锌库存中
        for (int i = 0;i < v_goods.size();i ++){
            try{
                out_NewInventory.write(v_goods.elementAt(i).Item_num+"\t"+v_goods.elementAt(i).Quantity+"\t"+v_goods.elementAt(i).supplier+"\t"+v_goods.elementAt(i).Description+"\n");
                out_NewInventory.flush();
                //out_NewInventory.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        try {
            out_NewInventory.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        read_Inventory();
        read_Transactions();
        operate_A_thing();
        operate_R_thing();
        operate_O_thing();
        operate_D_thing();
        OUT_newInventory();
    }
}
