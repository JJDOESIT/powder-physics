package it.jjdoes.Atomix.Engine.Rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonStyle {
    private final Skin _skin;
    private final TextButton.TextButtonStyle _selectedStyle;
    private final TextButton.TextButtonStyle _notSelectedStyle;

    public TextButtonStyle(BitmapFont font, Color selectedBackgroundColor, Color notSelectedBackgroundColor, Color selectedFontColor, Color notSelectedFontColor) {
        // Create the skin
        _skin = new Skin();

        // Not selected pixmap
        Pixmap notSelectedPM = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        notSelectedPM.setColor(notSelectedBackgroundColor);
        notSelectedPM.fill();

        // Selected pixmap
        Pixmap selectedPM = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        selectedPM.setColor(selectedBackgroundColor);
        selectedPM.fill();

        // Add two skins
        _skin.add("not-selected", new TextureRegion(new Texture(notSelectedPM)));
        _skin.add("not-selected-font", new BitmapFont(font.getData(), font.getRegions(), font.usesIntegerPositions()));
        _skin.add("selected", new TextureRegion(new Texture(selectedPM)));
        _skin.add("selected-font", new BitmapFont(font.getData(), font.getRegions(), font.usesIntegerPositions()));

        // Create styles
        _notSelectedStyle = new TextButton.TextButtonStyle();
        _selectedStyle = new TextButton.TextButtonStyle();

        _notSelectedStyle.up = _skin.getDrawable("not-selected");
        _notSelectedStyle.font = _skin.getFont("not-selected-font");
        _notSelectedStyle.fontColor = notSelectedFontColor;

        _selectedStyle.up = _skin.getDrawable("selected");
        _selectedStyle.font = _skin.getFont("selected-font");
        _selectedStyle.fontColor = selectedFontColor;

        _skin.add("not-selected-style", _notSelectedStyle);
        _skin.add("selected-style", _selectedStyle);
    }

    public Skin GetSkin() {
        return _skin;
    }

    public TextButton.TextButtonStyle GetSelectedStyle() {
        return _selectedStyle;
    }

    public TextButton.TextButtonStyle GetNotSelectedStyle() {
        return _notSelectedStyle;
    }
}
