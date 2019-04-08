/**
 The HamFather
 * This game is about you, a hamster, who tries to escape his domestic life 
 * to lead the rodent mafia of your dreams.
 * 
 * @author Isabella Dela Pena, Aidan Varkoly
 * @version 2019.04.04
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Items foundItems;    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
    }
    private void createItems()
    {
     Items redpill;
     redpill = new Items("");
     
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room cage, hamWheel, hamHouse, hamBowl, hamDoor;
        Room brokenVent, vent;
        Room humanRoom, bed, mirror, dresser;
        Room ventDuct, bathroom;
        Room toilet, medCab;
        Room sewer, sewerPub;
        Room garden, petStore, ratCage;
      
        // Inside the Hamster Cage //
        cage = new Room("in hamster prison.");
        hamWheel = new Room("at the hamster wheel. Your personal gym");
        hamHouse = new Room("in your hammy home. Home sweet home");
        hamBowl = new Room("at your feeding grounds");
        hamDoor = new Room("looking outside the prison bars. The gate");
        
        //Escape routes//
        brokenVent = new Room("at the old shitty vent that smells " 
        + "like mothballs");
        vent = new Room ("at one of the vents. This one seems brand new");
        
        //In the Human Room//
        humanRoom = new Room("in the warden's room");
        bed = new Room("at the warden's bed. It looks comfy, but smells like" +
        " socks and cheese puffs");
        mirror = new Room("at the mirror. You look handsome");
        dresser = new Room("at the dresser. You often see the warden keep " +
        "clothes and valuables here");
        
        //Vent - Bathroom//
        ventDuct = new Room("in the vent duct. It's dusty as fuck");
        bathroom = new Room("in the bathroom");
        toilet = new Room("on the toilet seat. It's gross.");
        sewer = new Room("in the sewer. I don't know why. You're a hamster, not "
        + "some dirty ass rat or mutant turtle");
        sewerPub = new Room("standing in front of a bar called 'The Sewer-Side'");
        medCab = new Room("at the medicine cabinet. You smell hemorrhoid cream");
        
        //Outside//
        garden = new Room("outside where the warden grows flowers");
        petStore = new Room("in the pet store where you were kidnapped");
        ratCage = new Room("standing in front of RBG's lair.");
        
        // EXITS 1: CAGE//
        cage.setExit("north", hamDoor);
        cage.setExit("south", hamWheel);
        cage.setExit("east", hamHouse);
        cage.setExit("west", hamBowl);
        
        hamHouse.setExit("west", cage);

        hamBowl.setExit("east", cage);
        
        hamDoor.setExit("north", humanRoom);
        hamDoor.setExit("south", cage);
        hamDoor.setExit("east", brokenVent);
        hamDoor.setExit("west", vent);

        hamWheel.setExit("north", cage);
        
        //EXITS 2: BEDROOM//
        humanRoom.setExit("south", hamDoor);
        humanRoom.setExit("northeast", mirror);
        humanRoom.setExit("east", dresser);
        humanRoom.setExit("west", bed);
        
        bed.setExit("east", humanRoom);
        
        mirror.setExit("south", dresser);
        mirror.setExit("west", humanRoom);
        
        dresser.setExit("north", mirror);
        dresser.setExit("west", humanRoom);
        
        //EXITS 3: VENTS//
        brokenVent.setExit("west", hamDoor);
        brokenVent.setExit("south", ventDuct);
        
        vent.setExit("east", hamDoor);
        
        ventDuct.setExit("east", bathroom);
        ventDuct.setExit("south", garden);
        ventDuct.setExit("north", brokenVent);
        
        //EXITS 4: BATHROOM//
        bathroom.setExit("east", toilet);
        bathroom.setExit("south", medCab);
        bathroom.setExit("west", ventDuct);
        
        toilet.setExit("down", sewer);
        toilet.setExit("west", bathroom);
        
        medCab.setExit("north", bathroom);
        
        sewer.setExit("up", toilet);
        sewer.setExit("west", sewerPub);
        
        sewerPub.setExit("east", sewer);
        
        //EXITS 5: OUTSIDE//
        garden.setExit("north", ventDuct);
        garden.setExit("across", petStore);
        
        petStore.setExit("across", garden);
        petStore.setExit("north", ratCage);
        
        ratCage.setExit("south", petStore);

        currentRoom = cage;  // start game in the cage
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing!");
        System.out.println("Goodbye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("The HamFather");
        System.out.println("-------------");
        System.out.println("You are a hamster trying to escape your domestic life");
        System.out.println("to lead the rodent mafia ring of your dreams.");
        System.out.println("Good luck.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        boolean wantToPickUp = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            case PICKUP:
                 pickUpItem(command);
                 break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("SAY HELLO TO MY LITTLE FRIENDS");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void pickUpItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pick up what?");
            return;
        }

        String use = command.getSecondWord();
        
        Items searchItem = foundItems.getItems(use);
        
        if (searchItem == null) {
            System.out.println("There is nothing to pick up!");
        }
        else {
            foundItems = searchItem;
            System.out.println(foundItems.getLongDescription());
        }
    }
  }
