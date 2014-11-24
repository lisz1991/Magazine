package com.git.magazine.entity;

import java.io.Serializable;

public class ZaZhi implements Serializable {
	
	private static final long serialVersionUID = 4103731575001824964L;
	
	public String curName;
	public String urlRead;
	public String urlDetail;
	public String urlImage;
	public String urlTotal;
	public String detailName;
	public String detailCurrent;
	public String detailTime;
	public String detailUpdate;
	public String detailTotal;
	public String detailPrice;
	public String pageTotal;

	public ZaZhi() {
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
