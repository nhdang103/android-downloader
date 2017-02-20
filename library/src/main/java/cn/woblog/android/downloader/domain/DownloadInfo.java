package cn.woblog.android.downloader.domain;

import android.support.annotation.IntDef;
import android.text.TextUtils;
import cn.woblog.android.downloader.DownloadException;
import cn.woblog.android.downloader.callback.DownloadListener;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by renpingqing on 15/01/2017.
 */

public class DownloadInfo implements Serializable {

  /**
   *
   */
  public static final int STATUS_NONE = 0;
  /**
   *
   */
  public static final int STATUS_PREPARE_DOWNLOAD = 1;
  /**
   *
   */
  public static final int STATUS_DOWNLOADING = 2;
  /**
   *
   */
  public static final int STATUS_WAIT = 3;
  /**
   *
   */
  public static final int STATUS_PAUSED = 4;
  /**
   *
   */
  public static final int STATUS_COMPLETED = 5;
  /**
   *
   */
  public static final int STATUS_ERROR = 6;

  /**
   *
   */
  public static final int STATUS_REMOVED = 7;


  private int key;
  private String id;
  private int supportRanges;
  private long createAt;
  private String url;
  private String path;
  private long size;
  private long progress;
  @DownloadStatus
  private int status;

  private List<DownloadThreadInfo> downloadThreadInfos;

  private transient DownloadListener downloadListener;

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getProgress() {
    return progress;
  }

  public void setProgress(long progress) {
    this.progress = progress;
  }

  public DownloadListener getDownloadListener() {
    return downloadListener;
  }

  public void setDownloadListener(
      DownloadListener downloadListener) {
    this.downloadListener = downloadListener;
  }

  @DownloadStatus
  public int getStatus() {
    return status;
  }

  public void setStatus(@DownloadStatus int status) {
    this.status = status;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public int getSupportRanges() {
    return supportRanges;
  }

  public void setSupportRanges(int supportRanges) {
    this.supportRanges = supportRanges;
  }

  public boolean isSupportRanges() {
    return supportRanges == 0;
  }

  public void setSupportRanges(boolean supportRanges) {
    this.supportRanges = supportRanges ? 0 : 1;
  }

  public List<DownloadThreadInfo> getDownloadThreadInfos() {
    return downloadThreadInfos;
  }

  public void setDownloadThreadInfos(List<DownloadThreadInfo> downloadThreadInfos) {
    this.downloadThreadInfos = downloadThreadInfos;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DownloadInfo downloadInfo = (DownloadInfo) o;

    return id.equals(downloadInfo.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public boolean isPause() {
    return status == DownloadInfo.STATUS_PAUSED || status == DownloadInfo.STATUS_ERROR
        || status == STATUS_REMOVED;
  }

  @IntDef({STATUS_NONE, STATUS_PREPARE_DOWNLOAD, STATUS_DOWNLOADING, STATUS_WAIT, STATUS_PAUSED,
      STATUS_COMPLETED, STATUS_ERROR, STATUS_REMOVED})
  @Retention(RetentionPolicy.SOURCE)
  public @interface DownloadStatus {

  }

  public static final class Builder {

    private static final String DEFAULT_ENCODE = "utf-8";

    private String id;
    private long createAt = -1;
    private String url;
    private String path;

    public Builder() {

    }


    public Builder setCreateAt(long createAt) {
      this.createAt = createAt;
      return this;
    }


    public Builder setUrl(String url) {
      this.url = url;
      return this;
    }


    public Builder setPath(String path) {
      this.path = path;
      return this;
    }

    public void setId(String id) {
      this.id = id;
    }

    public DownloadInfo build() {
      DownloadInfo downloadInfo = new DownloadInfo();

      if (TextUtils.isEmpty(url)) {
        throw new DownloadException(DownloadException.EXCEPTION_URL_NULL, "url cannot be null.");
      }

      downloadInfo.setUrl(url);

      if (TextUtils.isEmpty(path)) {
        throw new DownloadException(DownloadException.EXCEPTION_PATH_NULL, "path cannot be null.");
      }

      downloadInfo.setPath(path);

      if (createAt == -1) {
        setCreateAt(System.currentTimeMillis());
      }

      downloadInfo.setKey(url.hashCode());

      if (TextUtils.isEmpty(id)) {
        downloadInfo.setId(url);
      }

      return downloadInfo;
    }

  }
}