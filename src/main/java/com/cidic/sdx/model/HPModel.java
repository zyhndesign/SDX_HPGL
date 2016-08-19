package com.cidic.sdx.model;

import java.io.Serializable;
import java.util.List;

public class HPModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -980151689545332204L;

	private int id;
	private String hp_num; //货品编号
	private String hpName; //货品名称
	private String brand; //品牌
	private String category;//品类
	private String size;//尺码类别
	private String color;//颜色
	private String price;//标准价格
	private String proxyPrice;//代理价格
	private String fPrice;//价F(联营)					
	private String sPrice;//价S(外围)
	private String tPrice;//价T(高端)
	private String f1Price;//价F(高端)-进价
	private String f2Price;//价F(联营)-进价
	private String f3Price;//价S(外围)-进价
	
	private String createTime;//建档时间
	private String state;//状态
	private String timeCategory;//时间分类
	private String remark; //备注
	private String unit;//单位
	private String isPanDian;//是否盘点
	private String kuanXing; //款型
	private String banXing;//版型
	
	private String upDown; //上下装划分
	private String huoPan; //货盘	
	private String cunhuo_type; //存货类型	
	private String priceSegment; //价格段
	private String productionType; //生产方式	
	private String releventMetting; //关联订货会	
	private String mettingTime; //订货会交期	
	private String productionArea; //产地	
	private String entryPerson ;//录入人
	private String entryTime; //录入日期	
	private String updatePerson; //修改人
	private String updateTime;//修改日期	
	private String effectPerson;//生效人
	private String effectTime;//生效日期	
	private String failurePerson;//失效人
	private String failureTime; //失效日期

	private String imageUrl1;//
	private String imageUrl2;//
	private String imageUrl3;//
	
	private String brandList;
	private String categoryList;
	private String sizeList;
	private String colorList;
	
	public String getHp_num() {
		return hp_num;
	}
	public void setHp_num(String hp_num) {
		this.hp_num = hp_num;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getImageUrl1() {
		return imageUrl1;
	}
	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}
	public String getImageUrl2() {
		return imageUrl2;
	}
	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}
	public String getImageUrl3() {
		return imageUrl3;
	}
	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrandList() {
		return brandList;
	}
	public void setBrandList(String brandList) {
		this.brandList = brandList;
	}
	public String getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}
	public String getSizeList() {
		return sizeList;
	}
	public void setSizeList(String sizeList) {
		this.sizeList = sizeList;
	}
	public String getColorList() {
		return colorList;
	}
	public void setColorList(String colorList) {
		this.colorList = colorList;
	}
	public String getHpName() {
		return hpName;
	}
	public void setHpName(String hpName) {
		this.hpName = hpName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTimeCategory() {
		return timeCategory;
	}
	public void setTimeCategory(String timeCategory) {
		this.timeCategory = timeCategory;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getIsPanDian() {
		return isPanDian;
	}
	public void setIsPanDian(String isPanDian) {
		this.isPanDian = isPanDian;
	}
	public String getKuanXing() {
		return kuanXing;
	}
	public void setKuanXing(String kuanXing) {
		this.kuanXing = kuanXing;
	}
	public String getBanXing() {
		return banXing;
	}
	public void setBanXing(String banXing) {
		this.banXing = banXing;
	}
	public String getUpDown() {
		return upDown;
	}
	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}
	public String getHuoPan() {
		return huoPan;
	}
	public void setHuoPan(String huoPan) {
		this.huoPan = huoPan;
	}
	public String getCunhuo_type() {
		return cunhuo_type;
	}
	public void setCunhuo_type(String cunhuo_type) {
		this.cunhuo_type = cunhuo_type;
	}
	public String getPriceSegment() {
		return priceSegment;
	}
	public void setPriceSegment(String priceSegment) {
		this.priceSegment = priceSegment;
	}
	public String getProductionType() {
		return productionType;
	}
	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}
	public String getReleventMetting() {
		return releventMetting;
	}
	public void setReleventMetting(String releventMetting) {
		this.releventMetting = releventMetting;
	}
	public String getMettingTime() {
		return mettingTime;
	}
	public void setMettingTime(String mettingTime) {
		this.mettingTime = mettingTime;
	}
	public String getProductionArea() {
		return productionArea;
	}
	public void setProductionArea(String productionArea) {
		this.productionArea = productionArea;
	}
	public String getEntryPerson() {
		return entryPerson;
	}
	public void setEntryPerson(String entryPerson) {
		this.entryPerson = entryPerson;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getEffectPerson() {
		return effectPerson;
	}
	public void setEffectPerson(String effectPerson) {
		this.effectPerson = effectPerson;
	}
	public String getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}
	public String getFailurePerson() {
		return failurePerson;
	}
	public void setFailurePerson(String failurePerson) {
		this.failurePerson = failurePerson;
	}
	public String getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProxyPrice() {
		return proxyPrice;
	}
	public void setProxyPrice(String proxyPrice) {
		this.proxyPrice = proxyPrice;
	}
	public String getfPrice() {
		return fPrice;
	}
	public void setfPrice(String fPrice) {
		this.fPrice = fPrice;
	}
	public String getsPrice() {
		return sPrice;
	}
	public void setsPrice(String sPrice) {
		this.sPrice = sPrice;
	}
	public String gettPrice() {
		return tPrice;
	}
	public void settPrice(String tPrice) {
		this.tPrice = tPrice;
	}
	public String getF1Price() {
		return f1Price;
	}
	public void setF1Price(String f1Price) {
		this.f1Price = f1Price;
	}
	public String getF2Price() {
		return f2Price;
	}
	public void setF2Price(String f2Price) {
		this.f2Price = f2Price;
	}
	public String getF3Price() {
		return f3Price;
	}
	public void setF3Price(String f3Price) {
		this.f3Price = f3Price;
	}
	
	
}
