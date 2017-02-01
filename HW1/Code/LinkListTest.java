package test;

import java.io.*;
import java.util.*;
import org.junit.Test;

public class LinkListTest {

	@Test
    public void test(){
		LinkList list = new LinkList();

		list.add(25);
		list.add(15);
		list.add(45);
		list.add(35);
		list.add(55);
		list.add(65);
		list.add(75);
		list.add(85);
		list.add(10);
		System.out.println("Before deleting 10 : ");
		list.traverse();
		System.out.println();
		System.out.println("Does 10 exist in the list ? " +list.find(42));
		System.out.println("Does 15 exist in the list ? " +list.find(15));
		
		list.delete(10);
		
		System.out.println("Before deleting 25 : ");
		list.traverse();
		list.delete(25);
		System.out.println();
		
		System.out.println("Before deleting 55 : ");
		list.traverse();
		list.delete(55);
		System.out.println();
		System.out.println("Final link list : ");
		list.traverse();
		System.out.println();
		System.out.println("The size of the linked list is : - "+list.size);

		

	}
}
