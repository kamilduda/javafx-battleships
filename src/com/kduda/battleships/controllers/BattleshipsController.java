package com.kduda.battleships.controllers;

import com.kduda.battleships.models.board.Board;
import com.kduda.battleships.models.board.Cell;
import com.kduda.battleships.models.board.PlayerBoard;
import com.kduda.battleships.models.units.Unit;
import com.kduda.battleships.models.units.UnitFactory;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {
    public VBox enemyBoardArea;
    public VBox playerBoardArea;

    private Board enemyBoard;
    private Board playerBoard;
    private Unit currentUnit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUnit = UnitFactory.INSTANCE.getNextUnit();
        initializeBoards();
    }

    private void initializeBoards() {
        enemyBoard = new PlayerBoard(true, this::enemyBoardClick, this::enemyBoardEntered, this::enemyBoardExited);
        enemyBoardArea.getChildren().add(enemyBoard);

        playerBoard = new PlayerBoard(false, this::playerBoardClick, this::playerBoardEntered, this::playerBoardExited);
        playerBoardArea.getChildren().addAll(playerBoard);
    }

    private void enemyBoardClick(MouseEvent event) {
        //TODO: click handler
        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            return;
    }

    private void enemyBoardEntered(MouseEvent event) {
        //TODO: mouse entered handler
        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            return;
    }

    private void enemyBoardExited(MouseEvent event) {
        //TODO: mouse exited handler
        if (!BattleshipsConfig.INSTANCE.isGameRunning)
            return;
    }

    private void playerBoardEntered(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        Cell cell = (Cell) event.getSource();
        playerBoard.showPlacementHint(currentUnit, cell);
    }

    private void playerBoardExited(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        playerBoard.removePlacementHint();
    }

    private void playerBoardClick(MouseEvent event) {
        if (BattleshipsConfig.INSTANCE.isGameRunning) return;

        Cell cell = (Cell) event.getSource();

        if (event.getButton() == MouseButton.SECONDARY) {
            playerBoard.removePlacementHint();
            this.currentUnit.rotateUnit();
            playerBoard.showPlacementHint(currentUnit, cell);
            return;
        }

        boolean wasPlacementSuccessful = playerBoard.placeUnit(currentUnit);

        if (wasPlacementSuccessful)
            this.currentUnit = UnitFactory.INSTANCE.getNextUnit();

        if (currentUnit == null) startGame();
    }


    //HANDLERS enemy board click
//        enemyBoard = new Board(true, event -> {
//            if (!running)
//                return;
//
//            Cell cell = (Cell) event.getSource();
//            if (cell.wasShot)
//                return;
//
//            enemyTurn = !cell.shootCell();
//
//            if (enemyBoard.ships == 0) {
//                System.out.println("YOU WIN");
//                System.exit(0);
//            }
//
//            if (enemyTurn)
//                enemyMove();
//        });


    private void startGame() {
        //TODO: zapisanie do pliku
        //TODO: ui changes

        BattleshipsConfig.INSTANCE.isGameRunning = true;
    }

    public void rotateUnitClicked() {
        this.currentUnit.rotateUnit();
    }

    public void exitClicked() {
        Platform.exit();
        System.exit(0);
    }
}
