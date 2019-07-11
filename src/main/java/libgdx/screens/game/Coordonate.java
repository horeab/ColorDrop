package libgdx.screens.game;

import java.util.Objects;

public class Coordonate {

	private int x;
	private int y;
	private CoordNeighbors neighbors;
	private int value;

	public Coordonate() {
	}

	public Coordonate(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public CoordNeighbors getNeighbors(int[][] matrix) {
		this.neighbors = new CoordNeighbors(this, matrix);
		return neighbors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coordonate that = (Coordonate) o;
		return x == that.x &&
				y == that.y;
	}

	@Override
	public int hashCode() {

		return Objects.hash(x, y);
	}
}