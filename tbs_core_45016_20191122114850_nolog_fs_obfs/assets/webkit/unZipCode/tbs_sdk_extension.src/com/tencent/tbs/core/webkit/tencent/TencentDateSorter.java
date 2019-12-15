package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.tbs.core.webkit.DateSorter;
import org.chromium.base.annotations.UsedByReflection;

public class TencentDateSorter
  extends DateSorter
  implements IX5DateSorter
{
  @UsedByReflection("WebCoreProxy.java")
  public TencentDateSorter(Context paramContext)
  {
    super(paramContext);
    paramContext = SmttResource.getQuantityString(SmttResource.loadIdentifierResource("x5_last_num_days", "plurals"), 7);
    this.mLabels[2] = String.format(paramContext, new Object[] { Integer.valueOf(7) });
    this.mLabels[3] = SmttResource.getString(SmttResource.loadIdentifierResource("x5_last_month", "string"));
    this.mLabels[4] = SmttResource.getString(SmttResource.loadIdentifierResource("x5_older", "string"));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentDateSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */