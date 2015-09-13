package com.example.hellolibgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyGame implements ApplicationListener {
	// 定义Stage对象
	Stage stage;
	// 定义TouchPad对象
	Touchpad touchpad;
	// 定义TouchPadStyle对象
	TouchpadStyle style;
	// 定义合图对象
	TextureAtlas atlas;
	// 定义游戏摇杆的底座所对应的Region
	TextureRegion bgRegion;
	// 定义游戏摇杆的操纵杆所对应的Region
	TextureRegion knobRegion;
	// 定义游戏摇杆的底座所对应的Drawable
	TextureRegionDrawable bgRegionDrawable;
	// 定义游戏摇杆的操纵杆所对应的Drawable
	TextureRegionDrawable knobRegionDrawable;
	// 定义合图的atlas文件
	TextureAtlas tweenAtlas;
	// 定义游戏元素所对应的Region对象
	TextureRegion targetRegion;
	// 定义游戏元素所对应的Iage对象
	Image targetImage;
	// 最大的偏移速度
	float positionChangeDelta = 10f;
	// 用于背景资源Atlas文件
	TextureAtlas bgAtlas;
	// 背景图片
	Image bbgImage;
	// 用作背景的资源的Region对象
	TextureRegion bbgRegion;

	/**
	 * 主要完成初始化、监听器的注册
	 */
	@Override
	public void create() {
		// 初始化Stage对象
		stage = new Stage(480, 800, false);
		// 初始化Atlas对象
		atlas = new TextureAtlas(Gdx.files.internal("data/touchpad.atlas"));
		// 初始化底座所对应的Region对象
		bgRegion = atlas.findRegion("ipan");
		// 初始化操控杆所对应的Region对象
		knobRegion = atlas.findRegion("ibtn");
		// 初始化底座所对应的Drawable对象
		bgRegionDrawable = new TextureRegionDrawable(bgRegion);
		// 初始化操控杆所对应的Drawable对象
		knobRegionDrawable = new TextureRegionDrawable(knobRegion);
		// 初始化TouchPadStyle对象
		style = new TouchpadStyle(bgRegionDrawable, knobRegionDrawable);
		// 初始化TouchPad对象
		touchpad = new Touchpad(10, style);
		// 给TouchPad设置颜色
		touchpad.setColor(Color.ORANGE);
		// 初始化atlas对象
		tweenAtlas = new TextureAtlas(Gdx.files.internal("data/tween.atlas"));
		// 在atlas中找到游戏元素所对应的Region对象
		targetRegion = tweenAtlas.findRegion("dianji");
		// 初始化游戏元素的Image对象
		targetImage = new Image(targetRegion);
		// 设置游戏元素的位置
		targetImage.setPosition(240, 400);
		// 给操控杆添加点击事件
		addListenerOnTouchPadToHandleTarget();
		// 初始化atlas对象
		bgAtlas = new TextureAtlas(Gdx.files.internal("data/movebg.atlas"));
		// 场景背景Region对象的初始化
		bbgRegion = bgAtlas.findRegion("movebg");
		// 场景Image对象的初始化
		bbgImage = new Image(bbgRegion);
		// 设置场景对象的大小
		bbgImage.setSize(480, 800);
		// 将场景添加到舞台上
		stage.addActor(bbgImage);
		// 将游戏元素添加到舞台中
		stage.addActor(targetImage);
		// 将游戏操控杆添加到舞台中
		stage.addActor(touchpad);
		// 使用stage来处理输入输出事件
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * 给操控杆添加监听器,用于实现其对游戏元素的控制
	 */
	public void addListenerOnTouchPadToHandleTarget() {
		// 给操控杆添加监听器
		touchpad.addListener(new InputListener() {
			/**
			 * 当手指按下时调用
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// 获取x轴方向上的偏离百分比
				float percentX = touchpad.getKnobPercentX();
				// 获取y轴方向上的偏离百分比
				float percentY = touchpad.getKnobPercentY();
				// 获取x轴方向上偏离的距离
				float positionX = targetImage.getX();
				// 获取y轴方向上偏离的距离
				float positionY = targetImage.getY();
				// 更新游戏元素的位置
				targetImage.setPosition(positionX + positionChangeDelta
						* percentX, positionY + positionChangeDelta * percentY);
				return true;
			}

			/**
			 * 当拖拽的时候调用
			 */
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				// 获取x轴方向上的偏离百分比
				float percentX = touchpad.getKnobPercentX();
				// 获取y轴方向上的偏离百分比
				float percentY = touchpad.getKnobPercentY();
				// 获取x轴方向上偏离的距离
				float positionX = targetImage.getX();
				// 获取y轴方向上偏离的距离
				float positionY = targetImage.getY();
				// 更新游戏元素的位置
				targetImage.setPosition(positionX + positionChangeDelta
						* percentX, positionY + positionChangeDelta * percentY);
			}
		});
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/**
	 * 每一帧都会执行，将画面渲染出来
	 */
	@Override
	public void render() {
		// 设置背景为白色
		Gdx.gl.glClearColor(1, 1, 1, 1);
		// 清屏
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// 将画面渲染出来
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
