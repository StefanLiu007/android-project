package com.example.leohuang.password_manager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 锟斤拷锟津滑筹拷锟剿碉拷锟斤拷ListView
 * 使锟斤拷锟斤拷注锟斤拷锟斤拷ListView锟斤拷Item锟侥诧拷锟斤拷锟斤拷希锟�
 * 锟斤拷效锟斤拷锟斤拷实锟斤拷锟角伙拷锟斤拷锟斤拷Item锟侥诧拷锟斤拷锟斤拷通锟斤拷锟斤拷锟斤拷PaddingLeft锟斤拷PaddingRight锟斤拷锟斤拷锟斤拷锟斤拷锟揭菜碉拷锟侥ｏ拷
 * 锟斤拷锟斤拷使锟矫达拷ListView时锟斤拷锟斤拷锟斤拷锟斤拷诓锟斤拷锟絀tem时使锟斤拷PaddingLeft锟斤拷PaddingRight锟斤拷
 * 锟斤拷锟斤拷锟皆硷拷锟斤拷写锟斤拷ListView锟斤拷锟窖达到锟斤拷要锟斤拷实锟街凤拷式
 * @author zhangshuo
 */
public class SlideListView2 extends ListView {

    /**锟斤拷止锟洁滑模式*/
    public static int MOD_FORBID = 0;
    /**锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟剿碉拷模式*/
    public static int MOD_LEFT = 1;
    /**锟斤拷锟斤拷锟斤拷锟襟滑筹拷锟剿碉拷模式*/
    public static int MOD_RIGHT = 2;
    /**锟斤拷锟揭撅拷锟斤拷锟皆伙拷锟斤拷锟剿碉拷模式*/
    public static int MOD_BOTH = 3;
    /**锟斤拷前锟斤拷模式*/
    private int mode = MOD_FORBID;
    /**锟斤拷锟剿碉拷锟侥筹拷锟斤拷*/
    private int leftLength = 0;
    /**锟揭诧拷说锟斤拷某锟斤拷锟�*/
    private int rightLength = 0;

    /**
     * 锟斤拷前锟斤拷锟斤拷锟斤拷ListView锟斤拷position
     */
    private int slidePosition;
    /**
     * 锟斤拷指锟斤拷锟斤拷X锟斤拷锟斤拷锟斤拷
     */
    private int downY;
    /**
     * 锟斤拷指锟斤拷锟斤拷Y锟斤拷锟斤拷锟斤拷
     */
    private int downX;
    /**
     * ListView锟斤拷item
     */
    private View itemView;
    /**
     * 锟斤拷锟斤拷锟斤拷
     */
    private Scroller scroller;
    /**
     * 锟斤拷为锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷小锟斤拷锟斤拷
     */
    private int mTouchSlop;

    /**
     * 锟叫讹拷锟角凤拷锟斤拷圆锟斤拷蚧锟�
     */
    private boolean canMove = false;
    /**
     * 锟斤拷示锟角凤拷锟斤拷刹嗷�
     */
    private boolean isSlided = false;

    public SlideListView2(Context context) {
        this(context, null);
    }

    public SlideListView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideListView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /**
     * 锟斤拷始锟斤拷锟剿碉拷锟侥伙拷锟斤拷模式
     * @param mode
     */
    public void initSlideMode(int mode){
        this.mode = mode;
    }

    /**
     * 锟斤拷锟斤拷锟斤拷锟斤拷锟较讹拷ListView item锟斤拷锟竭硷拷
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        int lastX = (int) ev.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("touch-->" + "down");
			
			/*锟斤拷前模式锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷直锟接凤拷锟截ｏ拷锟斤拷锟斤拷ListView锟斤拷锟斤拷去锟斤拷锟斤拷*/
                if(this.mode == MOD_FORBID){
                    return super.onTouchEvent(ev);
                }

