package com.aidchow.mm36d.ui.rv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.aidchow.entity.ImageEntity;

import java.util.List;

/**
 * Created by 78537 on 2017/4/4.
 */

public class ImageDiffCallBack extends DiffUtil.Callback {
    List<ImageEntity> oldImageEntityList, newImageEntityList;

    public ImageDiffCallBack(List<ImageEntity> oldImageEntityList, List<ImageEntity> newImageEntityList) {
        this.oldImageEntityList = oldImageEntityList;
        this.newImageEntityList = newImageEntityList;
    }

    @Override
    public int getOldListSize() {
        return oldImageEntityList == null ? 0 : oldImageEntityList.size();
    }

    @Override
    public int getNewListSize() {
        return newImageEntityList == null ? 0 : newImageEntityList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldImageEntityList.get(oldItemPosition).equals(newImageEntityList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ImageEntity oldImageEntity = oldImageEntityList.get(oldItemPosition);
        ImageEntity newImageEntity = newImageEntityList.get(newItemPosition);
        if (!oldImageEntity.equals(newImageEntity)) {
            return false;
        } else {
            return true;
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        ImageEntity oldImageEntity = oldImageEntityList.get(oldItemPosition);
        ImageEntity newImageEntity = newImageEntityList.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!oldImageEntity.getLikeNum().equals(newImageEntity.getLikeNum())) {
            payload.putString("LIKE_NUM", newImageEntity.getLikeNum());
        }
        if (!oldImageEntity.getBrowseNum().equals(newImageEntity.getBrowseNum())) {
            payload.putString("BROWSE_NUM", newImageEntity.getBrowseNum());
        }
        if (payload.size() == 0) {
            return null;
        }
        return payload;
    }
}
