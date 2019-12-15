package com.tencent.basesupport.setting;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.proguard.KeepNameAndPublic;
import java.util.Map;

@KeepNameAndPublic
public class PrefSettingDelegate
  implements IPrefSetting
{
  SharedPreferences.Editor jdField_a_of_type_AndroidContentSharedPreferences$Editor = null;
  SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences = null;
  IPrefSetting jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting = null;
  boolean jdField_a_of_type_Boolean = false;
  
  public PrefSettingDelegate() {}
  
  public PrefSettingDelegate(SharedPreferences paramSharedPreferences)
  {
    this.jdField_a_of_type_AndroidContentSharedPreferences = paramSharedPreferences;
    this.jdField_a_of_type_AndroidContentSharedPreferences$Editor = paramSharedPreferences.edit();
  }
  
  public PrefSettingDelegate(IPrefSetting paramIPrefSetting)
  {
    this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting = paramIPrefSetting;
  }
  
  void a(SharedPreferences.Editor paramEditor)
  {
    if (paramEditor != null) {
      if ((paramEditor == this.jdField_a_of_type_AndroidContentSharedPreferences$Editor) && (this.jdField_a_of_type_Boolean)) {
        return;
      }
    }
    try
    {
      paramEditor.apply();
      return;
    }
    catch (Throwable paramEditor) {}
    return;
  }
  
  public final void commitAll()
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).commitAll();
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null) {}
    try
    {
      ((SharedPreferences.Editor)localObject).apply();
      this.jdField_a_of_type_Boolean = false;
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  public final void commitAllSync()
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).commitAllSync();
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null) {
      ((SharedPreferences.Editor)localObject).commit();
    }
    this.jdField_a_of_type_Boolean = false;
  }
  
  public final boolean contains(String paramString)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).contains(paramString);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).contains(paramString);
    }
    return false;
  }
  
  public void copyValues(IPrefSetting paramIPrefSetting, String[][] paramArrayOfString)
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject1 != null)
    {
      ((IPrefSetting)localObject1).copyValues(paramIPrefSetting, paramArrayOfString);
      return;
    }
    if (paramIPrefSetting != null)
    {
      if (this.jdField_a_of_type_AndroidContentSharedPreferences$Editor == null) {
        return;
      }
      localObject1 = this.jdField_a_of_type_AndroidContentSharedPreferences.getAll();
      if (localObject1 != null)
      {
        if (((Map)localObject1).size() < 1) {
          return;
        }
        int j = paramArrayOfString.length;
        int i = 0;
        while (i < j)
        {
          Object localObject4 = paramArrayOfString[i];
          Object localObject2 = localObject4[1];
          Object localObject3 = ((Map)localObject1).get(localObject2);
          if (localObject3 != null)
          {
            localObject4 = localObject4[0];
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("copyValues: ");
            localStringBuilder.append((String)localObject2);
            localStringBuilder.append(":");
            localStringBuilder.append((String)localObject4);
            localStringBuilder.append("=");
            localStringBuilder.append(localObject3);
            FLogger.d("PrefSettingDelegate", localStringBuilder.toString());
            if ((localObject3 instanceof String)) {
              paramIPrefSetting.setString((String)localObject4, (String)localObject3);
            } else if ((localObject3 instanceof Integer)) {
              paramIPrefSetting.setInt((String)localObject4, ((Integer)localObject3).intValue());
            } else if ((localObject3 instanceof Long)) {
              paramIPrefSetting.setLong((String)localObject4, ((Long)localObject3).longValue());
            } else if ((localObject3 instanceof Boolean)) {
              paramIPrefSetting.setBoolean((String)localObject4, ((Boolean)localObject3).booleanValue());
            } else if ((localObject3 instanceof Float)) {
              paramIPrefSetting.setFloat((String)localObject4, ((Float)localObject3).floatValue());
            }
            localObject3 = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
            if (localObject3 != null) {
              ((SharedPreferences.Editor)localObject3).remove((String)localObject2);
            }
          }
          i += 1;
        }
      }
    }
    try
    {
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.apply();
      return;
    }
    catch (Throwable paramIPrefSetting) {}
    return;
    return;
  }
  
  public final void delegateTo(SharedPreferences paramSharedPreferences)
  {
    if ((this.jdField_a_of_type_AndroidContentSharedPreferences == null) && (this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting == null))
    {
      this.jdField_a_of_type_AndroidContentSharedPreferences = paramSharedPreferences;
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor = paramSharedPreferences.edit();
      return;
    }
    throw new RuntimeException("delegation already set, can't set delegate twice");
  }
  
  public final void delegateTo(IPrefSetting paramIPrefSetting)
  {
    if ((this.jdField_a_of_type_AndroidContentSharedPreferences == null) && (this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting == null))
    {
      this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting = paramIPrefSetting;
      return;
    }
    throw new RuntimeException("delegation already set, can't set delegate twice");
  }
  
  public final void enableBreakCommit()
  {
    this.jdField_a_of_type_Boolean = true;
  }
  
  public final boolean getBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).getBoolean(paramString, paramBoolean);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).getBoolean(paramString, paramBoolean);
    }
    return paramBoolean;
  }
  
  public final float getFloat(String paramString, float paramFloat)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).getFloat(paramString, paramFloat);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).getFloat(paramString, paramFloat);
    }
    return paramFloat;
  }
  
  public final int getInt(String paramString, int paramInt)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).getInt(paramString, paramInt);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).getInt(paramString, paramInt);
    }
    return paramInt;
  }
  
  public final long getLong(String paramString, long paramLong)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).getLong(paramString, paramLong);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).getLong(paramString, paramLong);
    }
    return paramLong;
  }
  
  public final String getString(String paramString1, String paramString2)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      return ((IPrefSetting)localObject).getString(paramString1, paramString2);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      return ((SharedPreferences)localObject).getString(paramString1, paramString2);
    }
    return paramString2;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null)
    {
      ((IPrefSetting)localObject).registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
      return;
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      ((SharedPreferences)localObject).registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
    }
  }
  
  public final void remove(String paramString)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null)
    {
      ((IPrefSetting)localObject).remove(paramString);
      return;
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).remove(paramString);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public final void setBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).setBoolean(paramString, paramBoolean);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).putBoolean(paramString, paramBoolean);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public final void setFloat(String paramString, float paramFloat)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).setFloat(paramString, paramFloat);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).putFloat(paramString, paramFloat);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public final void setInt(String paramString, int paramInt)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).setInt(paramString, paramInt);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).putInt(paramString, paramInt);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public final void setLong(String paramString, long paramLong)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).setLong(paramString, paramLong);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).putLong(paramString, paramLong);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public final void setString(String paramString1, String paramString2)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null) {
      ((IPrefSetting)localObject).setString(paramString1, paramString2);
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences$Editor;
    if (localObject != null)
    {
      ((SharedPreferences.Editor)localObject).putString(paramString1, paramString2);
      a(this.jdField_a_of_type_AndroidContentSharedPreferences$Editor);
    }
  }
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    Object localObject = this.jdField_a_of_type_ComTencentBasesupportSettingIPrefSetting;
    if (localObject != null)
    {
      ((IPrefSetting)localObject).unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
      return;
    }
    localObject = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localObject != null) {
      ((SharedPreferences)localObject).unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\setting\PrefSettingDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */