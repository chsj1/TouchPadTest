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
	// ����Stage����
	Stage stage;
	// ����TouchPad����
	Touchpad touchpad;
	// ����TouchPadStyle����
	TouchpadStyle style;
	// �����ͼ����
	TextureAtlas atlas;
	// ������Ϸҡ�˵ĵ�������Ӧ��Region
	TextureRegion bgRegion;
	// ������Ϸҡ�˵Ĳ��ݸ�����Ӧ��Region
	TextureRegion knobRegion;
	// ������Ϸҡ�˵ĵ�������Ӧ��Drawable
	TextureRegionDrawable bgRegionDrawable;
	// ������Ϸҡ�˵Ĳ��ݸ�����Ӧ��Drawable
	TextureRegionDrawable knobRegionDrawable;
	// �����ͼ��atlas�ļ�
	TextureAtlas tweenAtlas;
	// ������ϷԪ������Ӧ��Region����
	TextureRegion targetRegion;
	// ������ϷԪ������Ӧ��Iage����
	Image targetImage;
	// ����ƫ���ٶ�
	float positionChangeDelta = 10f;
	// ���ڱ�����ԴAtlas�ļ�
	TextureAtlas bgAtlas;
	// ����ͼƬ
	Image bbgImage;
	// ������������Դ��Region����
	TextureRegion bbgRegion;

	/**
	 * ��Ҫ��ɳ�ʼ������������ע��
	 */
	@Override
	public void create() {
		// ��ʼ��Stage����
		stage = new Stage(480, 800, false);
		// ��ʼ��Atlas����
		atlas = new TextureAtlas(Gdx.files.internal("data/touchpad.atlas"));
		// ��ʼ����������Ӧ��Region����
		bgRegion = atlas.findRegion("ipan");
		// ��ʼ���ٿظ�����Ӧ��Region����
		knobRegion = atlas.findRegion("ibtn");
		// ��ʼ����������Ӧ��Drawable����
		bgRegionDrawable = new TextureRegionDrawable(bgRegion);
		// ��ʼ���ٿظ�����Ӧ��Drawable����
		knobRegionDrawable = new TextureRegionDrawable(knobRegion);
		// ��ʼ��TouchPadStyle����
		style = new TouchpadStyle(bgRegionDrawable, knobRegionDrawable);
		// ��ʼ��TouchPad����
		touchpad = new Touchpad(10, style);
		// ��TouchPad������ɫ
		touchpad.setColor(Color.ORANGE);
		// ��ʼ��atlas����
		tweenAtlas = new TextureAtlas(Gdx.files.internal("data/tween.atlas"));
		// ��atlas���ҵ���ϷԪ������Ӧ��Region����
		targetRegion = tweenAtlas.findRegion("dianji");
		// ��ʼ����ϷԪ�ص�Image����
		targetImage = new Image(targetRegion);
		// ������ϷԪ�ص�λ��
		targetImage.setPosition(240, 400);
		// ���ٿظ���ӵ���¼�
		addListenerOnTouchPadToHandleTarget();
		// ��ʼ��atlas����
		bgAtlas = new TextureAtlas(Gdx.files.internal("data/movebg.atlas"));
		// ��������Region����ĳ�ʼ��
		bbgRegion = bgAtlas.findRegion("movebg");
		// ����Image����ĳ�ʼ��
		bbgImage = new Image(bbgRegion);
		// ���ó�������Ĵ�С
		bbgImage.setSize(480, 800);
		// ��������ӵ���̨��
		stage.addActor(bbgImage);
		// ����ϷԪ����ӵ���̨��
		stage.addActor(targetImage);
		// ����Ϸ�ٿظ���ӵ���̨��
		stage.addActor(touchpad);
		// ʹ��stage��������������¼�
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * ���ٿظ���Ӽ�����,����ʵ�������ϷԪ�صĿ���
	 */
	public void addListenerOnTouchPadToHandleTarget() {
		// ���ٿظ���Ӽ�����
		touchpad.addListener(new InputListener() {
			/**
			 * ����ָ����ʱ����
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// ��ȡx�᷽���ϵ�ƫ��ٷֱ�
				float percentX = touchpad.getKnobPercentX();
				// ��ȡy�᷽���ϵ�ƫ��ٷֱ�
				float percentY = touchpad.getKnobPercentY();
				// ��ȡx�᷽����ƫ��ľ���
				float positionX = targetImage.getX();
				// ��ȡy�᷽����ƫ��ľ���
				float positionY = targetImage.getY();
				// ������ϷԪ�ص�λ��
				targetImage.setPosition(positionX + positionChangeDelta
						* percentX, positionY + positionChangeDelta * percentY);
				return true;
			}

			/**
			 * ����ק��ʱ�����
			 */
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				// ��ȡx�᷽���ϵ�ƫ��ٷֱ�
				float percentX = touchpad.getKnobPercentX();
				// ��ȡy�᷽���ϵ�ƫ��ٷֱ�
				float percentY = touchpad.getKnobPercentY();
				// ��ȡx�᷽����ƫ��ľ���
				float positionX = targetImage.getX();
				// ��ȡy�᷽����ƫ��ľ���
				float positionY = targetImage.getY();
				// ������ϷԪ�ص�λ��
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
	 * ÿһ֡����ִ�У���������Ⱦ����
	 */
	@Override
	public void render() {
		// ���ñ���Ϊ��ɫ
		Gdx.gl.glClearColor(1, 1, 1, 1);
		// ����
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// ��������Ⱦ����
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
