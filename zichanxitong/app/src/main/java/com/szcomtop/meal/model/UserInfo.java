package com.szcomtop.meal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by wuming on 16/11/23.
 */

@DatabaseTable(tableName = "tb_user")
public class UserInfo {


    /**
     * id : 1
     * user_name : admin
     * pass_word : 191d175eed93edbce89700f8322c1b98
     * mobile : 13400000000
     * email : aa@qq.com
     * number : dddd
     * office_id : 11
     */

    @DatabaseField(columnName = "id",id = true)
    public String id;

    @DatabaseField(columnName = "user_name")
    public String user_name;

    @DatabaseField(columnName = "pass_word")
    public String pass_word;

    @DatabaseField(columnName = "mobile")
    public String mobile;

    @DatabaseField(columnName = "email")
    public String email;

    @DatabaseField(columnName = "number")
    public String number;

    @DatabaseField(columnName = "office_id")
    public String office_id;

    public List<School>  rbac_school;

    public UserInfo() {
    }

    public   class  School {

        public   String   school_id ;

        public   String   school_name ;

    }

}
