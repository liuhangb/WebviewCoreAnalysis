package com.tencent.tbs.core.partner.ad;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import com.tencent.smtt.webkit.SmttResource;

public class AdUtils
{
  private static final String AD_SEPARATOR = "&&&&";
  static final int CLOSE_BUTTON_GAP = 0;
  static final int CLOSE_BUTTON_SIZE = 38;
  static final String POS_ID_BUBBLE = "60";
  static final String POS_ID_BUBBLE_TOP = "61";
  static final String POS_ID_SPLICE = "70";
  static final String POS_ID_SPLICE_TOP = "71";
  static final String STAT_BUBBLE_CLICK = "AGNQ2_";
  static final String STAT_BUBBLE_CLICK_MINIQB_ENTRYID = "32";
  static final String STAT_BUBBLE_CLICK_QB_POSID = "51";
  static final String STAT_BUBBLE_EXPOSURE = "AGNQ1_";
  private static final String STAT_BUBBLE_IN_MINIQB_CLICK = "AGNQ6_";
  private static final String STAT_BUBBLE_IN_MINIQB_EXPOSURE = "AGNQ5_";
  static final String STAT_BUBBLE_TOP_CLICK = "AGNQ4_";
  static final String STAT_BUBBLE_TOP_CLICK_MINIQB_ENTRYID = "35";
  static final String STAT_BUBBLE_TOP_CLICK_QB_POSID = "53";
  static final String STAT_BUBBLE_TOP_EXPOSURE = "AGNQ3_";
  private static final String STAT_BUBBLE_TOP_IN_MINIQB_CLICK = "AGNQ8_";
  private static final String STAT_BUBBLE_TOP_IN_MINIQB_EXPOSURE = "AGNQ7_";
  static final String STAT_KEY_CLICK_BTN_CLOSE_NATIVEAD = "AGTG2";
  static final String STAT_KEY_CLICK_BTN_TENCENTSPREAD = "AGTG1";
  static final int STAT_POSITION_ID_CLICKE_BUBBLE_OPEN_MINIQB = 10;
  static final int STAT_POSITION_ID_CLICKE_SPLICE_OPEN_MINIQB = 9;
  static final int STAT_POSITION_ID_CLICKE_TOP_BUBBLE_OPEN_MINIQB = 34;
  static final int STAT_POSITION_ID_CLICKE_TOP_SPLICE_OPEN_MINIQB = 36;
  static final String STAT_QQ_ICON_SHINE_NOTIFY = "AGND1";
  static final String STAT_SPLICE_CLICK = "AGNP2_";
  static final String STAT_SPLICE_CLICK_MINIQB_ENTRYID = "33";
  static final String STAT_SPLICE_CLICK_QB_POSID = "52";
  static final String STAT_SPLICE_CLICK_TOP = "AGNP4_";
  static final String STAT_SPLICE_EXPOSURE = "AGNP1_";
  static final String STAT_SPLICE_MINIQB_CLICK = "AGNP6_";
  static final String STAT_SPLICE_MINIQB_TOP_LICK = "AGNP8_";
  static final String STAT_SPLICE_TOP_CLICK_MINIQB_ENTRYID = "37";
  static final String STAT_SPLICE_TOP_CLICK_QB_POSID = "54";
  
  public static String getAdUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = paramString.indexOf("&&&&");
    if (i != -1) {
      return paramString.substring(0, i);
    }
    return paramString;
  }
  
  public static String getClickStatKey(int paramInt1, int paramInt2)
  {
    if ((paramInt1 & 0x4) == 4)
    {
      if (paramInt2 == 1) {
        return "AGNQ2_";
      }
      if (paramInt2 == 2) {
        return "AGNQ4_";
      }
    }
    else if (paramInt1 == 32)
    {
      if (paramInt2 == 1) {
        return "AGNQ6_";
      }
      if (paramInt2 == 2) {
        return "AGNQ8_";
      }
    }
    else if ((paramInt1 & 0x8) == 8)
    {
      if (paramInt2 == 1) {
        return "AGNP2_";
      }
      if (paramInt2 == 2) {
        return "AGNP4_";
      }
    }
    else if (paramInt1 == 2)
    {
      if (paramInt2 == 1) {
        return "AGNP6_";
      }
      if (paramInt2 == 2) {
        return "AGNP8_";
      }
    }
    return "";
  }
  
  public static String getDetailAdUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = paramString.indexOf("&&&&");
    if (i != -1) {
      return paramString.substring(i + 4);
    }
    return null;
  }
  
  public static Drawable getDrawableFromBitmap(Bitmap paramBitmap)
  {
    Resources localResources = SmttResource.getResources();
    int i = localResources.getDisplayMetrics().densityDpi;
    if (paramBitmap != null)
    {
      paramBitmap.setDensity(i);
      return new BitmapDrawable(localResources, paramBitmap);
    }
    return null;
  }
  
  public static Drawable getEditTextHandle(String paramString)
  {
    Resources localResources = SmttResource.getResources();
    int i = SmttResource.loadIdentifierResource(paramString, "drawable");
    paramString = new BitmapFactory.Options();
    paramString.inScaled = false;
    Drawable localDrawable = getDrawableFromBitmap(BitmapFactory.decodeResource(localResources, i, paramString));
    paramString = localDrawable;
    if (localDrawable == null) {
      paramString = localResources.getDrawable(i);
    }
    return paramString;
  }
  
  public static String getExposureStatKeyForBubbleAd(int paramInt1, int paramInt2)
  {
    if ((paramInt1 & 0x4) == 4)
    {
      if (paramInt2 == 1) {
        return "AGNQ1_";
      }
      if (paramInt2 == 2) {
        return "AGNQ3_";
      }
    }
    else if (paramInt1 == 32)
    {
      if (paramInt2 == 1) {
        return "AGNQ5_";
      }
      if (paramInt2 == 2) {
        return "AGNQ7_";
      }
    }
    return "";
  }
  
  @SuppressLint({"NewApi"})
  public static void setBackground(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT > 15)
    {
      paramView.setBackground(paramDrawable);
      return;
    }
    paramView.setBackgroundDrawable(paramDrawable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */