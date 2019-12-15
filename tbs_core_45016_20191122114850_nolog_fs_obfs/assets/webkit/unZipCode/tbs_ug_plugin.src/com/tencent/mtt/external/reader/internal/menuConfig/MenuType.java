package com.tencent.mtt.external.reader.internal.menuConfig;

import com.tencent.mtt.external.reader.base.ReaderConfig;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class MenuType
{
  private static String jdField_a_of_type_JavaLangString = "MenuType";
  private JSONObject jdField_a_of_type_OrgJsonJSONObject = null;
  
  public MenuType(JSONObject paramJSONObject)
  {
    this.jdField_a_of_type_OrgJsonJSONObject = paramJSONObject;
  }
  
  public TreeMap<Integer, MenuButton> getMenuButtons(ReaderConfig paramReaderConfig)
  {
    Object localObject1 = new TreeMap();
    for (;;)
    {
      int i;
      try
      {
        JSONArray localJSONArray = this.jdField_a_of_type_OrgJsonJSONObject.getJSONArray(MenuConstants.MENU_BUTTON);
        i = 0;
        if (i < localJSONArray.length())
        {
          Object localObject2 = (JSONObject)localJSONArray.get(i);
          int j = ((JSONObject)localObject2).getInt(MenuConstants.MENU_BUTTON_INDEX);
          localObject2 = new MenuButton((JSONObject)localObject2);
          if (((MenuButton)localObject2).getShouldShow(paramReaderConfig.installed_qb_version)) {
            if (((MenuButton)localObject2).getName().equalsIgnoreCase("name_btn_pdf_outline"))
            {
              if ((paramReaderConfig != null) && (paramReaderConfig.pdf_contain_outline)) {
                ((TreeMap)localObject1).put(Integer.valueOf(j), localObject2);
              }
            }
            else if (((MenuButton)localObject2).getName().equalsIgnoreCase("name_btn_txt_reading_model"))
            {
              if ((paramReaderConfig != null) && (paramReaderConfig.txt_could_be_noval)) {
                ((TreeMap)localObject1).put(Integer.valueOf(j), localObject2);
              }
            }
            else
            {
              Object localObject3 = jdField_a_of_type_JavaLangString;
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("Show button:");
              ((StringBuilder)localObject3).append(((MenuButton)localObject2).getName());
              ((StringBuilder)localObject3).toString();
              ((TreeMap)localObject1).put(Integer.valueOf(j), localObject2);
            }
          }
        }
        else
        {
          paramReaderConfig = jdField_a_of_type_JavaLangString;
          return (TreeMap<Integer, MenuButton>)localObject1;
        }
      }
      catch (Exception paramReaderConfig)
      {
        paramReaderConfig.printStackTrace();
        localObject1 = jdField_a_of_type_JavaLangString;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("getPadding Exception:");
        ((StringBuilder)localObject1).append(paramReaderConfig.toString());
        ((StringBuilder)localObject1).toString();
        return null;
      }
      i += 1;
    }
  }
  
  public boolean getShouldShow()
  {
    try
    {
      boolean bool = this.jdField_a_of_type_OrgJsonJSONObject.getBoolean(MenuConstants.MENU_SHOULD_SHOW);
      String str = jdField_a_of_type_JavaLangString;
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = jdField_a_of_type_JavaLangString;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getShouldShow Exception:");
      ((StringBuilder)localObject).append(localException.toString());
      ((StringBuilder)localObject).toString();
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */