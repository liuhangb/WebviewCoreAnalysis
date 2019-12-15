package com.tencent.tbs.tbsshell.partner.reader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.tencent.mtt.external.reader.export.IReaderEventProxy;
import com.tencent.mtt.external.reader.export.IReaderProxy;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;
import com.tencent.smtt.export.external.DexLoader;
import dalvik.system.DexClassLoader;

public class TbsReaderProxy
  implements IReaderProxy
{
  private Context mContext;
  private DexLoader mLoader;
  private Drawable mLogoDrawable = null;
  private TbsReaderEventProxy mReaderEventProxy;
  private TbsReaderWebViewProxy mReaderWebViewProxy;
  
  public TbsReaderProxy(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void destroy()
  {
    Object localObject = this.mReaderWebViewProxy;
    if (localObject != null)
    {
      ((TbsReaderWebViewProxy)localObject).destroy();
      this.mReaderWebViewProxy = null;
    }
    localObject = this.mReaderEventProxy;
    if (localObject != null)
    {
      ((TbsReaderEventProxy)localObject).destroy();
      this.mReaderEventProxy = null;
    }
    this.mContext = null;
    this.mLoader = null;
  }
  
  public void doSearch(Context paramContext, String paramString)
  {
    getWebViewProxy();
    TbsReaderWebViewProxy localTbsReaderWebViewProxy = this.mReaderWebViewProxy;
    if (localTbsReaderWebViewProxy != null) {
      localTbsReaderWebViewProxy.doSearch(paramContext, paramString);
    }
  }
  
  public DexClassLoader getClassLoader()
  {
    return this.mLoader.getClassLoader();
  }
  
  public IReaderEventProxy getReaderEventProxy()
  {
    return this.mReaderEventProxy;
  }
  
  public IReaderWebViewProxy getWebViewProxy()
  {
    if (this.mReaderWebViewProxy == null)
    {
      this.mReaderWebViewProxy = new TbsReaderWebViewProxy(this.mContext, this.mReaderEventProxy);
      Drawable localDrawable = this.mLogoDrawable;
      if (localDrawable != null) {
        this.mReaderWebViewProxy.setLogo(localDrawable);
      }
    }
    return this.mReaderWebViewProxy;
  }
  
  public void setCallBackListener(Object paramObject)
  {
    this.mReaderEventProxy = new TbsReaderEventProxy(paramObject, this.mLoader);
  }
  
  public void setLoader(DexLoader paramDexLoader)
  {
    this.mLoader = paramDexLoader;
  }
  
  public void setLogoDrawable(Drawable paramDrawable)
  {
    this.mLogoDrawable = paramDrawable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\TbsReaderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */