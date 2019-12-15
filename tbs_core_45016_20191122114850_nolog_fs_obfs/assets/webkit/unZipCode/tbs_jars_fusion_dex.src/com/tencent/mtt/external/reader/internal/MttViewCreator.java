package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import com.tencent.mtt.external.reader.base.ReaderBaseView;
import com.tencent.mtt.external.reader.base.ReaderViewCreator;

public class MttViewCreator
  extends ReaderViewCreator
{
  public ReaderBaseView create(Context paramContext, int paramInt)
  {
    Object localObject1;
    if (paramInt == 1) {
      localObject1 = new MttReaderContentView(paramContext);
    } else if (paramInt == 5) {
      localObject1 = new MttWebView(paramContext);
    } else if (paramInt == 7) {
      localObject1 = new MttSelectView(paramContext);
    } else if (paramInt == 8) {
      localObject1 = new MttChmWebView(paramContext);
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = super.create(paramContext, paramInt);
    }
    return (ReaderBaseView)localObject2;
  }
  
  public ReaderBaseView create(Context paramContext, String paramString)
  {
    return super.create(paramContext, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttViewCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */