package com.tencent.tbs.common.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NativeImageLoader
{
  private static NativeImageLoader mInstance = new NativeImageLoader();
  int mCountsPreload;
  int mFoldCounts = 0;
  final Handler mHanderStack = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      if (NativeImageLoader.this.mStack.isEmpty()) {
        return;
      }
      NativeImageLoader.this.mImageThreadPool.execute(new Runnable()
      {
        public void run()
        {
          Object localObject1 = (NativeImageLoader.stackData)NativeImageLoader.this.mStack.pop();
          String str = ((NativeImageLoader.stackData)localObject1).getPath();
          Point localPoint = ((NativeImageLoader.stackData)localObject1).getPoint();
          Handler localHandler = ((NativeImageLoader.stackData)localObject1).getHandler();
          Object localObject2 = NativeImageLoader.this.getBitmapFromMemCache(str);
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = NativeImageLoader.this;
            int j = 0;
            int i;
            if (localPoint == null) {
              i = 0;
            } else {
              i = localPoint.x;
            }
            if (localPoint != null) {
              j = localPoint.y;
            }
            localObject1 = ((NativeImageLoader)localObject1).decodeThumbBitmapForFile(str, i, j);
          }
          localObject2 = localHandler.obtainMessage();
          ((Message)localObject2).obj = localObject1;
          localHandler.sendMessage((Message)localObject2);
          NativeImageLoader.this.addBitmapToMemoryCache(str, (Bitmap)localObject1);
        }
      });
    }
  };
  private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(10);
  int mImageViewWidth;
  List<String>[] mListDataInFoldNamePreLoad = null;
  private LruCache<String, Bitmap> mMemoryCache = new LruCache((int)(Runtime.getRuntime().maxMemory() / 1024L) / 4)
  {
    protected int sizeOf(String paramAnonymousString, Bitmap paramAnonymousBitmap)
    {
      return paramAnonymousBitmap.getRowBytes() * paramAnonymousBitmap.getHeight() / 1024;
    }
  };
  private boolean mNativeImageLoaderDestroyed = true;
  private Stack<stackData> mStack = new Stack();
  LruCache<String, Bitmap>[] preLoadImageCacheArrays = null;
  
  private void addBitmapToMemoryCache(String paramString, Bitmap paramBitmap)
  {
    if ((getBitmapFromMemCache(paramString) == null) && (paramBitmap != null)) {
      this.mMemoryCache.put(paramString, paramBitmap);
    }
  }
  
  private int computeScale(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    paramInt2 = 1;
    if (paramInt1 != 0)
    {
      if (paramInt1 == 0) {
        return 1;
      }
      int j = paramOptions.outWidth;
      int i = paramOptions.outHeight;
      if ((j > paramInt1) || (i > paramInt1))
      {
        float f1 = j;
        float f2 = paramInt1;
        paramInt1 = Math.round(f1 / f2);
        paramInt2 = Math.round(i / f2);
        if (paramInt1 < paramInt2) {
          return paramInt1;
        }
      }
      return paramInt2;
    }
    return 1;
  }
  
  private static Bitmap createScaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramInt1 > 0)
    {
      if (paramInt2 <= 0) {
        return null;
      }
      if (paramBitmap == null) {
        return null;
      }
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      Bitmap localBitmap;
      if (i > j) {
        localBitmap = Bitmap.createBitmap(paramBitmap, (i - j) / 2, 0, j, j);
      } else if (i < j) {
        localBitmap = Bitmap.createBitmap(paramBitmap, 0, (j - i) / 2, i, i);
      } else {
        localBitmap = paramBitmap;
      }
      if (paramBitmap != localBitmap) {
        paramBitmap.recycle();
      }
      paramBitmap = Bitmap.createScaledBitmap(localBitmap, paramInt1, paramInt2, false);
      if (localBitmap != paramBitmap) {
        localBitmap.recycle();
      }
      return paramBitmap;
    }
    return null;
  }
  
  private Bitmap decodeThumbBitmapForFile(String paramString, int paramInt1, int paramInt2)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions);
    localOptions.inSampleSize = computeScale(localOptions, paramInt1, paramInt2);
    localOptions.inJustDecodeBounds = false;
    return createScaleBitmap(BitmapFactory.decodeFile(paramString, localOptions), paramInt1, paramInt2);
  }
  
  private Bitmap getBitmapFromMemCache(String paramString)
  {
    return (Bitmap)this.mMemoryCache.get(paramString);
  }
  
  public static NativeImageLoader getInstance()
  {
    return mInstance;
  }
  
  public void clearCache()
  {
    this.mNativeImageLoaderDestroyed = true;
    Object localObject = this.mMemoryCache;
    if (localObject != null) {
      ((LruCache)localObject).evictAll();
    }
    if (this.preLoadImageCacheArrays != null)
    {
      int i = 0;
      while (i < this.mFoldCounts)
      {
        localObject = this.preLoadImageCacheArrays;
        if (localObject[i] != null) {
          localObject[i].evictAll();
        }
        i += 1;
      }
      this.preLoadImageCacheArrays = null;
    }
  }
  
  public Bitmap loadNativeImage(final String paramString, Point paramPoint, int paramInt, final NativeImageCallBack paramNativeImageCallBack)
  {
    Object localObject2 = getBitmapFromMemCache(paramString);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      LruCache[] arrayOfLruCache = this.preLoadImageCacheArrays;
      localObject1 = localObject2;
      if (arrayOfLruCache[paramInt] != null)
      {
        addBitmapToMemoryCache(paramString, (Bitmap)arrayOfLruCache[paramInt].get(paramString));
        localObject1 = getBitmapFromMemCache(paramString);
      }
    }
    paramNativeImageCallBack = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        super.handleMessage(paramAnonymousMessage);
        paramNativeImageCallBack.onImageLoader((Bitmap)paramAnonymousMessage.obj, paramString);
      }
    };
    if (localObject1 == null)
    {
      localObject2 = new stackData(null);
      ((stackData)localObject2).setPath(paramString);
      ((stackData)localObject2).setPoint(paramPoint);
      ((stackData)localObject2).setHandler(paramNativeImageCallBack);
      this.mStack.push(localObject2);
      paramString = this.mHanderStack.obtainMessage();
      this.mHanderStack.sendMessage(paramString);
    }
    return (Bitmap)localObject1;
  }
  
  public void preLoadImages(int paramInt1, HashMap<String, List<String>> paramHashMap, List<TBSFileChooserDialog.ItemImages> paramList, int paramInt2)
  {
    int i = 0;
    this.mNativeImageLoaderDestroyed = false;
    if (this.preLoadImageCacheArrays == null)
    {
      this.mImageViewWidth = paramInt2;
      this.mFoldCounts = paramList.size();
      paramInt2 = this.mFoldCounts;
      this.preLoadImageCacheArrays = ((LruCache[])new LruCache[paramInt2]);
      this.mListDataInFoldNamePreLoad = ((List[])new List[paramInt2]);
      this.mCountsPreload = paramInt1;
      paramInt1 = i;
      while (paramInt1 < this.mFoldCounts)
      {
        this.mListDataInFoldNamePreLoad[paramInt1] = ((List)paramHashMap.get(((TBSFileChooserDialog.ItemImages)paramList.get(paramInt1)).getFolderName()));
        paramInt1 += 1;
      }
      this.mImageThreadPool.execute(new Runnable()
      {
        public void run()
        {
          int i = 0;
          while (i < NativeImageLoader.this.mFoldCounts)
          {
            if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
              return;
            }
            if (NativeImageLoader.this.preLoadImageCacheArrays[i] == null)
            {
              j = (int)(Runtime.getRuntime().maxMemory() / 1024L) / 10;
              if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
                return;
              }
              NativeImageLoader.this.preLoadImageCacheArrays[i] = new LruCache(j);
            }
            if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
              return;
            }
            int k = NativeImageLoader.this.mListDataInFoldNamePreLoad[i].size();
            int j = 0;
            while ((j < k) && (j < NativeImageLoader.this.mCountsPreload) && (!NativeImageLoader.this.mNativeImageLoaderDestroyed))
            {
              String str = (String)NativeImageLoader.this.mListDataInFoldNamePreLoad[i].get(j);
              if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
                break;
              }
              Bitmap localBitmap2 = (Bitmap)NativeImageLoader.this.preLoadImageCacheArrays[i].get(str);
              if (localBitmap2 == null)
              {
                if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
                  break;
                }
                Bitmap localBitmap1 = (Bitmap)NativeImageLoader.this.preLoadImageCacheArrays[i].get(str);
                Object localObject = localBitmap1;
                if (localBitmap1 == null)
                {
                  localObject = NativeImageLoader.this;
                  localObject = ((NativeImageLoader)localObject).decodeThumbBitmapForFile(str, ((NativeImageLoader)localObject).mImageViewWidth, NativeImageLoader.this.mImageViewWidth);
                }
                NativeImageLoader.this.addBitmapToMemoryCache(str, (Bitmap)localObject);
                if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
                  break;
                }
                if ((NativeImageLoader.this.preLoadImageCacheArrays[i].get(str) == null) && (localBitmap2 != null))
                {
                  if (NativeImageLoader.this.mNativeImageLoaderDestroyed) {
                    break;
                  }
                  NativeImageLoader.this.preLoadImageCacheArrays[i].put(str, localBitmap2);
                }
              }
              j += 1;
            }
            i += 1;
          }
        }
      });
    }
  }
  
  public static abstract interface NativeImageCallBack
  {
    public abstract void onImageLoader(Bitmap paramBitmap, String paramString);
  }
  
  private class stackData
  {
    Handler mHandler;
    String mPath;
    Point mPoint;
    
    private stackData() {}
    
    public Handler getHandler()
    {
      return this.mHandler;
    }
    
    public String getPath()
    {
      return this.mPath;
    }
    
    public Point getPoint()
    {
      return this.mPoint;
    }
    
    public void setHandler(Handler paramHandler)
    {
      this.mHandler = paramHandler;
    }
    
    public void setPath(String paramString)
    {
      this.mPath = paramString;
    }
    
    public void setPoint(Point paramPoint)
    {
      this.mPoint = paramPoint;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\NativeImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */