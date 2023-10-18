import javax.management.RuntimeMBeanException;
import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*  Name: Tyler Baboolal, Student ID: 501176018
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{


		//Get the pass through contents type and use a switch to determine if song or Audiobook
		switch(content.getType()){
			case "SONG":
				//Type cast content into a song
				Song otherS = (Song)content;
				//Check if in the library
				if (songs.contains(otherS)){
					throw new SongAlreadyExistsException("SONG "+otherS.getTitle()+" is already in library!");
				}
				else{
					//Add into library
					songs.add(otherS);
					System.out.println("SONG "+otherS.getTitle()+" added to library!");
				}
				break;
			case "AUDIOBOOK":
				//Type cast content into an Audiobook
				AudioBook otherA = (AudioBook)content;
				//Chcck if in the library
				if(audiobooks.contains(otherA)){
					throw new AudioBookAlreadyExistsException("AUDIOBOOK "+otherA.getTitle() + " is already in library!");
				}
				else{
					//Add into library
					audiobooks.add(otherA);
					System.out.println("AUDIOBOOK "+otherA.getTitle()+" added to library!");
				}
		}

	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{

		//Loop through all the songs in library and print out their info
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		//Loop through all the audiobooks in library and print out their info
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		//Did not do
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		//List through all the playlist in library and print their names
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());
			System.out.println();
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();

		//Loop through all the songs library
		for (int i = 0; i<songs.size();i++){
			//Check if the artists name is already in library
			int index = artists.indexOf(songs.get(i).getArtist());
			//If not add to library
			if(index == -1){
				artists.add(songs.get(i).getArtist());
			}
		}
		//Print out the artists stored in artists
		for (int i = 0; i<artists.size();i++){
			int index = i +1;
			System.out.print(""+index+". ");
			System.out.println(artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		//Check if song is in the library
		if(index<1||index>songs.size()){
			throw new SongNotFoundException("Song does not exists");
		}
		//Get the song name
		String name= songs.get(index-1).getTitle();

		//Remove the song from the library
		songs.remove(index-1);

		//Loop through the playlists
		for (int i = 0; i<playlists.size();i++){
			//Loop through the content within each playlist
			for (int j = 0; j<playlists.get(i).getContent().size();j++){
				//If the song name is in the playlist remove it
				if (playlists.get(i).getContent().get(j).getTitle().equalsIgnoreCase(name)){
					playlists.get(i).getContent().remove(j);
				}
			}
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort()

		//Using comprator interface sort them
		Collections.sort(songs, new SongLengthComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		//compare method to compare the year
		public int compare (Song a, Song b){
			return a.getYear() - b.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()

		//Using comparator interfac to sort
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		//Sort songs by length
		public int compare(Song a, Song b){
			return a.getLength()-b.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code


		//Use comparator to sort
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{

		//Check if song is in library
		if (index < 1 || index > songs.size())
		{
			throw new SongNotFoundException("Song does not exists");

		}

		//Play the song
		songs.get(index-1).play();

	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//Check if audiobook is in the library
		if (index <1 || index > audiobooks.size()){
			throw new AudioBookNotFoundException("Audiobook does not exists");
		}

		//Check if chapter is valid within the audiobook
		if (chapter<1 || chapter>audiobooks.get(index-1).getNumberOfChapters()){
			throw new AudioBookChapterNotFoundException("Audiobook chapter does not exists");

		}

		//Call select chapter
		audiobooks.get(index-1).selectChapter(chapter);

		//Play the audiocontent
		audiobooks.get(index-1).play();

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		//Check if the index is within library.
		if (index <1 || index > audiobooks.size()){
			throw new AudioBookNotFoundException("Audiobook is not in library");
		}
		audiobooks.get(index-1).printTOC();

	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{

		//Loop to check if the playlist is already created
		for (int i = 0; i<playlists.size();i++){
			if (playlists.get(i).getTitle().equalsIgnoreCase(title)){
				throw new PlayListSameNameException("Playlist cannot have the same name");

			}
		}

		//If not create the playlist
		Playlist playlist = new Playlist(title);
		//Add it to the playlist arraylist
		playlists.add(playlist);

	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{

		//Loop through the playlists and check if the playlist is in the arraylist
		for (int i = 0; i<playlists.size();i++){

			//If yes print the contents
			if (playlists.get(i).getTitle().equalsIgnoreCase(title)){
				playlists.get(i).printContents();
				return;
			}
		}
		errorMsg = "Playlist not found!";

	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{

		//For loop to check if the playlist is in the arraylist
		for (int i = 0; i<playlists.size();i++){
			if(playlists.get(i).getTitle().equalsIgnoreCase(playlistTitle)){
				playlists.get(i).playAll();
				return;
			}
		}
		throw new PlayListNameNotFoundException("Playlist not found");
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		//For loop to check if the playlist is in the arraylist
		for (int i = 0; i <playlists.size();i++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				//Check if the index is valid within that playlist
				if (indexInPL<1 || indexInPL>playlists.get(i).getContent().size()){
					throw new ContentNotFoundException("Content does not exists ");
				}
				//If is valid play the audio content
				playlists.get(i).play(indexInPL);
				return;
			}
		}
		throw new PlayListNameNotFoundException("Playlist name not found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//Force string to upper
		type = type.toUpperCase();
		for (int i = 0; i<playlists.size();i++){
			//If the title is in playlist
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				//Use a switch case to determine if the type is a Song or Audio and add the content to the playlist
				switch (type){
					case "SONG":
						if (index<0||index>songs.size()){
							throw new SongNotFoundException("Song does not exists ");
						}
						playlists.get(i).getContent().add(songs.get(index-1));
						return;
					case "AUDIOBOOK":
						if (index<0||index>audiobooks.size()){
							throw new AudioBookNotFoundException("Audiobook does not exists");
						}
						playlists.get(i).getContent().add(audiobooks.get(index-1));
						return;
					default:
						throw new TypeNotFoundException("Incorrect type");
				}
			}
		}
		throw new PlayListNameNotFoundException("Playlist name does not exists");
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		//Loop through the playlists arraylist
		for (int i = 0; i<playlists.size();i++){
			//Check if the playlist exists
			if (playlists.get(i).getTitle().equalsIgnoreCase(title)){
				//Check if the index passed through is valid
				if (index<1||index>playlists.get(i).getContent().size()){
					//Throw exception
					throw new ContentNotFoundException("Content not found");
				}
				playlists.get(i).deleteContent(index);
				return;
			}
		}
		throw new PlayListNameNotFoundException("Playlist not found");
	}
	
}


//Classes for Exceptions
class SongNotFoundException extends RuntimeException{
	public SongNotFoundException(String message){
		super(message);
	}
}
class SongAlreadyExistsException extends  RuntimeException{
	public SongAlreadyExistsException(String message){
		super(message);
	}
}

class AudioBookAlreadyExistsException extends  RuntimeException{
	public AudioBookAlreadyExistsException(String message){
		super(message);
	}
}

class AudioBookNotFoundException extends RuntimeException{
	public AudioBookNotFoundException(String message){
		super(message);
	}
}

class AudioBookChapterNotFoundException extends RuntimeException{
	public AudioBookChapterNotFoundException(String message){
		super (message);
	}
}
class PlayListSameNameException extends RuntimeException{
	public PlayListSameNameException (String message){
		super(message);
	}
}

class PlayListNameNotFoundException extends RuntimeException{
	public PlayListNameNotFoundException (String message){
		super(message);
	}
}

class TypeNotFoundException extends RuntimeException{
	public TypeNotFoundException (String message){
		super(message);
	}
}

class ContentNotFoundException extends RuntimeException{
	public ContentNotFoundException (String message){
		super(message);
	}
}
