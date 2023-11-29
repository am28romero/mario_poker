package com.oblivion;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class CardJavaFX extends Card {
    final private Rectangle rectangle;
    final private Label label = new Label();
    final private StackPane stackPane = new StackPane();
    private boolean selected = false;

    // public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }
    // public void setLabel(Label label) { this.label = label; }

    public CardJavaFX(Suit suit, int value, Rectangle rectangle) {
        super(suit, value);
        this.rectangle = rectangle;
        initialize();
        stackPane.getChildren().addAll(rectangle, label);
    }

    public CardJavaFX(Rectangle rectangle) {
        super();
        this.rectangle = rectangle;
        initialize();
        stackPane.getChildren().addAll(rectangle, label);
    }

    public Suit getSuit() { return suit; }
    public int getValue() { return value; }
    public Rectangle getRectangle() { return rectangle; }
    public Label getLabel() { return label; }
    public StackPane getStackPane() { return stackPane; }

    public void setSuit(Suit suit) {
        this.suit = suit;
        updateCardDisplay();
    }

    public void setValue(int value) {
        this.value = value;
        updateCardDisplay();
    }

    public void setCard(Card card) {
        this.suit = card.getSuit();
        this.value = card.getValue();
        updateCardDisplay();
    }

    public boolean toggleSelected() {
        if (selected) { setSelected(false); }
        else { setSelected(true); }
        return selected;
    }

    private void initialize() {
        updateCardDisplay();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        label.toFront();
        label.setMouseTransparent(true);
    }

    public void updateCardDisplay() {
        label.setText(suit.name().substring(0,1) + "\n" + value + "");
    }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selected) {
            rectangle.setFill(Color.RED);
        } else {
            rectangle.setFill(Color.LIGHTGREY);
        }
    }
}
