import java.util.Iterator;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer> {
	
	private class RangeIterator implements Iterator<Integer> {
		
		private int lower;
		private int upper;
		private int step;
		private int current;
		
		public RangeIterator(int min, int max, int step) {
			lower = min;
			upper = max;
			this.step = step;
			current = lower-step;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return (current+step)<=upper;
		}

		@Override
		public Integer next() {
			// TODO Auto-generated method stub
			current += step;
			if (current > upper) throw new NoSuchElementException();
			return current;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private int lower = 0;
	private int upper = 1;
	private int step = 1;
	
	public Range(int max) {
		upper = max;
	}
	
	public Range(int min, int max) {
		lower = min;
		upper = max;
	}
	
	public Range(int min, int max, int step) {
		lower = min;
		upper = max;
		this.step = step;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new RangeIterator(lower, upper, step);
	}
	
	public static void main(String[] args) {
		System.out.println("First: Match 012345");
		for (int i : new Range(5)) {
			System.out.print(i);
		}
		System.out.println();
		
		System.out.println("Second: Match 2345");
		for (int i : new Range(2,5)) {
			System.out.print(i);
		}
		System.out.println();
		
		System.out.println("Third: Match 3579");
		for (int i : new Range(3, 9, 2)) {
			System.out.print(i);
		}
		System.out.println();
		
		System.out.println("Fourth: Match 357911");
		for (int i : new Range(3, 12, 2)) {
			System.out.print(i);
		}
	}
}
