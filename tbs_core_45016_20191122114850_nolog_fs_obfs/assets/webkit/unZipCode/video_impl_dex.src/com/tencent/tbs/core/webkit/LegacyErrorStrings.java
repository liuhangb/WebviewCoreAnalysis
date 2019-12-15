package com.tencent.tbs.core.webkit;

import android.content.Context;

class LegacyErrorStrings
{
  private static final String LOGTAG = "Http";
  
  private static int getResource(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 17039943;
    case 0: 
      return 17039951;
    case -1: 
      return 17039943;
    case -2: 
      return 17039950;
    case -3: 
      return 17039956;
    case -4: 
      return 17039944;
    case -5: 
      return 17039952;
    case -6: 
      return 17039945;
    case -7: 
      return 17039949;
    case -8: 
      return 17039954;
    case -9: 
      return 17039953;
    case -10: 
      return 17039368;
    case -11: 
      return 17039946;
    case -12: 
      return 17039367;
    case -13: 
      return 17039947;
    case -14: 
      return 17039948;
    }
    return 17039955;
  }
  
  static String getString(int paramInt, Context paramContext)
  {
    return paramContext.getText(getResource(paramInt)).toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\LegacyErrorStrings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */