package org.chromium.ui;

public abstract interface DropdownItem
{
  public abstract int getIconId();
  
  public abstract int getIconMarginResId();
  
  public abstract int getIconSizeResId();
  
  public abstract String getLabel();
  
  public abstract int getLabelFontColorResId();
  
  public abstract int getLabelFontSizeResId();
  
  public abstract String getSublabel();
  
  public abstract int getSublabelFontSizeResId();
  
  public abstract boolean isBoldLabel();
  
  public abstract boolean isEnabled();
  
  public abstract boolean isGroupHeader();
  
  public abstract boolean isIconAtStart();
  
  public abstract boolean isLabelAndSublabelOnSameLine();
  
  public abstract boolean isMultilineLabel();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\DropdownItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */