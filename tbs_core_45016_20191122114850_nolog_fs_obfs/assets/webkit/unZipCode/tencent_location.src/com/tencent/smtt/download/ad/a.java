package com.tencent.smtt.download.ad;

import org.json.JSONException;
import org.json.JSONObject;

public final class a
{
  int jdField_a_of_type_Int;
  long jdField_a_of_type_Long;
  public String a;
  int jdField_b_of_type_Int = 0;
  long jdField_b_of_type_Long;
  String jdField_b_of_type_JavaLangString;
  long c;
  public String c;
  String d;
  String e;
  String f;
  String g;
  String h;
  private String i;
  
  a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.jdField_c_of_type_Long = 0L;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_c_of_type_JavaLangString = paramString2;
    this.i = paramString3;
    this.d = paramString4;
    this.e = paramString5;
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_a_of_type_Int = 0;
    this.f = paramString6;
    this.jdField_b_of_type_JavaLangString = paramString7;
    this.g = paramString8;
    this.h = paramString9;
  }
  
  a(JSONObject paramJSONObject)
  {
    this.jdField_c_of_type_Long = 0L;
    try
    {
      this.jdField_a_of_type_JavaLangString = paramJSONObject.optString("packageName", null);
      this.jdField_c_of_type_JavaLangString = paramJSONObject.optString("refer", null);
      this.i = paramJSONObject.optString("linkUrl", null);
      this.d = paramJSONObject.optString("packagePath", null);
      this.e = paramJSONObject.optString("packageMd5", null);
      this.jdField_a_of_type_Long = paramJSONObject.optLong("pkgFileLastModifyTime", 0L);
      this.jdField_b_of_type_Long = paramJSONObject.optLong("lastShownTime", 0L);
      this.jdField_a_of_type_Int = paramJSONObject.optInt("showTime", 0);
      this.f = paramJSONObject.optString("businessId", null);
      this.jdField_b_of_type_JavaLangString = paramJSONObject.optString("appName", null);
      this.g = paramJSONObject.optString("imageBase64", null);
      this.jdField_b_of_type_Int = paramJSONObject.optInt("appState", 0);
      this.jdField_c_of_type_Long = paramJSONObject.optLong("installedTimestamp", 0L);
      this.h = paramJSONObject.optString("channelPackageName", null);
      return;
    }
    catch (Exception paramJSONObject) {}
  }
  
  JSONObject a()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("packageName", this.jdField_a_of_type_JavaLangString);
      localJSONObject.put("refer", this.jdField_c_of_type_JavaLangString);
      localJSONObject.put("linkUrl", this.i);
      localJSONObject.put("packagePath", this.d);
      localJSONObject.put("packageMd5", this.e);
      localJSONObject.put("pkgFileLastModifyTime", this.jdField_a_of_type_Long);
      localJSONObject.put("lastShownTime", this.jdField_b_of_type_Long);
      localJSONObject.put("showTime", this.jdField_a_of_type_Int);
      localJSONObject.put("businessId", this.f);
      localJSONObject.put("appName", this.jdField_b_of_type_JavaLangString);
      localJSONObject.put("imageBase64", this.g);
      localJSONObject.put("appState", this.jdField_b_of_type_Int);
      localJSONObject.put("installedTimestamp", this.jdField_c_of_type_Long);
      localJSONObject.put("channelPackageName", this.h);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public String toString()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("appName", this.jdField_b_of_type_JavaLangString);
      localJSONObject.put("packageName", this.jdField_a_of_type_JavaLangString);
      localJSONObject.put("channelPackageName", this.h);
      localJSONObject.put("refer", this.jdField_c_of_type_JavaLangString);
      localJSONObject.put("appState", this.jdField_b_of_type_Int);
      localJSONObject.put("packagePath", this.d);
      localJSONObject.put("packageMd5", this.e);
      localJSONObject.put("installedTimestamp", this.jdField_c_of_type_Long);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\download\ad\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */