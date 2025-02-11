import java.util.Random;

public class HandsMaxHeap {
    private Hands[] myHeap;  // array
    private int size;      // heap size (number of items stored in the heap)
    private int capacity;  // heap capacity (the maximum number of items the heap could store)

    // Constructor 1: creates an empty heap with a given capacity
    public HandsMaxHeap(int bufSize)
    {
        // set capacity = bufSize, and size = 0
        // instantiate myHeap as a Hands array with capacity + 1 slots (think about why capacity + 1)
        // finally, set the first element in the Hands array to a dummy Hand (using the default Hands() constructor) 
        capacity = bufSize;
        size = 0;
        myHeap = new Hands[capacity + 1];
        myHeap[0] = new Hands();
    }

    // Constructor 2: constructs a heap out of the array someHands 
    // the first element in the array is treated as a dummy, the remaining elements are organized as a heap using 
    // the private method bulidMaxHeap, which you have to implement
    
    public HandsMaxHeap(Hands[] someHands)
    {
        myHeap = someHands;
        capacity = someHands.length - 1;
        size = capacity;
        buildMaxHeap();
    }

    // [Problem 0] Implement buildMaxHeap 
    
    // When this method is invoked by the constructor, the array myHeap is not organized as a heap yet;
    // the method should organize the array as a heap (disregarding the element at index 0) using the O(n)-time algorithm 
  
    // [Problem 1] Implement Max Heap for 5-Card Hands

    // [Problem 1-1] Implement Private Utility Methods
    // 1. A private method calculating the parent index
    private int parent(int index)
    {
        return index/2;
    }
    
    // 2. A private method calculating the left-child index
    private int leftChild(int index)
    {
        return 2*index;
    }

    // 3. A private method calculating the right-child index
    private int rightChild(int index)
    {
        return 2*index + 1;
    }

    // [Problem 1-2] Implement the Downward Heap Reorganization Private Method from the provided index 
    private void downHeapify(int index)
    {
        //percolateDown the Heap Node at index; stop when it fits 
        int l = leftChild(index);
        int r = rightChild(index);
        Hands key = myHeap[index];
    
        while (l <= size) { // compare left and right children if they exist
            if (r <= size && myHeap[r].isMyHandLarger(myHeap[l])) { 
                l++;
            }
    
            if (myHeap[l].isMyHandLarger(key)) { // swap with the largest of the children
                myHeap[index] = myHeap[l]; 
                index = l; 
                l = leftChild(index); 
                r = rightChild(index); 
            } 
            else break; 
        } 
    
        myHeap[index] = key; 
    } 

    // [Problem 1-3] Implement Upward Heap Reorganization Private Method from the provided index 
    private void heapifyUp(int index)
    {   
        Hands key = myHeap[index];

        while (index > 1) { 
            int parentIndex = parent(index); // compare with parent if it exists

            if (key.isMyHandLarger(myHeap[parentIndex])) {
                myHeap[index] = myHeap[parentIndex]; // if larger, swap
                index = parentIndex; 
            } 
            else break; 
        }

        myHeap[index] = key;              
    }


    // [Problem 1-4] Complete the Max Heap ADT Public Methods
    

    // Insert Method
    public void insert(Hands thisHand)
    {
        // insert thisHand into the heap; if there is no room for insertion allocate a bigger array (the capacity of the new heap should be twice larger) and copy the data over 
        if(size >= capacity){
            capacity = 2*capacity;
            Hands[] x = new Hands[capacity + 1]; // temp hands array with new capacity to store the new value in
            x[0] = new Hands();
            for(int i = 1; i <= size; i++){
                x[i] = myHeap[i];
            }
            myHeap = x; 
        }
        size++;
        myHeap[size] = thisHand;
        heapifyUp(size); // percolate new insertion up 
    }

    public Hands removeMax() throws RuntimeException
    {
        //remove the largest Hand from the heap; if the heap is empty throw a RuntimeException
        if(isEmpty()) throw new RuntimeException();
        Hands max = myHeap[1];
        myHeap[1] = myHeap[size];
        size--;
        downHeapify(1); // percolate the element at the end down to ts appropriate place
        return max;
    }

    public int getSize()
    {
        // return the size of the heap
        return size;
    }

    public boolean isEmpty()
    {
        //return true if heap is empty; return false otherwise
        if(size == 0) return true;
        else return false;

    }

    public void printHeap()
    {
        // For Debugging Purpose - Print all the heap elements (i.e. Hands) by traversing the heap in level order        
        //  For valid hands, print the hand using the respective method in Hands class
        //  For invalid hands, just print "--INV--"
        //  Use the required method in Hands class to determine whether a Hand is valid.
        for(int i = 0; i <= size; i++){
            if(!myHeap[i].isAValidHand()) System.out.print("--INV--");
            else myHeap[i].printMyHand();
        }
    }

    // [Problem 1-5] Implement the efficient algorithm to build a max heap
    // Uses the fast algorithm to organize the array elements (except for the first one) as a max heap
    private void buildMaxHeap()
    {
        Hands max = myHeap[0];
        for(int i = 1; i < size; i++){
            if(myHeap[i].isMyHandLarger(max)){ // inserting value at index 0 into its appropriate place
                max = myHeap[i];
                myHeap[i] = myHeap[0];
                myHeap[0] = max;
            }
        }

        for(int i = size/2; i >= 1; i--) downHeapify(i); // start at last internal node and work backwards
    }

    // Sorts the array IN PLACE using the heap sort algorithm
    // Sorting IN PLACE means O(1) extra memory

    public static void heapSort(Hands myHands[])
    {
        HandsMaxHeap heap = new HandsMaxHeap(myHands);
        for (int i = heap.size; i > 1; i--) {
            myHands[i] = heap.removeMax();  // remove the max value and move it to the end of the array
        }
        int l = 1;
        int r = myHands.length - 1;
        Hands temp;
        while (l < r){ // reverse order to descending
            temp = myHands[l];
            myHands[l] = myHands[r];
            myHands[r] = temp;
            l++;
            r--;
        }

    }


   // This is the Test Bench!!

   private static boolean totalPassed = true;
   private static int totalTestCount = 0;
   private static int totalPassCount = 0;

   public static void main(String args[])
   {
       testParent();
       testLeftChild();
       testRightChild();
       testHandsMaxHeap1();        
       testHandsMaxHeap2();
       testInsert1();
       testInsert2();
       
       testGetSize1();
       testGetSize2();
       testRemoveMax1();
       testRemoveMax2();

       CustomTestInsert1();
       CustomTestInsert2();
       CustomTestRemoveMax1();
       CustomTestRemoveMax2();

       testHeapSort1();
       testHeapSort2();

       System.out.println("================================");
       System.out.printf("Test Score: %d / %d\n", 
                         totalPassCount, 
                         totalTestCount);
       if(totalPassed)  
       {
           System.out.println("All Tests Passed.");
           System.exit(0);
       }
       else
       {   
           System.out.println("Tests Failed.");
           System.exit(-1);
       }
   }

   private static void testParent(){
       
       // Setup
       System.out.println("============testParent=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(10);
       int result, expected;
      
       // Action
       for(int i = 1; i < 50; i++)
       {
           result = myMaxHeap.parent(i);
           expected = i / 2;
           passed &= assertEquals(expected, result);
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
       
   }
   
   private static void testLeftChild(){
       // Setup
       System.out.println("============testLeftChild=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(10);
       int result, expected;
       
       // Action
       for(int i = 1; i < 50; i++)
       {
           result = myMaxHeap.leftChild(i);
           expected = 2 * i;
           passed &= assertEquals(expected, result);
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   
   private static void testRightChild(){
       // Setup
       System.out.println("============testRightChild=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(10);
       int result, expected;
       
       // Action
       for(int i = 1; i < 50; i++)
       {
           result = myMaxHeap.rightChild(i);
           expected = (2 * i) + 1;
           passed &= assertEquals(expected, result);
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testHandsMaxHeap1(){
       // Setup
       System.out.println("============testHandsMaxHeap1=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);
       int result, expected;

       // Action
       result = myMaxHeap.capacity;
       expected = 20;
       passed &= assertEquals(expected, result);
       
       result = myMaxHeap.size;
       expected = 0;
       passed &= assertEquals(expected, result);

       Hands expectedHand = new Hands();
       Hands actualHand = myMaxHeap.myHeap[0];
       passed &= assertEquals(expectedHand, actualHand);
       
       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testHandsMaxHeap2(){
       // Setup
       System.out.println("============testHandsMaxHeap2=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(50);
       int result, expected;

       // Action
       result = myMaxHeap.capacity;
       expected = 50;
       passed &= assertEquals(expected, result);
       
       result = myMaxHeap.size;
       expected = 0;
       passed &= assertEquals(expected, result);

       Hands expectedHand = new Hands();
       Hands actualHand = myMaxHeap.myHeap[0];
       passed &= assertEquals(expectedHand, actualHand);
       
       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testInsert1(){
       // Setup
       System.out.println("============testInsert1=============");
       boolean passed = true;
       totalTestCount++;
       
       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);
       Hands myHandsArray[] = new Hands[15];  
       Hands expectedHandsArray[] = new Hands[20];
       
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(2, 'C'), new Card(2, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(6, 'H'));
       myHandsArray[1] = new Hands(new Card(8, 'D'), new Card(9, 'D'), new Card(10, 'H'), new Card(11, 'D'), new Card(12, 'H'));
       myHandsArray[2] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C'));
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       myHandsArray[4] = new Hands(new Card(10, 'C'), new Card(11, 'D'), new Card(10, 'D'), new Card(10, 'S'), new Card(10, 'H'));
       myHandsArray[5] = new Hands(new Card(6, 'S'), new Card(7, 'D'), new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'));
       myHandsArray[6] = new Hands(new Card(14, 'C'), new Card(14, 'D'), new Card(6, 'C'), new Card(14, 'S'), new Card(14, 'H'));
       myHandsArray[7] = new Hands(new Card(11, 'H'), new Card(11, 'D'), new Card(11, 'C'), new Card(5, 'H'), new Card(5, 'S'));
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'));
       myHandsArray[9] = new Hands(new Card(8, 'S'), new Card(8, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(8, 'H'));
       myHandsArray[10] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(13, 'S'), new Card(14, 'S'));
       myHandsArray[11] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       myHandsArray[12] = new Hands(new Card(5, 'S'), new Card(10, 'D'), new Card(10, 'C'), new Card(5, 'C'), new Card(10, 'H'));
       myHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(4, 'H'), new Card(3, 'H'));
       myHandsArray[14] = new Hands(new Card(3, 'C'), new Card(5, 'D'), new Card(3, 'S'), new Card(5, 'S'), new Card(3, 'D'));
       
       for(int i = 0; i < 15; i++)
           myMaxHeap.insert(myHandsArray[i]);

       expectedHandsArray[0] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(13, 'S'), new Card(14, 'S'));
       expectedHandsArray[1] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'));
       expectedHandsArray[2] = new Hands(new Card(14, 'C'), new Card(14, 'D'), new Card(6, 'C'), new Card(14, 'S'), new Card(14, 'H'));
       expectedHandsArray[3] = new Hands(new Card(10, 'C'), new Card(11, 'D'), new Card(10, 'D'), new Card(10, 'S'), new Card(10, 'H'));
       expectedHandsArray[4] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C'));
       expectedHandsArray[5] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       expectedHandsArray[6] = new Hands(new Card(2, 'C'), new Card(2, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(6, 'H'));
       expectedHandsArray[7] = new Hands(new Card(8, 'D'), new Card(9, 'D'), new Card(10, 'H'), new Card(11, 'D'), new Card(12, 'H'));
       expectedHandsArray[8] = new Hands(new Card(11, 'H'), new Card(11, 'D'), new Card(11, 'C'), new Card(5, 'H'), new Card(5, 'S'));
       expectedHandsArray[9] = new Hands(new Card(8, 'S'), new Card(8, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(8, 'H'));
       expectedHandsArray[10] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       expectedHandsArray[11] = new Hands(new Card(6, 'S'), new Card(7, 'D'), new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'));
       expectedHandsArray[12] = new Hands(new Card(5, 'S'), new Card(10, 'D'), new Card(10, 'C'), new Card(5, 'C'), new Card(10, 'H'));
       expectedHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(4, 'H'), new Card(3, 'H'));
       expectedHandsArray[14] = new Hands(new Card(3, 'C'), new Card(5, 'D'), new Card(3, 'S'), new Card(5, 'S'), new Card(3, 'D'));
               

       // Action
       for(int i = 0; i < myMaxHeap.size; i++)
           passed &= assertEquals(expectedHandsArray[i], myMaxHeap.myHeap[i + 1]);
       
       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testInsert2(){
       // Setup
       System.out.println("============testInsert2=============");
       boolean passed = true;
       totalTestCount++;
       
       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);
       Hands myHandsArray[] = new Hands[22];  
       Hands expectedHandsArray[] = new Hands[20];
       
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(6, 'S'), new Card(3, 'D'), new Card(6, 'C'), new Card(6, 'H'), new Card(3, 'H'));
       myHandsArray[1] = new Hands(new Card(6, 'D'), new Card(6, 'S'), new Card(3, 'H'), new Card(6, 'C'), new Card(6, 'H'));
       myHandsArray[2] = new Hands(new Card(9, 'C'), new Card(11, 'C'), new Card(12, 'D'), new Card(10, 'H'), new Card(8, 'H'));
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       myHandsArray[4] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       myHandsArray[5] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(8, 'H'), new Card(10, 'H'), new Card(8, 'S'));
       myHandsArray[6] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
       myHandsArray[7] = new Hands(new Card(3, 'H'), new Card(5, 'D'), new Card(4, 'C'), new Card(6, 'H'), new Card(2, 'S'));
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       myHandsArray[9] = new Hands(new Card(2, 'S'), new Card(2, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(2, 'H'));
       myHandsArray[10] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       myHandsArray[11] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       myHandsArray[12] = new Hands(new Card(6, 'S'), new Card(5, 'D'), new Card(6, 'C'), new Card(5, 'C'), new Card(5, 'H'));
       myHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(8, 'H'), new Card(9, 'H'));
       myHandsArray[14] = new Hands(new Card(13, 'C'), new Card(5, 'D'), new Card(13, 'S'), new Card(5, 'S'), new Card(13, 'D'));
       myHandsArray[15] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       myHandsArray[16] = new Hands(new Card(4, 'C'), new Card(4, 'D'), new Card(4, 'S'), new Card(6, 'S'), new Card(4, 'H'));
       myHandsArray[17] = new Hands(new Card(3, 'H'), new Card(5, 'H'), new Card(3, 'S'), new Card(5, 'S'), new Card(5, 'D'));
       myHandsArray[18] = new Hands(new Card(10, 'S'), new Card(8, 'C'), new Card(8, 'S'), new Card(10, 'H'), new Card(10, 'D'));
       myHandsArray[19] = new Hands(new Card(5, 'C'), new Card(8, 'D'), new Card(7, 'S'), new Card(6, 'S'), new Card(9, 'D'));
       myHandsArray[20] = new Hands(new Card(7, 'S'), new Card(7, 'D'), new Card(4, 'S'), new Card(4, 'D'), new Card(7, 'C'));
       myHandsArray[21] = new Hands(new Card(9, 'D'), new Card(10, 'D'), new Card(13, 'D'), new Card(11, 'D'), new Card(12, 'D'));

       
       for(int i = 0; i < 20; i++)
           myMaxHeap.insert(myHandsArray[i]);
       

       expectedHandsArray[0] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       expectedHandsArray[1] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       expectedHandsArray[2] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
       expectedHandsArray[3] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       expectedHandsArray[4] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       expectedHandsArray[5] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       expectedHandsArray[6] = new Hands(new Card(13, 'C'), new Card(5, 'D'), new Card(13, 'S'), new Card(5, 'S'), new Card(13, 'D'));
       expectedHandsArray[7] = new Hands(new Card(6, 'D'), new Card(6, 'S'), new Card(3, 'H'), new Card(6, 'C'), new Card(6, 'H'));
       expectedHandsArray[8] = new Hands(new Card(10, 'S'), new Card(8, 'C'), new Card(8, 'S'), new Card(10, 'H'), new Card(10, 'D'));
       expectedHandsArray[9] = new Hands(new Card(2, 'S'), new Card(2, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(2, 'H'));
       expectedHandsArray[10] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       expectedHandsArray[11] = new Hands(new Card(9, 'C'), new Card(11, 'C'), new Card(12, 'D'), new Card(10, 'H'), new Card(8, 'H'));
       expectedHandsArray[12] = new Hands(new Card(6, 'S'), new Card(5, 'D'), new Card(6, 'C'), new Card(5, 'C'), new Card(5, 'H'));
       expectedHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(8, 'H'), new Card(9, 'H'));
       expectedHandsArray[14] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(8, 'H'), new Card(10, 'H'), new Card(8, 'S'));
       expectedHandsArray[15] = new Hands(new Card(3, 'H'), new Card(5, 'D'), new Card(4, 'C'), new Card(6, 'H'), new Card(2, 'S'));
       expectedHandsArray[16] = new Hands(new Card(4, 'C'), new Card(4, 'D'), new Card(4, 'S'), new Card(6, 'S'), new Card(4, 'H'));
       expectedHandsArray[17] = new Hands(new Card(3, 'H'), new Card(5, 'H'), new Card(3, 'S'), new Card(5, 'S'), new Card(5, 'D'));
       expectedHandsArray[18] = new Hands(new Card(6, 'S'), new Card(3, 'D'), new Card(6, 'C'), new Card(6, 'H'), new Card(3, 'H'));
       expectedHandsArray[19] = new Hands(new Card(5, 'C'), new Card(8, 'D'), new Card(7, 'S'), new Card(6, 'S'), new Card(9, 'D'));
           
       // Action
       for(int i = 0; i < myMaxHeap.size; i++)
           passed &= assertEquals(expectedHandsArray[i], myMaxHeap.myHeap[i + 1]);
       
       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void CustomTestInsert1(){
       // Setup
       System.out.println("============CustomTestInsert1=============");
       boolean passed = true;
       totalTestCount++;


       HandsMaxHeap myMaxHeap = new HandsMaxHeap(3);
       Hands myHandsArray[] = new Hands[7];  
       Hands expectedHandsArray[] = new Hands[3];
       
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(10, 'C'), new Card(10, 'D'), new Card(10, 'H'), new Card(5, 'C'), new Card(5, 'D'));
       myHandsArray[1] = new Hands(new Card(10, 'H'), new Card(9, 'S'), new Card(8, 'C'), new Card(6, 'C'), new Card(7, 'D'));
       myHandsArray[2] = new Hands(new Card(8, 'C'), new Card(7, 'C'), new Card(6, 'C'), new Card(5, 'C'), new Card(4, 'C'));
       myHandsArray[3] = new Hands(new Card(12, 'C'), new Card(12, 'D'), new Card(12, 'S'), new Card(8, 'H'), new Card(2, 'C'));
       myHandsArray[4] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(3, 'H')); 
       myHandsArray[5] = new Hands(new Card(11, 'H'), new Card(11, 'S'), new Card(4, 'D'), new Card(7, 'C'), new Card(2, 'D')); 
       myHandsArray[6] = new Hands(new Card(9, 'D'), new Card(3, 'H'), new Card(7, 'S'), new Card(5, 'C'), new Card(2, 'H')); 
      
       for(int i = 0; i < 3; i++)
           myMaxHeap.insert(myHandsArray[i]);
       
       expectedHandsArray[0] = new Hands(new Card(8, 'C'), new Card(7, 'C'), new Card(6, 'C'), new Card(5, 'C'), new Card(4, 'C'));
       expectedHandsArray[1] = new Hands(new Card(10, 'H'), new Card(9, 'S'), new Card(8, 'C'), new Card(6, 'C'), new Card(7, 'D'));
       expectedHandsArray[2] = new Hands(new Card(10, 'C'), new Card(10, 'D'), new Card(10, 'H'), new Card(5, 'C'), new Card(5, 'D'));

       for(int i = 0; i < myMaxHeap.size; i++)
           passed &= assertEquals(expectedHandsArray[i], myMaxHeap.myHeap[i + 1]);

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void CustomTestInsert2(){
       // Setup
       System.out.println("============CustomTestInsert2=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(6);
       Hands myHandsArray[] = new Hands[6];  
       Hands expectedHandsArray[] = new Hands[6];

        myHandsArray[0] = new Hands(new Card(2, 'C'), new Card(3, 'D'), new Card(4, 'H'), new Card(5, 'S'), new Card(6, 'C'));  
        myHandsArray[1] = new Hands(new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'), new Card(13, 'H'), new Card(14, 'H'));  
        myHandsArray[2] = new Hands(new Card(9, 'S'), new Card(9, 'D'), new Card(9, 'H'), new Card(9, 'C'), new Card(2, 'D'));  
        myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));  
        myHandsArray[4] = new Hands(new Card(5, 'H'), new Card(5, 'C'), new Card(5, 'S'), new Card(7, 'D'), new Card(7, 'H'));  
        myHandsArray[5] = new Hands(new Card(2, 'H'), new Card(4, 'H'), new Card(6, 'H'), new Card(8, 'H'), new Card(10, 'H'));  

        for(int i = 0; i < 6; i++) myMaxHeap.insert(myHandsArray[i]);
        
        expectedHandsArray[0] = new Hands(new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'), new Card(13, 'H'), new Card(14, 'H'));
        expectedHandsArray[1] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
        expectedHandsArray[2] = new Hands(new Card(9, 'S'), new Card(9, 'D'), new Card(9, 'H'), new Card(9, 'C'), new Card(2, 'D')); 
        expectedHandsArray[3] = new Hands(new Card(2, 'C'), new Card(3, 'D'), new Card(4, 'H'), new Card(5, 'S'), new Card(6, 'C'));  
        expectedHandsArray[4] = new Hands(new Card(5, 'H'), new Card(5, 'C'), new Card(5, 'S'), new Card(7, 'D'), new Card(7, 'H')); 
        expectedHandsArray[5] = new Hands(new Card(2, 'H'), new Card(4, 'H'), new Card(6, 'H'), new Card(8, 'H'), new Card(10, 'H'));
        
        for(int i = 0; i < myMaxHeap.size; i++)
            passed &= assertEquals(expectedHandsArray[i], myMaxHeap.myHeap[i + 1]);
        // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testGetSize1(){
       // Setup
       System.out.println("============testGetSize1=============");
       boolean passed = true;
       totalTestCount++;

       Random rn = new Random();
       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);

       int expected = rn.nextInt(10);
       for(int i = 0; i < expected; i++)
           myMaxHeap.insert(
               new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'))
           );

       // Action
       passed &= assertEquals(expected, myMaxHeap.getSize());

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testGetSize2(){
       // Setup
       System.out.println("============testGetSize2=============");
       boolean passed = true;
       totalTestCount++;

       Random rn = new Random();
       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);

       int expected = rn.nextInt(19);
       for(int i = 0; i < expected; i++)
           myMaxHeap.insert(
               new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'))
           );

       // Action
       passed &= assertEquals(expected, myMaxHeap.getSize());

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testRemoveMax1(){
       // Setup
       System.out.println("============testRemoveMax1=============");
       boolean passed = true;
       totalTestCount++;
       
       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);
       Hands myHandsArray[] = new Hands[15];  
       Hands expectedMaxHand[] = new Hands[3];
       
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(2, 'C'), new Card(2, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(6, 'H'));
       myHandsArray[1] = new Hands(new Card(8, 'D'), new Card(9, 'D'), new Card(10, 'H'), new Card(11, 'D'), new Card(12, 'H'));
       myHandsArray[2] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C'));
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       myHandsArray[4] = new Hands(new Card(10, 'C'), new Card(11, 'D'), new Card(10, 'D'), new Card(10, 'S'), new Card(10, 'H'));
       myHandsArray[5] = new Hands(new Card(6, 'S'), new Card(7, 'D'), new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'));
       myHandsArray[6] = new Hands(new Card(14, 'C'), new Card(14, 'D'), new Card(6, 'C'), new Card(14, 'S'), new Card(14, 'H'));
       myHandsArray[7] = new Hands(new Card(11, 'H'), new Card(11, 'D'), new Card(11, 'C'), new Card(5, 'H'), new Card(5, 'S'));
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'));
       myHandsArray[9] = new Hands(new Card(8, 'S'), new Card(8, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(8, 'H'));
       myHandsArray[10] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(13, 'S'), new Card(14, 'S'));
       myHandsArray[11] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       myHandsArray[12] = new Hands(new Card(5, 'S'), new Card(10, 'D'), new Card(10, 'C'), new Card(5, 'C'), new Card(10, 'H'));
       myHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(4, 'H'), new Card(3, 'H'));
       myHandsArray[14] = new Hands(new Card(3, 'C'), new Card(5, 'D'), new Card(3, 'S'), new Card(5, 'S'), new Card(3, 'D'));
       
       for(int i = 0; i < 15; i++)
           myMaxHeap.insert(myHandsArray[i]);

       
       expectedMaxHand[0] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(13, 'S'), new Card(14, 'S'));        
       expectedMaxHand[1] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H'));        
       expectedMaxHand[2] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C'));
      
       
              
       // Action
       for(int i = 0; i < 3; i++)
       {
           passed &= assertEquals(expectedMaxHand[i], myMaxHeap.removeMax());            
       }
           
       
       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void testRemoveMax2(){
       // Setup
       System.out.println("============testRemoveMax2=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(20);
       Hands myHandsArray[] = new Hands[22];  
       Hands expectedMaxHand[] = new Hands[5];

       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(6, 'S'), new Card(3, 'D'), new Card(6, 'C'), new Card(6, 'H'), new Card(3, 'H'));
       myHandsArray[1] = new Hands(new Card(6, 'D'), new Card(6, 'S'), new Card(3, 'H'), new Card(6, 'C'), new Card(6, 'H'));
       myHandsArray[2] = new Hands(new Card(9, 'C'), new Card(11, 'C'), new Card(12, 'D'), new Card(10, 'H'), new Card(8, 'H'));
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       myHandsArray[4] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       myHandsArray[5] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(8, 'H'), new Card(10, 'H'), new Card(8, 'S'));
       myHandsArray[6] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
       myHandsArray[7] = new Hands(new Card(3, 'H'), new Card(5, 'D'), new Card(4, 'C'), new Card(6, 'H'), new Card(2, 'S'));
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       myHandsArray[9] = new Hands(new Card(2, 'S'), new Card(2, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(2, 'H'));
       myHandsArray[10] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       myHandsArray[11] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       myHandsArray[12] = new Hands(new Card(6, 'S'), new Card(5, 'D'), new Card(6, 'C'), new Card(5, 'C'), new Card(5, 'H'));
       myHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(8, 'H'), new Card(9, 'H'));
       myHandsArray[14] = new Hands(new Card(13, 'C'), new Card(5, 'D'), new Card(13, 'S'), new Card(5, 'S'), new Card(13, 'D'));
       myHandsArray[15] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       myHandsArray[16] = new Hands(new Card(4, 'C'), new Card(4, 'D'), new Card(4, 'S'), new Card(6, 'S'), new Card(4, 'H'));
       myHandsArray[17] = new Hands(new Card(3, 'H'), new Card(5, 'H'), new Card(3, 'S'), new Card(5, 'S'), new Card(5, 'D'));
       myHandsArray[18] = new Hands(new Card(10, 'S'), new Card(8, 'C'), new Card(8, 'S'), new Card(10, 'H'), new Card(10, 'D'));
       myHandsArray[19] = new Hands(new Card(5, 'C'), new Card(8, 'D'), new Card(7, 'S'), new Card(6, 'S'), new Card(9, 'D'));
       myHandsArray[20] = new Hands(new Card(7, 'S'), new Card(7, 'D'), new Card(4, 'S'), new Card(4, 'D'), new Card(7, 'C'));
       myHandsArray[21] = new Hands(new Card(9, 'D'), new Card(10, 'D'), new Card(13, 'D'), new Card(11, 'D'), new Card(12, 'D'));


       for(int i = 0; i < 20; i++)
       myMaxHeap.insert(myHandsArray[i]);


       expectedMaxHand[0] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       expectedMaxHand[1] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       expectedMaxHand[2] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       expectedMaxHand[3] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       expectedMaxHand[4] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
               
       // Action
       for(int i = 0; i < 5; i++)
       {
           passed &= assertEquals(expectedMaxHand[i], myMaxHeap.removeMax());            
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }

   private static void CustomTestRemoveMax1(){
       // Setup
       System.out.println("============CustomTestRemoveMax1=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(10);
       Hands myHandsArray[] = new Hands[10];  
       Hands expectedMaxHand[] = new Hands[2];
       
     
        myHandsArray[0] = new Hands(new Card(3, 'D'), new Card(4, 'S'), new Card(5, 'C'), new Card(6, 'H'), new Card(7, 'D'));
        myHandsArray[1] = new Hands(new Card(2, 'H'), new Card(2, 'D'), new Card(2, 'S'), new Card(5, 'C'), new Card(9, 'H'));
        myHandsArray[2] = new Hands(new Card(10, 'C'), new Card(10, 'S'), new Card(6, 'D'), new Card(6, 'H'), new Card(4, 'C'));
        myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(8, 'C'), new Card(8, 'D'));
        myHandsArray[4] = new Hands(new Card(5, 'S'), new Card(6, 'S'), new Card(7, 'S'), new Card(8, 'S'), new Card(9, 'S'));
        myHandsArray[5] = new Hands(new Card(12, 'H'), new Card(12, 'D'), new Card(12, 'C'), new Card(4, 'S'), new Card(7, 'H'));
        myHandsArray[6] = new Hands(new Card(11, 'S'), new Card(11, 'C'), new Card(3, 'H'), new Card(3, 'D'), new Card(9, 'S'));
        myHandsArray[7] = new Hands(new Card(2, 'C'), new Card(5, 'D'), new Card(8, 'H'), new Card(10, 'S'), new Card(14, 'D'));
        myHandsArray[8] = new Hands(new Card(7, 'D'), new Card(8, 'C'), new Card(9, 'H'), new Card(10, 'S'), new Card(11, 'D'));
        myHandsArray[9] = new Hands(new Card(14, 'H'), new Card(13, 'H'), new Card(12, 'H'), new Card(11, 'H'), new Card(10, 'H'));
       
       for(int i = 0; i < 10; i++)
           myMaxHeap.insert(myHandsArray[i]);

       
       expectedMaxHand[0] = new Hands(new Card(14, 'H'), new Card(13, 'H'), new Card(12, 'H'), new Card(11, 'H'), new Card(10, 'H'));        
       expectedMaxHand[1] = new Hands(new Card(8, 'S'), new Card(9, 'S'), new Card(7, 'S'), new Card(5, 'S'), new Card(6, 'S'));        

      
       
              
       // Action
       for(int i = 0; i < 2; i++)
       {
           passed &= assertEquals(expectedMaxHand[i], myMaxHeap.removeMax());            
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }
   
   private static void CustomTestRemoveMax2(){
       // Setup
       System.out.println("============CustomTestRemoveMax2=============");
       boolean passed = true;
       totalTestCount++;

       HandsMaxHeap myMaxHeap = new HandsMaxHeap(3);
       Hands myHandsArray[] = new Hands[3];  
       Hands expectedMaxHand[] = new Hands[3];
       
     
       myHandsArray[0] = new Hands(new Card(2, 'C'), new Card(3, 'D'), new Card(4, 'H'), new Card(5, 'S'), new Card(6, 'C'));
       myHandsArray[1] = new Hands(new Card(3, 'H'), new Card(4, 'S'), new Card(5, 'C'), new Card(6, 'D'), new Card(7, 'H'));
       myHandsArray[2] = new Hands(new Card(4, 'S'), new Card(5, 'H'), new Card(6, 'D'), new Card(7, 'C'), new Card(8, 'S'));
       
       for(int i = 0; i < 3; i++)
           myMaxHeap.insert(myHandsArray[i]);

       
        expectedMaxHand[0] = new Hands(new Card(4, 'S'), new Card(5, 'H'), new Card(6, 'D'), new Card(7, 'C'), new Card(8, 'S'));
        expectedMaxHand[1] = new Hands(new Card(3, 'H'), new Card(4, 'S'), new Card(5, 'C'), new Card(6, 'D'), new Card(7, 'H'));
        expectedMaxHand[2] = new Hands(new Card(2, 'C'), new Card(3, 'D'), new Card(4, 'H'), new Card(5, 'S'), new Card(6, 'C')); 
              
       
              
       // Action
       for(int i = 0; i < 3; i++)
       {
           passed &= assertEquals(expectedMaxHand[i], myMaxHeap.removeMax());            
       }


       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }


   private static void testHeapSort1(){
       // Setup
       System.out.println("============testHeapSort1=============");
       boolean passed = true;
       totalTestCount++;

       Hands myHandsArray[] = new Hands[10];  
       Hands expectedHandsArray[] = new Hands[10];
       
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(2, 'C'), new Card(2, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(6, 'H')); // FH 6
       myHandsArray[1] = new Hands(new Card(8, 'D'), new Card(9, 'D'), new Card(10, 'H'), new Card(11, 'D'), new Card(12, 'H')); // S HQ
       myHandsArray[2] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C')); // SF C8
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D')); // FH A
       myHandsArray[4] = new Hands(new Card(10, 'C'), new Card(11, 'D'), new Card(10, 'D'), new Card(10, 'S'), new Card(10, 'H')); // FK 10
       myHandsArray[5] = new Hands(new Card(6, 'S'), new Card(7, 'D'), new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H')); // SF H10
       myHandsArray[6] = new Hands(new Card(14, 'C'), new Card(14, 'D'), new Card(6, 'C'), new Card(14, 'S'), new Card(14, 'H')); // FK A
       myHandsArray[7] = new Hands(new Card(11, 'H'), new Card(11, 'D'), new Card(11, 'C'), new Card(5, 'H'), new Card(5, 'S')); // FH J
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H')); // SF HQ
       myHandsArray[9] = new Hands(new Card(8, 'S'), new Card(8, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(8, 'H'));  // FH 8
       
       expectedHandsArray[0] = new Hands(new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H'), new Card(11, 'H'), new Card(12, 'H')); // SF HQ
       expectedHandsArray[1] = new Hands(new Card(4, 'C'), new Card(5, 'C'), new Card(6, 'C'), new Card(7, 'C'), new Card(8, 'C')); // SF C8
       expectedHandsArray[2] = new Hands(new Card(14, 'C'), new Card(14, 'D'), new Card(6, 'C'), new Card(14, 'S'), new Card(14, 'H')); // FK A
       expectedHandsArray[3] = new Hands(new Card(10, 'C'), new Card(11, 'D'), new Card(10, 'D'), new Card(10, 'S'), new Card(10, 'H')); // FK 10
       expectedHandsArray[4] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D')); // FH A
       expectedHandsArray[5] = new Hands(new Card(11, 'H'), new Card(11, 'D'), new Card(11, 'C'), new Card(5, 'H'), new Card(5, 'S')); // FH J
       expectedHandsArray[6] = new Hands(new Card(8, 'S'), new Card(8, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(8, 'H'));  // FH 8
       expectedHandsArray[7] = new Hands(new Card(2, 'C'), new Card(2, 'D'), new Card(6, 'C'), new Card(6, 'S'), new Card(6, 'H')); // FH 6
       expectedHandsArray[8] = new Hands(new Card(8, 'D'), new Card(9, 'D'), new Card(10, 'H'), new Card(11, 'D'), new Card(12, 'H')); // S HQ
       expectedHandsArray[9] = new Hands(new Card(6, 'S'), new Card(7, 'D'), new Card(8, 'H'), new Card(9, 'H'), new Card(10, 'H')); // S H10
       
       // Action
       heapSort(myHandsArray);

       for(int i = 0; i < 10; i++)
       {            
           passed &= assertEquals(expectedHandsArray[i], myHandsArray[i]);            
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }

   private static void testHeapSort2(){
       // Setup
       System.out.println("============testHeapSort2=============");
       boolean passed = true;
       totalTestCount++;

       Hands myHandsArray[] = new Hands[20];  
       Hands expectedHandsArray[] = new Hands[20];
      
       // [Scott] Need initialization of all hands
       myHandsArray[0] = new Hands(new Card(6, 'S'), new Card(3, 'D'), new Card(6, 'C'), new Card(6, 'H'), new Card(3, 'H'));
       myHandsArray[1] = new Hands(new Card(6, 'D'), new Card(6, 'S'), new Card(3, 'H'), new Card(6, 'C'), new Card(6, 'H'));
       myHandsArray[2] = new Hands(new Card(9, 'C'), new Card(11, 'C'), new Card(12, 'D'), new Card(10, 'H'), new Card(8, 'H'));
       myHandsArray[3] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       myHandsArray[4] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       myHandsArray[5] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(8, 'H'), new Card(10, 'H'), new Card(8, 'S'));
       myHandsArray[6] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
       myHandsArray[7] = new Hands(new Card(3, 'H'), new Card(5, 'D'), new Card(4, 'C'), new Card(6, 'H'), new Card(2, 'S'));
       myHandsArray[8] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       myHandsArray[9] = new Hands(new Card(2, 'S'), new Card(2, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(2, 'H'));
       myHandsArray[10] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       myHandsArray[11] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       myHandsArray[12] = new Hands(new Card(6, 'S'), new Card(5, 'D'), new Card(6, 'C'), new Card(5, 'C'), new Card(5, 'H'));
       myHandsArray[13] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(8, 'H'), new Card(9, 'H'));
       myHandsArray[14] = new Hands(new Card(13, 'C'), new Card(5, 'D'), new Card(13, 'S'), new Card(5, 'S'), new Card(13, 'D'));
       myHandsArray[15] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       myHandsArray[16] = new Hands(new Card(4, 'C'), new Card(4, 'D'), new Card(4, 'S'), new Card(6, 'S'), new Card(4, 'H'));
       myHandsArray[17] = new Hands(new Card(3, 'H'), new Card(5, 'H'), new Card(3, 'S'), new Card(5, 'S'), new Card(5, 'D'));
       myHandsArray[18] = new Hands(new Card(10, 'S'), new Card(8, 'C'), new Card(8, 'S'), new Card(10, 'H'), new Card(10, 'D'));
       myHandsArray[19] = new Hands(new Card(5, 'C'), new Card(8, 'D'), new Card(7, 'S'), new Card(6, 'S'), new Card(9, 'D'));
       
       expectedHandsArray[0] = new Hands(new Card(14, 'D'), new Card(12, 'D'), new Card(10, 'D'), new Card(11, 'D'), new Card(13, 'D'));
       expectedHandsArray[1] = new Hands(new Card(10, 'S'), new Card(11, 'S'), new Card(12, 'S'), new Card(8, 'S'), new Card(9, 'S'));
       expectedHandsArray[2] = new Hands(new Card(8, 'H'), new Card(10, 'H'), new Card(9, 'H'), new Card(11, 'H'), new Card(7, 'H'));
       expectedHandsArray[3] = new Hands(new Card(7, 'C'), new Card(8, 'C'), new Card(10, 'C'), new Card(11, 'C'), new Card(9, 'C'));
       expectedHandsArray[4] = new Hands(new Card(11, 'C'), new Card(12, 'D'), new Card(12, 'C'), new Card(12, 'S'), new Card(12, 'H'));
       expectedHandsArray[5] = new Hands(new Card(6, 'D'), new Card(6, 'S'), new Card(3, 'H'), new Card(6, 'C'), new Card(6, 'H'));
       expectedHandsArray[6] = new Hands(new Card(4, 'C'), new Card(4, 'D'), new Card(4, 'S'), new Card(6, 'S'), new Card(4, 'H'));
       expectedHandsArray[7] = new Hands(new Card(14, 'S'), new Card(14, 'H'), new Card(14, 'D'), new Card(10, 'C'), new Card(10, 'D'));
       expectedHandsArray[8] = new Hands(new Card(13, 'C'), new Card(5, 'D'), new Card(13, 'S'), new Card(5, 'S'), new Card(13, 'D'));
       expectedHandsArray[9] = new Hands(new Card(12, 'D'), new Card(12, 'C'), new Card(9, 'C'), new Card(12, 'S'), new Card(9, 'H'));
       expectedHandsArray[10] = new Hands(new Card(10, 'S'), new Card(10, 'D'), new Card(8, 'H'), new Card(10, 'H'), new Card(8, 'S'));
       expectedHandsArray[11] = new Hands(new Card(10, 'S'), new Card(8, 'C'), new Card(8, 'S'), new Card(10, 'H'), new Card(10, 'D'));
       expectedHandsArray[12] = new Hands(new Card(6, 'S'), new Card(3, 'D'), new Card(6, 'C'), new Card(6, 'H'), new Card(3, 'H'));
       expectedHandsArray[13] = new Hands(new Card(3, 'H'), new Card(5, 'H'), new Card(3, 'S'), new Card(5, 'S'), new Card(5, 'D'));
       expectedHandsArray[14] = new Hands(new Card(6, 'S'), new Card(5, 'D'), new Card(6, 'C'), new Card(5, 'C'), new Card(5, 'H'));
       expectedHandsArray[15] = new Hands(new Card(2, 'S'), new Card(2, 'D'), new Card(7, 'S'), new Card(7, 'C'), new Card(2, 'H'));
       expectedHandsArray[16] = new Hands(new Card(9, 'C'), new Card(11, 'C'), new Card(12, 'D'), new Card(10, 'H'), new Card(8, 'H'));
       expectedHandsArray[17] = new Hands(new Card(7, 'S'), new Card(6, 'C'), new Card(5, 'C'), new Card(8, 'H'), new Card(9, 'H'));
       expectedHandsArray[18] = new Hands(new Card(5, 'C'), new Card(8, 'D'), new Card(7, 'S'), new Card(6, 'S'), new Card(9, 'D'));
       expectedHandsArray[19] = new Hands(new Card(3, 'H'), new Card(5, 'D'), new Card(4, 'C'), new Card(6, 'H'), new Card(2, 'S'));
          
           
           
       
       // Action
       heapSort(myHandsArray);

       for(int i = 0; i < 20; i++)
       {
           passed &= assertEquals(expectedHandsArray[i], myHandsArray[i]);            
       }

       // Tear Down
       totalPassed &= passed;
       if(passed) 
       {
           System.out.println("\tPassed");
           totalPassCount++;            
       }
   }


   
   private static boolean assertEquals(Hands a, Hands b)
   {
       if(!a.isMyHandEqual(b))
       {
           System.out.println("\tAssert Failed!");
           System.out.printf("\tExpected: ");
           a.printMyHand();
           System.out.printf(", Actual: ");
           b.printMyHand();
           System.out.printf("\n");
           return false;
       }

       return true;
   }

   private static boolean assertEquals(int a, int b)
   {
       if(a != b)
       {
           System.out.println("\tAssert Failed!");
           System.out.printf("\tExpected: %d, Actual: %d\n\n", a, b);
           return false;
       }

       return true;
   }
   
}
