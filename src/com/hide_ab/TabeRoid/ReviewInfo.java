package com.hide_ab.TabeRoid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ReviewInfo {
	private String NickName;
	private String VisitDate;
	private String ReviewDate;
	private String UseType;
	private String Situations;
	private String TotalScore;
	private String TasteScore;
	private String ServiceScore;
	private String MoodScore;
	private String DinnerPrice;
	private String LunchPrice;
	private String Title;
	private String Comment;
	private String PcSiteUrl;
	private String MobileSiteUrl;
	private Bitmap TotalScoreStar;
	private Bitmap TasteScoreStar;
	private Bitmap ServiceScoreStar;
	private Bitmap MoodScoreStar;
	private Bitmap StarBack;
	private Bitmap StarFront;

	public ReviewInfo(Bitmap StarBack, Bitmap StarFront) {
		this.NickName = "";
		this.VisitDate = "";
		this.ReviewDate = "";
		this.UseType = "";
		this.Situations = "";
		this.TotalScore = "";
		this.TasteScore = "";
		this.ServiceScore = "";
		this.MoodScore = "";
		this.DinnerPrice = "";
		this.LunchPrice = "";
		this.Title = "";
		this.Comment = "";
		this.PcSiteUrl = "";
		this.MobileSiteUrl = "";
		this.StarBack  = StarBack;
		this.StarFront = StarFront;
	}

	public String getNickName() {
		return this.NickName;
	}

	public void setNickName(String NickName) {
		this.NickName = NickName;
	}

	public String getVisitDate() {
		return this.VisitDate;
	}

	public void setVisitDate(String VisitDate) {
		this.VisitDate = VisitDate;
	}

	public String getReviewDate() {
		return this.ReviewDate;
	}

	public void setReviewDate(String ReviewDate) {
		this.ReviewDate = ReviewDate;
	}

	public String getUseType() {
		return this.UseType;
	}

	public void setUseType(String UseType) {
		this.UseType = UseType;
	}

	public String getSituations() {
		return this.Situations;
	}

	public void setSituations(String Situations) {
		this.Situations = Situations;
	}

	public String getTotalScore() {
		return this.TotalScore;
	}

	public void setTotalScore(String TotalScore) {
		this.TotalScore = TotalScore;
		// ï]âøÉ}Å[ÉNê∂ê¨
		this.TotalScoreStar = this.createMarkStar(TotalScore);
	}

	public String getTasteScore() {
		return this.TasteScore;
	}

	public void setTasteScore(String TasteScore) {
		this.TasteScore = TasteScore;
		// ï]âøÉ}Å[ÉNê∂ê¨
		this.TasteScoreStar = this.createMarkStar(TasteScore);
	}

	public String getServiceScore() {
		return this.ServiceScore;
	}

	public void setServiceScore(String ServiceScore) {
		this.ServiceScore = ServiceScore;
		// ï]âøÉ}Å[ÉNê∂ê¨
		this.ServiceScoreStar = this.createMarkStar(ServiceScore);
	}

	public String getMoodScore() {
		return this.MoodScore;
	}

	public void setMoodScore(String MoodScore) {
		this.MoodScore = MoodScore;
		// ï]âøÉ}Å[ÉNê∂ê¨
		this.MoodScoreStar = this.createMarkStar(MoodScore);
	}

	public String getDinnerPrice() {
		return this.DinnerPrice;
	}

	public void setDinnerPrice(String DinnerPrice) {
		this.DinnerPrice = DinnerPrice;
	}

	public String getLunchPrice() {
		return this.LunchPrice;
	}

	public void setLunchPrice(String LunchPrice) {
		this.LunchPrice = LunchPrice;
	}

	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getComment() {
		return this.Comment;
	}

	public void setComment(String Comment) {
		this.Comment = Comment;
	}

	public String getPcSiteUrl() {
		return this.PcSiteUrl;
	}

	public void setPcSiteUrl(String PcSiteUrl) {
		this.PcSiteUrl = PcSiteUrl;
	}

	public String getMobileSiteUrl() {
		return this.MobileSiteUrl;
	}

	public void setMobileSiteUrl(String MobileSiteUrl) {
		this.MobileSiteUrl = MobileSiteUrl;
	}

	public Bitmap getTotalScoreStar() {
		return this.TotalScoreStar;
	}

	public Bitmap getTasteScoreStar() {
		return this.TasteScoreStar;
	}

	public Bitmap getServiceScoreStar() {
		return this.ServiceScoreStar;
	}

	public Bitmap getMoodScoreStar() {
		return this.MoodScoreStar;
	}

	// ï]âøÉ}Å[ÉNê∂ê¨
	private Bitmap createMarkStar(String Score) {
		// Ç∆ÇËÇ†Ç¶Ç∏îwåiï™Çê›íË
		int width  = this.StarBack.getWidth();
		int height = this.StarBack.getHeight();
		Bitmap MarkStar = Bitmap.createBitmap(this.StarBack, 0, 0, width, height);
		// ï]âøï™ÇÃBitmapÇê∂ê¨
		width  = (int)(this.StarFront.getWidth() * (Float.parseFloat(Score) / 5));
		Bitmap Tmp = Bitmap.createBitmap(this.StarFront, 0, 0, width, height);
		// çáê¨
		Canvas offScreen = new Canvas(MarkStar);
		offScreen.drawBitmap(Tmp, 0, 0, (Paint)null);

		return(MarkStar);
	}
}