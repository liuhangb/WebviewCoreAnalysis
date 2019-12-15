package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.Map;

public class ad
{
  public static final long a = ;
  
  public static String a(Context paramContext, boolean paramBoolean)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("T:");
    localStringBuilder1.append(a);
    localStringBuilder1.append(",");
    localStringBuilder1.append("F:");
    if (paramBoolean) {
      localObject = "1";
    } else {
      localObject = "0";
    }
    localStringBuilder1.append((String)localObject);
    if (!c.a.containsKey(c.b))
    {
      localObject = "";
    }
    else
    {
      localObject = (c.a)c.a.get(c.b);
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(c.b);
      localStringBuilder2.append("_");
      localStringBuilder2.append(((c.a)localObject).a.b(paramContext));
      localObject = localStringBuilder2.toString();
    }
    if (!TextUtils.isEmpty((CharSequence)localObject))
    {
      localStringBuilder1.append(",");
      localStringBuilder1.append("MSA:");
      localStringBuilder1.append((String)localObject);
    }
    Object localObject = ab.a(paramContext, "204");
    if (!TextUtils.isEmpty((CharSequence)localObject))
    {
      localStringBuilder1.append(",");
      localStringBuilder1.append("R:");
      localStringBuilder1.append((String)localObject);
    }
    int i = paramContext.getSharedPreferences("turingfd_protect_105578_28_tbsMini", 0).getInt("301", 0);
    if (i > 0)
    {
      localStringBuilder1.append(",");
      localStringBuilder1.append("RB:");
      localStringBuilder1.append(i);
    }
    if (!TextUtils.isEmpty(ab.a(paramContext, "105")))
    {
      localStringBuilder1.append(",");
      localStringBuilder1.append("TAT:");
      localStringBuilder1.append("1");
    }
    return localStringBuilder1.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\ad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */