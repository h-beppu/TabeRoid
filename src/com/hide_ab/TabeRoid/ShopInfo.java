package com.hide_ab.TabeRoid;

import android.graphics.Bitmap;

public class ShopInfo {
	private String Rcd;
	private String RestaurantName;
	private String TabelogUrl;
	private String TotalScore;
	private String TasteScore;
	private String ServiceScore;
	private String MoodScore;
	private String Situation;
	private String Category;
	private String Station;
	private Bitmap Photo;

	public ShopInfo() {
		Rcd = "";
		RestaurantName = "";
		TabelogUrl = "";
		TotalScore = "";
		TasteScore = "";
		ServiceScore = "";
		MoodScore = "";
		Situation = "";
		Category = "";
		Station = "";
		Photo = null;
	}

	public String getRcd() {
		return this.Rcd;
	}

	public void setRcd(String Rcd) {
		this.Rcd = Rcd;
	}

	public String getRestaurantName() {
		return this.RestaurantName;
	}

	public void setRestaurantName(String RestaurantName) {
		this.RestaurantName = RestaurantName;
	}

	public String getTabelogUrl() {
		return this.TabelogUrl;
	}

	public void setTabelogUrl(String TabelogUrl) {
		this.TabelogUrl = TabelogUrl;
	}

	public String getTotalScore() {
		return this.TotalScore;
	}

	public void setTotalScore(String TotalScore) {
		this.TotalScore = TotalScore;
	}

	public String getTasteScore() {
		return this.TasteScore;
	}

	public void setTasteScore(String TasteScore) {
		this.TasteScore = TasteScore;
	}

	public String getServiceScore() {
		return this.ServiceScore;
	}

	public void setServiceScore(String ServiceScore) {
		this.ServiceScore = ServiceScore;
	}

	public String getMoodScore() {
		return this.MoodScore;
	}

	public void setMoodScore(String MoodScore) {
		this.MoodScore = MoodScore;
	}

	public String getSituation() {
		return this.Situation;
	}

	public void setSituation(String Situation) {
		this.Situation = Situation;
	}

	public String getCategory() {
		return this.Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	public void setStation(String Station) {
		this.Station = Station;
	}

	public String getStation() {
		return this.Station;
	}

	public void setPhoto(Bitmap Photo) {
		this.Photo = Photo;
	}

	public Bitmap getPhoto() {
		return this.Photo;
	}
}