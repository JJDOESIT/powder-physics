package it.jjdoes.Atomix.Engine.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;

import it.jjdoes.Atomix.Engine.InputProcessing.InputProcessor;

public class Header {
    public static HashMap<TextButton, TextButtonStyle> styles = new HashMap<>();

    public static void InitializeHeader(float width, float height, float headerHeight, Stage stage) {
        final int NUM_ROWS = 3;
        final int ITEMS_TABLE_COLUMNS = 4;
        final int TOOLS_TABLE_COLUMNS = 3;
        final int FONT_SIZE = 48;

        // Create the items table
        Table itemsTable = new Table();
        itemsTable.setSize(width, headerHeight - headerHeight / NUM_ROWS);
        itemsTable.setPosition(0, height);
        itemsTable.top().left();

        // Create the tools table
        Table toolsTable = new Table();
        toolsTable.setSize(width, headerHeight / NUM_ROWS);
        toolsTable.setPosition(0, height + headerHeight / NUM_ROWS * 2);
        toolsTable.top().left();

        // Create font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("queensides.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();

        // First row
        AddTypeToHeader(itemsTable, font, "Sand", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, true, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(210 / 255f, 180 / 255f, 140 / 255f, 1f), Color.WHITE, new Color(80 / 255f, 60 / 255f, 40 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Dirt", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(139 / 255f, 115 / 255f, 85 / 255f, 1f), Color.WHITE, new Color(245 / 255f, 240 / 255f, 225 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Stone", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(120 / 255f, 120 / 255f, 120 / 255f, 1f), Color.WHITE, new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Water", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(0 / 255f, 120 / 255f, 255 / 255f, 1f), Color.WHITE, Color.WHITE);

        itemsTable.row();

        // Second row
        AddTypeToHeader(itemsTable, font, "Oil", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(30 / 255f, 30 / 255f, 30 / 255f, 1f), Color.WHITE, new Color(200 / 255f, 200 / 255f, 200 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Honey", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 180 / 255f, 50 / 255f, 1f), Color.WHITE, Color.BLACK);
        AddTypeToHeader(itemsTable, font, "Acid", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(100 / 255f, 255 / 255f, 0 / 255f, 1f), Color.WHITE, Color.BLACK);
        AddTypeToHeader(itemsTable, font, "Lava", width / ITEMS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 80 / 255f, 0 / 255f, 1f), Color.WHITE, Color.BLACK);

        // Third row
        AddLabelToHeader(toolsTable, font, "Brush Size ->", width / TOOLS_TABLE_COLUMNS, headerHeight / NUM_ROWS, Color.WHITE, Color.BLACK);
        AddRadiusSliderToHeader(toolsTable, width / TOOLS_TABLE_COLUMNS, headerHeight / NUM_ROWS, new Color(0 / 255f, 0 / 255f, 102 / 255f, 1), new Color(102 / 255f, 178 / 255f, 255 / 255f, 1), 1, 50, 5, 5);
        AddTypeToHeader(toolsTable, font, "Erase", width / TOOLS_TABLE_COLUMNS, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 182 / 255f, 193 / 255f, 1f), Color.WHITE, Color.WHITE);

        // Add the tables to the stage
        stage.addActor(itemsTable);
        stage.addActor(toolsTable);
    }

    public static void AddTypeToHeader(Table table, BitmapFont font, String label, float width, float height, boolean selected, Color selectedBackgroundColor, Color notSelectedBackgroundColor, Color selectedFontColor, Color notSelectedFontColor) {
        // Create the TextButtonStyle
        TextButtonStyle style = new TextButtonStyle(font, selectedBackgroundColor, notSelectedBackgroundColor, selectedFontColor, notSelectedFontColor);

        // Create the TextButton, and save the style to the HashMap
        TextButton button = new TextButton(label, style.GetSkin(), "not-selected-style");
        button.getLabel().setFontScale(0.25f);
        styles.put(button, style);

        // If the button is selected, set the selected type
        if (selected) {
            InputProcessor.SetSelectedType(button);
        }

        // Add an event handler to the button
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // De-select the other previously clicked button
                TextButton selectedType = InputProcessor.GetSelectedType();
                selectedType.setStyle(styles.get(selectedType).GetNotSelectedStyle());
                // Select the current type by fetching the style from the HashMap
                InputProcessor.SetSelectedType(button);
                button.setStyle(styles.get(button).GetSelectedStyle());
            }
        });
        // Set the button to be either selected by default or not
        button.setChecked(selected);
        // Add the button to the table
        table.add(button).width(width).height(height);
    }

    public static void AddRadiusSliderToHeader(Table table, float width, float height, Color backgroundColor, Color knobColor, float min, float max, float step, float initial) {
        // Create a new Skin
        Skin skin = new Skin();

        // Load textures for the slider's background and knob
        Pixmap backgroundPM = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        backgroundPM.setColor(backgroundColor);
        backgroundPM.fill();

        Pixmap knobPM = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        knobPM.setColor(knobColor);
        knobPM.fillCircle(32, 32, 32);

        // Add the textures to the skin
        skin.add("background", new TextureRegion(new Texture(backgroundPM)));
        skin.add("knob", new TextureRegion(new Texture(knobPM)));

        // Create the slider style
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = skin.getDrawable("background");
        sliderStyle.knob = skin.getDrawable("knob");
        sliderStyle.knob.setMinWidth(16);
        sliderStyle.knob.setMinHeight(16);
        sliderStyle.background.setMinHeight(height);
        skin.add("default-horizontal", sliderStyle);

        // Create the slider
        Slider slider = new Slider(min, max, step, false, skin);
        slider.setValue(initial);
        InputProcessor.SetBrushSize((int) initial);
        slider.setStyle(sliderStyle);

        // Add a listener to detect value changes
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Get the value of the slider when it changes
                float sliderValue = slider.getValue();
                // Change the value
                InputProcessor.SetBrushSize((int) sliderValue);
            }
        });
        // Add the slider to the table with specified width and height
        table.add(slider).width(width).height(height);
    }

    public static void AddLabelToHeader(Table table, BitmapFont font, String text, float width, float height, Color backgroundColor, Color fontColor) {
        // Create the skin
        Skin skin = new Skin();

        // Create the pixmap
        Pixmap backgroundPM = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        backgroundPM.setColor(backgroundColor);
        backgroundPM.fill();
        skin.add("background", new TextureRegion(new Texture(backgroundPM)));
        skin.add("font", font);

        // Create the label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("font");
        labelStyle.fontColor = fontColor;
        labelStyle.background = skin.getDrawable("background");
        skin.add("default", labelStyle);

        // Create the label
        Label label = new Label(text, skin);
        label.setAlignment(Align.center);
        label.setFontScale(0.2f);

        // Add the label to the table
        table.add(label).width(width).height(height);
    }
}
