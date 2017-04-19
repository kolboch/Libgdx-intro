package com.example.kb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CantorGasketDrawing extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private final int DEPTH = 4;
    private final Color backgroundGasket = Color.WHITE;
    private final Color fillGasket = Color.BLACK;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Rectangle outer = findLargestSquare();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundGasket);
        shapeRenderer.rect(outer.x, outer.y, outer.width, outer.height);
        drawCantorGasket(outer.x, outer.y, outer.width, DEPTH); // instead outer.width can be outer.height as we're dealing with square
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        shapeRenderer = new ShapeRenderer();
    }

    private void drawCantorGasket(float x, float y, float squareSide, int depth) {
        if (depth == 0) {
            return;
        } else {
            float nextSquareSide = squareSide / 3;
            //finding 9 squares
            Vector2[] points = new Vector2[9];
            /*
            * that is we divide squares, we always fill square 5, and make rec. call for other ones
            * 7 8 9
            * 4 5 6
            * 1 2 3
            * */
            // points setup
            for(int i = 0; i < 3; i++){
                float cordY = y + i * (squareSide / 3);
                for(int j = 0; j < 3; j++){
                    points[3*i + j] = new Vector2(x + j * (squareSide / 3),cordY);
                }
            }
            //filling center square
            Vector2 toFill = points[4];
            fillSquare(toFill.x, toFill.y, nextSquareSide, fillGasket);

            for (int i = 0; i < points.length; i++) {
                if(i == 4){
                    continue;
                }
                drawCantorGasket(points[i].x, points[i].y, nextSquareSide, depth - 1);
            }
        }
    }

    private void fillSquare(float x, float y, float side, Color c) {
        boolean wasDrawing = shapeRenderer.isDrawing();
        if (!wasDrawing) {
            shapeRenderer.begin();
        }
        shapeRenderer.setColor(c);
        shapeRenderer.rect(x, y, side, side);
        if (!wasDrawing) {
            shapeRenderer.end();
        }
    }

    private Rectangle findLargestSquare() {
        Rectangle largestSquare = new Rectangle();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (screenWidth > screenHeight) {
            largestSquare.x = screenWidth / 2 - screenHeight / 2; // to center wider dimension
            largestSquare.y = 0;    // no change here
            largestSquare.width = screenHeight;
            largestSquare.height = screenHeight;
        } else {
            largestSquare.y = screenHeight / 2 - screenWidth / 2; // to center wider dimension
            largestSquare.x = 0;    // no change here
            largestSquare.width = screenWidth;
            largestSquare.height = screenWidth;
        }
        return largestSquare;
    }
}
