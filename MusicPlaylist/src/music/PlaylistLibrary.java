package music;

import java.util.*;

import org.w3c.dom.Node;

/**
 * @MaryGrlic
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular linked list.
     * 
     * 3. While there are still lines in the input file:
     *      1. read a song from the file
     *      2. create an object of type Song with the song information
     *      3. Create a SongNode object that holds the Song object from step 3.2.
     *      4. insert the Song object at the END of the circular linked list (use the reference from step 2)
     *      5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     *      songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract 
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last, 
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {

        Playlist myPlaylist = new Playlist();
        StdIn.setFile(filename);

        SongNode head = null;
        SongNode last = null;

        while(!StdIn.isEmpty()) {
            String[] data = StdIn.readLine().split(",");

            String name = data[0];
            String artist = data[1];
            int year = Integer.parseInt(data[2]);
            int pop = Integer.parseInt(data[3]);
            String link = data[4];
            Song song = new Song(name, artist, year, pop, link);
            SongNode songNode = new SongNode(song, last);
            
            if (head == null) { // if there is nothing in the first node
                head = songNode; // put in songnode
                last = songNode; // circular so last also gets songnode
                songNode.setNext(songNode);
            } else {
                last.setNext(songNode); // next songnode
                last = songNode;
                last.setNext(head); // set it to head -- circular
            } 

            int size = myPlaylist.getSize();
            myPlaylist.setSize(size + 1);

        }
        myPlaylist.setLast(last);
        return myPlaylist;
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param filename the playlist information input file
     * @param playlistIndex the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     *       add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i]. 
     * Use the addPlaylist() method. 
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {

        ArrayList<Playlist> songLibrary = new ArrayList<Playlist>();

        if (filenames == null) {
            return;
        }
        for (int i = 0; i < filenames.length; i++) {
            String playlistFile = filenames[i];
            addPlaylist(playlistFile, i);
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the 
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the 
     * specified playlist (and thus has been added to the playlist), false 
     * otherwise (and the song will not be added). 
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position the position inthe playlist to which the song 
     * is to be added 
     * @param song the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
    public boolean insertSong(int playlistIndex, int position, Song song) {

        Playlist myPlaylist = songLibrary.get(playlistIndex);

        int size = myPlaylist.getSize();
        boolean exists;

        if (myPlaylist.getLast() == null) {
        // if playlist is null
            if (position != 1) {
                exists = false;
                // position out of bounds
            } else {
                SongNode head = new SongNode(song, null);
                head.setNext(head);
                myPlaylist.setSize(size + 1);
                myPlaylist.setLast(head);
                exists = true;
            } // create a new playlist of one node

        } else if ((position < 1) || (position > size + 1)) {
            exists = false; // position out of bounds
        } else if (position == 1) {
            SongNode head = myPlaylist.getLast().getNext();
            SongNode newSong = new SongNode(song, head);
            myPlaylist.getLast().setNext(newSong);
            exists = true;
        } else { 
            SongNode currentNode = myPlaylist.getLast().getNext(); // 1

            for (int i = 1; i < position - 1; i++) {
                currentNode = currentNode.getNext(); 
            } // traverse until you get to random index

            if (currentNode.getSong().equals(myPlaylist.getLast().getSong())) {
                SongNode first = myPlaylist.getLast().getNext();
                SongNode newSong = new SongNode(song, null);;
                myPlaylist.setSize(size + 1);

                myPlaylist.getLast().setNext(newSong);
                myPlaylist.setLast(newSong);
                myPlaylist.getLast().setNext(first);

                exists = true;
            } else { 
                SongNode newSong = new SongNode(song, currentNode.getNext());
                myPlaylist.setSize(size + 1);
                currentNode.setNext(newSong);
                exists = true;
            }
        }
        return exists;
    }

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * Use the .equals() method of the Song class to check if an element of 
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been 
     * removed), false otherwise (and thus nothing is removed). 
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
    public boolean removeSong(int playlistIndex, Song song) {

        Playlist myPlaylist = songLibrary.get(playlistIndex);
        int size = myPlaylist.getSize();
        boolean exists = false;

        if (myPlaylist.getSize() <= 0) {
            exists = false;
        } 
        else if (myPlaylist.getSize() == 1) {
            if (myPlaylist.getLast().getSong().equals(song)) {
                myPlaylist.setLast(null);
                myPlaylist.setSize(myPlaylist.getSize() - 1);
                exists = true;
            } else {
                exists = false;
            }
        }
        else { 
                SongNode pointer = myPlaylist.getLast().getNext();
                SongNode head = pointer;
                SongNode last = myPlaylist.getLast();
                SongNode prev = last;

                do {

                    if (pointer.getSong().equals(song)) {
                        exists = true;
                        myPlaylist.setSize(size - 1);
                        break;
                    }

                    prev = pointer;
                    pointer = pointer.getNext();

                } while (pointer != head);

                if (pointer.getSong().equals(song)) {
                    if (last.getSong().equals(song)) {
                        myPlaylist.setLast(prev);
                        prev.setNext(pointer.getNext());
                    } else {
                        prev.setNext(pointer.getNext());
                    }
                }

            }
        return exists;
    }

    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will 
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {
        
        Playlist myPlaylist = songLibrary.get(playlistIndex);

        if (myPlaylist.getSize() <= 0) {
            return;
        }

        SongNode head = myPlaylist.getLast().getNext();
        SongNode pointer = head;
        SongNode prev = null;
        SongNode next;

        do {
            next = pointer.getNext();
            pointer.setNext(prev);
            prev = pointer;
            pointer = next; 
        } while (pointer != head);

        myPlaylist.setLast(pointer);
        head.setNext(prev);
    }

    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting 
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity 
     * order. If the songs have the same popularity, add the song from the 
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     *  - store the merged playlist at the lower playlistIndex
     *  - remove playlist at the higher playlistIndex 
     * 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */
    public void mergePlaylists(int playlistIndex1, int playlistIndex2) {

        Playlist mergedPlaylist = new Playlist();
        SongNode mergeHead = null, first = null;
        
        int low = Math.min(playlistIndex1, playlistIndex2);
        int high = Math.max(playlistIndex1, playlistIndex2);

        Playlist lowPlaylist = new Playlist();
        lowPlaylist = songLibrary.get(low);
        Playlist highPlaylist = new Playlist();
        highPlaylist = songLibrary.get(high);

        int size1 = lowPlaylist.getSize();
        int size2 = highPlaylist.getSize();
        mergedPlaylist.setSize(size1 + size2);

        if (lowPlaylist.getSize() == 0 && highPlaylist.getSize() == 0) {
            removePlaylist(high);
            return;
        } else if (lowPlaylist.getSize() > 0 && highPlaylist.getSize() == 0) {
            mergedPlaylist = lowPlaylist;
            removePlaylist(high);
            songLibrary.set(low, mergedPlaylist);
        } else if (highPlaylist.getSize() > 0 && lowPlaylist.getSize() == 0) {
            mergedPlaylist = highPlaylist;
            removePlaylist(high);
            songLibrary.set(low, mergedPlaylist);
        } else { 

            SongNode head1 = lowPlaylist.getLast().getNext(); 
            SongNode head2 = highPlaylist.getLast().getNext();

            while (lowPlaylist.getSize() > 0 && highPlaylist.getSize() > 0) { 
            // while both playlists are not empty

                if (head1.getSong().getPopularity() >= head2.getSong().getPopularity()) { // compare popularities of each head

                    SongNode current = head1;

                    if (mergeHead == null) {
                        first = new SongNode(head1.getSong(), head1.getNext());
                        mergeHead = first;
                    } else {
                        mergeHead.setNext(current);
                        mergeHead = current;
                    }
                    removeSong(low, head1.getSong());
                    head1 = head1.getNext();
                    

                } else if (head2.getSong().getPopularity() > head1.getSong().getPopularity()) { // if head2 popularity is greater than the head 1 popularity

                    SongNode current = head2;

                    if (mergeHead == null) {
                        first = new SongNode(head2.getSong(), head2.getNext());
                        mergeHead = first;
                    } else {
                        mergeHead.setNext(current);
                        mergeHead = current;
                    } 
                    removeSong(high, head2.getSong());
                    head2 = head2.getNext();
                }
            }
            // once a playlist is empty, end the non-empty playlist
            // to the end of the merged playlist
            while (lowPlaylist.getSize() > 0) {
                if (mergeHead == null) {
                    first = new SongNode(head1.getSong(), head1.getNext());
                    mergeHead = first;
                    removeSong(low, head1.getSong());
                } else {
                    SongNode current = head1;
                    mergeHead.setNext(current);
                    mergeHead = current;
                    removeSong(low, head1.getSong());
                    head1 = head1.getNext();
                }
            }

            while (highPlaylist.getSize() > 0) {
                if (mergeHead == null) {
                    first = new SongNode(head2.getSong(), head2.getNext());
                    mergeHead = first;
                    removeSong(high, head2.getSong());                    
                } else {
                    SongNode current = head2;
                    mergeHead.setNext(current);
                    mergeHead = current;
                    removeSong(high, head2.getSong());
                    head2 = head2.getNext();
                }

            }
        mergedPlaylist.setLast(mergeHead);
        mergedPlaylist.getLast().setNext(first);
        removePlaylist(high);
        songLibrary.set(low, mergedPlaylist);
    }
}
       
    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     * 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a number 
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert 
     * it into the END of the new playlist (1 being the first node, 2 being the 
     * second, etc). 
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     *    
     * @param index the playlist to shuffle in songLibrary
     */
    public void shufflePlaylist(int playlistIndex) {

        Playlist myPlaylist = songLibrary.get(playlistIndex);
        int size = myPlaylist.getSize();
        Playlist shuffle = new Playlist();
        SongNode head = null, last = null;

        if (myPlaylist.getSize() == 0) {
            return;
        } else {

            while (myPlaylist.getSize() > 0) {
                
                int r = StdRandom.uniformInt(myPlaylist.getSize() + 1) + 1;
                SongNode current = myPlaylist.getLast().getNext();

                for (int i = 1; i < r; i ++) {
                    current = current.getNext();
                }

                if (last == null) {
                    head = new SongNode(current.getSong(), current.getNext());
                    last = head;
                } else {
                    last.setNext(current);
                    last = current;
                } 

                removeSong(playlistIndex, current.getSong());
            } 
            shuffle.setLast(last);
            shuffle.getLast().setNext(head);
            shuffle.setSize(size);
            songLibrary.set(playlistIndex, shuffle);
        }
    }
    /**
     * This method sorts a specified playlist using linearithmic sort.
     * 
     * Set the playlist located at the corresponding playlistIndex
     * in decreasing popularity index order.
     * 
     * This method should  use a sort that has O(nlogn), such as with merge sort.
     * 
     * @param playlistIndex the playlist to shuffle
     */
    public void sortPlaylist ( int playlistIndex ) {

        // WRITE YOUR CODE HERE
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            }
            else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first) repeats--;
        } while (ptr != first || repeats > 0);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}
