package com.example.leohuang.password_manager.interfaces;

/**
 * @auther ZhangJianTao
 * @function
 * @Version 1.0
 * @since 2015/8/9
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
