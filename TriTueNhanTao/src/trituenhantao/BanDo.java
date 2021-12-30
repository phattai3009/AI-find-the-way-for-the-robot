/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trituenhantao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorListener;

class Toado{
    public int x;
    public int y;
}

public class BanDo implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame fr;
    private int nx, ny;      //chiều dài ma trận
    private TimDuong tim;

    private JPanel panel, panel1, panel2;
    private JLayeredPane layer;
    private ImageIcon mauxanh, maublue, maudau, maucuoi;
    private ImageIcon maudaduyet, maudangduyet, mauduongdi, mauduongdiquathoigian;
    private ImageIcon maurobot, mauthoigian, mauchuottrai;
    private JLabel[][] label;
    private int[][] Trangthaihientai; //biến này cho biết những nút nào đã duyệt
    private int[][] LuulaibandoGoc;
    private int[][] LuulaibandoDuongDi;
    private JButton batdau, reset, giulaibando ,toiuuduongdi, toiuuthoigian, toiuuca2;
    private JButton diemdau, diemcuoi, taotuongvathoigian;
    private JLabel vitri, timetimduong, toiuu, labelQuangduong, labelThoigian;
    private JTextField textvitri, textTimetimduong, textQuangduong, textThoigian;
    
    private ArrayList<Toado> duongDaDi = new ArrayList();
    private boolean isdiemdau = false, isdiemcuoi = false; //cai nay de xac dinh xem da chon diem dau diem cuoi hay chua
    private boolean vaobien = false;
    private boolean clickkhidangchay;
    private int x = -1, y = -1; //cái này để lưu giá trị toạ độ điểm ấn cuối cùng
    private int toadodauX, toadodauY, toadocuoiX, toadocuoiY; //toạ độ đầu và toạ độ cuối
    private int luuToadodauX, luuToadodauY; //CẦN LƯU TOẠ ĐỘ ĐẦU ĐỂ RESET LẠI BẢN ĐỒ
    private float quangduong = 0, thoigian = 0;
    private int kieutoiuu = 1;
    private boolean chuyenKieuToiUu;

    public int getNx() {
        return nx;
    }
    public int getNy(){
        return ny;
    }
    public JLabel[][] getLabel() {
        return label;
    }
    public void setLabel_daduyet(int x, int y) {
        label[x][y].setIcon(null);
        label[x][y].setIcon(maudaduyet);
    }
    public void setLabel_dangduyet(int x, int y){
        label[x][y].setIcon(null);
        label[x][y].setIcon(maudangduyet);
    }
    public void setMaurobot(int x, int y){
        label[x][y].setIcon(null);
        label[x][y].setIcon(maurobot);
    }
    public void setMauduongdi(int x, int y) {
        label[x][y].setIcon(null);
        label[x][y].setIcon(mauduongdi);
    }
    public void setMauduongdiQuathoigian(int x, int y){
        label[x][y].setIcon(null);
        label[x][y].setIcon(mauduongdiquathoigian);
    }
    public boolean IsDiemDau(int x, int y){
        if (x == luuToadodauX && y == luuToadodauY)
            return true;
        else return false;
    }
    public void setMauDiemDau(int x, int y){
        label[x][y].setIcon(null);
        label[x][y].setIcon(maudau);
    }
    public void setReset(){
        reset.setEnabled(true);
        giulaibando.setEnabled(true);
    }
    public boolean isInDuongDaDi(int x, int y){
        //KIỂM TRA CÁI VỪA TẠO CÓ NẰM TRÊN ĐƯỜNG ĐI HAY KHÔNG
        for (int i = 0; i < duongDaDi.size(); i++) {
            if(x == duongDaDi.get(i).x && y == duongDaDi.get(i).y)
                return true;
        }
        return false;
    }
    public void setDuongDi(int x, int y){
        Toado tmp = new Toado();
        tmp.x = x;
        tmp.y = y;
        duongDaDi.add(0, tmp);
    }
    public void setThoigiantimduong(long x){
        this.textTimetimduong.setText(x + " ms");
    }
    public int getKieutoiuu(){
        return kieutoiuu;
    }

    public void setClickkhidangchay(boolean clickkhidangchay) {
        this.clickkhidangchay = clickkhidangchay;
    }

    public float getQuangduong() {
        return quangduong;
    }
    public float getThoigian() {
        return thoigian;
    }

    public void setTextQuangduong(float x) {
        this.textQuangduong.setText(String.valueOf(x));
    }
    public void setTextThoigian(float x) {
        this.textThoigian.setText(String.valueOf(x));
    }
    
    
    
    public ImageIcon getMaudangduyet() {
        return maudaduyet;
    }
    public ImageIcon getMauxanh() {
        return mauxanh;
    }
    public ImageIcon getMaublue() {
        return maublue;
    }
    public ImageIcon getMaudau() {
        return maudau;
    }
    public ImageIcon getMaucuoi() {
        return maucuoi;
    }
    public int getToadodauX() {
        return toadodauX;
    }
    public int getToadodauY() {
        return toadodauY;
    }
    public int getToadocuoiX() {
        return toadocuoiX;
    }
    public int getToadocuoiY() {
        return toadocuoiY;
    }
    public int getTrangthaihientai(int x, int y) {
        return Trangthaihientai[x][y];
    }
    public int setTrangthaihientai(int x, int y) {
        return Trangthaihientai[x][y] = 0;
    }
    public int getGiatriNutbandau(int x, int y){
        return this.LuulaibandoGoc[x][y];
    } 
    
    public BanDo(int x, int y) {
        //x = 1360; y = 768;
        this.nx = 50; this.ny = 50;
        
        fr = new JFrame("Tìm đường về nhà");
        fr.setBounds(0, 0, nx*20 + 360, ny*20 + 60);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);

        //KHAI BÁO MẢNG ĐỘNG
        layer = new JLayeredPane(); //khởi tạo đối tượng JLayeredPane
        layer.setPreferredSize(new Dimension(nx*20, ny*20));
        Trangthaihientai = new int[nx-1][ny-1];
        LuulaibandoGoc = new int[nx-1][ny-1];
        LuulaibandoDuongDi = new int[nx-1][ny-1];
        for (int i = 1; i < nx-1; i++) {
            for (int j = 1; j < ny-1; j++) {
                Trangthaihientai[i][j] = 1;
            }
        }
        
        mauxanh = new ImageIcon("picture/green.png");
        maublue = new ImageIcon("picture/blue.png");
        maudau = new ImageIcon("picture/diemdau.png");
        maucuoi = new ImageIcon("picture/diemcuoi.png");
        maudaduyet = new ImageIcon("picture/diemdaduyet.png");
        maudangduyet = new ImageIcon("picture/diemdangduyet.png");
        mauduongdi = new ImageIcon("picture/duongdi.png");
        mauduongdiquathoigian = new ImageIcon("picture/duongdiquathoigian.png");
        maurobot = new ImageIcon("picture/robot.png");
        mauthoigian = new ImageIcon("picture/thoigian.png");
        mauchuottrai = new ImageIcon("picture/thoigian.png");
        mauchuottrai = maublue;
        
        //tạo bản đồ
        label = new JLabel[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i == 0 || i == nx - 1 || j == 0 || j == ny - 1) //label[i][j].setBackground(Color.red);
                {
                    label[i][j] = new JLabel(maublue);
                } else {
                    label[i][j] = new JLabel(mauxanh);
                }
                label[i][j].setBounds(20 * i, j * 20, 20, 20); //10 là khoảng cách cho lọt vào frame
                layer.add(label[i][j]);
            }
        }
        
        
        //lắng nghe sự kiện chuột
        layer.addMouseListener(this);
        layer.addMouseMotionListener(this);

        //add các panel vào frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        panel = new JPanel(); //add bản đồ
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(nx*20, ny*20));
        panel1.add(layer, gbc);

        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(300, ny*20));
        batdau = new JButton("Bắt đầu TÌM ĐƯỜNG");
        batdau.setBackground(Color.BLACK);
        batdau.setForeground(Color.red);
        batdau.addActionListener((ActionListener) this);
        batdau.setPreferredSize(new Dimension(200, 100));
        reset = new JButton("XOÁ bản đồ");
        reset.setPreferredSize(new Dimension(200, 50));
        reset.addActionListener((ActionListener) this);
        reset.setBackground(Color.BLACK);
        giulaibando = new JButton("TẠO LẠI bản đồ");
        giulaibando.setPreferredSize(new Dimension(200, 50));
        giulaibando.addActionListener((ActionListener) this);
        giulaibando.setBackground(Color.BLACK);
        giulaibando.setEnabled(false);
        taotuongvathoigian = new JButton("Nhấn để tạo thời gian");
        taotuongvathoigian.setPreferredSize(new Dimension(200, 100));
        taotuongvathoigian.addActionListener((ActionListener) this);
        taotuongvathoigian.setBackground(Color.GREEN);
        toiuu = new JLabel("Đang tối ưu về: THỜI GIAN");
        toiuu.setPreferredSize(new Dimension(260, 30));
        toiuuduongdi = new JButton("đường đi");
        toiuuduongdi.setPreferredSize(new Dimension(100, 50));
        toiuuduongdi.addActionListener((ActionListener) this);
        toiuuduongdi.setBackground(Color.ORANGE);
        toiuuthoigian = new JButton("thời gian");
        toiuuthoigian.setPreferredSize(new Dimension(100, 50));
        toiuuthoigian.addActionListener((ActionListener) this);
        toiuuthoigian.setBackground(Color.ORANGE); 
        toiuuthoigian.setEnabled(false);
        toiuuca2 = new JButton("Cả 2");
        toiuuca2.setPreferredSize(new Dimension(80, 50));
        toiuuca2.addActionListener((ActionListener) this);
        toiuuca2.setBackground(Color.ORANGE);
        toiuuca2.setEnabled(true);
        diemdau = new JButton("Điểm ĐẦU");
        diemdau.setPreferredSize(new Dimension(100, 50));
        diemdau.setForeground(Color.red);
        diemdau.setBackground(Color.CYAN);
        diemdau.addActionListener((ActionListener) this);
        diemcuoi = new JButton("Điểm CUỐI");
        diemcuoi.setForeground(Color.red);
        diemcuoi.setBackground(Color.red);
        diemcuoi.setPreferredSize(new Dimension(100, 50));
        diemcuoi.addActionListener((ActionListener) this);
        
        labelQuangduong = new JLabel("Quãng đường đã đi:");
        labelQuangduong.setPreferredSize(new Dimension(130, 30));
        textQuangduong = new JTextField();
        textQuangduong.setEnabled(false);
        textQuangduong.setPreferredSize(new Dimension(80, 30));
        labelThoigian = new JLabel("Thời gian đã đi:");
        labelThoigian.setPreferredSize(new Dimension(130, 30));
        textThoigian = new JTextField();
        textThoigian.setEnabled(false);
        textThoigian.setPreferredSize(new Dimension(80, 30));
        vitri = new JLabel("Toạ độ con trỏ:");
        vitri.setPreferredSize(new Dimension(130, 30));
        textvitri = new JTextField();
        textvitri.setEnabled(false);
        textvitri.setPreferredSize(new Dimension(50, 30));
        timetimduong = new JLabel("Thời gian tìm đường:");
        timetimduong.setPreferredSize(new Dimension(130, 30));
        textTimetimduong = new JTextField();
        textTimetimduong.setEnabled(false);
        textTimetimduong.setPreferredSize(new Dimension(50, 30));
        
        //làm 1 cái label ảo, do k muốn tìm hiểu t bố cục
        JLabel ao1 = new JLabel("");
        ao1.setPreferredSize(new Dimension(200, 10));
        JLabel ao2 = new JLabel("");
        ao2.setPreferredSize(new Dimension(200, 20));
        JLabel ao3 = new JLabel("");
        ao3.setPreferredSize(new Dimension(200, 10));
        
        
        panel2.add(batdau, gbc);
        panel2.add(ao3, gbc);
        
        panel2.add(reset, gbc);
        panel2.add(giulaibando, gbc);
        panel2.add(ao1, gbc);
        
        panel2.add(toiuu, gbc);
        panel2.add(toiuuduongdi, gbc);
        panel2.add(toiuuthoigian, gbc);
        panel2.add(toiuuca2, gbc);
        
        panel2.add(ao2, gbc);
        
        panel2.add(taotuongvathoigian, gbc);
        panel2.add(diemdau, gbc);
        panel2.add(diemcuoi, gbc);
        
        panel2.add(labelQuangduong, gbc);
        panel2.add(textQuangduong, gbc);
        panel2.add(labelThoigian, gbc);
        panel2.add(textThoigian, gbc);
        
        panel2.add(vitri, gbc);
        panel2.add(textvitri, gbc);
        panel2.add(timetimduong, gbc);
        panel2.add(textTimetimduong, gbc);

        panel.add(panel1, gbc); //add 2 panel vào panel chính
        panel.add(panel2, gbc);
        fr.add(panel);

        fr.setVisible(true);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
            int a = (int) e.getX() / 20; //trừ cái viền bản đồ + cái viền frame
            int b = (int) e.getY() / 20; //trừ đi cái viền
            if ((a > 0 && a < nx - 1) && (b > 0 && b < ny - 1)) { //nếu chuột nhấp vào bản đồ thì
                    x = a; y = b;
                //CLICK CHUỘT TRÁI
                 if (!clickkhidangchay && SwingUtilities.isLeftMouseButton(e)){
                    if (label[x][y].getIcon() == mauxanh) { //màu xanh thì chuyển sang da trời hoặc màu thời gian
                        if(mauchuottrai == maublue)
                            Trangthaihientai[x][y] = 0;     //khong phai duyet
                        else
                            Trangthaihientai[x][y] = 3;   //đặt thời gian
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauchuottrai);
                    } else if (label[x][y].getIcon() == mauthoigian || label[x][y].getIcon() == maublue) { //ấn cái nữa thì chuyển lại thành xanh
                        if(mauchuottrai == maublue)
                            Trangthaihientai[x][y] = 1; //bỏ chọn tường, đc duyệt
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauxanh);
                    } else if(label[x][y].getIcon() == maudau){   //điểm đầu hay điểm cuối thì chuyển sang xanh và khời tạo lại toạ độ đầu, cuối
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauxanh);
                        isdiemdau = false;
                        diemdau.setEnabled(true); //cai nut lai bam duoc
                        Trangthaihientai[x][y] = 1;
                        x = -1;
                        y = -1;
                    } else {
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauxanh);
                        isdiemcuoi = false;
                        diemcuoi.setEnabled(true);
                        Trangthaihientai[x][y] = 1;
                        x = -1;
                        y = -1;
                    }
                    textvitri.setText(x + ", " + y);
                }
                else if (SwingUtilities.isRightMouseButton(e)){
                    Icon mau = label[x][y].getIcon();
                    if( mau != maudau && mau != maucuoi){
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauthoigian);
                    }
                }
                //NẾU ĐANG CHẠY MÀ CLICK và KHÔNG CÓ TRONG NHỮNG ĐIỂM ĐÃ ĐI THÌ TẠO LẠI ĐƯỜNG ĐI
                if (clickkhidangchay  && SwingUtilities.isLeftMouseButton(e)){
                    TimLaiDuongDi();
                }
                
            }
    }

    @Override
    public void mouseDragged(MouseEvent e) { //kéo chuột
        int a = (int) e.getX() / 20;
        int b = (int) e.getY() / 20;
        if ((a >= 1 && a < nx - 1) && (b >= 1 && b < ny - 1)) { //clickkhidangchay
            if (x != a || y != b) {//nằm trong layer và vào label khác thì
                    vaobien = true;     //thay đổi màu
                    x = a;
                    y = b;
                    //System.out.println("this.a = "+ this.x+ "this.b" + this.y);
                    if (!clickkhidangchay && label[x][y].getIcon() == mauxanh && SwingUtilities.isLeftMouseButton(e)) { // xanh, chuột trái thì sang đỏ
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauchuottrai);
                        if(mauchuottrai == maublue)     //nếu là tường thì mới true
                            Trangthaihientai[x][y] = 0;
                        else
                            Trangthaihientai[x][y] = 3;
                    } else if (!clickkhidangchay && (label[x][y].getIcon() == maublue || label[x][y].getIcon() == mauthoigian) && SwingUtilities.isRightMouseButton(e)) {//chuột phải, xoá
                        label[x][y].setIcon(null);
                        label[x][y].setIcon(mauxanh);
                        if(mauchuottrai == maublue)
                            Trangthaihientai[x][y] = 1;
                    }
            } else { //vẫn phần tử ấy thì không thay đổi
                vaobien = false;
            }
            
        }
        if (vaobien) {
            textvitri.setText(x + ", " + y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            batdau.setEnabled(true);
            diemdau.setEnabled(true);
            diemcuoi.setEnabled(true);
            //RESET LẠI BẢN ĐỒ và màu bản đồ
            Reset_bando();
            for (int i = 1; i < nx - 1; i++) {
                for (int j = 1; j < ny - 1; j++) {
                    label[i][j].setIcon(null);
                    label[i][j].setIcon(mauxanh);       //cho lại mau xanh
                    Trangthaihientai[i][j] = 1;  //khởi tạo  lại thuộc tính duyệt
                }
            }
            //RESET LẠI ĐƯỜNG ĐÃ ĐI
            duongDaDi.removeAll(duongDaDi);
            quangduong = 0; thoigian = 0; //reset quãng đường và thời gian
            x = -1;
            y = -1;               //khoi tao lai diem cuoi cung
            isdiemdau = false;
            isdiemcuoi = false; //khoi tao lai: chua chon diem dau, cuoi
        }
        else if(e.getSource() == giulaibando){
            batdau.setEnabled(true);
            diemdau.setEnabled(false);
            diemcuoi.setEnabled(false);
            //RESET LẠI ĐƯỜNG ĐÃ ĐI
            duongDaDi.removeAll(duongDaDi);
            quangduong = 0; thoigian = 0; //reset quãng đường và thời gian
            toadodauX = luuToadodauX; toadodauY = luuToadodauY;
            for (int i = 1; i < nx - 1; i++) {
                for (int j = 1; j < ny - 1; j++) {
                    Trangthaihientai[i][j] = LuulaibandoGoc[i][j]; //lấy lại bản đồ
                    if(LuulaibandoGoc[i][j] == 0){             //khởi tạo lại màu
                        label[i][j].setIcon(null);
                        label[i][j].setIcon(maublue);
                    }else if(LuulaibandoGoc[i][j] == 1){
                        label[i][j].setIcon(null);
                        label[i][j].setIcon(mauxanh);
                    }else if(LuulaibandoGoc[i][j] == 3){
                        label[i][j].setIcon(null);
                        label[i][j].setIcon(mauthoigian);
                    }
                    //TẠO LAI MÀU ĐẦU VÀ MÀU ĐÍCH
                    label[toadodauX][toadodauY].setIcon(null);
                    label[toadodauX][toadodauY].setIcon(maudau);
                    label[toadocuoiX][toadocuoiY].setIcon(null);
                    label[toadocuoiX][toadocuoiY].setIcon(maucuoi);
                }
            }
            x = -1;
            y = -1;               //khoi tao lai diem cuoi cung
            isdiemdau = true;
            isdiemcuoi = true;
            System.out.println("toạ lại: toạ đồ đàu: " + this.toadodauX + ", " + this.toadodauY);
        }else if (e.getSource() == batdau){
                if(isdiemcuoi && isdiemdau){
                    //KHÔNG CHO NHẤN CHUỘT KHI ĐANG TÌM ĐƯỜNG
                    clickkhidangchay = true;
                    //KHÔNG CHO ẤN 1 SỐ NÚT KHI ĐANG CHẠY
                    batdau.setEnabled(false);
                    reset.setEnabled(false);
                    giulaibando.setEnabled(false);
                    //lưu lại bản đồ
                    Luulai_bando();
                    //bắt đầu tìm đường
                    tim = new TimDuong();
                    tim.setBanDo(this);
                    tim.start();
                }
            } 
        
        //ĐỔI GIỮA MÀU THỜI GIAN VÀ MÀU BLUE
        else if (e.getSource() == taotuongvathoigian){
            if( mauchuottrai == maublue){
                mauchuottrai = mauthoigian;
                taotuongvathoigian.setText("Nhấn để tạo Tường");
                taotuongvathoigian.setForeground(Color.BLUE);
                taotuongvathoigian.setBackground(Color.BLUE);
            }else {
                mauchuottrai = maublue;
                taotuongvathoigian.setText("Nhấn để tạo thời gian");
                taotuongvathoigian.setBackground(Color.GREEN);
            }
        } else if (e.getSource() == diemdau && !isdiemdau && x >= 0 && (x!=this.toadocuoiX || y != this.toadodauY)) { //chon diem dau, chưa có điểm đầu và đã ấn vào 1 điểm
            label[x][y].setIcon(null);  //set màu
            label[x][y].setIcon(maudau);
            diemdau.setEnabled(false);  //không cho ấn nữa
            isdiemdau = true;      //đã có điểm đầu
            toadodauX = x;              //set toạ độ cho điểm đầu
            toadodauY = y;
            luuToadodauX = toadodauX; //CẦN LƯU TOẠ ĐỘ ĐẦU ĐỂ TÁI TẠO LẠI BẢN ĐỒ
                Trangthaihientai[x][y] = 1;
        } else if (e.getSource() == diemcuoi &&!isdiemcuoi && x >= 0 &&(x!= this.toadodauX || y!= this.toadodauY)) { //chon diem cuoi
            label[x][y].setIcon(null);
            label[x][y].setIcon(maucuoi);
            diemcuoi.setEnabled(false);
            this.isdiemcuoi = true;
            toadocuoiX = x;     //set toạ độ cho điểm cuối
            toadocuoiY = y;
            luuToadodauY = toadodauY;   //CẦN LƯU TOẠ ĐỘ ĐẦU ĐỂ TÁI TẠO LẠI BẢN ĐỒ
                Trangthaihientai[x][y] = 1;
        } else if (e.getSource() == toiuuduongdi){
            kieutoiuu = 0;
            toiuuduongdi.setEnabled(false);
            toiuu.setText("Đang tối ưu về: ĐƯỜNG ĐI");
            toiuuthoigian.setEnabled(true);
            toiuuca2.setEnabled(true);
            if (clickkhidangchay){ //nếu click khi đang chạy thì tìm lại đường
                chuyenKieuToiUu = true;
                TimLaiDuongDi();
            }
        } else if (e.getSource() == toiuuthoigian){
            kieutoiuu = 1;
            toiuuthoigian.setEnabled(false);
            toiuu.setText("Đang tối ưu về: THỜI GIAN");
            toiuuduongdi.setEnabled(true);
            toiuuca2.setEnabled(true);
            if (clickkhidangchay){
                chuyenKieuToiUu = true;
                TimLaiDuongDi();
            }
        } else if (e.getSource() == toiuuca2){
            kieutoiuu = 2;
            toiuuca2.setEnabled(false);
            toiuu.setText("Đang tối ưu về: cả THỜI GIAN và ĐƯỜNG ĐI");
            toiuuduongdi.setEnabled(true);
            toiuuthoigian.setEnabled(true);
            if (clickkhidangchay){
                chuyenKieuToiUu = true;
                TimLaiDuongDi();
            }
        }
        
    }
    
    private void TimLaiDuongDi(){
        //SET LẠI MÀU
        if(duongDaDi.size() != 0){
            this.label[duongDaDi.get(0).x][duongDaDi.get(0).y].setIcon(mauduongdi);
            //quangduong += 
            quangduong += tim.LayQuangDuongDaDi(duongDaDi.get(0).x, duongDaDi.get(0).y);
            thoigian += tim.LayThoiGianDaDi(duongDaDi.get(0).x, duongDaDi.get(0).y);
        }
        try{
            //RESET LẠI BẢN ĐỒ
            Update_bando();
            tim.suspend();
        }catch(Exception v){
            System.out.println("lỗi");
        }
        //biến này nhận sự kiện ĐỔI KIỂU TỐI ƯU
        if(!chuyenKieuToiUu){
                //ĐỔI MÀU ĐIỂM VỪA CLICK
                if (Trangthaihientai[x][y] == 1) { //màu xanh thì chuyển sang da trời hoặc màu thời gian
                    if(mauchuottrai == maublue){
                        Trangthaihientai[x][y] = 0;
                        LuulaibandoDuongDi[x][y] = 0;
                    } else{
                        Trangthaihientai[x][y] = 3;   //đặt thời gian
                        LuulaibandoDuongDi[x][y] = 3;
                    }
                    label[x][y].setIcon(null);
                    label[x][y].setIcon(mauchuottrai);
                } else if (Trangthaihientai[x][y] == 3 || Trangthaihientai[x][y] == 0) { //ấn cái nữa thì chuyển lại thành xanh
                    if(mauchuottrai == maublue){
                        Trangthaihientai[x][y] = 1; //bỏ chọn tường, đc duyệt
                        LuulaibandoDuongDi[x][y] = 1;
                    }
                    label[x][y].setIcon(null);
                    label[x][y].setIcon(mauxanh);
                }
        }
        else{
                chuyenKieuToiUu = false;
                clickkhidangchay = true;
        }
            //LẤY TOẠ ĐỘ HIỆN TẠI, MÀU THÌ VẪN GIỮ NGUYÊN
            try{
                this.toadodauX = tim.LayToaDoRobotX();
                this.toadodauY = tim.LayToaDoRobotY();
            }catch(Exception t){
            } finally{
                tim.stop();
                //ấn bắt đầu
                tim = new TimDuong();
                tim.setBanDo(this);
                tim.start();
            }
            
    }
    private void Luulai_bando(){
        for (int i = 1; i < nx-1; i++) {
            for (int j = 1; j < ny-1; j++) {
                LuulaibandoGoc[i][j] = Trangthaihientai[i][j];
                LuulaibandoDuongDi[i][j] = Trangthaihientai[i][j];
            }
        }
    }
    private void Reset_bando(){
        for (int i = 1; i < nx-1; i++) {
            for (int j = 1; j < ny-1; j++) {
                Trangthaihientai[i][j] = LuulaibandoGoc[i][j];
                LuulaibandoDuongDi[i][j] = LuulaibandoGoc[i][j];
            }
        }
    }
    private void Update_bando(){
        for (int i = 1; i < nx-1; i++) {
            for (int j = 1; j < ny-1; j++) {
                Trangthaihientai[i][j] = LuulaibandoDuongDi[i][j];
            }
        }
    }
    
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        
    }
    @Override
    public void mouseExited(java.awt.event.MouseEvent e) { //con trỏ chuột ra khỏi đường biên
    }


}
