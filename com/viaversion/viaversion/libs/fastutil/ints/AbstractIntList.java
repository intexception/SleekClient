package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.io.*;
import java.util.*;

public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack
{
    protected AbstractIntList() {
    }
    
    protected void ensureIndex(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
        }
    }
    
    protected void ensureRestrictedIndex(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }
    
    @Override
    public void add(final int index, final int k) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean add(final int k) {
        this.add(this.size(), k);
        return true;
    }
    
    @Override
    public int removeInt(final int i) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int set(final int index, final int k) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(int index, final Collection<? extends Integer> c) {
        if (c instanceof IntCollection) {
            return this.addAll(index, (IntCollection)c);
        }
        this.ensureIndex(index);
        final Iterator<? extends Integer> i = c.iterator();
        final boolean retVal = i.hasNext();
        while (i.hasNext()) {
            this.add(index++, (int)i.next());
        }
        return retVal;
    }
    
    @Override
    public boolean addAll(final Collection<? extends Integer> c) {
        return this.addAll(this.size(), c);
    }
    
    @Override
    public IntListIterator iterator() {
        return this.listIterator();
    }
    
    @Override
    public IntListIterator listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public IntListIterator listIterator(final int index) {
        this.ensureIndex(index);
        return new IntIterators.AbstractIndexBasedListIterator(0, index) {
            @Override
            protected final int get(final int i) {
                return AbstractIntList.this.getInt(i);
            }
            
            @Override
            protected final void add(final int i, final int k) {
                AbstractIntList.this.add(i, k);
            }
            
            @Override
            protected final void set(final int i, final int k) {
                AbstractIntList.this.set(i, k);
            }
            
            @Override
            protected final void remove(final int i) {
                AbstractIntList.this.removeInt(i);
            }
            
            @Override
            protected final int getMaxPos() {
                return AbstractIntList.this.size();
            }
        };
    }
    
    @Override
    public boolean contains(final int k) {
        return this.indexOf(k) >= 0;
    }
    
    @Override
    public int indexOf(final int k) {
        final IntListIterator i = this.listIterator();
        while (i.hasNext()) {
            final int e = i.nextInt();
            if (k == e) {
                return i.previousIndex();
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final int k) {
        final IntListIterator i = this.listIterator(this.size());
        while (i.hasPrevious()) {
            final int e = i.previousInt();
            if (k == e) {
                return i.nextIndex();
            }
        }
        return -1;
    }
    
    @Override
    public void size(final int size) {
        int i = this.size();
        if (size > i) {
            while (i++ < size) {
                this.add(0);
            }
        }
        else {
            while (i-- != size) {
                this.removeInt(i);
            }
        }
    }
    
    @Override
    public IntList subList(final int from, final int to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return (this instanceof RandomAccess) ? new IntRandomAccessSubList(this, from, to) : new IntSubList(this, from, to);
    }
    
    @Override
    public void forEach(final IntConsumer action) {
        if (this instanceof RandomAccess) {
            for (int i = 0, max = this.size(); i < max; ++i) {
                action.accept(this.getInt(i));
            }
        }
        else {
            super.forEach(action);
        }
    }
    
    @Override
    public void removeElements(final int from, final int to) {
        this.ensureIndex(to);
        final IntListIterator i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.nextInt();
            i.remove();
        }
    }
    
    @Override
    public void addElements(int index, final int[] a, int offset, int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (this instanceof RandomAccess) {
            while (length-- != 0) {
                this.add(index++, a[offset++]);
            }
        }
        else {
            final IntListIterator iter = this.listIterator(index);
            while (length-- != 0) {
                iter.add(a[offset++]);
            }
        }
    }
    
    @Override
    public void addElements(final int index, final int[] a) {
        this.addElements(index, a, 0, a.length);
    }
    
    @Override
    public void getElements(final int from, final int[] a, int offset, int length) {
        this.ensureIndex(from);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int current = from;
            while (length-- != 0) {
                a[offset++] = this.getInt(current++);
            }
        }
        else {
            final IntListIterator i = this.listIterator(from);
            while (length-- != 0) {
                a[offset++] = i.nextInt();
            }
        }
    }
    
    @Override
    public void setElements(final int index, final int[] a, final int offset, final int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            for (int i = 0; i < length; ++i) {
                this.set(i + index, a[i + offset]);
            }
        }
        else {
            final IntListIterator iter = this.listIterator(index);
            int j = 0;
            while (j < length) {
                iter.nextInt();
                iter.set(a[offset + j++]);
            }
        }
    }
    
    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }
    
    @Override
    public int hashCode() {
        final IntIterator i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
            final int k = i.nextInt();
            h = 31 * h + k;
        }
        return h;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        final List<?> l = (List<?>)o;
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        if (l instanceof IntList) {
            final IntListIterator i1 = this.listIterator();
            final IntListIterator i2 = ((IntList)l).listIterator();
            while (s-- != 0) {
                if (i1.nextInt() != i2.nextInt()) {
                    return false;
                }
            }
            return true;
        }
        final ListIterator<?> i3 = this.listIterator();
        final ListIterator<?> i4 = l.listIterator();
        while (s-- != 0) {
            if (!Objects.equals(i3.next(), i4.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int compareTo(final List<? extends Integer> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof IntList) {
            final IntListIterator i1 = this.listIterator();
            final IntListIterator i2 = ((IntList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                final int e1 = i1.nextInt();
                final int e2 = i2.nextInt();
                final int r;
                if ((r = Integer.compare(e1, e2)) != 0) {
                    return r;
                }
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        final ListIterator<? extends Integer> i3 = this.listIterator();
        final ListIterator<? extends Integer> i4 = l.listIterator();
        while (i3.hasNext() && i4.hasNext()) {
            final int r;
            if ((r = ((Comparable)i3.next()).compareTo(i4.next())) != 0) {
                return r;
            }
        }
        return i4.hasNext() ? -1 : (i3.hasNext() ? 1 : 0);
    }
    
    @Override
    public void push(final int o) {
        this.add(o);
    }
    
    @Override
    public int popInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeInt(this.size() - 1);
    }
    
    @Override
    public int topInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getInt(this.size() - 1);
    }
    
    @Override
    public int peekInt(final int i) {
        return this.getInt(this.size() - 1 - i);
    }
    
    @Override
    public boolean rem(final int k) {
        final int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.removeInt(index);
        return true;
    }
    
    @Override
    public int[] toIntArray() {
        final int size = this.size();
        final int[] ret = new int[size];
        this.getElements(0, ret, 0, size);
        return ret;
    }
    
    @Override
    public int[] toArray(int[] a) {
        final int size = this.size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        this.getElements(0, a, 0, size);
        return a;
    }
    
    @Override
    public boolean addAll(int index, final IntCollection c) {
        this.ensureIndex(index);
        final IntIterator i = c.iterator();
        final boolean retVal = i.hasNext();
        while (i.hasNext()) {
            this.add(index++, i.nextInt());
        }
        return retVal;
    }
    
    @Override
    public boolean addAll(final IntCollection c) {
        return this.addAll(this.size(), c);
    }
    
    @Override
    public final void replaceAll(final IntUnaryOperator operator) {
        this.replaceAll((java.util.function.IntUnaryOperator)operator);
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        final IntIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (n-- != 0) {
            if (first) {
                first = false;
            }
            else {
                s.append(", ");
            }
            final int k = i.nextInt();
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }
    
    static final class IndexBasedSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator
    {
        final IntList l;
        
        IndexBasedSpliterator(final IntList l, final int pos) {
            super(pos);
            this.l = l;
        }
        
        IndexBasedSpliterator(final IntList l, final int pos, final int maxPos) {
            super(pos, maxPos);
            this.l = l;
        }
        
        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }
        
        @Override
        protected final int get(final int i) {
            return this.l.getInt(i);
        }
        
        @Override
        protected final IndexBasedSpliterator makeForSplit(final int pos, final int maxPos) {
            return new IndexBasedSpliterator(this.l, pos, maxPos);
        }
    }
    
    public static class IntSubList extends AbstractIntList implements Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntList l;
        protected final int from;
        protected int to;
        
        public IntSubList(final IntList l, final int from, final int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }
        
        private boolean assertRange() {
            assert this.from <= this.l.size();
            assert this.to <= this.l.size();
            assert this.to >= this.from;
            return true;
        }
        
        @Override
        public boolean add(final int k) {
            this.l.add(this.to, k);
            ++this.to;
            assert this.assertRange();
            return true;
        }
        
        @Override
        public void add(final int index, final int k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
            assert this.assertRange();
        }
        
        @Override
        public boolean addAll(final int index, final Collection<? extends Integer> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }
        
        @Override
        public int getInt(final int index) {
            this.ensureRestrictedIndex(index);
            return this.l.getInt(this.from + index);
        }
        
        @Override
        public int removeInt(final int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeInt(this.from + index);
        }
        
        @Override
        public int set(final int index, final int k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }
        
        @Override
        public int size() {
            return this.to - this.from;
        }
        
        @Override
        public void getElements(final int from, final int[] a, final int offset, final int length) {
            this.ensureIndex(from);
            if (from + length > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }
        
        @Override
        public void removeElements(final int from, final int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
            assert this.assertRange();
        }
        
        @Override
        public void addElements(final int index, final int[] a, final int offset, final int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
            assert this.assertRange();
        }
        
        @Override
        public void setElements(final int index, final int[] a, final int offset, final int length) {
            this.ensureIndex(index);
            this.l.setElements(this.from + index, a, offset, length);
            assert this.assertRange();
        }
        
        @Override
        public IntListIterator listIterator(final int index) {
            this.ensureIndex(index);
            return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
        }
        
        @Override
        public IntSpliterator spliterator() {
            return (this.l instanceof RandomAccess) ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }
        
        @Override
        public IntList subList(final int from, final int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new IntSubList(this, from, to);
        }
        
        @Override
        public boolean rem(final int k) {
            final int index = this.indexOf(k);
            if (index == -1) {
                return false;
            }
            --this.to;
            this.l.removeInt(this.from + index);
            assert this.assertRange();
            return true;
        }
        
        @Override
        public boolean addAll(final int index, final IntCollection c) {
            this.ensureIndex(index);
            return super.addAll(index, c);
        }
        
        @Override
        public boolean addAll(final int index, final IntList l) {
            this.ensureIndex(index);
            return super.addAll(index, l);
        }
        
        private final class RandomAccessIter extends IntIterators.AbstractIndexBasedListIterator
        {
            RandomAccessIter(final int pos) {
                super(0, pos);
            }
            
            @Override
            protected final int get(final int i) {
                return IntSubList.this.l.getInt(IntSubList.this.from + i);
            }
            
            @Override
            protected final void add(final int i, final int k) {
                IntSubList.this.add(i, k);
            }
            
            @Override
            protected final void set(final int i, final int k) {
                IntSubList.this.set(i, k);
            }
            
            @Override
            protected final void remove(final int i) {
                IntSubList.this.removeInt(i);
            }
            
            @Override
            protected final int getMaxPos() {
                return IntSubList.this.to - IntSubList.this.from;
            }
            
            @Override
            public void add(final int k) {
                super.add(k);
                assert IntSubList.this.assertRange();
            }
            
            @Override
            public void remove() {
                super.remove();
                assert IntSubList.this.assertRange();
            }
        }
        
        private class ParentWrappingIter implements IntListIterator
        {
            private IntListIterator parent;
            
            ParentWrappingIter(final IntListIterator parent) {
                this.parent = parent;
            }
            
            @Override
            public int nextIndex() {
                return this.parent.nextIndex() - IntSubList.this.from;
            }
            
            @Override
            public int previousIndex() {
                return this.parent.previousIndex() - IntSubList.this.from;
            }
            
            @Override
            public boolean hasNext() {
                return this.parent.nextIndex() < IntSubList.this.to;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= IntSubList.this.from;
            }
            
            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.nextInt();
            }
            
            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previousInt();
            }
            
            @Override
            public void add(final int k) {
                this.parent.add(k);
            }
            
            @Override
            public void set(final int k) {
                this.parent.set(k);
            }
            
            @Override
            public void remove() {
                this.parent.remove();
            }
            
            @Override
            public int back(final int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int currentPos = this.parent.previousIndex();
                int parentNewPos = currentPos - n;
                if (parentNewPos < IntSubList.this.from - 1) {
                    parentNewPos = IntSubList.this.from - 1;
                }
                final int toSkip = parentNewPos - currentPos;
                return this.parent.back(toSkip);
            }
            
            @Override
            public int skip(final int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int currentPos = this.parent.nextIndex();
                int parentNewPos = currentPos + n;
                if (parentNewPos > IntSubList.this.to) {
                    parentNewPos = IntSubList.this.to;
                }
                final int toSkip = parentNewPos - currentPos;
                return this.parent.skip(toSkip);
            }
        }
    }
    
    public static class IntRandomAccessSubList extends IntSubList implements RandomAccess
    {
        private static final long serialVersionUID = -107070782945191929L;
        
        public IntRandomAccessSubList(final IntList l, final int from, final int to) {
            super(l, from, to);
        }
        
        @Override
        public IntList subList(final int from, final int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new IntRandomAccessSubList(this, from, to);
        }
    }
}
