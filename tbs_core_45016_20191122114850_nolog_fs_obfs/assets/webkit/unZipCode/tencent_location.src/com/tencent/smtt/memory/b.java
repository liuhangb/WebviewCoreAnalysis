package com.tencent.smtt.memory;

import android.os.Build.VERSION;
import com.tencent.smtt.webkit.ContextHolder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b
{
  private static int a = -2;
  
  public static int a()
  {
    int i = a;
    if (i != -2) {
      return i;
    }
    i = -1;
    int j = i;
    if (Build.VERSION.SDK_INT >= 16)
    {
      int k = 0;
      Object localObject = a(new String[] { "dumpsys", "vmeminfo" });
      j = i;
      try
      {
        Pattern localPattern = Pattern.compile("^VmLimit:\\s+([0-9]+) kB$");
        j = i;
        localObject = ((String)localObject).split(System.getProperty("line.separator"));
        j = i;
        int n = localObject.length;
        for (;;)
        {
          j = i;
          if (k >= n) {
            break;
          }
          j = i;
          Matcher localMatcher = localPattern.matcher(localObject[k]);
          int m = i;
          j = i;
          if (localMatcher.find())
          {
            j = i;
            m = Integer.parseInt(localMatcher.group(1));
          }
          k += 1;
          i = m;
        }
        localStringBuilder = new StringBuilder();
      }
      catch (Throwable localThrowable)
      {
        c.a(localThrowable);
      }
    }
    StringBuilder localStringBuilder;
    localStringBuilder.append("vmLimit = ");
    localStringBuilder.append(j);
    c.a("cmdsh", localStringBuilder.toString());
    a = j;
    return j;
  }
  
  public static String a(String... paramVarArgs)
  {
    Object localObject2 = "";
    Object localObject1 = localObject2;
    for (;;)
    {
      int i;
      try
      {
        Object localObject3 = new StringBuilder();
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(ContextHolder.getInstance().getDynamicLibFolderPath());
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append("/");
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append("libcmdsh.so");
        localObject1 = localObject2;
        localObject3 = ((StringBuilder)localObject3).toString();
        localObject1 = localObject2;
        localObject4 = new ArrayList();
        localObject1 = localObject2;
        ((List)localObject4).add(localObject3);
        localObject1 = localObject2;
        int j = paramVarArgs.length;
        i = 0;
        if (i < j)
        {
          localObject1 = localObject2;
          ((List)localObject4).add(paramVarArgs[i]);
          i += 1;
          continue;
        }
        localObject1 = localObject2;
        localObject3 = new ProcessBuilder((List)localObject4).start().getInputStream();
        localObject1 = localObject2;
        localObject4 = new byte['Ѐ'];
        paramVarArgs = (String[])localObject2;
        localObject1 = paramVarArgs;
        i = ((InputStream)localObject3).read((byte[])localObject4);
        if (i != -1) {
          break label276;
        }
        localObject1 = paramVarArgs;
        ((InputStream)localObject3).close();
      }
      catch (Throwable paramVarArgs)
      {
        Object localObject4;
        c.a(paramVarArgs);
        paramVarArgs = (String[])localObject1;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("result begin:\n");
        ((StringBuilder)localObject1).append(paramVarArgs);
        ((StringBuilder)localObject1).append("\nresult end");
        c.a("cmdsh", ((StringBuilder)localObject1).toString());
        return paramVarArgs;
      }
      localObject1 = paramVarArgs;
      localObject2 = new StringBuilder();
      localObject1 = paramVarArgs;
      ((StringBuilder)localObject2).append(paramVarArgs);
      localObject1 = paramVarArgs;
      ((StringBuilder)localObject2).append(new String((byte[])localObject4, 0, i));
      localObject1 = paramVarArgs;
      paramVarArgs = ((StringBuilder)localObject2).toString();
      continue;
      label276:
      if (i != 0) {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */