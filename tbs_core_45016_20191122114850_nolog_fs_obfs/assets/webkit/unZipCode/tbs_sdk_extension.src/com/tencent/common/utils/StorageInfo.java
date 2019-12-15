package com.tencent.common.utils;

import android.text.TextUtils;

public class StorageInfo
{
  public boolean isInternal;
  public boolean isWritable = true;
  public String path;
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof StorageInfo))
    {
      paramObject = (StorageInfo)paramObject;
      if ((!TextUtils.isEmpty(this.path)) && (StringUtils.isStringEqual(this.path, ((StorageInfo)paramObject).path))) {
        return true;
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("\r\npath:");
    localStringBuilder.append(this.path);
    localStringBuilder.append("   isInternal:");
    localStringBuilder.append(this.isInternal);
    localStringBuilder.append("   isWritable:");
    localStringBuilder.append(this.isWritable);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\StorageInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */