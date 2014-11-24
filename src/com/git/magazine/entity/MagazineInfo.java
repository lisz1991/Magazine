package com.git.magazine.entity;

import java.io.Serializable;

public class MagazineInfo implements Serializable {
	
	private static final long serialVersionUID = 4103731575001824964L;
	
	public String curName;  //名称
	public String urlRead;   //阅读地址
	public String urlDetail;	//详情页地址
	public String urlImage;		//封面图片地址
	public String urlTotal;		//总期次地址
	public String detailName;	//详细名称
	public String detailCurrent;	//当前期次
	public String detailTime;		//本期更新时间
	public String detailUpdate;		//更新间隔
	public String detailTotal;		//总期次
	public String detailPrice;		//纸质版价格
	public String pageTotal;		//本期总页数

	public MagazineInfo() {
		super();
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public String getUrlRead() {
		return urlRead;
	}

	public void setUrlRead(String urlRead) {
		this.urlRead = urlRead;
	}

	public String getUrlDetail() {
		return urlDetail;
	}

	public void setUrlDetail(String urlDetail) {
		this.urlDetail = urlDetail;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getUrlTotal() {
		return urlTotal;
	}

	public void setUrlTotal(String urlTotal) {
		this.urlTotal = urlTotal;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getDetailCurrent() {
		return detailCurrent;
	}

	public void setDetailCurrent(String detailCurrent) {
		this.detailCurrent = detailCurrent;
	}

	public String getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

	public String getDetailUpdate() {
		return detailUpdate;
	}

	public void setDetailUpdate(String detailUpdate) {
		this.detailUpdate = detailUpdate;
	}

	public String getDetailTotal() {
		return detailTotal;
	}

	public void setDetailTotal(String detailTotal) {
		this.detailTotal = detailTotal;
	}

	public String getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(String detailPrice) {
		this.detailPrice = detailPrice;
	}

	public String getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(String pageTotal) {
		this.pageTotal = pageTotal;
	}

	@Override
	public String toString() {
		return "ZaZhi [curName=" + curName + ", urlRead=" + urlRead
				+ ", urlDetail=" + urlDetail + ", urlImage=" + urlImage
				+ ", urlTotal=" + urlTotal + ", detailName=" + detailName
				+ ", detailCurrent=" + detailCurrent + ", detailTime="
				+ detailTime + ", detailUpdate=" + detailUpdate
				+ ", detailTotal=" + detailTotal + ", detailPrice="
				+ detailPrice + "]";
	}
}
