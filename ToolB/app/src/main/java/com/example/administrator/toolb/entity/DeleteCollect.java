package com.example.administrator.toolb.entity;

/**
 * Created by Administrator on 2016/7/21.
 */
public class DeleteCollect {
    private Collect collect;
    private boolean isSelect;

    @Override
    public String toString() {
        return "DeleteCollect{" +
                "collect=" + collect +
                ", isSelect=" + isSelect +
                '}';
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public DeleteCollect(Collect collect, boolean isSelect) {

        this.collect = collect;
        this.isSelect = isSelect;
    }
}
