package com.mph.retrofitutils;

/**
 * 测试文件上传成功返回实体类
 * Created by：hcs on 2016/10/20 11:33
 * e_mail：aaron1539@163.com
 */
public class User {


    /**
     * address :
     * avatar : /userrole/bad78004-17db-45b4-a8fc-8ef606994346.png
     * height : 0
     * realname : 公民
     * userid : 285
     * waist : 0
     * weight : 0
     */

    private DataBean data;
    /**
     * data : {"address":"","avatar":"/userrole/bad78004-17db-45b4-a8fc-8ef606994346.png","height":0,"realname":"公民","userid":285,"waist":0,"weight":0}
     * msg : 修改成功
     * status : 0
     */

    private String msg;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        private String address;
        private String avatar;
        private int height;
        private String realname;
        private int userid;
        private int waist;
        private int weight;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getWaist() {
            return waist;
        }

        public void setWaist(int waist) {
            this.waist = waist;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "address='" + address + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", height=" + height +
                    ", realname='" + realname + '\'' +
                    ", userid=" + userid +
                    ", waist=" + waist +
                    ", weight=" + weight +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
