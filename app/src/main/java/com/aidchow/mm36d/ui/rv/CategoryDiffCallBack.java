package com.aidchow.mm36d.ui.rv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.entity.FindFlowerEntity;

import java.util.List;

/**
 * Created by 78537 on 2017/4/4.
 */

public class CategoryDiffCallBack extends DiffUtil.Callback {
    List<FindFlowerEntity> oldFindFlowerEntityList, newFindFlowerEntityList;

    public CategoryDiffCallBack(List<FindFlowerEntity> oldFindFlowerEntityList, List<FindFlowerEntity> newFindFlowerEntityList) {
        this.oldFindFlowerEntityList = oldFindFlowerEntityList;
        this.newFindFlowerEntityList = newFindFlowerEntityList;
    }

    @Override
    public int getOldListSize() {
        return oldFindFlowerEntityList == null ? 0 : oldFindFlowerEntityList.size();
    }

    @Override
    public int getNewListSize() {
        return newFindFlowerEntityList == null ? 0 : newFindFlowerEntityList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFindFlowerEntityList.get(oldItemPosition).equals(newFindFlowerEntityList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        FindFlowerEntity oldFindFlowerEntity = oldFindFlowerEntityList.get(oldItemPosition);
        FindFlowerEntity newFindFlowerEntity = newFindFlowerEntityList.get(newItemPosition);
        if (!oldFindFlowerEntity.equals(newFindFlowerEntity)) {
            return false;
        } else {
            return true;
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        FindFlowerEntity oldFindFlowerEntity = oldFindFlowerEntityList.get(oldItemPosition);
        FindFlowerEntity newFindFlowerEntity = newFindFlowerEntityList.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!oldFindFlowerEntity.getTotal().equals(newFindFlowerEntity.getToLabel())) {
            payload.putString("TOTAL_NUM", newFindFlowerEntity.getTotal());
        }
        if (payload.size() == 0) {
            return null;
        }
        return payload;
    }
}
