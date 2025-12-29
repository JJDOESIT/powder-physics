package it.jjdoes.Atomix.Engine.InputProcessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Engine.Rendering.Renderer;

public class InputProcessor {
    private static int _brushSize;
    private static TextButton _selectedType;

    public void Update(Grid grid, Renderer renderer) {
        // Declare width and height of the grid
        int height = grid.GetHeight();
        int width = grid.GetWidth();

        // Left-click detected
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Fetch mouse coordinates
            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            renderer.GetViewport().unproject(mousePosition);

            // Convert mouse coordinates to grid coordinates
            int gridX = (int) mousePosition.x;
            int gridY = height - (int) mousePosition.y;

            // If the mouse was clicked outside the grid, return early
            if (gridX < 0 || gridX >= width || gridY < 0 || gridY >= height) {
                return;
            }

            // Fetch brush size and selected type
            int brushSize = _brushSize;
            String selectedType = _selectedType.getText().toString();

            // Create the block of selected type
            for (int i = gridY; i < gridY + _brushSize; i++) {
                for (int j = gridX; j < gridX + brushSize; j++) {
                    if (i < height && j < width) {
                        grid.Replace(TypeFactory.Create(selectedType), i, j);
                    }
                }
            }
        }
    }

    public static void SetBrushSize(int size) {
        _brushSize = size;
    }

    public static void SetSelectedType(TextButton type) {
        _selectedType = type;
    }

    public static TextButton GetSelectedType() {
        return _selectedType;
    }
}
