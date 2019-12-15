package com.tencent.mtt.external.reader.base;

import android.content.Context;

public class ReaderChmView
  extends ReaderDefaultView
{
  String a = "file://";
  
  public ReaderChmView(Context paramContext)
  {
    super(paramContext);
  }
  
  public int create()
  {
    return 0;
  }
  
  public void loadLocalFile(String paramString)
  {
    this.mFeatureView.createChmWebview(this.mParentLayout, this.b);
    if (this.mFeatureView.a != null) {
      this.mFeatureView.a.setConfig(this.mReaderConfig);
    }
    ReaderFeatureWrapper localReaderFeatureWrapper = this.mFeatureView;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.a);
    localStringBuilder.append(paramString);
    localReaderFeatureWrapper.loadUrl(localStringBuilder.toString());
    a();
  }
  
  protected boolean onOtherEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    if (paramInt != 19) {
      if (paramInt == 3013) {}
    }
    switch (paramInt)
    {
    default: 
      return false;
    case 3011: 
      doCommand(((Integer)paramObject1).intValue(), null, paramObject2);
      break;
      this.mFeatureView.menuHide(false);
      break;
      if (((Integer)paramObject2).intValue() == 0)
      {
        loadLocalFile((String)paramObject1);
        postEvent(12, null, null);
      }
      break;
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderChmView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */