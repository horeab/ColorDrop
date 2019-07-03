package libgdx.screens.game;

public class CoordNeighbors {

	private Coordonate topNeighbor = null;
	private Coordonate bottomNeighbor = null;
	private Coordonate leftNeighbor = null;
	private Coordonate rightNeighbor = null;

	CoordNeighbors(Coordonate coord, int[][] matrix) {
		if (coord.getX() - 1 >= 0) {
			leftNeighbor = new Coordonate(coord.getX() - 1, coord.getY(), matrix[coord.getY()][coord.getX() - 1]);
		}
		if (coord.getX() + 1 <= matrix[0].length - 1) {
			rightNeighbor = new Coordonate(coord.getX() + 1, coord.getY(), matrix[coord.getY()][coord.getX() + 1]);
		}
		if (coord.getY() - 1 >= 0) {
			topNeighbor = new Coordonate(coord.getX(), coord.getY() - 1, matrix[coord.getY() - 1][coord.getX()]);
		}
		if (coord.getY() + 1 <= matrix.length - 1) {
			bottomNeighbor = new Coordonate(coord.getX(), coord.getY() + 1, matrix[coord.getY() + 1][coord.getX()]);
		}
	}

	public Coordonate getTopNeighbor() {
		return topNeighbor;
	}

	public Coordonate getBottomNeighbor() {
		return bottomNeighbor;
	}

	public Coordonate getLeftNeighbor() {
		return leftNeighbor;
	}

	public Coordonate getRightNeighbor() {
		return rightNeighbor;
	}
}