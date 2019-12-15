package com.tencent.tbs.reader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.DexLoader;

public class TbsReader
{
  private com.tencent.tbs.tbsshell.partner.reader.TbsReader mReader = new com.tencent.tbs.tbsshell.partner.reader.TbsReader();
  
  public static Drawable getResDrawable(Integer paramInteger)
  {
    return com.tencent.tbs.tbsshell.partner.reader.TbsReader.getResDrawable(paramInteger);
  }
  
  public static String getResString(Integer paramInteger)
  {
    return com.tencent.tbs.tbsshell.partner.reader.TbsReader.getResString(paramInteger);
  }
  
  public static boolean isSupportCurrentPlatform(Context paramContext)
  {
    return com.tencent.tbs.tbsshell.partner.reader.TbsReader.isSupportCurrentPlatform(paramContext);
  }
  
  public static boolean isSupportExt(String paramString)
  {
    return com.tencent.tbs.tbsshell.partner.reader.TbsReader.isSupportExt(paramString);
  }
  
  public boolean checkPlugin(Context paramContext, String paramString, Boolean paramBoolean)
  {
    return this.mReader.checkPlugin(paramContext, paramString, paramBoolean);
  }
  
  public void destroy()
  {
    this.mReader.destroy();
  }
  
  public void doCommand(Integer paramInteger, Object paramObject1, Object paramObject2)
  {
    this.mReader.doCommand(paramInteger, paramObject1, paramObject2);
  }
  
  public boolean init(Context paramContext, DexLoader paramDexLoader, Object paramObject)
  {
    return this.mReader.init(paramContext, paramDexLoader, paramObject);
  }
  
  public boolean isDocumentTop()
  {
    return this.mReader.isDocumentTop();
  }
  
  public boolean isWebviewBasedPlugin()
  {
    return this.mReader.isWebviewBasedPlugin();
  }
  
  public void onSizeChanged(Integer paramInteger1, Integer paramInteger2)
  {
    this.mReader.onSizeChanged(paramInteger1, paramInteger2);
  }
  
  public boolean openFile(Context paramContext, Bundle paramBundle, FrameLayout paramFrameLayout)
  {
    return this.mReader.openFile(paramContext, paramBundle, paramFrameLayout);
  }
  
  public boolean openFile(Context paramContext, Bundle paramBundle, FrameLayout paramFrameLayout, View paramView)
  {
    return this.mReader.openFile(paramContext, paramBundle, paramFrameLayout);
  }
  
  public void setLogoDrawable(Drawable paramDrawable)
  {
    this.mReader.setLogoDrawable(paramDrawable);
  }
  
  public void userStatistics(String paramString)
  {
    this.mReader.userStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\reader\TbsReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */