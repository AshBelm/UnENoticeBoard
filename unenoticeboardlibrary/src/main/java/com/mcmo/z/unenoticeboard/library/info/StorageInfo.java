package com.mcmo.z.unenoticeboard.library.info;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StorageInfo implements FormatAble {
    private List<SDCardInfo> sdCardInfos;

    public StorageInfo(Context context) {
        sdCardInfos = getSDCardInfo(context);
    }

    public static boolean hadSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    public static String getSDCardInfoString(String sdcardPath) {
        if (TextUtils.isEmpty(sdcardPath)) {
            return "没有SD卡信息";
        }
        File file = new File(sdcardPath);
        StatFs statFs = new StatFs(file.getPath());
        StringBuilder builder = new StringBuilder();
        builder.append("可用/总共 ： ");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long blockCount = statFs.getBlockCountLong();
            long blockSize = statFs.getBlockSizeLong();
            long totalSpace = blockSize * blockCount;
            long availableBlocks = statFs.getAvailableBlocksLong();
            long availableSpace = availableBlocks * blockSize;
            builder.append(formatFileSize(availableSpace)).append("/").append(formatFileSize(totalSpace));
        } else {
            int blockCount = statFs.getBlockCount();
            int blockSize = statFs.getBlockSize();
            long totalSpace = blockSize * blockCount;
            int availableBlocks = statFs.getAvailableBlocks();
            long availableSpace = availableBlocks * blockSize;
            builder.append(formatFileSize(availableSpace)).append("/").append(formatFileSize(totalSpace));
        }
        return builder.toString();
    }

    /**
     * Return the information of sdcard.
     *
     * @return the information of sdcard ,
     */
    public static List<SDCardInfo> getSDCardInfo(Context context) {
        List<SDCardInfo> paths = new ArrayList<>();
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        if (sm == null) return paths;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<StorageVolume> storageVolumes = sm.getStorageVolumes();
            try {
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = StorageVolume.class.getMethod("getPath");
                for (StorageVolume storageVolume : storageVolumes) {
                    boolean isRemovable = storageVolume.isRemovable();
                    String state = storageVolume.getState();
                    String path = (String) getPathMethod.invoke(storageVolume);
                    paths.add(new SDCardInfo(path, state, isRemovable));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return paths;

        } else {
            try {
                Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = storageVolumeClazz.getMethod("getPath");
                Method isRemovableMethod = storageVolumeClazz.getMethod("isRemovable");
                //noinspection JavaReflectionMemberAccess
                Method getVolumeStateMethod = StorageManager.class.getMethod("getVolumeState", String.class);
                //noinspection JavaReflectionMemberAccess
                Method getVolumeListMethod = StorageManager.class.getMethod("getVolumeList");
                Object result = getVolumeListMethod.invoke(sm);
                final int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPathMethod.invoke(storageVolumeElement);
                    boolean isRemovable = (Boolean) isRemovableMethod.invoke(storageVolumeElement);
                    String state = (String) getVolumeStateMethod.invoke(sm, path);
                    paths.add(new SDCardInfo(path, state, isRemovable));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return paths;
        }
    }

    public static String formatFileSize(long size) {
        final int ratio = 1000;
        final float ratioF = 1000.0f;
        int kb = (int) (size / 1000);
        int m = kb / ratio;
        int g = m / ratio;
        if (g > 0) {
            float G = g + m % ratio / ratioF;
            return String.format("%.2fG", G);
        } else if (m > 0) {
            float M = m + kb % ratio / ratioF;
            return String.format("%.2fM", M);
        }else{
            return kb+"KB";
        }
    }

    /**
     * 内置SD卡：INTERNAL_STORAGE = 0;
     * 外置SD卡：EXTERNAL_STORAGE = 1;
     *
     * @return
     */
    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sdCardInfos.size(); i++) {
            switch (i) {
                case 0:
                    builder.append("内置SD卡:\n");
                    builder.append("\t\t").append(getSDCardInfoString(sdCardInfos.get(i).path)).append("\n");
                    break;
                case 1:
                    builder.append("外置SD卡:\n");
                    builder.append("\t\t").append(getSDCardInfoString(sdCardInfos.get(i).path)).append("\n");
                    break;
                default:
                    builder.append("\t\t").append(getSDCardInfoString(sdCardInfos.get(i).path)).append("\n");
                    break;
            }
        }
        return builder.toString();
    }

    public static class SDCardInfo {

        private String path;
        private String state;
        private boolean isRemovable;

        SDCardInfo(String path, String state, boolean isRemovable) {
            this.path = path;
            this.state = state;
            this.isRemovable = isRemovable;
        }

        public String getPath() {
            return path;
        }

        public String getState() {
            return state;
        }

        public boolean isRemovable() {
            return isRemovable;
        }

        @Override
        public String toString() {
            return "SDCardInfo {" +
                    "path = " + path +
                    ", state = " + state +
                    ", isRemovable = " + isRemovable +
                    '}';
        }
    }


}
