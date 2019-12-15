package org.chromium.content.browser.input;

import org.chromium.base.metrics.RecordHistogram;

class InputMethodUma
{
  void a()
  {
    RecordHistogram.recordEnumeratedHistogram("InputMethod.RegisterProxyView", 0, 4);
  }
  
  void b()
  {
    RecordHistogram.recordEnumeratedHistogram("InputMethod.RegisterProxyView", 1, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\InputMethodUma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */