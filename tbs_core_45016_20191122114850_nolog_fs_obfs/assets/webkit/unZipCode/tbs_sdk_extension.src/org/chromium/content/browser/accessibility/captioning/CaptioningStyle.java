package org.chromium.content.browser.accessibility.captioning;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.view.accessibility.CaptioningManager.CaptionStyle;

@TargetApi(19)
public class CaptioningStyle
{
  private Typeface jdField_a_of_type_AndroidGraphicsTypeface;
  private Integer jdField_a_of_type_JavaLangInteger;
  private Integer b;
  private Integer c;
  private Integer d;
  private Integer e;
  
  public CaptioningStyle(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, Typeface paramTypeface)
  {
    this.jdField_a_of_type_JavaLangInteger = paramInteger1;
    this.b = paramInteger2;
    this.c = paramInteger3;
    this.d = paramInteger4;
    this.e = paramInteger5;
    this.jdField_a_of_type_AndroidGraphicsTypeface = paramTypeface;
  }
  
  @SuppressLint({"NewApi"})
  public static CaptioningStyle createFrom(CaptioningManager.CaptionStyle paramCaptionStyle)
  {
    if (paramCaptionStyle == null) {
      return new CaptioningStyle(null, null, null, null, null, null);
    }
    Object localObject3;
    Object localObject4;
    Object localObject1;
    Object localObject2;
    int i;
    Object localObject5;
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (paramCaptionStyle.hasBackgroundColor()) {
        localObject3 = Integer.valueOf(paramCaptionStyle.backgroundColor);
      } else {
        localObject3 = null;
      }
      if (paramCaptionStyle.hasEdgeColor()) {
        localObject4 = Integer.valueOf(paramCaptionStyle.edgeColor);
      } else {
        localObject4 = null;
      }
      if (paramCaptionStyle.hasEdgeType()) {
        localObject1 = Integer.valueOf(paramCaptionStyle.edgeType);
      } else {
        localObject1 = null;
      }
      if (paramCaptionStyle.hasForegroundColor()) {
        localObject2 = Integer.valueOf(paramCaptionStyle.foregroundColor);
      } else {
        localObject2 = null;
      }
      Integer localInteger;
      Object localObject6;
      if (paramCaptionStyle.hasWindowColor())
      {
        i = paramCaptionStyle.windowColor;
        localInteger = Integer.valueOf(i);
        localObject5 = localObject1;
        localObject6 = localObject2;
        localObject1 = localObject3;
        localObject2 = localObject4;
        localObject3 = localObject5;
        localObject4 = localObject6;
        localObject5 = localInteger;
      }
      else
      {
        localInteger = null;
        localObject5 = localObject1;
        localObject6 = localObject2;
        localObject1 = localObject3;
        localObject2 = localObject4;
        localObject3 = localObject5;
        localObject4 = localObject6;
        localObject5 = localInteger;
      }
    }
    else
    {
      i = paramCaptionStyle.backgroundColor;
      int j = paramCaptionStyle.edgeColor;
      int k = paramCaptionStyle.edgeType;
      int m = paramCaptionStyle.foregroundColor;
      localObject1 = Integer.valueOf(i);
      localObject2 = Integer.valueOf(j);
      localObject5 = null;
      localObject3 = Integer.valueOf(k);
      localObject4 = Integer.valueOf(m);
    }
    return new CaptioningStyle((Integer)localObject1, (Integer)localObject2, (Integer)localObject3, (Integer)localObject4, (Integer)localObject5, paramCaptionStyle.getTypeface());
  }
  
  public Integer getBackgroundColor()
  {
    return this.jdField_a_of_type_JavaLangInteger;
  }
  
  public Integer getEdgeColor()
  {
    return this.b;
  }
  
  public Integer getEdgeType()
  {
    return this.c;
  }
  
  public Integer getForegroundColor()
  {
    return this.d;
  }
  
  public Typeface getTypeface()
  {
    return this.jdField_a_of_type_AndroidGraphicsTypeface;
  }
  
  public Integer getWindowColor()
  {
    return this.e;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\CaptioningStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */