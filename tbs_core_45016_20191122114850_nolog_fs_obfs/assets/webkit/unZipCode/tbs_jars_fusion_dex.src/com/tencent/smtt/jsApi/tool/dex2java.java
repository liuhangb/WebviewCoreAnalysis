package com.tencent.smtt.jsApi.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class dex2java
{
  private static int a = 1048576;
  
  public static void toByte(String paramString)
    throws IOException
  {
    paramString = new BufferedInputStream(new FileInputStream(paramString));
    Object localObject1 = new ByteArrayOutputStream(a);
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Available bytes:");
    ((StringBuilder)localObject2).append(paramString.available());
    ((StringBuilder)localObject2).toString();
    localObject2 = new byte[a];
    for (;;)
    {
      int i = paramString.read((byte[])localObject2);
      if (i == -1) {
        break;
      }
      ((ByteArrayOutputStream)localObject1).write((byte[])localObject2, 0, i);
    }
    paramString.close();
    paramString = ((ByteArrayOutputStream)localObject1).toByteArray();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Readed bytes count:");
    ((StringBuilder)localObject1).append(paramString.length);
    ((StringBuilder)localObject1).toString();
    if (paramString.length > 0)
    {
      apiContentTemplate.updateApiDexContents(paramString.toString());
      apiContentTemplate.toLocal();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\tool\dex2java.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */