package com.oblivion;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class App extends Application { 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        CardJavaFX[] dealerCards = new CardJavaFX[5];
        CardJavaFX[] playerCards = new CardJavaFX[5];
        Deck deck = new Deck();

        Label dealerScoreLabel = new Label("0");
        Label playerScoreLabel = new Label("0");
        Label moneyLabel = new Label("42");
        Polygon betButton = createTriangleButton();

        // Add rectangles
        for (int col = 0; col < 5; col++) {
            dealerCards[col] = createDealerCard();
            dealerCards[col].getLabel().setVisible(false);
            dealerCards[col].getRectangle().setFill(Color.LIGHTSKYBLUE);
            grid.add(dealerCards[col].getStackPane(), col, 0);
        }
        
        // Add hold button
        Button holdButton = new Button("Hold");
        holdButton.setOnAction(event -> {
            System.out.println("Hold button pressed");
            for (CardJavaFX card : playerCards) {
                if (card.isSelected()) {
                    card.setCard(deck.getNextCard());
                    card.getLabel().setText(card.getSuit().name().substring(0,1) + "\n" + card.getValue() + "");
                    card.setSelected(false);
                }
                card.getRectangle().setDisable(true);
                holdButton.setDisable(true);
            }
            playerScoreLabel.setText(getScore(playerCards) + "");

            for (CardJavaFX card : dealerCards) {
                card.getLabel().setVisible(true);
            }
            dealerScoreLabel.setVisible(true);
            dealerScoreLabel.setText(getScore(dealerCards) + "");
        });
        grid.add(holdButton, 2, 1);

        for (int col = 0; col < 5; col++) {
            playerCards[col] = createPlayerCard();
            final int cardIndex = col;
            playerCards[col].getRectangle().setOnMouseClicked(event -> {
                // Add your logic for rectangle click (toggle on/off) here
                System.out.println("Player card " + (cardIndex + 1) + " toggled" +
                                    (playerCards[cardIndex].toggleSelected() ? " on" : " off"));
            });
            grid.add(playerCards[col].getStackPane(), col, 2);
        }

        // Add the score value of the current hand
        dealerScoreLabel.setVisible(false);
        dealerScoreLabel.setAlignment(Pos.CENTER);
        dealerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        grid.add(dealerScoreLabel, 5, 0);

        playerScoreLabel.setAlignment(Pos.CENTER);
        playerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        playerScoreLabel.setMinSize(30, 30);
        grid.add(playerScoreLabel, 5, 2);

        // Add money box
        moneyLabel.setStyle("-fx-border-color: black;");
        grid.add(moneyLabel, 0, 3);

        betButton.setOnMouseClicked(event -> {
            System.out.println("Bet button clicked");
        });
        grid.add(betButton, 1, 3);

        // for (int i = 0; i < 52; i++) {
        //     Card card = deck.getNextCard();
        //     System.out.println(card.getSuit().name() + " " + card.getValue());
        // }

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mario Poker");
        primaryStage.show();
    }

    private CardJavaFX createPlayerCard() {
        Rectangle rectangle = new Rectangle(46, 64);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setOnMouseClicked(event -> {
            // Add your logic for rectangle click (toggle on/off) here
            System.out.println("Rectangle clicked");
        });
        CardJavaFX card = new CardJavaFX(rectangle);
        return card;
    }

    private CardJavaFX createDealerCard() {
        Rectangle rectangle = new Rectangle(46, 64);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);
        CardJavaFX card = new CardJavaFX(rectangle);
        return card;
    }

    private Polygon createTriangleButton() {
        Polygon triangle = new Polygon();
        // make a equilateral triangle facing up
        triangle.getPoints().addAll(new Double[]{
            0.0, 0.0,
            15.0, -25.981,
            30.0, 0.0
        });
        
        triangle.setFill(Color.LIGHTBLUE);
        triangle.setStroke(Color.BLACK);
        triangle.setOnMouseClicked(event -> {
            // Add your logic for triangle button click here
            System.out.println("Triangle button clicked");
        });
        // triangle.autosize();
        return triangle;
    }

    private int getScore(Card[] cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("Card Array length should be 5");
        }
        Card[] sortedCards = Card.sort(cards);
        boolean sameSuit = false;
        boolean straight = false;
        boolean fourOfAKind = false;
        boolean fullHouse = false;
        boolean threeOfAKind = false;
        boolean twoPair = false;
        boolean pair = false;


        // convert ace to 14 if it is low
        for (Card card : sortedCards) {
            if (card.getValue() == 1) {
                card.setValue(14);
            }
            // check if all cards are the same suit
            if (card.getSuit() != sortedCards[0].getSuit()) {
                sameSuit = false;
            }
        }

        // check for flush
        for (int i = 0; i < sortedCards.length - 1; i++) {
            Card card = sortedCards[i];
            Card nextCard = sortedCards[i + 1];
            if (card.getValue() != nextCard.getValue() - 1) {
                break;
            }
        }
        


        if (straight) {
            if (sameSuit) {
                return 80;
            } else {
                return 40;
            }
        }else if (fourOfAKind) {
            return 70;
        } else if (fullHouse) {
            return 60;
        } else if (sameSuit) {
            return 50;
        } else if (threeOfAKind) {
            return 30;
        } else if (twoPair) {
            return 20;
        } else if (pair) {
            return 10;
        } else {
            return 0; // high card
        }
    }
}