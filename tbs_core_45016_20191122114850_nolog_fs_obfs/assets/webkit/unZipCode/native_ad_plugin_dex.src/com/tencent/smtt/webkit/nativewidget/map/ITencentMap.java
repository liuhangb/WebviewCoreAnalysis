package com.tencent.smtt.webkit.nativewidget.map;

import com.tencent.smtt.webkit.nativewidget.INativeWidget;

public abstract interface ITencentMap
  extends INativeWidget
{
  public abstract boolean addCircles(String paramString, boolean paramBoolean);
  
  public abstract boolean addControls(String paramString, boolean paramBoolean);
  
  public abstract boolean addMarkers(String paramString, boolean paramBoolean);
  
  public abstract boolean addPolylines(String paramString, boolean paramBoolean);
  
  public abstract void getCenterLocation(String paramString);
  
  public abstract void getRegion(String paramString);
  
  public abstract void getScale(String paramString);
  
  public abstract void includePoints(String paramString);
  
  public abstract void moveToLocation();
  
  public abstract void orientationChanged(float paramFloat);
  
  public abstract void removeAllCircles();
  
  public abstract void removeAllControls();
  
  public abstract void removeAllMarkers();
  
  public abstract void removeAllPolylines();
  
  public abstract void setCenter(double paramDouble1, double paramDouble2);
  
  public abstract void setScale(float paramFloat);
  
  public abstract void setShowLocation(boolean paramBoolean);
  
  public abstract void translateMarker(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\nativewidget\map\ITencentMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */