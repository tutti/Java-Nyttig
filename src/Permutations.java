import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Permutations<T> implements Iterable<T[]> {
	
	private class PermutationIterator<E> implements Iterator<E[]> {
		
		private int size;
		private boolean done = false;
		private int[] counter;
		private ArrayList<E> list;
		
		private PermutationIterator(E[] list) {
			size = list.length;
			counter = new int[size];
			this.list = new ArrayList<E>(Arrays.asList(list));
		}
		
		private void advanceCounter() {
			int i = 0;
			boolean keep = true;
			while (keep) {
				++counter[i];
				if (counter[i] > i) {
					counter[i++] = 0;
					if (i >= size) keep = false;
				} else {
					keep = false;
				}
			}
			if (i == size) done = true;
		}
		
		private E[] nextPermutation() {
			ArrayList<E> copyList = new ArrayList<E>(list);
			Object[] permutation = new Object[size];
			for (int i = 0; i < size; ++i) {
				permutation[i] = copyList.remove(counter[size-i-1]);
			}
			return (E[])permutation;
		}

		@Override
		public boolean hasNext() {
			return !done;
		}

		@Override
		public E[] next() {
			E[] permutation = nextPermutation();
			advanceCounter();
			return permutation;
		}
		
	}
	
	private T[] list;
	
	public Permutations(T[] list) {
		this.list = list;
	}

	@Override
	public Iterator<T[]> iterator() {
		return new PermutationIterator<T>(list);
	}
	
	public static void main(String[] args) {
		Integer[] test = { 1, 2, 3, 4 };
		for (Object[] perm : new Permutations<Integer>(test)) {
			for (Object s : perm) {
				System.out.print(s);
			}
			System.out.println();
		}
	}
	
}
