package com.oblivion;

import com.oblivion.Card.Suit;

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
        Button holdButton = new Button("Hold");

        // Add rectangles
        for (int col = 0; col < 5; col++) {
            dealerCards[col] = createDealerCard();
            dealerCards[col].getLabel().setVisible(false);
            dealerCards[col].getRectangle().setFill(Color.LIGHTSKYBLUE);
            grid.add(dealerCards[col].getStackPane(), col, 0);
        }
        
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

        // playerCards[0].setCard(new Card(Suit.CLUBS, 1));
        // playerCards[1].setCard(new Card(Suit.CLUBS, 10));
        // playerCards[2].setCard(new Card(Suit.CLUBS, 13));
        // playerCards[3].setCard(new Card(Suit.CLUBS, 12));
        // playerCards[4].setCard(new Card(Suit.CLUBS, 11));

        
        // Add hold button
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
            updateScoreLabel(playerScoreLabel, playerCards);
            
            for (CardJavaFX card : dealerCards) {
                card.getLabel().setVisible(true);
            }
            dealerScoreLabel.setVisible(true);
            updateScoreLabel(dealerScoreLabel, dealerCards);
        });
        grid.add(holdButton, 2, 1);
        
        
        // Add the score value of the current hand
        // dealerScoreLabel.setVisible(false);
        dealerScoreLabel.setAlignment(Pos.CENTER);
        dealerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        updateScoreLabel(dealerScoreLabel, dealerCards);
        grid.add(dealerScoreLabel, 5, 0);
        
        playerScoreLabel.setAlignment(Pos.CENTER);
        playerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        playerScoreLabel.setMinSize(30, 30);
        updateScoreLabel(playerScoreLabel, playerCards);
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

    private int getScore(CardJavaFX[] cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("Card Array length should be 5");
        }
        /*  First digit is the type of hand (high card, pair, two pair, three of a kind, straight, flush, full house, four of a kind, straight flush, royal flush)
        Second digit is the value of the highest pair card
        Third digit is the value of the other pair card (0 if there is no other pair); for a full house, this is the value of the three of a kind
        Fourth digit is the value of the highest card that isnt part of a pair; for a full house, this value is the two of a kind      */
        int score = 0x0000;
        int maxPair0 = 0x0;
        int maxPair1 = 0x0;
        int max = 0x0; // highest card that isnt part of a pair. 0 if all cards are part of a pair
        boolean sameSuit = true;
        boolean straight = false;
        boolean fourOfAKind = false;
        boolean fullHouse = false;
        boolean threeOfAKind = false;
        boolean twoPair = false;
        boolean onePair = false;


        Card[] sortedCards = new Card[5];
        for (int i = 0; i < cards.length; i++) {
            sortedCards[i] = cards[i].getCard();
        }
        
        // convert aces to 14 and check for same suit
        for (Card card : sortedCards) {
            if (card.getValue() == 1) {
                card.setValue(14); 
            }
            // check if all cards are the same suit
            if (card.getSuit() != sortedCards[0].getSuit()) {
                sameSuit = false;
            }
        }

        // sort the cards by descending value
        sortedCards = Card.sort(sortedCards);

        // check for straight
        for (int i = 0; i < sortedCards.length - 1; i++) {
            if (sortedCards[i].getValue() - sortedCards[i + 1].getValue() != 1) {
                straight = false;
                break;
            }
            straight = true;
        }
        
        if (!straight) {
            // check for four of a kind
            if (sortedCards[0].getValue() == sortedCards[3].getValue() || sortedCards[1].getValue() == sortedCards[4].getValue()) {
                fourOfAKind = true;
                maxPair0 = sortedCards[1].getValue();
                max = sortedCards[4].getValue();
            } else {
                // check for full house
                if (sortedCards[0].getValue() == sortedCards[2].getValue() && sortedCards[3].getValue() == sortedCards[4].getValue()) {
                    fullHouse = true;
                    maxPair0 = sortedCards[0].getValue();
                    maxPair1 = sortedCards[4].getValue();
                } else if (sortedCards[0].getValue() == sortedCards[1].getValue() && sortedCards[2].getValue() == sortedCards[4].getValue()) {
                    fullHouse = true;
                    maxPair0 = sortedCards[4].getValue();
                    maxPair1 = sortedCards[0].getValue();
                } else {
                    // check for three of a kind
                    if (sortedCards[0].getValue() == sortedCards[2].getValue() || sortedCards[1].getValue() == sortedCards[3].getValue() || sortedCards[2].getValue() == sortedCards[4].getValue()) {
                        threeOfAKind = true;
                        maxPair0 = sortedCards[2].getValue();
                    } else {
                        // check for two pair
                        if (sortedCards[0].getValue() == sortedCards[1].getValue() && sortedCards[2].getValue() == sortedCards[3].getValue()) {
                            twoPair = true;
                            maxPair0 = sortedCards[0].getValue();
                            maxPair1 = sortedCards[2].getValue();
                            max = sortedCards[4].getValue();
                        } else if (sortedCards[0].getValue() == sortedCards[1].getValue() && sortedCards[3].getValue() == sortedCards[4].getValue()) {
                            twoPair = true;
                            maxPair0 = sortedCards[2].getValue();
                            maxPair1 = sortedCards[4].getValue();
                            max = sortedCards[2].getValue();
                        } else if (sortedCards[1].getValue() == sortedCards[2].getValue() && sortedCards[3].getValue() == sortedCards[4].getValue()) {
                            twoPair = true;
                            maxPair0 = sortedCards[0].getValue();
                            maxPair1 = sortedCards[4].getValue();
                            max = sortedCards[0].getValue();
                        } else {
                            // check for pair
                            if (sortedCards[0].getValue() == sortedCards[1].getValue()) {
                                onePair = true;
                                maxPair0 = sortedCards[1].getValue();
                            } else if (sortedCards[1].getValue() == sortedCards[2].getValue()) {
                                onePair = true;
                                maxPair0 = sortedCards[2].getValue();
                            } else if (sortedCards[2].getValue() == sortedCards[3].getValue()) {
                                onePair = true;
                                maxPair0 = sortedCards[3].getValue();
                            } else if (sortedCards[3].getValue() == sortedCards[4].getValue()) {
                                onePair = true;
                                maxPair0 = sortedCards[4].getValue();
                            }
                        }
                    }
                }
            }
            for (Card card : sortedCards) {
                if (card.getValue() > max && card.getValue() != maxPair0 && card.getValue() != maxPair1) {
                    max = card.getValue();
                }
            }
        }

        if (straight && sameSuit) {
            if (sortedCards[0].getValue() == 14) {
                score += 0x9000; // royal flush
            } else {
                score += 0x8000; // straight flush
            }
        } else if (fourOfAKind) {
            score += 0x7000;
        } else if (fullHouse) {
            score += 0x6000;
        } else if (sameSuit) {
            score += 0x5000;
        } else if (straight) {
            score += 0x4000;
        } else if (threeOfAKind) {
            score += 0x3000;
        } else if (twoPair) {
            score += 0x2000;
        } else if (onePair) {
            score += 0x1000;
        } else {
            score += 0x0000; // high card
        }

        score += max + maxPair1 * 16 + maxPair0 * 16 * 16; // add the highest card that isnt part of a pair

        return score;
    }

    private void updateScoreLabel(Label label, CardJavaFX[] cards) {
        label.setText(String.format("%06X", getScore(cards)) + "");
    }
}