
package com.appswallet.jamatfinder.Models;

import java.util.HashMap;
import java.util.Map;



public class NamazTime {

    private Integer code;

    private String status;

    private Data data;


    /**
     * 
     * @return
     *     The code
     */

    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The status
     */

    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The data
     */

    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */

    public void setData(Data data) {
        this.data = data;
    }



}
