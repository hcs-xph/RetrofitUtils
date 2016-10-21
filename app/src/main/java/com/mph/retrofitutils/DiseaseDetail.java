package com.mph.retrofitutils;

/**
 * 测试post请求返回实体类(即data字段对应数据)
 * Created by：hcs on 2016/10/19 11:28
 * e_mail：aaron1539@163.com
 */
public class DiseaseDetail {
    private String name;
    private String clinicalExamination;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClinicalExamination() {
        return clinicalExamination;
    }

    public void setClinicalExamination(String clinicalExamination) {
        this.clinicalExamination = clinicalExamination;
    }

    @Override
    public String toString() {
        return "DiseaseDetail{" +
                "name='" + name + '\'' +
                ", clinicalExamination='" + clinicalExamination + '\'' +
                '}';
    }
}
