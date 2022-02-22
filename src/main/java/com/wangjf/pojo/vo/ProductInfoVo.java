package com.wangjf.pojo.vo;


public class ProductInfoVo {
     //商品名称
    private String proName;
     //商品类型
    private Integer proType;
     //最低价格
    private Integer proLoPrice;
     //最高价格
    private Integer proHiPrice;
     //页码
    private Integer page = 1;

    public ProductInfoVo() {
    }

    public ProductInfoVo(String proName, Integer proType, Integer proLoPrice, Integer proHiPrice, Integer page) {
        this.proName = proName;
        this.proType = proType;
        this.proLoPrice = proLoPrice;
        this.proHiPrice = proHiPrice;
        this.page = page;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getProType() {
        return proType;
    }

    public void setProType(Integer proType) {
        this.proType = proType;
    }

    public Integer getProLoPrice() {
        return proLoPrice;
    }

    public void setProLoPrice(Integer proLoPrice) {
        this.proLoPrice = proLoPrice;
    }

    public Integer getProHiPrice() {
        return proHiPrice;
    }

    public void setProHiPrice(Integer proHiPrice) {
        this.proHiPrice = proHiPrice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "proName='" + proName + '\'' +
                ", proType=" + proType +
                ", proLoPrice=" + proLoPrice +
                ", proHiPrice=" + proHiPrice +
                ", page=" + page +
                '}';
    }
}
