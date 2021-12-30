/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trituenhantao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Huongxet {

    public int x;
    public int y;
}

class HangDoiXet {

    public int x, y;    //toạ độ
    public float fn, gn, hn;  //g(n) và f(n)
    public float thoigian; //trọng số thời gian
    //trạng thái đi qua hay chưa thì dựa vào màu
}

public class TimDuong implements Runnable {

    Thread thread = null;
    VeDuongDi ve;
    private BanDo bando = null;
    private ArrayList<Huongxet> huongxet = new ArrayList(); //lưu 8 vị trí xung quanh
    private int[] hoanhdohuongxet = {1, 0, -1, -1, -1, 0, 1, 1}; //biên độ của toạ độ các nút xung quanh
    private int[] tungdohuongxet = {1, 1, 1, 0, -1, -1, -1, 0};

    private ArrayList<HangDoiXet> HangdoiOPEN = new ArrayList();//hàng đợi nút đợi xét
    private ArrayList<HangDoiXet> HangdoiCLOSE = new ArrayList();//hàng đợi nút đã xét xong
    private int[][] toadocha;               //mảng chưa toạ độ cha của nút. có giá trị: x*n +y (x, y là toạ độ của cha)
    private boolean[][] DacoToadocha;
    private boolean[][] Namtronghangdoi;
    private ArrayList<Huongxet> duongSeDi = new ArrayList();
    private int dauX, dauY, cuoiX, cuoiY; //toạ độ đầu và toạ độ cuối
    private int hientaiX, hientaiY;
    private int dangduyetX, dangduyetY;
    private boolean isSleepFull;

    public TimDuong() {

        for (int i = 0; i < 8; i++) {   //khởi tạo biên độ các nút xung quanh
            Huongxet a = new Huongxet();
            a.x = this.hoanhdohuongxet[i];
            a.y = this.tungdohuongxet[i];
            huongxet.add(a);
        }

    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.stop();
        thread = null;
    }

    public void suspend() {
        thread.suspend();
    }

