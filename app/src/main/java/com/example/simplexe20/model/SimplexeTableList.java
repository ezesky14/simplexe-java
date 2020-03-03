package com.example.simplexe20.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SimplexeTableList implements List<ColonneTableau> {


    public float[] toFloat (List<ColonneTableau> list) {
        float [] res = {};
        int a =0;
        for (ColonneTableau tb : list) {
            res[a] = tb.getValue();
            a++;
        }
        return res;
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<ColonneTableau> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean add(ColonneTableau colonneTableau) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends ColonneTableau> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends ColonneTableau> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ColonneTableau get(int index) {
        return null;
    }

    @Override
    public ColonneTableau set(int index, ColonneTableau element) {
        return null;
    }

    @Override
    public void add(int index, ColonneTableau element) {

    }

    @Override
    public ColonneTableau remove(int index) {
        return null;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<ColonneTableau> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<ColonneTableau> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<ColonneTableau> subList(int fromIndex, int toIndex) {
        return null;
    }
}
