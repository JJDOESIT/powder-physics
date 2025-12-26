package it.jjdoes.Atomix.Rendering;

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

import it.jjdoes.Atomix.Main;


public class Header {
    public static HashMap<TextButton, TextButtonStyle> styles = new HashMap<>();

    public static void InitializeHeader(float screenWidth, float screenHeight, float headerHeight, Stage stage) {
        final int NUM_ROWS = 3;

        // Create the items table
        Table itemsTable = new Table();
        itemsTable.setSize(screenWidth, headerHeight - headerHeight / NUM_ROWS);
        itemsTable.setPosition(0, screenHeight - headerHeight);
        itemsTable.top().left();

        // Default font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("queensides.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();

        // First row
        AddTypeToHeader(itemsTable, font, "Sand", screenWidth / 4, headerHeight / NUM_ROWS, true, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(210 / 255f, 180 / 255f, 140 / 255f, 1f), Color.WHITE, new Color(80 / 255f, 60 / 255f, 40 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Dirt", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(139 / 255f, 115 / 255f, 85 / 255f, 1f), Color.WHITE, new Color(245 / 255f, 240 / 255f, 225 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Stone", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(120 / 255f, 120 / 255f, 120 / 255f, 1f), Color.WHITE, new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Water", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(0 / 255f, 120 / 255f, 255 / 255f, 1f), Color.WHITE, Color.WHITE);

        itemsTable.row();

        // Second row
        AddTypeToHeader(itemsTable, font, "Oil", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(30 / 255f, 30 / 255f, 30 / 255f, 1f), Color.WHITE, new Color(200 / 255f, 200 / 255f, 200 / 255f, 1f));
        AddTypeToHeader(itemsTable, font, "Honey", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 180 / 255f, 50 / 255f, 1f), Color.WHITE, Color.BLACK);
        AddTypeToHeader(itemsTable, font, "Acid", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(100 / 255f, 255 / 255f, 0 / 255f, 1f), Color.WHITE, Color.BLACK);
        AddTypeToHeader(itemsTable, font, "Lava", screenWidth / 4, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 80 / 255f, 0 / 255f, 1f), Color.WHITE, Color.BLACK);

        // Third row
        // Create the tools table
        Table toolsTable = new Table();
        toolsTable.setSize(screenWidth, headerHeight / NUM_ROWS);
        toolsTable.setPosition(0, screenHeight - headerHeight + headerHeight / NUM_ROWS * 2);
        toolsTable.top().left();
        AddLabelToHeader(toolsTable, font, "Brush Size ->", screenWidth / 3, headerHeight / NUM_ROWS, Color.WHITE, Color.BLACK);
        AddRadiusSliderToHeader(toolsTable, screenWidth / 3, headerHeight / NUM_ROWS);
        AddTypeToHeader(toolsTable, font, "Erase", screenWidth / 3, headerHeight / NUM_ROWS, false, new Color(0 / 255f, 204 / 255f, 0 / 255f, 1f), new Color(255 / 255f, 182 / 255f, 193 / 255f, 1f), Color.WHITE, Color.WHITE);

        // Add the tables to the stage
        stage.addActor(itemsTable);
        stage.addActor(toolsTable);
    }

    public static void AddTypeToHeader(Table table, BitmapFont font, String label, float width, float height, boolean selected, Color selectedBackgroundColor, Color notSelectedBackgroundColor, Color selectedFontColor, Color notSelectedFontColor) {
        TextButtonStyle style = new TextButtonStyle(font, selectedBackgroundColor, notSelectedBackgroundColor, selectedFontColor, notSelectedFontColor);

        TextButton button = new TextButton(label, style.GetSkin(), "not-selected-style");
        styles.put(button, style);
        button.getLabel().setFontScale(0.25f);

        // If the button is selected, set the selected type
        if (selected) {
            Main.SetSelectedType(button);
        }

        // Add an event handler to the button
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // De-select the other previously clicked button
                TextButton selectedType = Main.GetSelectedType();
                selectedType.setStyle(styles.get(selectedType).GetNotSelectedStyle());
                // Select the current type
                Main.SetSelectedType(button);
                button.setStyle(styles.get(button).GetSelectedStyle());
            }
        });
        // Set the button to be either selected by default or not
        button.setChecked(selected);
        // Add the button to the table
        table.add(button).width(width).height(height);
    }

    public static void AddRadiusSliderToHeader(Table table, float width, float height) {
        // Create a new Skin
        Skin skin = new Skin();

        // Load textures for the slider's background and knob
        Pixmap bg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bg.setColor(new Color(0 / 255f, 0 / 255f, 102 / 255f, 1));
        bg.fill();
        Pixmap knob = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        knob.setColor(new Color(102 / 255f, 178 / 255f, 255 / 255f, 1));
        knob.fillCircle(32, 32, 32);

        // Add the textures to the skin
        skin.add("bg", new TextureRegion(new Texture(bg)));
        skin.add("knob", new TextureRegion(new Texture(knob)));

        // Create the slider style
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = skin.getDrawable("bg"); // Set the background
        sliderStyle.knob = skin.getDrawable("knob"); // Set the knob
        sliderStyle.knob.setMinWidth(16);
        sliderStyle.knob.setMinHeight(16);
        sliderStyle.background.setMinHeight(height);
        skin.add("default-horizontal", sliderStyle);

        // Create the slider
        Slider slider = new Slider(1, 50, 5, false, skin); // Set min, max, step size
        slider.setStyle(sliderStyle); // Apply the custom style
        slider.setValue(5); // Set the initial value
        Main.SetBrushSize(5);

        // Add a listener to detect value changes
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Get the value of the slider when it changes
                float sliderValue = slider.getValue();
                // Change the gravity
                Main.SetBrushSize((int) sliderValue);
            }
        });

        // Add the slider to the table with specified width and height
        table.add(slider).width(width).height(height);
    }

    // Add an arbitrary label to the header
    public static void AddLabelToHeader(Table table, BitmapFont font, String text, float width, float height, Color backgroundColor, Color fontColor) {
        // Create the skin
        Skin skin = new Skin();

        // Create the pixmap
        Pixmap bg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bg.setColor(backgroundColor);
        bg.fill();
        skin.add("bg", new TextureRegion(new Texture(bg)));
        skin.add("default", font);

        // Create the label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = fontColor;
        labelStyle.background = skin.getDrawable("bg");
        skin.add("default", labelStyle);

        // Create the label
        Label label = new Label(text, skin);
        label.setFontScale(0.3f);
        label.setAlignment(Align.center);

        // Add the label to the table
        table.add(label).width(width).height(height);
    }
}
