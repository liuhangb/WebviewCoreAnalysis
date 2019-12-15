package com.tencent.smtt.net;

public class SessionInfo
{
  public static final byte MODE_DELETED = 2;
  public static final byte MODE_NEW = 0;
  public static final byte MODE_NORMAL = 1;
  public static final byte MODE_REPLACED = 3;
  public String cacheKey;
  public int cipherValue;
  public byte dbmode;
  public byte[] masterKey;
  public int masterKeyLength;
  public byte[] sessionID;
  public int sessionIDLength;
  public byte[] sessionTickets;
  public int sessionTicketsLength;
  public long sessionTicketsLifeHint;
  
  public byte[] getMasterKey()
  {
    return this.masterKey;
  }
  
  public int getMasterKeyLength()
  {
    return this.masterKeyLength;
  }
  
  public byte[] getSessionID()
  {
    return this.sessionID;
  }
  
  public int getSessionIDLength()
  {
    return this.sessionIDLength;
  }
  
  public byte[] getSessionTickets()
  {
    return this.sessionTickets;
  }
  
  public int getSessionTicketsLength()
  {
    return this.sessionTicketsLength;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\SessionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */