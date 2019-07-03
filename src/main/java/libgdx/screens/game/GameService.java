package libgdx.screens.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameService {

	public static Set<Coordonate> getSameColorCoordNeighbors(int[][] matrix,
															 Coordonate coord, Set<Coordonate> searchedCoords,
															 Set<Coordonate> foundNeighbors) {
		Set<Coordonate> validNeighbors = new HashSet<>();
		validNeighbors.addAll(getTopNeighbors(matrix, coord, validNeighbors));
		validNeighbors
				.addAll(getBottomNeighbors(matrix, coord, validNeighbors));
		validNeighbors.addAll(getRightNeighbors(matrix, coord, validNeighbors));
		validNeighbors.addAll(getLeftNeighbors(matrix, coord, validNeighbors));
		for (Coordonate validCoord : validNeighbors) {
			if (!verifyIfCoordSearched(validCoord, searchedCoords)) {
				searchedCoords.add(validCoord);
				foundNeighbors
						.addAll(filterList(validNeighbors, foundNeighbors));
				getSameColorCoordNeighbors(matrix, validCoord, searchedCoords,
						foundNeighbors);
			}
		}
		if (foundNeighbors.size() != searchedCoords.size()) {
			Coordonate unsearchedCoord = getFirstUnsearchedCoord(
					foundNeighbors, searchedCoords);
			searchedCoords.add(unsearchedCoord);
			return getSameColorCoordNeighbors(matrix, unsearchedCoord,
					searchedCoords, foundNeighbors);
		} else {
			if (foundNeighbors.size() == 0) {
				foundNeighbors.add(coord);
			}
			return foundNeighbors;
		}
	}

	private static Coordonate getFirstUnsearchedCoord(Set<Coordonate> list1,
			Set<Coordonate> list2) {
		Coordonate coordToReturn = new Coordonate();
		boolean coordIsFound = false;
		for (Coordonate coord1 : list1) {
			for (Coordonate coord2 : list2) {
				if (coord1.getX() == coord2.getX()
						&& coord1.getY() == coord2.getY()) {
					coordIsFound = true;
					break;
				}
			}
			if (!coordIsFound) {
				coordToReturn = coord1;
				break;
			}
			coordIsFound = false;
		}
		return coordToReturn;
	}

	private static Set<Coordonate> filterList(Set<Coordonate> list1,
			Set<Coordonate> list2) {
		Set<Coordonate> listToReturn = new HashSet<Coordonate>();
		for (Coordonate coord1 : list1) {
			if (!verifyIfCoordSearched(coord1, list2)) {
				listToReturn.add(coord1);
			}
		}
		list2.addAll(listToReturn);
		return list2;
	}

	private static boolean verifyIfCoordSearched(Coordonate coord,
			Set<Coordonate> searchedCoords) {
		for (Coordonate searchedCoord : searchedCoords) {
			if (searchedCoord.getX() == coord.getX()
					&& searchedCoord.getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}

	private static Set<Coordonate> getTopNeighbors(int[][] matrix,
			Coordonate coord, Set<Coordonate> neighbors) {
		CoordNeighbors neighb = coord.getNeighbors(matrix);
		if (neighb.getTopNeighbor() != null
				&& coord.getValue() == neighb.getTopNeighbor().getValue()) {
			neighbors.add(neighb.getTopNeighbor());
			return getTopNeighbors(matrix, neighb.getTopNeighbor(), neighbors);
		} else {
			return neighbors;
		}
	}

	private static Set<Coordonate> getBottomNeighbors(int[][] matrix,
			Coordonate coord, Set<Coordonate> neighbors) {
		CoordNeighbors neighb = coord.getNeighbors(matrix);
		if (neighb.getBottomNeighbor() != null
				&& coord.getValue() == neighb.getBottomNeighbor().getValue()) {
			neighbors.add(neighb.getBottomNeighbor());
			return getBottomNeighbors(matrix, neighb.getBottomNeighbor(),
					neighbors);
		} else {
			return neighbors;
		}
	}

	private static Set<Coordonate> getRightNeighbors(int[][] matrix,
			Coordonate coord, Set<Coordonate> neighbors) {
		CoordNeighbors neighb = coord.getNeighbors(matrix);
		if (neighb.getRightNeighbor() != null
				&& coord.getValue() == neighb.getRightNeighbor().getValue()) {
			neighbors.add(neighb.getRightNeighbor());
			return getRightNeighbors(matrix, neighb.getRightNeighbor(),
					neighbors);
		} else {
			return neighbors;
		}
	}

	private static Set<Coordonate> getLeftNeighbors(int[][] matrix,
			Coordonate coord, Set<Coordonate> neighbors) {
		CoordNeighbors neighb = coord.getNeighbors(matrix);
		if (neighb.getLeftNeighbor() != null
				&& coord.getValue() == neighb.getLeftNeighbor().getValue()) {
			neighbors.add(neighb.getLeftNeighbor());
			return getLeftNeighbors(matrix, neighb.getLeftNeighbor(), neighbors);
		} else {
			return neighbors;
		}
	}

	public static int[][] processMatrixClear(Set<Coordonate> coordsToBeDeleted,
			int[][] originalMatrix, GameTypeEnum gameType, int nrColors) {
		for (Coordonate coord : coordsToBeDeleted) {
			originalMatrix[coord.getY()][coord.getX()] = Util.HIDE_BUTTON_MATRIX_CODE;
		}
		return orderMatrix(originalMatrix, gameType, nrColors);
	}

	private static int[][] orderMatrix(int[][] matrix, GameTypeEnum gameType,
			int nrColors) {
		List<List<Integer>> matrixColumns = new ArrayList<>();
		for (int j = 0; j < matrix[0].length; j++) {
			List<Integer> column = new ArrayList<Integer>();
			for (int k = matrix.length - 1; k >= 0; k--) {
				column.add(matrix[k][j]);
			}
			if (gameType.equals(GameTypeEnum.MULTI_LEVEL)) {
				matrixColumns.add(sortColumn(column));
			} else if (gameType.equals(GameTypeEnum.UNLIMITED)) {
				matrixColumns.add(fillEmptyCells(sortColumn(column), nrColors));
			}
		}
		if (gameType.equals(GameTypeEnum.MULTI_LEVEL)) {
			matrixColumns = pushLeftColumns(matrixColumns);
		}
		int i = matrix.length - 1, j = 0;
		for (List<Integer> column : matrixColumns) {
			for (Integer value : column) {
				matrix[i][j] = value;
				i--;
			}
			i = matrix.length - 1;
			j++;
		}
		return matrix;
	}

	private static List<List<Integer>> pushLeftColumns(
			List<List<Integer>> matrixColumns) {
		List<List<Integer>> pushedColumns = new ArrayList<List<Integer>>();
		List<Integer> emptyColumn = new ArrayList<Integer>();
		int emptyColumns = 0;
		for (List<Integer> column : matrixColumns) {
			if (!column.get(0).equals(Util.HIDE_BUTTON_MATRIX_CODE)) {
				pushedColumns.add(column);
			} else {
				emptyColumns++;
				emptyColumn = column;
			}
		}
		for (int i = 0; i < emptyColumns; i++) {
			pushedColumns.add(emptyColumn);
		}
		return pushedColumns;
	}

	private static List<Integer> sortColumn(List<Integer> column) {
		List<Integer> sortedColumn = new ArrayList<Integer>();
		int buttonsToHide = 0;
		if (column.contains(Util.HIDE_BUTTON_MATRIX_CODE)) {
			for (Integer val : column) {
				if (!val.equals(Util.HIDE_BUTTON_MATRIX_CODE)) {
					sortedColumn.add(val);
				} else {
					buttonsToHide++;
				}
			}
			for (int i = 0; i < buttonsToHide; i++) {
				sortedColumn.add(Util.HIDE_BUTTON_MATRIX_CODE);
			}
		} else {
			return column;
		}
		return sortedColumn;
	}

	public static boolean thereArePossibilities(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == Util.BOMB_BUTTON_MATRIX_CODE) {
					return true;
				}
				if (thereAreSameColorNeighbors(new Coordonate(j, i,
						matrix[i][j]), matrix)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean thereAreSameColorNeighbors(Coordonate coordonate,
			int[][] matrix) {
		CoordNeighbors neighbors = coordonate.getNeighbors(matrix);
		if (compareWithNullCheck(neighbors.getBottomNeighbor(),
				coordonate.getValue())
				|| compareWithNullCheck(neighbors.getTopNeighbor(),
						coordonate.getValue())
				|| compareWithNullCheck(neighbors.getLeftNeighbor(),
						coordonate.getValue())
				|| compareWithNullCheck(neighbors.getRightNeighbor(),
						coordonate.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareWithNullCheck(Coordonate neighbor, int value) {
		if (neighbor != null) {
			if (neighbor.getValue() == value
					&& value != Util.HIDE_BUTTON_MATRIX_CODE) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static List<Integer> fillEmptyCells(List<Integer> column,
			int nrColors) {
		List<Integer> filledColumn = new ArrayList<Integer>();
		for (Integer val : column) {
			if (val.equals(Util.HIDE_BUTTON_MATRIX_CODE)) {
				filledColumn.add(Util.randomNumber(nrColors));
			} else {
				filledColumn.add(val);
			}
		}
		return filledColumn;
	}

	public static Set<Coordonate> getBombNeighbors(Coordonate bomb,
			int[][] matrix) {
		Set<Coordonate> bombNeighbors = new HashSet<Coordonate>();
		CoordNeighbors cardinalNeigb = bomb.getNeighbors(matrix);
		if (cardinalNeigb.getBottomNeighbor() != null
				&& cardinalNeigb.getBottomNeighbor().getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
			bombNeighbors.add(cardinalNeigb.getBottomNeighbor());
		}
		if (cardinalNeigb.getTopNeighbor() != null
				&& cardinalNeigb.getTopNeighbor().getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
			bombNeighbors.add(cardinalNeigb.getTopNeighbor());
		}
		if (cardinalNeigb.getLeftNeighbor() != null
				&& cardinalNeigb.getLeftNeighbor().getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
			bombNeighbors.add(cardinalNeigb.getLeftNeighbor());
		}
		if (cardinalNeigb.getRightNeighbor() != null
				&& cardinalNeigb.getRightNeighbor().getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
			bombNeighbors.add(cardinalNeigb.getRightNeighbor());
		}
		// bottomLeft
		if (bomb.getY() + 1 <= matrix.length - 1 && bomb.getX() - 1 >= 0) {
			Coordonate coor = new Coordonate(bomb.getX() - 1, bomb.getY() + 1,
					matrix[bomb.getY() + 1][bomb.getX() - 1]);
			if (coor.getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
				bombNeighbors.add(coor);
			}
		}
		// bottomRight
		if (bomb.getY() + 1 <= matrix.length - 1
				&& bomb.getX() + 1 <= matrix[0].length - 1) {
			Coordonate coor = new Coordonate(bomb.getX() + 1, bomb.getY() + 1,
					matrix[bomb.getY() + 1][bomb.getX() + 1]);
			if (coor.getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
				bombNeighbors.add(coor);
			}
		}
		// topLeft
		if (bomb.getY() - 1 >= 0 && bomb.getX() - 1 >= 0) {
			Coordonate coor = new Coordonate(bomb.getX() - 1, bomb.getY() - 1,
					matrix[bomb.getY() - 1][bomb.getX() - 1]);
			if (coor.getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
				bombNeighbors.add(coor);
			}
		}
		// topRight
		if (bomb.getY() - 1 >= 0 && bomb.getX() + 1 <= matrix[0].length - 1) {
			Coordonate coor = new Coordonate(bomb.getX() + 1, bomb.getY() - 1,
					matrix[bomb.getY() - 1][bomb.getX() + 1]);
			if (coor.getValue() != Util.HIDE_BUTTON_MATRIX_CODE) {
				bombNeighbors.add(coor);
			}
		}
		bombNeighbors.add(bomb);
		return bombNeighbors;
	}
}