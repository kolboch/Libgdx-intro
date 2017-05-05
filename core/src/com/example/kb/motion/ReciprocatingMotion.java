package com.example.kb.motion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Karlo on 2017-05-05.
 */

public class ReciprocatingMotion extends ApplicationAdapter {

    private static final float WORLD_SIZE = 600;
    private static final float CIRCLE_RADIUS = WORLD_SIZE / 30;
    private static final float MOVEMENT_DISTANCE = WORLD_SIZE / 3;
    private static final float CYCLE_TIME = 2.0f;

    private ShapeRenderer shapeRenderer;
    private ExtendViewport viewport;
    private long creationTime;

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);
        creationTime = TimeUtils.nanoTime();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        super.render();
        viewport.apply();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float worldCenterX = viewport.getWorldWidth() / 2;
        float worldCenterY = viewport.getWorldHeight() / 2;

        float timeFromStart = TimeUtils.nanoTime() - creationTime;
        float secondsFromStart = MathUtils.nanoToSec * timeFromStart;
        float cyclesPassed = secondsFromStart / CYCLE_TIME;
        float currentCycleState = cyclesPassed % 1;

        shapeRenderer.circle(worldCenterX + MathUtils.sin(currentCycleState * MathUtils.PI * 2) * MOVEMENT_DISTANCE, worldCenterY, CIRCLE_RADIUS);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}
