package com.cmt.qq.view;


import com.cmt.qq.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView{
	
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	
	private int mScreenWidth;
	
	private int mMenuWidth;
	
	//dp
	private int mMenuRightPadding = 50;
	
	private boolean once;
	
	private boolean isOpen;
	public boolean getIsOpen(){
		return this.isOpen;
	}
	/**
	 * 使用未定义属性时调用
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	/**
	 * 当使用此自定义属性时，会调用此构造方法
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//获取定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, 
				R.styleable.SlideMenu, defStyleAttr, 0);
		
		int n = a.getIndexCount();
		for(int i=0; i<n; ++i){
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlideMenu_rightPadding:
					mMenuRightPadding = a.getDimensionPixelSize(attr,
							(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
							,50,context.getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		
		a.recycle();
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		mScreenWidth = outMetrics.widthPixels;
		
	}

	public SlidingMenu(Context context) {
		this(context, null);
	}

	/**
	 * 设置子View的宽和高
	 * 设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if(!once){
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
		
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			
			once = true;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/**
	 * 通过设置偏移量，将Menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		super.onLayout(changed, l, t, r, b);
		
		if(changed){
			this.scrollTo(mMenuWidth,0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		int action = ev.getAction();
		switch (action) {
			case MotionEvent.ACTION_UP:
				//隐藏在左边的宽度
				int scrollX = getScrollX();
				
				if(scrollX >= mMenuWidth/2){
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				}else{
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
				return true;
			default:
				break;
		}
		
		return super.onTouchEvent(ev);
	}
	/**
	 * 打开菜单
	 */
	public void openMenu(){
		this.smoothScrollBy(-mMenuWidth, 0);
		isOpen = true;
	}
	/**
	 * 关闭菜单
	 */
	public void closeMenu(){
		this.smoothScrollBy(mMenuWidth, 0);
		isOpen = false;
	}
	/**
	 * 切换菜单
	 */
	public void toggle(){
		
		if(isOpen){
			Log.d("cmt", "关闭菜单");
			closeMenu();
		}else{
			openMenu();
		}
	}
	
	/**
	 * 滚动发生时
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		//调用属性动画，设置TranslationX
		float scale = l*1.0f/mMenuWidth;
		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale*0.7f);
		//设置缩放动画以及透明度
		float rightScale = 0.7f+0.3f*scale;
		float leftScale = 1.0f -scale*0.3f;
		float leftAlpha = 0.6f+0.4f*(1-scale);
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);
	}
}
