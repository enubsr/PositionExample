/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package positionexample;



/**
 *
 * @author Enubs
 * @param <E>
 */
public class LinkedPositionalList<E> implements PositionalList<E> {
    
    private class Node<E> implements Position<E>{

        private E e;
        private Node<E> prev, next;
        
        public Node(Node<E> prev, E e, Node<E> next){
            this.e = e;
            this.prev = prev;
            this.next = next;
        }
        
        @Override
        public E getElement() throws IllegalStateException {
            return e;
        }

        /**
         * @return the prev
         */
        public Node<E> getPrev() {
            return prev;
        }

        /**
         * @param prev the prev to set
         */
        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        /**
         * @return the next
         */
        public Node<E> getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }

        /**
         * @param e the e to set
         */
        public void setElement(E e) {
            this.e = e;
        }
        
    }
    
    private Node<E> header, trailer;
    private int size;
    
    public LinkedPositionalList(){
        header = new Node(null, null, null);
        trailer = new Node(header, null, null);
        header.setNext(trailer);
        size = 0;
    }
    
    private Node<E> validate(Position<E> p) throws IllegalArgumentException{
        if(!(p instanceof Node)){
            throw new IllegalArgumentException("Invalid node.");
        }
        Node<E> node = (Node<E>) p;
        
        if(node.getNext() == null){
            throw new IllegalArgumentException("Node is not in the list.");
        }
        
        return node;
    }
    
    private Position<E> position(Node<E> node){
        if(node == header || node == trailer){
            return null;
        }
        
        Position<E> p = (Position<E>) node;
        
        return p;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public Position<E> first() {
        return position(header.getNext());
    }

    @Override
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }
    
    private Position<E> addBetween(Node<E> prev, E e, Node<E> next){
        Node<E> newNode = new Node<>(prev, e, next);
        prev.setNext(newNode);
        next.setPrev(newNode);
        size++;
        return position(newNode);
    }

    @Override
    public Position<E> addFirst(E e) {
        return addBetween(header, e, header.getNext());
    }

    @Override
    public Position<E> addLast(E e) {
        return addBetween(trailer.getPrev(), e, trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(node.getPrev(), e, node);
    }

    @Override
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(node, e, node.getNext());
    }

    @Override
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        // prev p next
        // A    p B
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        size--;
        E temp = p.getElement();
        node.setNext(null);
        node.setPrev(null);
        node.setElement(null);
        
        return temp;
    }
    
    public void showPositionList(){
//        System.out.println(first().getElement());
//        System.out.println(after(first()).getElement());
//        System.out.println(after(after(first())).getElement());
        Position<E> temp = first();
        for(int i = 0; i < size; i++){
            System.out.print("[" + temp.getElement() + "]");
            temp = after(temp);
        }
    }
}
