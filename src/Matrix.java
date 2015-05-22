/**
 * A matrix of integers, with some basic matrix operations (add matrix, subtract
 * matrix, multiply by matrix or scalar, transpose).
 * 
 * @author tutti
 */
public class Matrix {
	private double[][] matrix;
	private int width;
	private int height;

	private static boolean isValidMatrix(double[][] m) {
		int length = m[0].length;
		for (double[] row : m) {
			if (row.length != length) {
				return false;
			}
		}
		return true;
	}

	private boolean isSquare() {
		return width == height;
	}

	/**
	 * Returns an identity matrix of the given size
	 * 
	 * @param size
	 *            Width and height of the resulting identity matrix
	 * @return The identity matrix
	 */
	public static Matrix identity(int size) {
		Matrix m = new Matrix(size, size);
		for (int i = 0; i < size; ++i) {
			m.matrix[i][i] = 1;
		}
		return m;
	}

	/**
	 * Creates a new Matrix of the given height and width. Initialises all
	 * values to 0.
	 * 
	 * @param width
	 *            The width of the new Matrix
	 * @param height
	 *            The height of the new Matrix
	 */
	public Matrix(int width, int height) {
		matrix = new double[width][height];
		this.width = width;
		this.height = height;
	}

	/**
	 * Takes an array of rows (arrays of int) and converts it into a Matrix.
	 * 
	 * @param matrix
	 *            A two-dimensional array of values
	 */
	public Matrix(double[][] matrix) {
		if (!isValidMatrix(matrix)) {
			throw new RuntimeException(
					"Matrix(): Not all rows are equally long");
		}
		width = matrix[0].length;
		height = matrix.length;
		this.matrix = new double[width][height];

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				this.matrix[x][y] = matrix[y][x];
			}
		}
	}
	
	public Matrix clone() {
		Matrix r = new Matrix(width, height);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r.matrix[x][y] = matrix[y][x];
			}
		}
		return r;
	}

	/**
	 * Adds two matrices together and returns a new Matrix. Neither Matrix is
	 * modified.
	 * 
	 * @param m
	 *            Another Matrix to add to this one.
	 * @return A new matrix containing the added values of the other two.
	 * @throws RuntimeException
	 *             If the matrices are not of equal size
	 */
	public Matrix add(Matrix m) throws RuntimeException {
		if (width != m.width || height != m.height) {
			throw new RuntimeException("Matrix.add(): Matrices not equal size");
		}
		Matrix r = new Matrix(width, height);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r.matrix[x][y] = (m.matrix[x][y] + matrix[x][y]);
			}
		}
		return r;
	}

	/**
	 * Adds a two-dimensional integer array to this Matrix as if it were another
	 * Matrix object.
	 * 
	 * @param m
	 *            A two-dimensional integer array
	 * @return The result.
	 * @throws RuntimeException
	 */
	public Matrix add(double[][] m) throws RuntimeException {
		return this.add(new Matrix(m));
	}

	/**
	 * Subtracts another Matrix from this one. A new Matrix is returned, neither
	 * existing ones are modified.
	 * 
	 * @param m
	 *            Another Matrix to subtract from this one.
	 * @return A matrix containing the subtracted values.
	 * @throws RuntimeException
	 *             If the matrices are not of equal size
	 */
	public Matrix sub(Matrix m) throws RuntimeException {
		if (width != m.width || height != m.height) {
			throw new RuntimeException("Matrix.sub(): Matrices not equal size");
		}
		Matrix r = new Matrix(width, height);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r.matrix[x][y] = (matrix[x][y] - m.matrix[x][y]);
			}
		}
		return r;
	}

	/**
	 * Subtracts the values of a two-dimensional array from this Matrix. A new
	 * Matrix is returned, this one is not modified.
	 * 
	 * @param m
	 *            A two-dimensional integer array
	 * @return A new Matrix equal to this one minus the array
	 * @throws RuntimeException
	 *             If the matrices are not of equal size
	 */
	public Matrix sub(double[][] m) throws RuntimeException {
		return this.sub(new Matrix(m));
	}

	/**
	 * Multiplies two matrices. A new Matrix is returned, neither of the
	 * existing ones are modified.
	 * 
	 * @param m
	 *            A matrix to multiply this one by.
	 * @return A new Matrix which is the product of the other two.
	 * @throws RuntimeException
	 *             If the Matrix dimensions are not compatible.
	 */
	public Matrix mul(Matrix m) throws RuntimeException {
		if (m.height != width) {
			throw new RuntimeException(
					"Matrix.mul(): Matrix dimensions not compatible");
		}
		Matrix r = new Matrix(m.width, height);
		for (int x = 0; x < m.width; ++x) {
			for (int y = 0; y < height; ++y) {
				double[] col = m.col(x);
				double[] row = this.row(y);
				int sum = 0;
				for (int i = 0; i < col.length; ++i) {
					sum += col[i] * row[i];
				}
				r.matrix[x][y] = sum;
			}
		}
		return r;
	}

	/**
	 * Multiplies this Matrix by a two-dimensional integer array as if it were a
	 * Matrix object.
	 * 
	 * @param m
	 *            A two-dimensional array of integers
	 * @return The product
	 * @throws RuntimeException
	 *             If the matrices are not of equal size
	 */
	public Matrix mul(double[][] m) throws RuntimeException {
		return this.mul(new Matrix(m));
	}

	/**
	 * Multiplies the Matrix by a scalar value.
	 * 
	 * @param s
	 *            A scalar value.
	 * @return A new matrix equal to this one multiplied by the scalar.
	 */
	public Matrix mul(double s) {
		Matrix r = new Matrix(width, height);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r.matrix[x][y] = (matrix[x][y] * s);
			}
		}
		return r;
	}

	/**
	 * Transposes the Matrix.
	 * 
	 * @return A transposed Matrix.
	 */
	public Matrix transpose() {
		Matrix r = new Matrix(height, width);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r.matrix[y][x] = matrix[x][y];
			}
		}
		return r;
	}

	private double determinant(double[][] m) {
		// Base case
		if (m.length == 2) {
			return (m[0][0] * m[1][1]) - (m[0][1] * m[1][0]);
		}

		// Make the smaller array that will be used to calculate the smaller
		// determinants.
		double[][] copy = new double[m.length-1][m.length-1];
		for (int i = 1; i < m.length; ++i) {
			for (int j = 1; j < m.length; ++j) {
				copy[i - 1][j - 1] = m[i][j];
			}
		}

		// Multiply the values in the first row with the determinants of the
		// smaller arrays.
		double sum = 0;
		boolean add = true;
		for (int i = 0; i < m.length - 1; ++i) {
			// Add the current number of the first row multiplied by its
			// corresponding sub-matrix to the sum.
			if (add) {
				sum += m[0][i] * determinant(copy);
				add = false;
			} else {
				sum -= m[0][i] * determinant(copy);
				add = true;
			}
			
			// Move the column of the sub-matrix one over
			for (int j = 1; j < m.length; ++j) {
				copy[j-1][i] = m[j][i];
			}
		}

		if (add) {
			sum += m[0][m.length-1] * determinant(copy);
		} else {
			sum -= m[0][m.length-1] * determinant(copy);
		}

		return sum;
	}

	public double determinant() {
		if (!isSquare()) {
			throw new RuntimeException("Matrix is non-square.");
		}

		return determinant(toArray());
	}
	
	public void swapRows(int row1, int row2) {
		for (int i=0; i<width; ++i) {
			double temp = matrix[i][row1];
			matrix[i][row1] = matrix[i][row2];
			matrix[i][row2] = temp;
		}
	}
	
	public void multiplyRow(int row, double scalar) {
		for (int i=0; i<width; ++i) {
			matrix[i][row] *= scalar;
		}
	}
	
	public void addRow(int row, int rowToAdd) {
		addRow(row, rowToAdd, 1);
	}
	
	public void addRow(int row, int rowToAdd, double scalar) {
		for (int i=0; i<width; ++i) {
			matrix[i][row] += matrix[i][rowToAdd] * scalar;
		}
	}
	
	public Matrix rref() {
		Matrix reduced = clone();
		
		// TODO
		
		return null;
	}

	/**
	 * Returns an array with the values of this Matrix. This is a copied array.
	 * 
	 * @return A two-dimensional array.
	 */
	public double[][] toArray() {
		double[][] r = new double[height][width];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				r[y][x] = matrix[x][y];
			}
		}
		return r;
	}

	/**
	 * Returns a given row from the Matrix.
	 * 
	 * @param rownum
	 *            The row number
	 * @return An array being equal to the row.
	 */
	public double[] row(int rownum) {
		double[] r = new double[width];
		for (int i = 0; i < width; ++i) {
			r[i] = matrix[i][rownum];
		}
		return r;
	}

	/**
	 * Returns a given column from the Matrix.
	 * 
	 * @param colnum
	 *            The column number
	 * @return An array being equal to the column.
	 */
	public double[] col(int colnum) {
		double[] r = new double[height];
		for (int i = 0; i < height; ++i) {
			r[i] = matrix[colnum][i];
		}
		return r;
	}

	/**
	 * Gets a given value from the Matrix.
	 * 
	 * @param row
	 *            The row
	 * @param col
	 *            The column
	 * @return The value
	 */
	public double get(int row, int col) {
		return matrix[col][row];
	}

	/**
	 * Sets a given value in the Matrix. The Matrix is returned, to allow
	 * chaining.
	 * 
	 * @param row
	 *            The row
	 * @param col
	 *            The column
	 * @param val
	 *            The value
	 * @return The same Matrix.
	 */
	public Matrix set(int row, int col, double val) {
		matrix[col][row] = val;
		return this;
	}

	/**
	 * Converts the Matrix into a string.
	 * 
	 * @return A string with the values in the Matrix.
	 */
	public String toString() {
		StringBuilder stb = new StringBuilder();
		for (int y = 0; y < height; ++y) {
			if (y > 0)
				stb.append('\n');
			for (int x = 0; x < width; ++x) {
				if (x > 0)
					stb.append(' ');
				stb.append(matrix[x][y]);
			}
		}
		return stb.toString();
	}

	/**
	 * Checks if two matrices are equal Two matrices are equal if they have the
	 * same size, and the same values in all positions.
	 * 
	 * @param m
	 * @return
	 */
	public boolean equals(Matrix m) {
		if (width != m.width || height != m.height)
			return false;
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (matrix[x][y] != m.matrix[x][y])
					return false;
			}
		}
		return true;
	}
}
