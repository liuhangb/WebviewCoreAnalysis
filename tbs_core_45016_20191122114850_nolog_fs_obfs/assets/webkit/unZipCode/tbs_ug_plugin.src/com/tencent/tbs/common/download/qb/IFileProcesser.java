package com.tencent.tbs.common.download.qb;

import android.content.Context;
import java.io.File;

public abstract interface IFileProcesser
{
  public abstract IFileProcesser next();
  
  public abstract File process(Context paramContext, File paramFile, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\IFileProcesser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */