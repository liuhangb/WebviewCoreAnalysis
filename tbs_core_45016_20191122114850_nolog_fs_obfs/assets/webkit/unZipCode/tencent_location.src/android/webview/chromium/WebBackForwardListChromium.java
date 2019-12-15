package android.webview.chromium;

import com.tencent.tbs.core.webkit.WebBackForwardList;
import com.tencent.tbs.core.webkit.WebHistoryItem;
import java.util.ArrayList;
import java.util.List;
import org.chromium.content_public.browser.NavigationHistory;

public class WebBackForwardListChromium
  extends WebBackForwardList
{
  private final int mCurrentIndex;
  public final List<WebHistoryItemChromium> mHistroryItemList;
  
  private WebBackForwardListChromium(List<WebHistoryItemChromium> paramList, int paramInt)
  {
    this.mHistroryItemList = paramList;
    this.mCurrentIndex = paramInt;
  }
  
  public WebBackForwardListChromium(NavigationHistory paramNavigationHistory)
  {
    this.mCurrentIndex = paramNavigationHistory.getCurrentEntryIndex();
    this.mHistroryItemList = new ArrayList(paramNavigationHistory.getEntryCount());
    int i = 0;
    while (i < paramNavigationHistory.getEntryCount())
    {
      this.mHistroryItemList.add(new WebHistoryItemChromium(paramNavigationHistory.getEntryAtIndex(i)));
      i += 1;
    }
  }
  
  public WebBackForwardListChromium clone()
  {
    try
    {
      Object localObject1 = new ArrayList(getSize());
      int i = 0;
      while (i < getSize())
      {
        ((List)localObject1).add(((WebHistoryItemChromium)this.mHistroryItemList.get(i)).clone());
        i += 1;
      }
      localObject1 = new WebBackForwardListChromium((List)localObject1, this.mCurrentIndex);
      return (WebBackForwardListChromium)localObject1;
    }
    finally {}
  }
  
  public int getCurrentIndex()
  {
    try
    {
      int i = this.mCurrentIndex;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public WebHistoryItem getCurrentItem()
  {
    try
    {
      int i = getSize();
      if (i == 0) {
        return null;
      }
      WebHistoryItem localWebHistoryItem = getItemAtIndex(getCurrentIndex());
      return localWebHistoryItem;
    }
    finally {}
  }
  
  public WebHistoryItem getItemAtIndex(int paramInt)
  {
    if (paramInt >= 0) {
      try
      {
        if (paramInt < getSize())
        {
          WebHistoryItem localWebHistoryItem = (WebHistoryItem)this.mHistroryItemList.get(paramInt);
          return localWebHistoryItem;
        }
      }
      finally {}
    }
    return null;
  }
  
  public int getSize()
  {
    try
    {
      int i = this.mHistroryItemList.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebBackForwardListChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */