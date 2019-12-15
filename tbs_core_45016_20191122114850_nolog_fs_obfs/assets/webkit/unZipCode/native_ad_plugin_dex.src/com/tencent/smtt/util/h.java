package com.tencent.smtt.util;

import org.chromium.base.CommandLine;

public class h
{
  int a;
  
  public h(int paramInt)
  {
    this.a = paramInt;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("switch_value:");
    localStringBuilder.append(this.a);
    MttLog.d("NativeOptSwitch", localStringBuilder.toString());
  }
  
  public void a(CommandLine paramCommandLine)
  {
    int j = this.a;
    if (j == 0)
    {
      MttLog.d("NativeOptSwitch", "OPT_CLOSE");
      return;
    }
    int i = 0;
    if (j == 1) {
      i = 1;
    }
    if ((i != 0) || ((this.a & 0x2) == 2))
    {
      MttLog.d("NativeOptSwitch", "OPT_PRECONNECT");
      paramCommandLine.appendSwitch("switch_enable_preconnect");
    }
    if ((i != 0) || ((this.a & 0x4) == 4))
    {
      MttLog.d("NativeOptSwitch", "OPT_MAIN_RESOURCE_PRECONNECT");
      paramCommandLine.appendSwitch("switch_enable_main_resource_preconnect");
    }
    if ((i != 0) || ((this.a & 0x8) == 8))
    {
      MttLog.d("NativeOptSwitch", "OPT_SUB_RESOURCE_PRECONNECT");
      paramCommandLine.appendSwitch("switch_enable_sub_resource_preconnect");
    }
    if ((i != 0) || ((this.a & 0x10) == 16))
    {
      MttLog.d("NativeOptSwitch", "OPT_CLICK_PRECONNECT");
      paramCommandLine.appendSwitch("switch_enable_click_preconnect");
    }
    if ((i != 0) || ((this.a & 0x20) == 32))
    {
      MttLog.d("NativeOptSwitch", "OPT_PRELOAD");
      paramCommandLine.appendSwitch("switch_enable_preload");
    }
    if ((i != 0) || ((this.a & 0x40) == 64))
    {
      MttLog.d("NativeOptSwitch", "OPT_SUB_RESOURCE_PRELOAD");
      paramCommandLine.appendSwitch("switch_enable_sub_resource_preload");
    }
    if ((i != 0) || ((this.a & 0x80) == 128))
    {
      MttLog.d("NativeOptSwitch", "OPT_FIRST_SCREEN");
      paramCommandLine.appendSwitch("switch_enable_first_screen");
    }
    if ((i != 0) || ((this.a & 0x100) == 256))
    {
      MttLog.d("NativeOptSwitch", "OPT_FONTCACHE_OPT");
      paramCommandLine.appendSwitch("switch_enable_fontcache_opt");
    }
    if ((i != 0) || ((this.a & 0x200) == 512))
    {
      MttLog.d("NativeOptSwitch", "OPT_FIRST_QUIC_LIST_ENABLE");
      paramCommandLine.appendSwitch("switch_first_quic_list_enable");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */