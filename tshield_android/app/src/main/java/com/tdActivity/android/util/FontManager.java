package com.tdActivity.android.util;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
	public static final String ROOT = "fonts/",
			FONTAWESOME = ROOT + "fontawesome-webfont.ttf",
			FONTAWESOME2 = ROOT + "digital.ttf",
	        FONTAWESOME3 = ROOT + "STHeiti Light.ttc";

	public static Typeface getTypeface(Context context, String font) {
		return Typeface.createFromAsset(context.getAssets(), font);
	}  

	public static Typeface getTypeface1(Context context, String font) {
		return Typeface.createFromAsset(context.getAssets(), FONTAWESOME2);
	}  

}
