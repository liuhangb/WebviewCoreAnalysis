package com.tencent.smtt.net;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class PageVisitRecorder
  implements Serializable
{
  private static final transient int MAINRESOURCE_OPT_ACTION_COUNT = 5;
  private static final transient int MAX_PAGELIST_SIZE = 10;
  private static final transient int MAX_SUBRESOURCE_SIZE = 10;
  public static final transient int PRECONNECT_SUBRESOURCE_NUM = 7;
  private static final transient int SUBRESOURCE_OPT_ACTION_COUNT = 7;
  private static final String TAG = "PageVisitRecorder";
  private LinkedHashMap<String, PageVisitRecoderPage> mPageVisitList = null;
  private LinkedHashMap<String, Integer> mSubResourceVisitList = null;
  private transient ArrayList<Map.Entry<String, PageVisitRecoderPage>> mTopMainResourceList = null;
  private transient ArrayList<Map.Entry<String, Integer>> mTopSubResourceList = null;
  
  private String removeParams(String paramString)
  {
    int i = paramString.indexOf("?");
    if (i != -1)
    {
      int j = paramString.indexOf('=');
      if (j != -1) {
        return paramString.substring(0, j + 1);
      }
      return paramString.substring(0, i + 1);
    }
    i = paramString.indexOf('#');
    if (i != -1) {
      return paramString.substring(0, i);
    }
    return paramString;
  }
  
  private void sort()
  {
    this.mTopMainResourceList = new ArrayList(this.mPageVisitList.entrySet());
    Collections.sort(this.mTopMainResourceList, new Comparator()
    {
      public int a(Map.Entry<String, PageVisitRecoderPage> paramAnonymousEntry1, Map.Entry<String, PageVisitRecoderPage> paramAnonymousEntry2)
      {
        if (((PageVisitRecoderPage)paramAnonymousEntry1.getValue()).mVisitNum - ((PageVisitRecoderPage)paramAnonymousEntry2.getValue()).mVisitNum == 0) {
          return 0;
        }
        if (((PageVisitRecoderPage)paramAnonymousEntry1.getValue()).mVisitNum - ((PageVisitRecoderPage)paramAnonymousEntry2.getValue()).mVisitNum < 0) {
          return 1;
        }
        return -1;
      }
    });
    this.mTopSubResourceList = new ArrayList(this.mSubResourceVisitList.entrySet());
    Collections.sort(this.mTopSubResourceList, new Comparator()
    {
      public int a(Map.Entry<String, Integer> paramAnonymousEntry1, Map.Entry<String, Integer> paramAnonymousEntry2)
      {
        if (((Integer)paramAnonymousEntry1.getValue()).intValue() - ((Integer)paramAnonymousEntry2.getValue()).intValue() == 0) {
          return 0;
        }
        if (((Integer)paramAnonymousEntry1.getValue()).intValue() - ((Integer)paramAnonymousEntry2.getValue()).intValue() < 0) {
          return 1;
        }
        return -1;
      }
    });
  }
  
  public void collectSubHost(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return;
      }
      paramString1 = removeParams(paramString1);
      if (this.mPageVisitList.containsKey(paramString1))
      {
        paramString1 = (PageVisitRecoderPage)this.mPageVisitList.get(paramString1);
        if ((paramString1.mHostlistHasDone) && (paramString1.mHostList.size() < 7) && (!paramString1.mHostList.contains(paramString2))) {
          paramString1.mHostList.add(paramString2);
        }
      }
      return;
    }
  }
  
  public Vector<String> getPageTopHost(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    paramString = removeParams(paramString);
    if (this.mPageVisitList.containsKey(paramString)) {
      return ((PageVisitRecoderPage)this.mPageVisitList.get(paramString)).mHostList;
    }
    return null;
  }
  
  public Vector<String> getTopMainResource()
  {
    if (this.mTopMainResourceList == null) {
      return null;
    }
    Vector localVector = new Vector();
    int i = 0;
    Iterator localIterator = this.mTopMainResourceList.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (i > 5) {
        return localVector;
      }
      localVector.add(localEntry.getKey());
      i += 1;
    }
    return localVector;
  }
  
  public Vector<String> getTopSubResource()
  {
    if (this.mTopSubResourceList == null) {
      return null;
    }
    Vector localVector = new Vector();
    int i = 0;
    Iterator localIterator = this.mTopSubResourceList.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (i >= 7) {
        return localVector;
      }
      localVector.add(localEntry.getKey());
      i += 1;
    }
    return localVector;
  }
  
  public void init()
  {
    if (this.mPageVisitList == null) {
      this.mPageVisitList = new LinkedHashMap(20, 0.75F, true);
    }
    if (this.mSubResourceVisitList == null) {
      this.mSubResourceVisitList = new LinkedHashMap(20, 0.75F, true);
    }
    sort();
  }
  
  public void onRequestSubResource(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    Object localObject;
    if (this.mSubResourceVisitList.containsKey(paramString))
    {
      localObject = this.mSubResourceVisitList;
      ((LinkedHashMap)localObject).put(paramString, Integer.valueOf(((Integer)((LinkedHashMap)localObject).get(paramString)).intValue() + 1));
    }
    else
    {
      this.mSubResourceVisitList.put(paramString, Integer.valueOf(1));
    }
    int i = this.mSubResourceVisitList.size() - 10;
    if (i > 0)
    {
      paramString = this.mSubResourceVisitList.entrySet().iterator();
      while ((paramString.hasNext()) && (i >= 0))
      {
        localObject = (Map.Entry)paramString.next();
        paramString.remove();
        i -= 1;
      }
    }
  }
  
  public void onRequestUrl(String paramString, Vector<String> paramVector)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramVector.size() == 0) {
        return;
      }
      paramString = removeParams(paramString);
      if (this.mPageVisitList.containsKey(paramString))
      {
        paramString = (PageVisitRecoderPage)this.mPageVisitList.get(paramString);
        paramString.mVisitNum += 1;
        paramString.mHostList = paramVector;
        paramString.mHostlistHasDone = true;
      }
      else
      {
        paramVector = new PageVisitRecoderPage(paramString, 1, paramVector);
        this.mPageVisitList.put(paramString, paramVector);
      }
      int i = this.mPageVisitList.size() - 10;
      if (i > 0)
      {
        paramString = this.mPageVisitList.entrySet().iterator();
        while ((paramString.hasNext()) && (i >= 0))
        {
          paramVector = (Map.Entry)paramString.next();
          paramString.remove();
          i -= 1;
        }
      }
      return;
    }
  }
  
  public void printToString() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\PageVisitRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */