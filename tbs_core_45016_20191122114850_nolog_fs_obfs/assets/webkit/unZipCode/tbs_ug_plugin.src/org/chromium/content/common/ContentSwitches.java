package org.chromium.content.common;

public abstract class ContentSwitches
{
  public static String a(String[] paramArrayOfString, String paramString)
  {
    if (paramArrayOfString != null)
    {
      if (paramString == null) {
        return null;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("--");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("=");
      paramString = ((StringBuilder)localObject).toString();
      int j = paramArrayOfString.length;
      int i = 0;
      while (i < j)
      {
        localObject = paramArrayOfString[i];
        if ((localObject != null) && (((String)localObject).startsWith(paramString))) {
          return ((String)localObject).substring(paramString.length());
        }
        i += 1;
      }
      return null;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\common\ContentSwitches.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */