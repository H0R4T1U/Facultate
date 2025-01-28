package ubb.scs.map.Utils.Paging;

public class Page<E> {
    final Iterable<E> elementsOnPage;

    public Page(Iterable<E> elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public Iterable<E> getElementsOnPage() {
        return elementsOnPage;
    }
}