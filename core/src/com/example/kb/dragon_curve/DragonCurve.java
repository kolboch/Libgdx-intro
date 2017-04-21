package com.example.kb.dragon_curve;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Created by Karlo on 2017-04-19.
 */

public class DragonCurve extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private List<Integer> turnsSequence;
    private Vector2 drawBeginning;
    private static final int ITERATIONS = 20; // above 27 likely to throw OutOfMemoryException
    private static final float sideLength = 1f;

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        turnsSequence = DragonCurveGenerator.getSequence(ITERATIONS);
        drawBeginning = new Vector2(Gdx.graphics.getWidth() / 2 + 100, Gdx.graphics.getHeight() / 2 + 100);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        float currentX, currentY;
        currentX = drawBeginning.x; currentY = drawBeginning.y;

        int directionX = 0;  // 1 for right, -1 for left, 0 not specified
        int directionY = 0;  // 1 for up, -1 for down, 0 not specified
        float endingX, endingY;
        int directionTicker = 1;

        for(int i = 0; i < turnsSequence.size(); i++){
            switch(Math.abs(directionTicker) % 4){
                case 0:
                    directionX = 0;
                    directionY = 1;
                    break;
                case 1:
                    directionX = 1;
                    directionY = 0;
                    break;
                case 2:
                    directionX = 0;
                    directionY = -1;
                    break;
                case 3:
                    directionX = -1;
                    directionY = 0;
                    break;
            }
            if(turnsSequence.get(i) == 1){
                directionTicker--;
            }
            else{
                directionTicker++;
            }
            endingX = currentX + sideLength * directionX; endingY = currentY + sideLength * directionY;
            shapeRenderer.line(currentX, currentY, endingX, endingY);
            currentX = endingX;
            currentY = endingY;

        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        shapeRenderer = new ShapeRenderer();
    }
}
