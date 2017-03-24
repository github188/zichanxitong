package com.szcomtop.meal.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuming on 16/12/1.
 * 未经作者书面同意，不得转发，复制，修改
 * 联系：hb.wuming@qq.com
 */

public class CommonOperateResult  implements Serializable {


    /**
     * state : 1
     * message : 入库成功
     * data : [{"id":"12","rfid_code":"E200001529170046202044A2","status":1},{"id":"13","rfid_code":"E2000015291700292020445E","status":1},{"id":"20","rfid_code":"E200001529170029202affafa","status":1},{"id":0,"rfid_code":"E200001529170029202afafkaj","status":-1},{"id":0,"rfid_code":"E200001529170029202affaf898","status":-1}]
     */

    public int state;
    public String message;
    /**
     * id : 12
     * rfid_code : E200001529170046202044A2
     * status : 1
     */

    public List<AssetInfo> data;
    


}