    @Override
    public void run() {
        //TÍNH THỜI GIAN TÌM ĐƯỜNG
        long start = System.currentTimeMillis();
        //khởi tạo 1 số mảng động, nên phải khai báo cùng với run
        toadocha = new int[bando.getNx() - 1][bando.getNy() - 1];
        DacoToadocha = new boolean[bando.getNx() - 1][bando.getNy() - 1];
        Namtronghangdoi = new boolean[bando.getNx() - 1][bando.getNy() - 1];

        //lấy toạ độ đầu, cuối từ đối tượng Bando
        dauX = this.getBanDo().getToadodauX();
        dauY = this.getBanDo().getToadodauY();
        cuoiX = this.getBanDo().getToadocuoiX();
        cuoiY = this.getBanDo().getToadocuoiY();

        //chèn nút khởi đầu vào hàng đợi
        HangDoiXet nutdau = new HangDoiXet();
        nutdau.x = dauX;
        nutdau.y = dauY;
        HangdoiOPEN.add(nutdau);

        //VÒNG LẶP TÌM ĐƯỜNG
        float dadiduoc = 0;
        float thoigian = 0;
        do {
            //LẤY NÚT RA TỪ HÀNG ĐỢI
            hientaiX = HangdoiOPEN.get(0).x; //lấy ra toạ độ, g(n) rồi xoá nút đầu đi
            hientaiY = HangdoiOPEN.get(0).y;
            dadiduoc = HangdoiOPEN.get(0).gn;
            thoigian = HangdoiOPEN.get(0).thoigian;

//            if(dauX != hientaiX || dauY != hientaiY) 
//                bando.setLabel_daduyet(hientaiX, hientaiY); //in màu đã duyệt
            HangdoiCLOSE.add(HangdoiOPEN.get(0));
            HangdoiOPEN.remove(0);
            //đánh dấu nút này đã duyệt
            bando.setTrangthaihientai(hientaiX, hientaiY);   //đánh dấu khong duyet nua

            //DUYỆT CÁC Ô KỀ
            for (int i = 0; i < 8; i++) {
                dangduyetX = hientaiX + huongxet.get(i).x;
                dangduyetY = hientaiY + huongxet.get(i).y;
                //NẾU LÀ ĐIỂM ĐÍCH THÌ DỪNG CHƯƠNG TRÌNH
                if (dangduyetY == cuoiY && dangduyetX == cuoiX) {
                    DungChuongTring(dadiduoc, thoigian, start, i);
                }

                //KIỂM TRA - NẾU ĐÃ DUYỆT (K PHÙ HỢP): BỎ QUA
                // nếu đã duyệt, tường thì bỏ qua: nếu màu xanh thì bỏ qua
                if (dangduyetX < 1 || dangduyetX > (bando.getNx() - 2) || dangduyetY < 1 || dangduyetY > (bando.getNy() - 2)
                        || bando.getTrangthaihientai(dangduyetX, dangduyetY) == 0) {
                    continue;
                }

                //KIỂM TRA ĐÃ NẰM TRONG HÀNG ĐỢI OPEN CHƯA, NẾU CHƯA THÌ THIẾT LẬP GIÁ TRỊ VÀ LƯU VÀO
                if (this.NutCHUANamTrongHangDoi(dadiduoc, thoigian)) {
                    //tạo màu đang duyệt
//                        bando.setLabel_dangduyet(dangduyetX, dangduyetY); //màu xanh nhạt
                    HangDoiXet tmp = new HangDoiXet();
                    //THIẾT LẬP CÁC TRƯỜNG ĐỂ LƯU VÀO HÀNG ĐỢI
                    tmp = ThietLapGiaTri(tmp, dadiduoc, i, thoigian);
                    //CHÈN PHẦN TỬ NÀY VÀO HÀNG ĐỢI
                    ChenVaoHangDoi(tmp);
                }
            }
        } while (!HangdoiOPEN.isEmpty()); //làm đến khi nào OPEN hết(k tìm thấy) hoặc đến đích

        //TRƯỜNG HỢP KHÔNG TÌM THẤY ĐƯỜNG ĐI
        long end = System.currentTimeMillis(); //set vào textField thời gian tìm đường
        bando.setThoigiantimduong(end - start);
        if (HangdoiOPEN.isEmpty()) {
            ThongBaoDenDich(false, 0, 0);
            bando.setReset();
            reset();
        }

    }

    void ve_duong_di(int dangduyetX, int dangduyetY) {
        try {
            //vẽ lại đường đi: duongSeDi
            //toadocha[dangduyetX][dangduyetY] là điểm sát cuối, bắt đầu duyệt từ đây duyệt lên cha của nó
            Huongxet temp = new Huongxet();
            temp.x = dangduyetX;
            temp.y = dangduyetY;
            duongSeDi.add(temp);
            do {
                int tong = toadocha[dangduyetX][dangduyetY];
                temp = new Huongxet();
                temp.x = tong / (bando.getNx() - 2) + 1;
                temp.y = tong % (bando.getNx() - 2) + 1;
                duongSeDi.add(temp);
                dangduyetX = temp.x;
                dangduyetY = temp.y;  //System.out.println(dangduyetX + ", " + dangduyetY);
            } while (dangduyetX != dauX || dangduyetY != dauY);  //dangduyetX != 0

            //bây giờ, phần tử cuối của duongSeDi là toạ độ đầu ,ta chỉ cần duyệt theo chiều ngược lại và trừ toạ độ đầu ra
            if (!duongSeDi.isEmpty()) {
                bando.setDuongDi(duongSeDi.get(duongSeDi.size() - 1).x, duongSeDi.get(duongSeDi.size() - 1).y); //set vào đường đã đi
                duongSeDi.remove(duongSeDi.size() - 1);   //chua nut dau, xoa luon
            }
            if (!duongSeDi.isEmpty()) {
                for (int i = duongSeDi.size() - 1; i >= 0; i--) {
                    
                    int tmp = bando.getGiatriNutbandau(duongSeDi.get(i).x, duongSeDi.get(i).y);
                    //ADD PHẦN TỬ NÀY VÀO DƯỜNG ĐÃ ĐI
                    bando.setDuongDi(duongSeDi.get(i).x, duongSeDi.get(i).y);
                    //isSleepFull = false;
                    bando.setMaurobot(duongSeDi.get(i).x, duongSeDi.get(i).y);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TimDuong.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    if (tmp == 3){ //nếu là đi qua màu thời gian thì cho màu khác
                        bando.setMauduongdiQuathoigian(duongSeDi.get(i).x, duongSeDi.get(i).y);
                        //nếu đã đi qua điểm đầu thì set lại màu cho điểm đầu
                    } else if (bando.IsDiemDau(duongSeDi.get(i).x, duongSeDi.get(i).y) ){
                            bando.setMauDiemDau(duongSeDi.get(i).x, duongSeDi.get(i).y);
                    } else
                        bando.setMauduongdi(duongSeDi.get(i).x, duongSeDi.get(i).y);
                    setTextQuangduongVaThoigian(duongSeDi.get(i).x, duongSeDi.get(i).y);
                    // XOÁ Ở ĐƯỜNG SẼ ĐI
                    duongSeDi.remove(i);  //phai xoa di vi k xoa di, sau khi reset van con
                }
            }

            bando.setReset();   //Enabled nut reset

            //CHẠY XONG, KHÔNG LẮNG NGHE CLICK CHUỘT ĐỂ TÌM LẠI NỮA
            bando.setClickkhidangchay(false);
        } catch (Exception e) {
            System.out.println("Lỗi vẽ đường đi");
        }

    }

    private void reset() {
        try {
            //reset lai  toadocha
            for (int i = 0; i < bando.getNx() - 2; i++) { //toadocha = new int [bando.getN()-1][bando.getN()-1];
                for (int j = 0; j < bando.getNy() - 2; j++) {
                    toadocha[i][j] = 0;
                    DacoToadocha[i][j] = false;
                    Namtronghangdoi[i][j] = false;
                }
            }
            //reset lại các hàng đợi
            HangdoiOPEN.removeAll(HangdoiOPEN);
            HangdoiCLOSE.removeAll(HangdoiCLOSE);
        } catch (Exception e) {
            System.out.println("lỗi reset");
        }
    }

    private void ThongBaoDenDich(boolean a, float x, float thoigian) {
        if (a) {
            JOptionPane.showConfirmDialog(null, "quãng đường: " + x + "\nThời gian: " + thoigian, "thông tin", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showConfirmDialog(null, "không tìm thấy đường", "thông tin", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }

    private float Khoangcach(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y2 - y1) * (y2 - y1));
    }

    private boolean NutCHUANamTrongHangDoi(float dadiduoc, float thoigian) {
        boolean sualai = false;
        try {
            //LƯU TOẠ ĐỘ NÚT CHA LẠI ĐỂ SAU DỄ LẦN TỪ DƯỚI LÊN MÀ VẼ LẠI HÌNH, NÓ LƯU TỪ ĐẦU ĐẾN SÁT ĐÍCH
            //co toadocha tuc la da co trong HangdoiOPEN
            if (DacoToadocha[dangduyetX][dangduyetY]) { //Nếu đã có toạ độ nút cha từ trước thì
                Namtronghangdoi[dangduyetX][dangduyetY] = true; // đã nằm trong hàng đợi rồi
                int chiso = 0;
                //tìm toạ độ của nút cha
                int tong = toadocha[dangduyetX][dangduyetY];
                int xcha = tong / (bando.getNx() - 2) + 1;
                int ycha = tong % (bando.getNx() - 2) + 1;
                //tìm toạ độ nút cha, nằm ở hàng đợi CLOSE
                for (int j = 0; j < HangdoiCLOSE.size(); j++) {      //tìm
                    if (HangdoiCLOSE.get(j).x == xcha && HangdoiCLOSE.get(j).y == ycha) {
                        chiso = j;
                        break;
                    }
                }
                if (bando.getKieutoiuu() == 0) {
                    //so sánh xem gn mới với gn cũ cái nào nhỏ hơn thì cho toạ độ cha theo đường đấy
                    if ((HangdoiCLOSE.get(chiso).gn + this.Khoangcach(xcha, ycha, dangduyetX, dangduyetY))
                            > (dadiduoc + this.Khoangcach(hientaiX, hientaiY, dangduyetX, dangduyetY))) {
                        sualai = true;
                        toadocha[dangduyetX][dangduyetY] = (hientaiX - 1) * (bando.getNx() - 2) + (hientaiY - 1); //chuyển từ 1->40 sang 0->39
                        DacoToadocha[dangduyetX][dangduyetY] = true;
                    }
                } else if (bando.getKieutoiuu() == 1) {
                    if ((HangdoiCLOSE.get(chiso).thoigian + this.Khoangcach(xcha, ycha, dangduyetX, dangduyetY) * bando.getTrangthaihientai(dangduyetX, dangduyetY))
                            > (thoigian + this.Khoangcach(hientaiX, hientaiY, dangduyetX, dangduyetY) * bando.getTrangthaihientai(dangduyetX, dangduyetY))) {
                        sualai = true;
                        toadocha[dangduyetX][dangduyetY] = (hientaiX - 1) * (bando.getNx() - 2) + (hientaiY - 1); //chuyển từ 1->40 sang 0->39
                        DacoToadocha[dangduyetX][dangduyetY] = true;
                    }
                } else { //ca2
                    if ((HangdoiCLOSE.get(chiso).thoigian + HangdoiCLOSE.get(chiso).gn + this.Khoangcach(xcha, ycha, dangduyetX, dangduyetY) * bando.getTrangthaihientai(dangduyetX, dangduyetY))
                            > (thoigian + dadiduoc + this.Khoangcach(hientaiX, hientaiY, dangduyetX, dangduyetY) * bando.getTrangthaihientai(dangduyetX, dangduyetY))) {
                        sualai = true;
                        toadocha[dangduyetX][dangduyetY] = (hientaiX - 1) * (bando.getNx() - 2) + (hientaiY - 1); //chuyển từ 1->40 sang 0->39
                        DacoToadocha[dangduyetX][dangduyetY] = true;
                    }
                }
            } else {
                sualai = true;
                toadocha[dangduyetX][dangduyetY] = (hientaiX - 1) * (bando.getNx() - 2) + (hientaiY - 1); //chuyển từ 1->40 sang 0->39
                DacoToadocha[dangduyetX][dangduyetY] = true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi NutCHUANamTrongHangDoi()");
        }
        return sualai;
    }

    private HangDoiXet ThietLapGiaTri(HangDoiXet tmp, float dadiduoc, int i, float thoigian) {
        //THIẾT LẬP CÁC TRƯỜNG ĐỂ LƯU VÀO HÀNG ĐỢI
        try {
            int a = Math.abs(this.cuoiX - this.dangduyetX);
            int b = Math.abs(this.cuoiY - this.dangduyetY);
            //======== phần quan trọng: đánh giá heroustic ==========
            tmp.hn = (float) (Math.min(a, b) * (float) Math.sqrt(2)) + Math.abs((float) (b - a)); //tinh và lưu vào hn, gn, toa do cha
            //=========================
            if (i == 1 || i == 3 || i == 5 || i == 7) {       //nếu hướng chéo thì nhân thêm căn 2
                tmp.gn = dadiduoc + 1;
                tmp.thoigian = thoigian + bando.getTrangthaihientai(dangduyetX, dangduyetY);
            } else {
                tmp.gn = dadiduoc + (float) Math.sqrt(2);
                tmp.thoigian = thoigian + (float) Math.sqrt(2) * bando.getTrangthaihientai(dangduyetX, dangduyetY);
            }
            if (bando.getKieutoiuu() == 0) //tuy kieu chon duong hay chon thoi gian
            {
                tmp.fn = tmp.hn + tmp.gn;
            } else if (bando.getKieutoiuu() == 1) {
                tmp.fn = tmp.hn + tmp.thoigian;
            } else { //ca2
                tmp.fn = tmp.gn + tmp.thoigian + tmp.hn;
            }
            tmp.x = dangduyetX;
            tmp.y = dangduyetY;
        } catch (Exception e) {
            System.out.println("lỗi ThietLapGiaTri()");
        }
        return tmp;
    }

    private void ChenVaoHangDoi(HangDoiXet tmp) {
        //CHÈN VÀO HÀNG ĐỢI
        try {
            int vitrichen = 0;
            if (!HangdoiOPEN.isEmpty()) {
                int m;
                for (m = 0; m < HangdoiOPEN.size(); m++) {
                    if (HangdoiOPEN.get(m).fn > tmp.fn) {
                        break;
                    }
                }
                vitrichen = m;

            }
            HangdoiOPEN.add(vitrichen, tmp);
        } catch (Exception e) {
            System.out.println("Lỗi ChenVaoHangDoi()");
        }
    }

    private void DungChuongTring(float dadiduoc, float thoigian, long start, int i) {

        long end = System.currentTimeMillis();
        bando.setThoigiantimduong(end - start);
        //lưu lại quãng đường đi được
        float tmp = (hientaiX == dangduyetX || hientaiY == dangduyetY) ? 1 : (float) Math.sqrt(2);
        dadiduoc += tmp;
        thoigian += tmp;
        //phục vụ cho việc vẽ lại đường đi
        dangduyetX -= huongxet.get(i).x;
        dangduyetY -= huongxet.get(i).y;
        //ve_duong_di( dangduyetX, dangduyetY);\
        ve = new VeDuongDi(dangduyetX, dangduyetY, this);
        ve.run();
        bando.setTextQuangduong(bando.getQuangduong() + dadiduoc);
        bando.setTextThoigian(bando.getThoigian() + thoigian);
        ThongBaoDenDich(true, bando.getQuangduong() + dadiduoc, bando.getThoigian() + thoigian);
        reset();
        stop();

    }

    void batbuocdung() {
        this.stop();
    }

    public float LayQuangDuongDaDi(int x, int y) {
        //HangdoiCLOSE
        for (int i = 0; i < HangdoiCLOSE.size(); i++) {
            if (HangdoiCLOSE.get(i).x == x && HangdoiCLOSE.get(i).y == y) {
                return HangdoiCLOSE.get(i).gn;
            }
        }
        return 0;
    }

    public float LayThoiGianDaDi(int x, int y) {
        for (int i = 0; i < HangdoiCLOSE.size(); i++) {
            if (HangdoiCLOSE.get(i).x == x && HangdoiCLOSE.get(i).y == y) {
                return HangdoiCLOSE.get(i).thoigian;
            }
        }
        return 0;
    }

    public void setTextQuangduongVaThoigian(int x, int y) {
        float quangduongHientai = bando.getQuangduong();
        float thoigianHientai = bando.getThoigian();
        int chiso = 0;
        for (int i = 0; i < HangdoiCLOSE.size(); i++) {
            if (HangdoiCLOSE.get(i).x == x && HangdoiCLOSE.get(i).y == y) {
                chiso = i;
                break;
            }
        }
        bando.setTextQuangduong(HangdoiCLOSE.get(chiso).gn + quangduongHientai);
        bando.setTextThoigian(HangdoiCLOSE.get(chiso).thoigian + thoigianHientai);
    }

    public int LayToaDoRobotX() {
        return duongSeDi.get(duongSeDi.size() - 1).x;
    }

    public int LayToaDoRobotY() {
        return duongSeDi.get(duongSeDi.size() - 1).y;
    }

    //==============
    public void setBanDo(BanDo a) {
        this.bando = a;
    }

    private BanDo getBanDo() {
        return bando;
    }
}
