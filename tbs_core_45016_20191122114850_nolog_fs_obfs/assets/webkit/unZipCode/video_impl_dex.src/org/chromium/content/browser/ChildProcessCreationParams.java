package org.chromium.content.browser;

import android.content.Context;
import android.os.Bundle;
import org.chromium.base.ContextUtils;

public class ChildProcessCreationParams
{
  private static ChildProcessCreationParams jdField_a_of_type_OrgChromiumContentBrowserChildProcessCreationParams;
  private final int jdField_a_of_type_Int;
  private final String jdField_a_of_type_JavaLangString;
  private final boolean b;
  private final boolean c;
  private final boolean d;
  
  public ChildProcessCreationParams(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    this.b = paramBoolean1;
    this.jdField_a_of_type_Int = paramInt;
    this.c = paramBoolean2;
    this.d = paramBoolean3;
  }
  
  public static ChildProcessCreationParams get()
  {
    return jdField_a_of_type_OrgChromiumContentBrowserChildProcessCreationParams;
  }
  
  public static boolean getBindToCallerCheck()
  {
    ChildProcessCreationParams localChildProcessCreationParams = get();
    return (localChildProcessCreationParams != null) && (localChildProcessCreationParams.c);
  }
  
  public static boolean getIgnoreVisibilityForImportance()
  {
    ChildProcessCreationParams localChildProcessCreationParams = get();
    return (localChildProcessCreationParams != null) && (localChildProcessCreationParams.d);
  }
  
  public static boolean getIsSandboxedServiceExternal()
  {
    ChildProcessCreationParams localChildProcessCreationParams = get();
    return (localChildProcessCreationParams != null) && (localChildProcessCreationParams.b);
  }
  
  public static int getLibraryProcessType(Bundle paramBundle)
  {
    return paramBundle.getInt("org.chromium.content.common.child_service_params.library_process_type", 2);
  }
  
  public static String getPackageNameForService()
  {
    ChildProcessCreationParams localChildProcessCreationParams = get();
    if (localChildProcessCreationParams != null) {
      return localChildProcessCreationParams.jdField_a_of_type_JavaLangString;
    }
    return ContextUtils.getApplicationContext().getPackageName();
  }
  
  public static void set(ChildProcessCreationParams paramChildProcessCreationParams)
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_OrgChromiumContentBrowserChildProcessCreationParams != null)) {
      throw new AssertionError();
    }
    jdField_a_of_type_OrgChromiumContentBrowserChildProcessCreationParams = paramChildProcessCreationParams;
  }
  
  public void addIntentExtras(Bundle paramBundle)
  {
    paramBundle.putInt("org.chromium.content.common.child_service_params.library_process_type", this.jdField_a_of_type_Int);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ChildProcessCreationParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */