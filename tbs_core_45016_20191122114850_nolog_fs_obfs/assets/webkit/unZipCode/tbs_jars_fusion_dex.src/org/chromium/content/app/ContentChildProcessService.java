package org.chromium.content.app;

import org.chromium.base.process_launcher.ChildProcessService;

public class ContentChildProcessService
  extends ChildProcessService
{
  public ContentChildProcessService()
  {
    super(new ContentChildProcessServiceDelegate());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\app\ContentChildProcessService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */