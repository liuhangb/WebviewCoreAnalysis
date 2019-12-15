package com.tencent.tbs.tbsshell.partner.player;

import android.content.Context;
import com.tencent.mtt.video.export.PlayerEnv;

public class TbsWhitePlayerEnv
  extends PlayerEnv
{
  TbsVideoProxy mTbsPlayer = null;
  
  public TbsWhitePlayerEnv(Context paramContext, TbsVideoProxy paramTbsVideoProxy)
  {
    super(paramContext);
    this.mTbsPlayer = paramTbsVideoProxy;
  }
  
  public int getPlayerEnvType()
  {
    return 4;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsWhitePlayerEnv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */