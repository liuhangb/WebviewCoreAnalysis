package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MiniQBCopyVerify
{
  private static final String TAG = "MiniQBUpgrade";
  private FileInfoSet mReferenceFileSet = null;
  private FileInfoSet mVerifyFileSet = null;
  
  private boolean isEqual(FileInfoSet paramFileInfoSet1, FileInfoSet paramFileInfoSet2)
  {
    if ((paramFileInfoSet1 != null) && (paramFileInfoSet1.get() != null) && (paramFileInfoSet2 != null) && (paramFileInfoSet2.get() != null))
    {
      Object localObject1 = paramFileInfoSet1.get();
      paramFileInfoSet1 = paramFileInfoSet2.get();
      paramFileInfoSet2 = ((Map)localObject1).entrySet().iterator();
      while (paramFileInfoSet2.hasNext())
      {
        Object localObject2 = (Map.Entry)paramFileInfoSet2.next();
        localObject1 = (String)((Map.Entry)localObject2).getKey();
        localObject2 = (FileInfo)((Map.Entry)localObject2).getValue();
        if (paramFileInfoSet1.containsKey(localObject1))
        {
          localObject1 = (FileInfo)paramFileInfoSet1.get(localObject1);
          if ((((FileInfo)localObject2).getFileSize() != ((FileInfo)localObject1).getFileSize()) || (((FileInfo)localObject2).getLastModify() != ((FileInfo)localObject1).getLastModify())) {
            return false;
          }
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public void generateReferenceValue(File paramFile)
  {
    this.mReferenceFileSet = new FileInfoSet(paramFile);
  }
  
  public void generateVerifyValue(File paramFile)
  {
    this.mVerifyFileSet = new FileInfoSet(paramFile);
  }
  
  public boolean verify()
  {
    FileInfoSet localFileInfoSet = this.mVerifyFileSet;
    if (localFileInfoSet != null)
    {
      if (this.mReferenceFileSet == null) {
        return false;
      }
      return (localFileInfoSet.get().size() == this.mReferenceFileSet.get().size()) && (isEqual(this.mReferenceFileSet, this.mVerifyFileSet));
    }
    return false;
  }
  
  class FileInfo
  {
    private long mFileSize;
    private long mLastModify;
    private String mName;
    
    FileInfo(String paramString, long paramLong1, long paramLong2)
    {
      this.mName = paramString;
      this.mFileSize = paramLong1;
      this.mLastModify = paramLong2;
    }
    
    long getFileSize()
    {
      return this.mFileSize;
    }
    
    long getLastModify()
    {
      return this.mLastModify;
    }
    
    String getName()
    {
      return this.mName;
    }
  }
  
  class FileInfoSet
  {
    private Map<String, MiniQBCopyVerify.FileInfo> mFileMap = new HashMap();
    
    FileInfoSet(File paramFile)
    {
      this.mFileMap.clear();
      generateFileInfo(paramFile);
    }
    
    private void generateFileInfo(File paramFile)
    {
      if (paramFile.isDirectory())
      {
        paramFile = paramFile.listFiles();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("generateFileInfo len=");
        localStringBuilder.append(paramFile.length);
        localStringBuilder.toString();
        int i = 0;
        while (i < paramFile.length)
        {
          generateFileInfo(paramFile[i]);
          i += 1;
        }
      }
      if (paramFile.isFile()) {
        generateFileInfo(paramFile.getName(), paramFile.length(), paramFile.lastModified());
      }
    }
    
    private void generateFileInfo(String paramString, long paramLong1, long paramLong2)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("generateFileInfo name=");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(",fileSize=");
      ((StringBuilder)localObject).append(paramLong1);
      ((StringBuilder)localObject).append(",lastModify=");
      ((StringBuilder)localObject).append(paramLong2);
      ((StringBuilder)localObject).toString();
      if ((paramString != null) && (paramString.length() > 0) && (paramLong1 > 0L) && (paramLong2 > 0L))
      {
        localObject = new MiniQBCopyVerify.FileInfo(MiniQBCopyVerify.this, paramString, paramLong1, paramLong2);
        if (!this.mFileMap.containsKey(paramString)) {
          this.mFileMap.put(paramString, localObject);
        }
      }
    }
    
    Map<String, MiniQBCopyVerify.FileInfo> get()
    {
      return this.mFileMap;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBCopyVerify.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */