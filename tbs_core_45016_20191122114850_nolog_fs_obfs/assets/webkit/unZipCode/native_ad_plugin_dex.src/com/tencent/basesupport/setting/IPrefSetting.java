package com.tencent.basesupport.setting;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.tencent.mtt.proguard.KeepNameAndPublic;

@KeepNameAndPublic
public abstract interface IPrefSetting
{
  public abstract void commitAll();
  
  public abstract void commitAllSync();
  
  public abstract boolean contains(String paramString);
  
  public abstract void copyValues(IPrefSetting paramIPrefSetting, String[][] paramArrayOfString);
  
  public abstract void enableBreakCommit();
  
  public abstract boolean getBoolean(String paramString, boolean paramBoolean);
  
  public abstract float getFloat(String paramString, float paramFloat);
  
  public abstract int getInt(String paramString, int paramInt);
  
  public abstract long getLong(String paramString, long paramLong);
  
  public abstract String getString(String paramString1, String paramString2);
  
  public abstract void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener);
  
  public abstract void remove(String paramString);
  
  public abstract void setBoolean(String paramString, boolean paramBoolean);
  
  public abstract void setFloat(String paramString, float paramFloat);
  
  public abstract void setInt(String paramString, int paramInt);
  
  public abstract void setLong(String paramString, long paramLong);
  
  public abstract void setString(String paramString1, String paramString2);
  
  public abstract void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\setting\IPrefSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */