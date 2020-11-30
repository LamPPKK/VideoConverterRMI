/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Flag;

import com.sun.source.doctree.SerialDataTree;
import java.io.Serializable;

/**
 *
 * @author DucVu
 */
public class Flag implements Serializable{

    static final long serialVersionUID = 1L;
    public boolean value=false;

    public void setValue() {
        this.value = true;
    }

}
