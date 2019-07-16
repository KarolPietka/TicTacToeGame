package com.kodilla.TicTacToe;

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.Random;

public class TickTacToe extends Application {

    private char player = 'X';
    private char computer = 'O';

    private Cell[][] cell = new Cell[3][3];

    private Label lblStatus = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        Scene scene = new Scene(borderPane, 650, 450);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;

        return true;
    }

    public boolean isWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() == token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }

        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        }

        if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }

        return false;
    }

    public class Cell extends Pane {
        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: GREEN");
            this.setPrefSize(1000, 1000);
            this.setOnMouseClicked(e -> playerTurn());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;

            if (token == 'X') {
                Line line1 = new Line(10, 10,
                        this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                line1.setStroke(Color.BLACK);
                Line line2 = new Line(10, this.getHeight() - 10,
                        this.getWidth() - 10, 10);
                line2.startYProperty().bind(
                        this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                line2.setStroke(Color.BLACK);

                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(
                        this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(
                        this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);
            }
        }
        private boolean playerTurn() {
            if (token == ' ' && player != ' ') {
                setToken(player);

                if (isWon(player)) {
                    lblStatus.setText(player + " won! The game is over");
                    player = ' ';
                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    player = ' ';

                } else {
                    computerTurn();
                }
            }
            return false;
        }
        private boolean computerTurn() {
            int see = 0;
            while (see == 0) {
                Random r = new Random();
                int i = r.nextInt(3);
                int j = r.nextInt(3);

                if (cell[i][j].getToken() == ' ') {
                    cell[i][j].setToken(computer);
                    see = 1;
                }
                if (isWon(computer)) {
                    lblStatus.setText(computer + " won! The game is over");
                    computer = ' ';

                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    computer = ' ';
                }
            }
            return false;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}


