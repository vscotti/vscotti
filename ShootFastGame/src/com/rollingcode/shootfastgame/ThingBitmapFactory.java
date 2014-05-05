package com.rollingcode.shootfastgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ThingBitmapFactory {

	public static Bitmap getBitmap(Resources res, int theme, float vxPosition, float vyPosition) {
		if(theme == Constants.theme.FLOWERS_THEME.ordinal() ||
				theme == Constants.theme.CUSTOM_THEME.ordinal()) {
			if(vxPosition < 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left_up);
			} else if(vxPosition > 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right_up);
			} else if(vxPosition == 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_up);
			} else if(vxPosition > 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right_down);
			} else if(vxPosition > 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right);
			} else if(vxPosition == 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_down);
			} else if(vxPosition < 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left);
			} else if(vxPosition < 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left_down);
			}
		} else if(theme == Constants.theme.LAKE_THEME.ordinal()) {
			if(vxPosition < 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left_up);
			} else if(vxPosition > 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right_up);
			} else if(vxPosition == 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_up);
			} else if(vxPosition > 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right_down);
			} else if(vxPosition > 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right);
			} else if(vxPosition == 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_down);
			} else if(vxPosition < 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left);
			} else if(vxPosition < 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left_down);
			}
		} else if(theme == Constants.theme.FOOTBALL_THEME.ordinal()) {
			return BitmapFactory.decodeResource(res, R.drawable.ball);
		} else if(theme == Constants.theme.TENNIS_THEME.ordinal()) {
			return BitmapFactory.decodeResource(res, R.drawable.ball_tenis);
		}

		return null;
	}

	public static Bitmap getKillBitmap(Resources res, int theme, float vxPosition, float vyPosition) {
		if(theme == Constants.theme.FLOWERS_THEME.ordinal() ||
				theme == Constants.theme.CUSTOM_THEME.ordinal()) {
			if(vxPosition < 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left_up_kill);
			} else if(vxPosition > 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right_up_kill);
			} else if(vxPosition == 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_up_kill);
			} else if(vxPosition > 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right_down_kill);
			} else if(vxPosition > 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_right_kill);
			} else if(vxPosition == 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_down_kill);
			} else if(vxPosition < 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left_kill);
			} else if(vxPosition < 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.bee_left_down_kill);
			}
		} else if(theme == Constants.theme.LAKE_THEME.ordinal()) {
			if(vxPosition < 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left_up_kill);
			} else if(vxPosition > 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right_up_kill);
			} else if(vxPosition == 0 && vyPosition < 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_up_kill);
			} else if(vxPosition > 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right_down_kill);
			} else if(vxPosition > 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_right_kill);
			} else if(vxPosition == 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_down_kill);
			} else if(vxPosition < 0 && vyPosition == 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left_kill);
			} else if(vxPosition < 0 && vyPosition > 0) {
				return BitmapFactory.decodeResource(res, R.drawable.duck_left_down_kill);
			}
		} else if(theme == Constants.theme.FOOTBALL_THEME.ordinal()) {
			return BitmapFactory.decodeResource(res, R.drawable.ball_kill);
		} else if(theme == Constants.theme.TENNIS_THEME.ordinal()) {
			return BitmapFactory.decodeResource(res, R.drawable.ball_tenis_kill);
		}
		return null;
	}
}

