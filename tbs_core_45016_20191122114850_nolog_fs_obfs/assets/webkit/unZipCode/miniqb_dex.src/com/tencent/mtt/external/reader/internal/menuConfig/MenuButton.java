package com.tencent.mtt.external.reader.internal.menuConfig;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.common.utils.LogUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class MenuButton
{
  private static String jdField_a_of_type_JavaLangString = "MenuButton";
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable = null;
  private JSONObject jdField_a_of_type_OrgJsonJSONObject = null;
  
  public MenuButton(JSONObject paramJSONObject)
  {
    this.jdField_a_of_type_OrgJsonJSONObject = paramJSONObject;
  }
  
  private int a()
  {
    try
    {
      int i = this.jdField_a_of_type_OrgJsonJSONObject.getInt(MenuConstants.MENU_REQUIRED_QB_VERSION_MIN);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getRequiredQbVersionMin Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return MenuConstants.ERR;
  }
  
  private boolean a(int paramInt)
  {
    try
    {
      localObject1 = this.jdField_a_of_type_OrgJsonJSONObject.getString(MenuConstants.MENU_QB_VERSION_FILTER).split(Pattern.quote("|"));
      int i = 0;
      while (i < localObject1.length)
      {
        if (paramInt == Integer.valueOf(localObject1[i]).intValue())
        {
          Object localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Ver filter:");
          ((StringBuilder)localObject2).append(localObject1[i]);
          ((StringBuilder)localObject2).toString();
          return true;
        }
        i += 1;
      }
    }
    catch (Exception localException)
    {
      Object localObject1;
      for (;;) {}
    }
    localObject1 = jdField_a_of_type_JavaLangString;
    return false;
  }
  
  public Drawable getDrawable()
  {
    return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  }
  
  public MenuEventObj getEventObj()
  {
    for (;;)
    {
      try
      {
        MenuEventObj localMenuEventObj = new MenuEventObj();
        Object localObject1 = getHashMap(getName(), MenuConstants.MENU_ACTION_TYPE);
        if (localObject1 == null) {
          return null;
        }
        if (((String)((HashMap)localObject1).get(MenuConstants.MENU_ACTION_TYPE)).equalsIgnoreCase(MenuConstants.MENU_ACTION_TYPE_START_INTENT))
        {
          localObject3 = (String)((HashMap)localObject1).get(MenuConstants.MENU_ACTION_INTENT);
          localObject1 = (String)((HashMap)localObject1).get(MenuConstants.MENU_ACTION_URI);
          if (!((String)localObject1).equalsIgnoreCase(MenuConstants.MENU_ACTION_URI))
          {
            localObject1 = Uri.parse((String)localObject1);
            localMenuEventObj.setIntentAction((String)localObject3, (Uri)localObject1);
          }
        }
        else
        {
          localObject1 = getName();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(MenuConstants.MENU_ACTION_BUNDEL_PARAMS);
          ((StringBuilder)localObject3).append("_");
          ((StringBuilder)localObject3).append(MenuConstants.MENU_INT);
          localObject1 = getHashMap((String)localObject1, ((StringBuilder)localObject3).toString());
          localObject3 = getName();
          Object localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append(MenuConstants.MENU_ACTION_BUNDEL_PARAMS);
          ((StringBuilder)localObject4).append("_");
          ((StringBuilder)localObject4).append(MenuConstants.MENU_STRING);
          HashMap localHashMap1 = getHashMap((String)localObject3, ((StringBuilder)localObject4).toString());
          localObject3 = getName();
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append(MenuConstants.MENU_ACTION_INTENT_PARAMS);
          ((StringBuilder)localObject4).append("_");
          ((StringBuilder)localObject4).append(MenuConstants.MENU_INT);
          HashMap localHashMap2 = getHashMap((String)localObject3, ((StringBuilder)localObject4).toString());
          localObject3 = getName();
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append(MenuConstants.MENU_ACTION_INTENT_PARAMS);
          ((StringBuilder)localObject4).append("_");
          ((StringBuilder)localObject4).append(MenuConstants.MENU_STRING);
          HashMap localHashMap3 = getHashMap((String)localObject3, ((StringBuilder)localObject4).toString());
          if ((localObject1 == null) || (((HashMap)localObject1).isEmpty())) {
            break label817;
          }
          localObject3 = new Bundle();
          localObject4 = ((HashMap)localObject1).entrySet().iterator();
          localObject1 = localObject3;
          if (((Iterator)localObject4).hasNext())
          {
            localObject1 = (Map.Entry)((Iterator)localObject4).next();
            ((Bundle)localObject3).putInt((String)((Map.Entry)localObject1).getKey(), Integer.parseInt((String)((Map.Entry)localObject1).getValue()));
            continue;
          }
          localObject4 = localObject1;
          if (localHashMap1 != null)
          {
            localObject4 = localObject1;
            if (!localHashMap1.isEmpty())
            {
              localObject3 = localObject1;
              if (localObject1 == null) {
                localObject3 = new Bundle();
              }
              localObject1 = localHashMap1.entrySet().iterator();
              localObject4 = localObject3;
              if (((Iterator)localObject1).hasNext())
              {
                localObject4 = (Map.Entry)((Iterator)localObject1).next();
                ((Bundle)localObject3).putString((String)((Map.Entry)localObject4).getKey(), (String)((Map.Entry)localObject4).getValue());
                continue;
              }
            }
          }
          if (localObject4 != null) {
            localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localObject4);
          }
          if ((localHashMap2 != null) && (!localHashMap2.isEmpty()))
          {
            localObject1 = localHashMap2.entrySet().iterator();
            if (((Iterator)localObject1).hasNext())
            {
              localObject3 = (Map.Entry)((Iterator)localObject1).next();
              localMenuEventObj.setIntentExtraKV((String)((Map.Entry)localObject3).getKey(), Integer.valueOf(Integer.parseInt((String)((Map.Entry)localObject3).getValue())));
              continue;
            }
          }
          if ((localHashMap3 != null) && (!localHashMap3.isEmpty()))
          {
            localObject1 = localHashMap3.entrySet().iterator();
            if (((Iterator)localObject1).hasNext())
            {
              localObject3 = (Map.Entry)((Iterator)localObject1).next();
              localMenuEventObj.setIntentExtraKV((String)((Map.Entry)localObject3).getKey(), (String)((Map.Entry)localObject3).getValue());
              continue;
            }
          }
          localObject1 = getStatistics(MenuConstants.MENU_DOWNLOAD_TIP);
          int j = 0;
          localObject3 = getStatistics(MenuConstants.MENU_DOWNLOAD_CHANNELID);
          int i = j;
          if (localObject3 != null)
          {
            i = j;
            if (!((String)localObject3).isEmpty()) {
              i = Integer.parseInt((String)localObject3);
            }
          }
          localObject3 = getStatistics(MenuConstants.MENU_STATISTICS_INTO_DOWNLOAD);
          localObject4 = new Bundle();
          ((Bundle)localObject4).putString("tip", (String)localObject1);
          ((Bundle)localObject4).putString("upgrade_tip", getStatistics(MenuConstants.MENU_UPGRADE_TIP));
          ((Bundle)localObject4).putInt("channel_id", i);
          ((Bundle)localObject4).putString("statistics", (String)localObject3);
          localMenuEventObj.setRequiredQB(a(), (Bundle)localObject4);
          return localMenuEventObj;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Object localObject3 = jdField_a_of_type_JavaLangString;
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("getEventObj Exception:");
        ((StringBuilder)localObject3).append(localException.toString());
        ((StringBuilder)localObject3).toString();
        return null;
      }
      Object localObject2 = null;
      continue;
      label817:
      localObject2 = null;
    }
  }
  
  public HashMap<String, String> getHashMap(String paramString1, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("_");
    ((StringBuilder)localObject).append(paramString2);
    paramString2 = ((StringBuilder)localObject).toString();
    paramString1 = new HashMap();
    paramString2 = MenuResources.getStringArray(paramString2);
    int j = paramString2.length;
    int i = 0;
    while (i < j)
    {
      localObject = paramString2[i].split("===");
      if (localObject.length == 2) {
        paramString1.put(localObject[0], localObject[1]);
      }
      i += 1;
    }
    return paramString1;
  }
  
  public int getIndex()
  {
    try
    {
      int i = this.jdField_a_of_type_OrgJsonJSONObject.getInt(MenuConstants.MENU_BUTTON_INDEX);
      String str = jdField_a_of_type_JavaLangString;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getIndex Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return MenuConstants.ERR;
  }
  
  public String getName()
  {
    try
    {
      String str = this.jdField_a_of_type_OrgJsonJSONObject.getString(MenuConstants.MENU_BUTTON_NAME);
      localObject = jdField_a_of_type_JavaLangString;
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getName Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return null;
  }
  
  public HashMap<String, Integer> getPadding()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Object localObject2 = this.jdField_a_of_type_OrgJsonJSONObject.getJSONObject(MenuConstants.MENU_PADDING);
      localObject1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_LEFT);
      String str1 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_TOP);
      String str2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_RIGHT);
      localObject2 = ((JSONObject)localObject2).getString(MenuConstants.MENU_POS_BOTTOM);
      localHashMap.put(MenuConstants.MENU_POS_LEFT, Integer.valueOf(MenuResources.getDimensionPixelSize((String)localObject1)));
      localHashMap.put(MenuConstants.MENU_POS_TOP, Integer.valueOf(MenuResources.getDimensionPixelSize(str1)));
      localHashMap.put(MenuConstants.MENU_POS_RIGHT, Integer.valueOf(MenuResources.getDimensionPixelSize(str2)));
      localHashMap.put(MenuConstants.MENU_POS_BOTTOM, Integer.valueOf(MenuResources.getDimensionPixelSize((String)localObject2)));
      localObject1 = jdField_a_of_type_JavaLangString;
      return localHashMap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject1 = jdField_a_of_type_JavaLangString;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getPadding Exception:");
      ((StringBuilder)localObject1).append(localException.toString());
      ((StringBuilder)localObject1).toString();
    }
    return null;
  }
  
  public boolean getShouldShow(int paramInt)
  {
    try
    {
      if (!this.jdField_a_of_type_OrgJsonJSONObject.getBoolean(MenuConstants.MENU_SHOULD_SHOW))
      {
        LogUtils.d(jdField_a_of_type_JavaLangString, "Config should show = false");
        return false;
      }
      if (a(paramInt))
      {
        String str = jdField_a_of_type_JavaLangString;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Config filter qb ver = ");
        ((StringBuilder)localObject).append(paramInt);
        LogUtils.d(str, ((StringBuilder)localObject).toString());
        return false;
      }
    }
    catch (Exception localException)
    {
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getShouldShow Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return true;
  }
  
  public String getStatistics(String paramString)
  {
    try
    {
      localObject = getHashMap(getName(), MenuConstants.MENU_STATISTICS);
      if (localObject == null) {
        return null;
      }
      paramString = (String)((HashMap)localObject).get(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getStatistics Exception:");
      ((StringBuilder)localObject).append(paramString.toString());
      ((StringBuilder)localObject).toString();
    }
    return null;
  }
  
  public String getText()
  {
    try
    {
      String str = MenuResources.getString(this.jdField_a_of_type_OrgJsonJSONObject.getString(MenuConstants.MENU_TEXT));
      localObject = jdField_a_of_type_JavaLangString;
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getText Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return null;
  }
  
  public int getTextColor()
  {
    try
    {
      int i = MenuResources.getColor(this.jdField_a_of_type_OrgJsonJSONObject.getString(MenuConstants.MENU_TEXT_COLOR));
      String str = jdField_a_of_type_JavaLangString;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getTextColor Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return MenuConstants.ERR;
  }
  
  public int getTextSize()
  {
    try
    {
      int i = MenuResources.getDimensionPixelSize(this.jdField_a_of_type_OrgJsonJSONObject.getString(MenuConstants.MENU_TEXT_SIZE));
      String str = jdField_a_of_type_JavaLangString;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getTextSize Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return MenuConstants.ERR;
  }
  
  public void setDrawable(Drawable paramDrawable)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */