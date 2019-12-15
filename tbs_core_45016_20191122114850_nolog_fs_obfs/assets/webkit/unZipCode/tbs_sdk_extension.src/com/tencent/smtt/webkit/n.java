package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.smtt.util.MttLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class n
{
  private static n jdField_a_of_type_ComTencentSmttWebkitN;
  private int jdField_a_of_type_Int = 2;
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences = null;
  private HashMap<String, Integer> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private boolean jdField_a_of_type_Boolean = false;
  private int[] jdField_a_of_type_ArrayOfInt = new int[6];
  
  private n()
  {
    b();
  }
  
  public static n a()
  {
    if (jdField_a_of_type_ComTencentSmttWebkitN == null) {
      jdField_a_of_type_ComTencentSmttWebkitN = new n();
    }
    return jdField_a_of_type_ComTencentSmttWebkitN;
  }
  
  private void a(String paramString, boolean paramBoolean)
  {
    if (paramString == "") {
      return;
    }
    int i = 0;
    if (paramBoolean) {}
    try
    {
      paramString = paramString.split(",");
      int k = paramString.length;
      int j = 0;
      Object localObject;
      while (i < k)
      {
        localObject = paramString[i];
        this.jdField_a_of_type_ArrayOfInt[j] = Integer.parseInt((String)localObject);
        j += 1;
        i += 1;
      }
      paramString = paramString.split(",");
      i = 0;
      while (i < paramString.length)
      {
        localObject = paramString[i].split(" ");
        this.jdField_a_of_type_JavaUtilHashMap.put(localObject[0], Integer.valueOf(Integer.parseInt(localObject[1])));
        i += 1;
      }
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    MttLog.e("SearchBehavior", "searchBehavoior-convertdata-fail");
  }
  
  private void b()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (this.jdField_a_of_type_AndroidContentSharedPreferences == null) {
      this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_search_behavior", 0);
    }
    a(this.jdField_a_of_type_AndroidContentSharedPreferences.getString("preferences_item_topnum", ""), true);
    a(this.jdField_a_of_type_AndroidContentSharedPreferences.getString("preferences_item_tophost", ""), false);
    if (this.jdField_a_of_type_ArrayOfInt[0] > 40) {
      c();
    }
    this.jdField_a_of_type_Boolean = true;
  }
  
  private void c()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      if (((Integer)localEntry.getValue()).intValue() <= 3)
      {
        ((Iterator)localObject).remove();
      }
      else
      {
        double d = ((Integer)localEntry.getValue()).intValue();
        Double.isNaN(d);
        localEntry.setValue(Integer.valueOf((int)(d * 0.8D)));
      }
    }
    if (this.jdField_a_of_type_ArrayOfInt[0] > 10000) {
      this.jdField_a_of_type_ArrayOfInt = new int[6];
    }
    if (this.jdField_a_of_type_JavaUtilHashMap.size() > 50) {
      this.jdField_a_of_type_JavaUtilHashMap.clear();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("reset new-size:");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaUtilHashMap.size());
    MttLog.d("SearchBehavior", ((StringBuilder)localObject).toString());
  }
  
  public int a(int paramInt)
  {
    int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
    if (arrayOfInt[0] >= 10)
    {
      double d1 = arrayOfInt[paramInt];
      double d2 = arrayOfInt[0];
      Double.isNaN(d2);
      Double.isNaN(d1);
      return (int)(d1 / (d2 * 1.0D) * 10.0D);
    }
    return -1;
  }
  
  public void a()
  {
    int i = this.jdField_a_of_type_Int;
    if (i % 2 != 0)
    {
      this.jdField_a_of_type_Int = (i + 1);
      return;
    }
    this.jdField_a_of_type_Int = (i + 1);
    Object localObject1 = this.jdField_a_of_type_AndroidContentSharedPreferences;
    i = 0;
    if (localObject1 == null) {
      this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_search_behavior", 0);
    }
    localObject1 = "";
    while (i < this.jdField_a_of_type_ArrayOfInt.length)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(",");
      ((StringBuilder)localObject2).append(this.jdField_a_of_type_ArrayOfInt[i]);
      localObject1 = ((StringBuilder)localObject2).toString();
      i += 1;
    }
    Object localObject2 = "";
    Object localObject3 = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
    while (((Iterator)localObject3).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject3).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject2);
      localStringBuilder.append(",");
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append(" ");
      localStringBuilder.append(localEntry.getValue());
      localObject2 = localStringBuilder.toString();
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("onSaveState top:");
    ((StringBuilder)localObject3).append(((String)localObject1).substring(1));
    ((StringBuilder)localObject3).append("/n topHost:");
    ((StringBuilder)localObject3).append(((String)localObject2).substring(1));
    MttLog.d("SearchBehavior", ((StringBuilder)localObject3).toString());
    localObject3 = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
    ((SharedPreferences.Editor)localObject3).putString("preferences_item_topnum", ((String)localObject1).substring(1));
    ((SharedPreferences.Editor)localObject3).putString("preferences_item_tophost", ((String)localObject2).substring(1));
    ((SharedPreferences.Editor)localObject3).commit();
  }
  
  public void a(int paramInt, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onAddNewBehavior host:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("  top:");
    ((StringBuilder)localObject).append(paramInt);
    MttLog.d("SearchBehavior", ((StringBuilder)localObject).toString());
    if (paramInt <= 5)
    {
      localObject = this.jdField_a_of_type_ArrayOfInt;
      localObject[paramInt] += 1;
    }
    if (this.jdField_a_of_type_JavaUtilHashMap.get(paramString) != null) {
      paramInt = ((Integer)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).intValue() + 1;
    } else {
      paramInt = 1;
    }
    if (!TextUtils.isEmpty(paramString)) {
      this.jdField_a_of_type_JavaUtilHashMap.put(paramString, Integer.valueOf(paramInt));
    }
    paramString = this.jdField_a_of_type_ArrayOfInt;
    paramString[0] += 1;
    a();
  }
  
  public boolean a(String paramString)
  {
    return (this.jdField_a_of_type_JavaUtilHashMap.get(paramString) != null) && (((Integer)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).intValue() >= 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */