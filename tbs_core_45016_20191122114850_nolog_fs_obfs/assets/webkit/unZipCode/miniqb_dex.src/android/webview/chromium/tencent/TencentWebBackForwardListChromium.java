package android.webview.chromium.tencent;

import android.webview.chromium.WebBackForwardListChromium;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import java.util.List;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.content_public.browser.NavigationHistory;

public class TencentWebBackForwardListChromium
  implements IX5WebBackForwardList, Cloneable
{
  private WebBackForwardListChromium real;
  
  private TencentWebBackForwardListChromium(WebBackForwardListChromium paramWebBackForwardListChromium)
  {
    this.real = paramWebBackForwardListChromium;
  }
  
  public TencentWebBackForwardListChromium(NavigationHistory paramNavigationHistory)
  {
    this.real = new WebBackForwardListChromium(paramNavigationHistory);
    this.real.mHistroryItemList.clear();
    int i = 0;
    while (i < paramNavigationHistory.getEntryCount())
    {
      this.real.mHistroryItemList.add(new TencentWebHistoryItemChromium(paramNavigationHistory.getEntryAtIndex(i)));
      i += 1;
    }
  }
  
  public TencentWebBackForwardListChromium clone()
  {
    return new TencentWebBackForwardListChromium(this.real.clone());
  }
  
  public int getCurrentIndex()
  {
    return this.real.getCurrentIndex();
  }
  
  public IX5WebHistoryItem getCurrentItem()
  {
    return (TencentWebHistoryItemChromium)this.real.getCurrentItem();
  }
  
  @UsedByReflection("UgJsApiExecutor.java")
  public IX5WebHistoryItem getItemAtIndex(int paramInt)
  {
    return (TencentWebHistoryItemChromium)this.real.getItemAtIndex(paramInt);
  }
  
  public int getSize()
  {
    return this.real.getSize();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebBackForwardListChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */