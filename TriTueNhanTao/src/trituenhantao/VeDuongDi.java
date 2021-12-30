/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trituenhantao;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toiyeubreakdance
 */
public class VeDuongDi implements Runnable{
    private int dangduyetX, dangduyetY;
    private TimDuong tim;
    
    
    public VeDuongDi(int dangduyetX, int dangduyetY, TimDuong tim){
        this.dangduyetX = dangduyetX;
        this.dangduyetY = dangduyetY;
        this.tim = tim;
    }
    @Override
    public void run() {
                
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VeDuongDi.class.getName()).log(Level.SEVERE, null, ex);
        }
        tim.ve_duong_di( dangduyetX, dangduyetY);
    }
}