package com.odigeo.interview.coding.battleshipservice.service;

import com.odigeo.interview.coding.battleshipservice.model.Cell;
import com.odigeo.interview.coding.battleshipservice.model.Coordinate;
import com.odigeo.interview.coding.battleshipservice.model.ship.Ship;
import com.odigeo.interview.coding.battleshipservice.util.GameConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Singleton
public class FieldService {

    @Inject
    private CoordinateService coordinateService;

    public boolean allShipsSunk(Cell[][] field) {
      for(int i = 0; i < field.length; i++) {
        for(int j = 0; j < field[i].length; j++) {
          if (!field[i][i].isWater()) {
            if (!field[i][i].isHit()) {
              return false;
            }
          }
        }
      }
      return true;
    }

    public boolean isShipSunk(Cell[][] field, Ship ship) {
      List<Coordinate> coordinates = ship.getCoordinates();
      for (Coordinate coordinate : coordinates) {
        if (!field[coordinate.getRow()][coordinate.getColumn()].isHit()) {
          return false;
        }
      }
        return true;
    }

    public Cell[][] buildField(List<Ship> shipsDeployment) {
        Cell[][] field = buildWater();
        deployShips(field, shipsDeployment);
        return field;
    }

    private Cell[][] buildWater() {
        Cell[][] field = new Cell[GameConfiguration.FIELD_HEIGHT][GameConfiguration.FIELD_WIDTH];
        for (int row = 0; row < GameConfiguration.FIELD_HEIGHT; row++) {
            for (int col = 0; col < GameConfiguration.FIELD_WIDTH; col++) {
                field[row][col] = new Cell();
            }
        }
        return field;
    }

    private void deployShips(Cell[][] field, List<Ship> ships) {
        ships.forEach(ship ->
            ship.getCoordinates().forEach(coordinate ->
                    field[coordinate.getRow()][coordinate.getColumn()] = new Cell(ship)
            )
        );
    }

}
