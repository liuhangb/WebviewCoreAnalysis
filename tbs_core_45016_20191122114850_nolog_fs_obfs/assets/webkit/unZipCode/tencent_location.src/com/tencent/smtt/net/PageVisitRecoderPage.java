package com.tencent.smtt.net;

import java.io.Serializable;
import java.util.Vector;

public class PageVisitRecoderPage
  implements Serializable
{
  Vector<String> mHostList = null;
  transient boolean mHostlistHasDone = false;
  String mUrl = null;
  int mVisitNum = 0;
  
  public PageVisitRecoderPage(String paramString, int paramInt, Vector<String> paramVector)
  {
    this.mUrl = paramString;
    this.mVisitNum = paramInt;
    this.mHostList = paramVector;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\PageVisitRecoderPage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */