package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class d
{
  private static d jdField_a_of_type_ComTencentSmttWebkitD;
  private SharedPreferences.Editor jdField_a_of_type_AndroidContentSharedPreferences$Editor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_render_debug_setting", 4);
  
  public static d a()
  {
    if (jdField_a_of_type_ComTencentSmttWebkitD == null) {
      jdField_a_of_type_ComTencentSmttWebkitD = new d();
    }
    return jdField_a_of_type_ComTencentSmttWebkitD;
  }
  
  public void a(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_AndroidContentSharedPreferences == null) {
      this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_render_debug_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
    localEditor.putBoolean("show-composited-layer-borders", paramBoolean);
    localEditor.commit();
  }
  
  public boolean a()
  {
    if (this.jdField_a_of_type_AndroidContentSharedPreferences == null) {
      this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_render_debug_setting", 0);
    }
    return this.jdField_a_of_type_AndroidContentSharedPreferences.getBoolean("show-composited-layer-borders", false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */