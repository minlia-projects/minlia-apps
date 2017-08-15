//package com.minlia.modules.rbac.config;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.ListIterator;
//
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
///**
// * Extension class between spring data commons and java.util.List that is
// * required to get MyBatis to use it as a return value.
// *
// * The list section is all undefined.
// *
// * @author sheenobu
// *
// * @param <T> The Type of the contained entity.
// */
//public class PageI<T> extends PageImpl<T> implements List<T> {
//
//	public PageI(List<T> content) {
//        super(content);
//	}
//
//	public PageI(List<T> content,Pageable pageable, int total) {
//		super(content,pageable,total);
//	}
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public boolean add(T e) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void add(int index, T element) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean addAll(Collection<? extends T> c) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean addAll(int index, Collection<? extends T> c) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public void clear() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean contains(Object o) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean containsAll(Collection<?> c) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public T get(int index) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public int indexOf(Object o) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean isEmpty() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public int lastIndexOf(Object o) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ListIterator<T> listIterator() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ListIterator<T> listIterator(int index) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean remove(Object o) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public T remove(int index) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean removeAll(Collection<?> c) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public boolean retainAll(Collection<?> c) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public T set(int index, T element) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public int size() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public List<T> subList(int fromIndex, int toIndex) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Object[] toArray() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public <T> T[] toArray(T[] a) {
//		throw new UnsupportedOperationException();
//	}
//
//
//}