package com.whq.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator
 * 能够获取焦点的自定义TextView
 */
public class FoucsTextView extends TextView {
	// 在JAVA代码中创建控件
	public FoucsTextView(Context context) {
		super(context);
	}

	// 由系统调用(带属性+带上下文环境构造方法)
	public FoucsTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 由系统调用（带属性+上下文环境+布局文件中定义样式文件构造方法)
	public FoucsTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	//重写获取焦点的方法,由系统调用，调用时默认就能获取焦点
	@Override
	public boolean isFocused() {
		return true;
	}

}
