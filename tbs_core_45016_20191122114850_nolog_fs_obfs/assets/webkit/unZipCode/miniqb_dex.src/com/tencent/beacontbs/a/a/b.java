package com.tencent.beacontbs.a.a;

import java.util.Locale;

public final class b
{
  public static final String[][] a = new String[4][];
  
  static
  {
    a[0] = { "t_event", String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s int , %s int , %s int , %s int , %s text)", new Object[] { "t_event", "_id", "_time", "_type", "_prority", "_length", "_datas" }) };
    a[1] = { "t_strategy", String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s int unique , %s int , %s blob)", new Object[] { "t_strategy", "_id", "_key", "_ut", "_datas" }) };
    a[2] = { "t_req_data", String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s text unique , %s int , %s int , %s blob)", new Object[] { "t_req_data", "_id", "_rid", "_time", "_cnt", "_datas" }) };
    a[3] = { "t_conf", String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s text unique , %s text , %s int , %s int)", new Object[] { "t_conf", "_id", "_key", "_value", "_vdate", "_time" }) };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */