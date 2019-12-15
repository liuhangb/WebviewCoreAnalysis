package com.tencent.mtt.external.reader.base;

public final class ReaderConfig
{
  public boolean core_Using_One = true;
  public int font_size = 0;
  public int installed_qb_version = -1;
  public boolean pdf_contain_outline = false;
  public boolean scroll_mode = true;
  public int select_bar_height = 0;
  public int select_bar_with = 0;
  public String select_holder_resouce_id = "";
  public boolean show_menu = true;
  public String tempPath = "";
  public boolean txt_could_be_noval = false;
  
  public ReaderConfig clone()
  {
    ReaderConfig localReaderConfig = new ReaderConfig();
    localReaderConfig.font_size = this.font_size;
    localReaderConfig.scroll_mode = this.scroll_mode;
    localReaderConfig.select_bar_with = this.select_bar_with;
    localReaderConfig.select_bar_height = this.select_bar_height;
    localReaderConfig.select_holder_resouce_id = this.select_holder_resouce_id;
    localReaderConfig.core_Using_One = this.core_Using_One;
    localReaderConfig.tempPath = this.tempPath;
    localReaderConfig.pdf_contain_outline = this.pdf_contain_outline;
    localReaderConfig.txt_could_be_noval = this.txt_could_be_noval;
    localReaderConfig.show_menu = this.show_menu;
    return localReaderConfig;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */