package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShopDetailAdapter extends ArrayAdapter<ReviewInfo> {
	private ArrayList<ReviewInfo> List;
	private LayoutInflater inflater;

	@SuppressWarnings("unchecked")
	public ShopDetailAdapter(Context context, int textViewResourceId, ArrayList<ReviewInfo> List) {
		super(context, textViewResourceId, List);
		this.List = List;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ビューを受け取る
		View view = convertView;
		// 受け取ったビューがnullなら新しくビューを生成
		if(view == null) {
			view = inflater.inflate(R.layout.shop_detailrow, null);
		}

		// 表示すべきデータの取得
		ReviewInfo reviewInfo = (ReviewInfo)List.get(position);
		if(reviewInfo != null) {
			// スクリーンネームをビューにセット
			TextView tvNickName = (TextView)view.findViewById(R.id.NickName);
			tvNickName.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvNickName != null) {
				tvNickName.setText(reviewInfo.getNickName());
			}

			TextView tvVisitDate = (TextView)view.findViewById(R.id.VisitDate);
			tvVisitDate.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvVisitDate != null) {
				tvVisitDate.setText(reviewInfo.getVisitDate());
			}

			TextView tvReviewDate = (TextView)view.findViewById(R.id.ReviewDate);
			tvReviewDate.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvReviewDate != null) {
				tvReviewDate.setText(reviewInfo.getReviewDate());
			}

			TextView tvUseType = (TextView)view.findViewById(R.id.UseType);
			tvUseType.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvUseType != null) {
				tvUseType.setText(reviewInfo.getUseType());
			}

			TextView tvSituations = (TextView)view.findViewById(R.id.Situations);
			tvSituations.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvSituations != null) {
				tvSituations.setText(reviewInfo.getSituations());
			}

			TextView tvTotalScore = (TextView)view.findViewById(R.id.TotalScore);
			tvTotalScore.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvTotalScore != null) {
				tvTotalScore.setText(reviewInfo.getTotalScore());
			}

			TextView tvTasteScore = (TextView)view.findViewById(R.id.TasteScore);
			tvTasteScore.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvTasteScore != null) {
				tvTasteScore.setText(reviewInfo.getTasteScore());
			}

			TextView tvServiceScore = (TextView)view.findViewById(R.id.ServiceScore);
			tvServiceScore.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvServiceScore != null) {
				tvServiceScore.setText(reviewInfo.getServiceScore());
			}

			TextView tvMoodScore = (TextView)view.findViewById(R.id.MoodScore);
			tvMoodScore.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvMoodScore != null) {
				tvMoodScore.setText(reviewInfo.getMoodScore());
			}

			TextView tvDinnerPrice = (TextView)view.findViewById(R.id.DinnerPrice);
			tvDinnerPrice.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvDinnerPrice != null) {
				tvDinnerPrice.setText(reviewInfo.getDinnerPrice());
			}

			TextView tvLunchPrice = (TextView)view.findViewById(R.id.LunchPrice);
			tvLunchPrice.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvLunchPrice != null) {
				tvLunchPrice.setText(reviewInfo.getLunchPrice());
			}

			TextView tvTitle = (TextView)view.findViewById(R.id.Title);
			tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvTitle != null) {
				tvTitle.setText(reviewInfo.getTitle());
			}

			TextView tvComment = (TextView)view.findViewById(R.id.Comment);
			tvComment.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvComment != null) {
				tvComment.setText(reviewInfo.getComment());
			}

			TextView tvPcSiteUrl = (TextView)view.findViewById(R.id.PcSiteUrl);
			tvPcSiteUrl.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvPcSiteUrl != null) {
				tvPcSiteUrl.setText(reviewInfo.getPcSiteUrl());
			}

			TextView tvMobileSiteUrl = (TextView)view.findViewById(R.id.MobileSiteUrl);
			tvMobileSiteUrl.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvMobileSiteUrl != null) {
				tvMobileSiteUrl.setText(reviewInfo.getMobileSiteUrl());
			}
		}
		return view;
	}
}