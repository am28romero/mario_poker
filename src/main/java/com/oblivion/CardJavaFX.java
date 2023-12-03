package com.oblivion;

import com.oblivion.Card.Suit;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class CardJavaFX {
    final private Rectangle rectangle;
    final private Label label = new Label();
    final private StackPane stackPane = new StackPane();
    Card card;
    private boolean selected = false;

    // public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }
    // public void setLabel(Label label) { this.label = label; }

    public CardJavaFX(Suit suit, int value, Rectangle rectangle) {
        card = new Card(suit, value);
        this.rectangle = rectangle;
        initialize();
        stackPane.getChildren().addAll(rectangle, label);
    }

    public CardJavaFX(Rectangle rectangle) {
        card = new Card();
        this.rectangle = rectangle;
        initialize();
        stackPane.getChildren().addAll(rectangle, label);
    }

    public Suit getSuit() { return card.getSuit(); }
    public int getValue() { return card.getValue(); }
    public Card getCard() { return card; }
    public Rectangle getRectangle() { return rectangle; }
    public Label getLabel() { return label; }
    public StackPane getStackPane() { return stackPane; }

    public void setSuit(Suit suit) {
        card.setSuit(suit);
        updateCardDisplay();
    }

    public void setValue(int value) {
        card.setValue(value);
        updateCardDisplay();
    }

    public void setCard(Card card) {
        this.card.setSuit(card.getSuit());
        this.card.setValue(card.getValue());
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
        label.setText(card.getSuit().name().substring(0,1) + "\n" + card.getValue() + "");
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