                // 锟斤拷锟斤拷锟斤拷诓嗷拷锟斤拷状态锟斤拷锟洁滑锟斤拷去锟斤拷锟斤拷直锟接凤拷锟斤拷
                if (isSlided) {
                    scrollBack();
                    return false;
                }
                // 锟斤拷锟斤拷scroller锟斤拷锟斤拷锟斤拷没锟叫斤拷锟斤拷锟斤拷锟斤拷锟斤拷直锟接凤拷锟斤拷
                if (!scroller.isFinished()) {
                    return false;
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();

                slidePosition = pointToPosition(downX, downY);

                // 锟斤拷效锟斤拷position, 锟斤拷锟斤拷锟轿何达拷锟斤拷
                if (slidePosition == AdapterView.INVALID_POSITION) {
                    return super.onTouchEvent(ev);
                }

                // 锟斤拷取锟斤拷锟角碉拷锟斤拷锟絠tem view
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			
			/*锟剿达拷锟斤拷锟斤拷锟斤拷锟矫的伙拷锟斤拷模式锟斤拷锟皆讹拷锟斤拷取锟斤拷锟斤拷锟揭诧拷说锟斤拷某锟斤拷锟�*/
                if(this.mode == MOD_BOTH){
                    this.leftLength = -itemView.getPaddingLeft();
                    this.rightLength = -itemView.getPaddingRight();
                }else if(this.mode == MOD_LEFT){
                    this.leftLength = -itemView.getPaddingLeft();
                }else if(this.mode == MOD_RIGHT){
                    this.rightLength = -itemView.getPaddingRight();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("touch-->" + "move");

                if (!canMove
                        && slidePosition != AdapterView.INVALID_POSITION
                        && (Math.abs(ev.getX() - downX) > mTouchSlop && Math.abs(ev
                        .getY() - downY) < mTouchSlop)) {
                    int offsetX = downX - lastX;
                    if(offsetX > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
					/*锟斤拷锟斤拷锟斤拷锟斤拷*/
                        canMove = true;
                    }else if(offsetX < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
					/*锟斤拷锟斤拷锟斤拷锟揭伙拷*/
                        canMove = true;
                    }else{
                        canMove = false;
                    }
				/*锟剿段达拷锟斤拷锟斤拷为锟剿憋拷锟斤拷锟斤拷锟斤拷锟节诧拷锟津滑讹拷时同时锟斤拷锟斤拷ListView锟斤拷OnItemClickListener时锟斤拷*/
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent
                            .setAction(MotionEvent.ACTION_CANCEL
                                    | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                }
                if (canMove) {
				/*锟斤拷锟矫达拷锟斤拷锟皆ｏ拷锟斤拷锟斤拷锟节诧拷锟津滑讹拷时锟斤拷锟斤拷锟斤拷ListView锟斤拷锟斤拷锟斤拷锟铰癸拷锟斤拷*/
                    requestDisallowInterceptTouchEvent(true);

                    // 锟斤拷指锟较讹拷itemView锟斤拷锟斤拷, deltaX锟斤拷锟斤拷0锟斤拷锟斤拷锟斤拷锟斤拷锟叫★拷锟�0锟斤拷锟揭癸拷
                    int deltaX = downX - lastX;
                    if(deltaX < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
					/*锟斤拷锟斤拷*/
                        itemView.scrollTo(deltaX, 0);
                    }else if(deltaX > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
					/*锟斤拷锟揭伙拷*/
                        itemView.scrollTo(deltaX, 0);
                    }else{
                        itemView.scrollTo(0, 0);
                    }
                    return true; // 锟较讹拷锟斤拷时锟斤拷ListView锟斤拷锟斤拷锟斤拷
                }
            case MotionEvent.ACTION_UP:
                System.out.println("touch-->" + "up");
                if (canMove){
                    canMove = false;
                    scrollByDistanceX();
                }
                break;
        }

        // 锟斤拷锟斤拷直锟接斤拷锟斤拷ListView锟斤拷锟斤拷锟斤拷onTouchEvent锟铰硷拷
        return super.onTouchEvent(ev);
    }

    /**
     * 锟斤拷锟斤拷锟斤拷指锟斤拷锟斤拷itemView锟侥撅拷锟斤拷锟斤拷锟叫讹拷锟角癸拷锟斤拷锟斤拷锟斤拷始位锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷夜锟斤拷锟�
     */
    private void scrollByDistanceX() {
		/*锟斤拷前模式锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷直锟接凤拷锟斤拷*/
        if(this.mode == MOD_FORBID){
            return;
        }
        if(itemView.getScrollX() > 0 && (this.mode == MOD_BOTH || this.mode == MOD_RIGHT)){
			/*锟斤拷锟斤拷锟斤拷锟斤拷*/
            if (itemView.getScrollX() >= rightLength / 2) {
                scrollLeft();
            }  else {
                // 锟斤拷锟截碉拷原始位锟斤拷
                scrollBack();
            }
        }else if(itemView.getScrollX() < 0 && (this.mode == MOD_BOTH || this.mode == MOD_LEFT)){
			/*锟斤拷锟斤拷锟斤拷锟揭伙拷*/
            if (itemView.getScrollX() <= -leftLength / 2) {
                scrollRight();
            } else {
                // 锟斤拷锟截碉拷原始位锟斤拷
                scrollBack();
            }
        }else{
            // 锟斤拷锟截碉拷原始位锟斤拷
            scrollBack();
        }

    }

    /**
     * 锟斤拷锟揭伙拷锟斤拷锟斤拷getScrollX()锟斤拷锟截碉拷锟斤拷锟斤拷锟皆碉拷木锟斤拷耄拷锟斤拷锟斤拷锟絍iew锟斤拷锟皆滴拷愕斤拷锟绞硷拷锟斤拷锟斤拷木锟斤拷耄拷锟斤拷锟斤拷锟斤拷冶呋锟斤拷锟轿拷锟街�
     */
    private void scrollRight() {
        isSlided = true;
        final int delta = (leftLength + itemView.getScrollX());
        // 锟斤拷锟斤拷startScroll锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷一些锟斤拷锟斤拷锟侥诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷computeScroll()锟斤拷锟斤拷锟叫碉拷锟斤拷scrollTo锟斤拷锟斤拷锟斤拷item
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷锟斤拷itemView
    }

    /**
     * 锟斤拷锟襟滑讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷知锟斤拷锟斤拷锟襟滑讹拷为锟斤拷值
     */
    private void scrollLeft() {
        isSlided = true;
        final int delta = (rightLength - itemView.getScrollX());
        // 锟斤拷锟斤拷startScroll锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷一些锟斤拷锟斤拷锟侥诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷computeScroll()锟斤拷锟斤拷锟叫碉拷锟斤拷scrollTo锟斤拷锟斤拷锟斤拷item
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷锟斤拷itemView
    }

    /**
     * 锟斤拷锟斤拷锟斤拷原锟斤拷锟斤拷位锟斤拷
     */
    private void scrollBack() {
        isSlided = false;
        scroller.startScroll(itemView.getScrollX(), 0, -itemView.getScrollX(),
                0, Math.abs(itemView.getScrollX()));
        postInvalidate(); // 刷锟斤拷itemView
    }

    @Override
    public void computeScroll() {
        // 锟斤拷锟斤拷startScroll锟斤拷时锟斤拷scroller.computeScrollOffset()锟斤拷锟斤拷true锟斤拷
        if (scroller.computeScrollOffset()) {
            // 锟斤拷ListView item锟斤拷锟捷碉拷前锟侥癸拷锟斤拷偏锟斤拷锟斤拷锟斤拷锟叫癸拷锟斤拷
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

            postInvalidate();
        }
    }

    /**
     * 锟结供锟斤拷锟解部锟斤拷锟矫ｏ拷锟斤拷锟皆斤拷锟洁滑锟斤拷锟斤拷锟侥伙拷锟斤拷去
     */
    public void slideBack() {
        this.scrollBack();
    }

}