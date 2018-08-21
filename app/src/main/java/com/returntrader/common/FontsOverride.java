package com.returntrader.common;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class FontsOverride {

	public static void setDefaultFont(Context context,
			String staticTypefaceFieldName, String fontAssetName) {
		final Typeface regular = Typeface.createFromAsset(context.getAssets(),
				fontAssetName);
		replaceFont(staticTypefaceFieldName, regular);
	}

	protected static void replaceFont(String staticTypefaceFieldName,
			final Typeface newTypeface) {
		try {
			final Field staticField = Typeface.class
					.getDeclaredField(staticTypefaceFieldName);
			staticField.setAccessible(true);
			staticField.set(null, newTypeface);
			Log.d("TypeFace", "replace font success:" + staticTypefaceFieldName);
		} catch (NoSuchFieldException e) {
			Log.e("TypeFace", "replace font error:" + staticTypefaceFieldName);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("TypeFace", "replace font error:" + staticTypefaceFieldName);
			e.printStackTrace();
		}
	}

	public static void overrideDefaultFonts(Context context) {
		setDefaultFont(context, "SERIF", "ProximaNovaSoftRegular.ttf");//Reg
		setDefaultFont(context, "DEFAULT", "ProximaNovaSoftRegular.ttf");//Reg
		setDefaultFont(context, "MONOSPACE", "ProximaNovaSoftBold.ttf");//Bold
		setDefaultFont(context, "SANS_SERIF", "ProximaNovaSoftSemibold.otf");//Semi-Bold
	}
}
