package com.example.kb.SierpinskiTriangle;

/**
 * Created by Karlo on 2017-05-02.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SierpinskiTriangle extends ApplicationAdapter {

    private static final int RECURSIONS = 6;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private float WORLD_WIDTH = 100;
    private float WORLD_HEIGHT = 100;
    private float start_X = 50;
    private float start_Y = 50;
    private float SIDE_LENGTH = 100;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(WORLD_WIDTH + start_X, WORLD_HEIGHT + start_Y);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.triangle(start_X, start_Y, start_X + SIDE_LENGTH, start_Y, start_X + SIDE_LENGTH / 2, start_Y + SIDE_LENGTH / 2 * (float) Math.sqrt(3));
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawSierpinskiTriangle(start_X, start_Y, SIDE_LENGTH, RECURSIONS, Color.WHITE);
        shapeRenderer.end();
    }

    private void drawSierpinskiTriangle(float x, float y, float side_length, int recursionsLeft, Color c) {
        if (recursionsLeft > 0) {
            shapeRenderer.setColor(c);
            float x2, y2, x3, y3;
            x2 = x + side_length / 2;
            y2 = y;
            x3 = x + side_length / 4;
            float next_length = side_length / 2;
            y3 = next_length / 2 * (float) Math.sqrt(3.0);

            shapeRenderer.triangle(x2, y,
                    x3, y + next_length / 2 * (float) Math.sqrt(3.0),
                    x + side_length / 4 * 3, y + next_length / 2 * (float) Math.sqrt(3.0));

            drawSierpinskiTriangle(x, y, next_length, recursionsLeft - 1, Color.CYAN);
            drawSierpinskiTriangle(x2, y2, next_length, recursionsLeft - 1, Color.ORANGE);
            drawSierpinskiTriangle(x3, y + y3, next_length, recursionsLeft - 1, Color.RED);
        }
    }

}

