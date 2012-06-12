package com.hide_ab.TabeRoid;

import java.util.ArrayList;
import android.app.Application;

public class ShopInfos extends Application {
	// 表示するデータのリスト
	protected ArrayList<ShopInfo> List = null;

	@Override
	public void onCreate() {
		super.onCreate();
		this.List = new ArrayList<ShopInfo>();
	}

	public ArrayList<ShopInfo> getList() {
		return(this.List);
	}

	public ShopInfo getInfo(int position) {
		return((ShopInfo)this.List.get(position));
	}

	public void putInfo(ShopInfo shopInfo) {
		this.List.add(shopInfo);
	}
}