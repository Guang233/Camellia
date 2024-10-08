package com.luoyu.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.luoyu.utils.FileUtil;
import com.luoyu.utils.PathUtil;
import com.luoyu.xposed.logging.LogCat;
import java.util.List;
import org.json.JSONObject;

public class MConfig {
  public static final String TAG = "Config(配置)";

  private volatile JSONObject json;

  private volatile MConfig config;

  private volatile String ConfigPath;

  /*
   * Create a new Config
   */
  public MConfig(@NonNull String ConfigPath) {
    try {
      this.config = this;
      this.ConfigPath = ConfigPath;
      var read = FileUtil.readFileString(PathUtil.getApkDataPath() + "config/" + this.ConfigPath);
      if (read == null) {
        FileUtil.writeToFile(PathUtil.getApkDataPath() + "config/" + this.ConfigPath, "{}");
      }
      read = FileUtil.readFileString(PathUtil.getApkDataPath() + "config/" + this.ConfigPath);
      this.json = new JSONObject(read);
    } catch (Exception e) {
      LogCat.e(TAG, e);
    }
  }

  public MConfig() {
    new MConfig("公式");
  }

  /*
   * ? it might be more graceful
   */

  public static MConfig create(@NonNull String ConfigPath) {
    return new MConfig(ConfigPath);
  }

  /*
   * void putData(String,Object);
   */
  public synchronized void putData(@NonNull String key, @Nullable Object value) {
    try {
      this.json.put(key, value);
      FileUtil.writeToFile(PathUtil.getApkDataPath() + "config/" + ConfigPath, json.toString());
    } catch (Exception e) {
      LogCat.e(TAG, e);
    }
  }

  /*
   * T getData(String,T);
   * 弃用
   */

  /*  @Deprecated
  public synchronized <T> T getData(@NonNull String key, @Nullable T defaultValue) {
      try {
          if (!this.json.isNull(key)) return (T) this.json.get(key);
      } catch (Exception err) {
          /*
           * Do nothing
           */
  /*  }
      return defaultValue;
  }*/

  /*
   * Object getData(String,Object);
   */

  public synchronized Object getData(@NonNull String key, @Nullable Object defaultValue) {
    try {
      if (!this.json.isNull(key)) return this.json.get(key);
    } catch (Exception err) {
      /*
       * Do nothing
       */
    }
    return defaultValue;
  }

  public synchronized String getStringData(@NonNull String key, @Nullable String defaultValue) {
    try {
      if (!this.json.isNull(key)) return (String) this.json.get(key);
    } catch (Exception err) {
      /*
       * Do nothing
       */
    }
    return defaultValue;
  }

  public synchronized int getIntData(@NonNull String key, @Nullable int defaultValue) {
    try {
      if (!this.json.isNull(key)) return (int) this.json.get(key);
    } catch (Exception err) {
      /*
       * Do nothing
       */
    }
    return defaultValue;
  }

  public synchronized Boolean getBooleanData(@NonNull String key, @Nullable boolean defaultValue) {
    try {
      return (Boolean) this.json.get(key);
    } catch (Exception err) {
      //   MItem.QQLog.i(TAG, Log.getStackTraceString(err));
      /*
       * Do nothing
       */
    }
    return defaultValue;
  }

  public synchronized List getListData(@NonNull String key, @Nullable List defaultValue) {
    try {
      if (!this.json.isNull(key)) return (List) this.json.get(key);
    } catch (Exception err) {
      /*
       * Do nothing
       */
    }
    return defaultValue;
  }
}
