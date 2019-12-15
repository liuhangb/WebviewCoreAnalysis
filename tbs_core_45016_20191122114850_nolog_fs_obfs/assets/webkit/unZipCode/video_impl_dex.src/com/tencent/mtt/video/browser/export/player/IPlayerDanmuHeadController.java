package com.tencent.mtt.video.browser.export.player;

import android.graphics.Bitmap;
import java.util.ArrayList;

public abstract interface IPlayerDanmuHeadController
{
  public abstract void destroy();
  
  public abstract Bitmap getHeadIcon(String paramString);
  
  public abstract void requestHeadIcon(ArrayList<String> paramArrayList);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IPlayerDanmuHeadController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */