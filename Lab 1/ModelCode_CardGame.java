
public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;

    public static CardPool myCardPool;
    public static HandsMaxHeap myMaxHeap;

    public static Card[] myCards, tempCards;
    public static int pocketSize = POCKETSIZE;

    // [Problem 2] Generate All Possible Valid Hands from the Pocket Cards and store them in myMaxHeap
    public static void generateHands(Card[] thisPocket)
    {
        // If thisPocket has less than 5 cards, no hand can be generated, thus the heap will be empty
        myMaxHeap = new HandsMaxHeap(53130); // total combinations possible = 25C5 = 53130
        if(thisPocket.length < 5) return;
        
        // Otherwise, generate all possible valid hands from thisPocket and store them in myMaxHeap
        Card[] hand = new Card[5];
        genCombinations(thisPocket, hand, myMaxHeap, 0, 0); // call helper
    }
    // helper function: recursively calculates nCr = 25C5 combinations of hands
    private static void genCombinations(Card[] thisPocket, Card[] holder, HandsMaxHeap myHeap, int start, int index){
        if(index == 5) { // break and return from the function when a combination of 5 cards is achieved
            Hands tempHand = new Hands(holder[0], holder[1], holder[2], holder[3], holder[4]);
            if(tempHand.isAValidHand()) {
                myHeap.insert(tempHand); // only insert in heap if valid
            }
            return;
        }
        // simulating a factorial
        for(int i = start; i <= thisPocket.length - 1 && thisPocket.length - i >= 5 - index; i++){
            holder[index] = thisPocket[i];
            genCombinations(thisPocket, holder, myHeap, i + 1, index + 1);

        }
    }

    // Sorts the array of Cards in ascending order: ascending order of ranks; cards of equal ranks are sorted in ascending order of suits
    public static void sortCards(Card[] cards)
    {
        int j;
        Card temp;        
        int size = cards.length;
        
        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }

    public static void main(String args[]) throws Exception
    {
        Hands myMove;        
        
        myCardPool = new CardPool();        
        myCardPool.printPool();

        myCards = new Card[pocketSize];
        myCards = myCardPool.getRandomCards(POCKETSIZE);  
        sortCards(myCards);

        // print cards
        System.out.println("My Pocket Cards:");
        for(int j = 0; j < pocketSize; j++)
        {            
            myCards[j].printCard();
        }
        System.out.println();
        
        generateHands(myCards); // generates all valid hands from myCards and stores them in myMaxHeap
        System.out.println("Initial Heap:");
        myMaxHeap.printHeap(); // prints the contents of the initial heap
        System.out.println("\n---GAME BEGIN!---");
        // Print the contents of myMaxHeap
        

        // [Problem 3] Implementing Game Logic Part 1 - Aggresive AI: Always Picks the Strongest Hand
        for(int i = 0; pocketSize > 4; i++)
        {            
                                   
            // Step 1:
            // - Check if the Max Heap contains any valid hands 
            //   - if yes, remove the Largest Hand from the heap as the current Move
            //   - otherwise, you are out of valid hands.  Just pick any 5 cards from the pocket as a "Pass Move"
            // - Once a move is chosen, print the Hand for confirmation. MUST PRINT FOR VALIDATION!!
            if(!myMaxHeap.isEmpty()) myMove = myMaxHeap.removeMax();
            else myMove = new Hands(myCards[0], myCards[1], myCards[2], myCards[3], myCards[4]);
            
            System.out.println("\nMy Move:");
            myMove.printMyHand();
            System.out.println();
            // Step 2:
            // - Remove the Cards used in the move from the pocket cards and update the Max Heap
            // - Print the remaining cards and the contents of the heap
            
            tempCards = new Card[pocketSize - 5];
            int count = 0;

            // deleting removed cards by appending all unused ones to tempCards
            for (int j = 0; j < pocketSize; j++) {
                boolean found = false;
                for (int k = 0; k < 5; k++) {
                    if (myCards[j].isMyCardEqual(myMove.getCard(k))) {
                        found = true;
                        break;
                    }   
                }
                if (!found) {
                    tempCards[count] = myCards[j];
                    count++;
                }
            }

            // ouputting results of the move
            myCards = tempCards;
            sortCards(myCards);

            System.out.println("Leftover cards:");
            pocketSize = pocketSize - 5;
            for(int j = 0; j < pocketSize; j++){            
                myCards[j].printCard();
            }
            System.out.println();

            generateHands(myCards); 
            System.out.println("New Heap:");
            myMaxHeap.printHeap();     
        }
        System.out.println("\n---GAME OVER!---");
    }

}
