import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)
//Name: Tyler Baboolal, Student ID: 501176018

public class MyAudioUI
{
	public static void main(String[] args) {
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary

			AudioContentStore store = new AudioContentStore();

			// Create my music mylibrary
			Library mylibrary = new Library();

			Scanner scanner = new Scanner(System.in);
			System.out.print(">");

			// Process keyboard actions
			while (scanner.hasNextLine()) {



					String action = scanner.nextLine();

					if (action == null || action.equals("")) {
						System.out.print("\n>");
						continue;
					} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
						return;

					else if (action.equalsIgnoreCase("STORE"))    // List all songs
					{
						store.listAll();
					} else if (action.equalsIgnoreCase("SONGS"))    // List all songs
					{
						mylibrary.listAllSongs();
					} else if (action.equalsIgnoreCase("BOOKS"))    // List all songs
					{
						mylibrary.listAllAudioBooks();
					} else if (action.equalsIgnoreCase("PODCASTS"))    // List all songs
					{
						mylibrary.listAllPodcasts();
					} else if (action.equalsIgnoreCase("ARTISTS"))    // List all songs
					{
						mylibrary.listAllArtists();
					} else if (action.equalsIgnoreCase("PLAYLISTS"))    // List all play lists
					{
						mylibrary.listAllPlaylists();
					}
					// Download audiocontent (song/audiobook/podcast) from the store
					// Specify the index of the content
					else if (action.equalsIgnoreCase("DOWNLOAD")) {
						int fromIndex = 0;
						int toIndex = 0;

						System.out.print("From Content #: ");
						if (scanner.hasNextInt()) {
							fromIndex = scanner.nextInt();
							scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						}

						System.out.print("To content #: ");
						if(scanner.hasNextInt()){
							toIndex = scanner.nextInt();
							scanner.nextLine();
						}

						for(int i = fromIndex; i<=toIndex; i++){
							AudioContent content = store.getContent(i);
							if (content == null) {
								System.out.println("Content Not Found in Store");
							}
							else {
								try{
									mylibrary.download(content);
								}
								catch(SongAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
								catch (AudioBookAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
							}
						}

					}
					// Get the *library* index (index of a song based on the songs list)
					// of a song from the keyboard and play the song
					else if (action.equalsIgnoreCase("PLAYSONG")) {
						int index = 0;
						System.out.print("Enter the song #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}
						try{
							mylibrary.playSong(index);
						}
						catch(SongNotFoundException err){
							System.out.println(err.getMessage());
						}


						// Print error message if the song doesn't exist in the library

					}
					// Print the table of contents (TOC) of an audiobook that
					// has been downloaded to the library. Get the desired book index
					// from the keyboard - the index is based on the list of books in the library
					else if (action.equalsIgnoreCase("BOOKTOC")) {
						int index = 0;
						System.out.print("Enter the Audiobook #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}

						try{
							mylibrary.printAudioBookTOC(index);
						}
						catch(AudioBookNotFoundException err){
							System.out.println(err.getMessage());
						}
						// Print error message if the book doesn't exist in the library
					}
					// Similar to playsong above except for audiobook
					// In addition to the book index, read the chapter
					// number from the keyboard - see class Library
					else if (action.equalsIgnoreCase("PLAYBOOK")) {
						int index = 0;
						int chapter = 0;
						System.out.print("Enter the audiobook #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}
						System.out.print("Enter the chapter #: ");

						if (scanner.hasNext()) {
							chapter = scanner.nextInt();
							scanner.nextLine();
						}

						try{
							mylibrary.playAudioBook(index, chapter);
						}catch(AudioBookNotFoundException err){
							System.out.println(err.getMessage());
						}catch(AudioBookChapterNotFoundException err){
							System.out.println(err.getMessage());
						}
					}
					// Print the episode titles for the given season of the given podcast
					// In addition to the podcast index from the list of podcasts,
					// read the season number from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PODTOC")) {

					}
					// Similar to playsong above except for podcast
					// In addition to the podcast index from the list of podcasts,
					// read the season number and the episode number from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PLAYPOD")) {

					}
					// Specify a playlist title (string)
					// Play all the audio content (songs, audiobooks, podcasts) of the playlist
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PLAYALLPL")) {
						String name = "";

						System.out.print("Enter playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}

						try{
							mylibrary.playPlaylist(name);
						}
						catch(PlayListNameNotFoundException err){
							System.out.println(err.getMessage());
						}
					}
					// Specify a playlist title (string)
					// Read the index of a song/audiobook/podcast in the playist from the keyboard
					// Play all the audio content
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PLAYPL")) {
						String name = "";
						int index = 0;

						System.out.print("Enter a playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}


						System.out.print("Enter the song/audiobook #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}


						try{
							mylibrary.playPlaylist(name, index);
						}catch(ContentNotFoundException err){
							System.out.println(err.getMessage());
						}catch (PlayListNameNotFoundException err){
							System.out.println(err.getMessage());
						}
					}
					// Delete a song from the list of songs in mylibrary and any play lists it belongs to
					// Read a song index from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("DELSONG")) {
						int index = 0;
						System.out.print("Enter the song #: ");

						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}

						try{
							mylibrary.deleteSong(index);
						}
						catch(SongNotFoundException err){
							System.out.println(err.getMessage());
						}
					}
					// Read a title string from the keyboard and make a playlist
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("MAKEPL")) {
						String name = "";
						System.out.print("Enter playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}

						try{
							mylibrary.makePlaylist(name);
						}catch (PlayListSameNameException err){
							System.out.println(err.getMessage());
						}



					}
					// Print the content information (songs, audiobooks, podcasts) in the playlist
					// Read a playlist title string from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("PRINTPL"))    // print playlist content
					{
						String name = "";

						System.out.print("Enter playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}

						try{
							mylibrary.printPlaylist(name);
						}
						catch (PlayListNameNotFoundException err){
							System.out.println(err.getMessage());
						}

					}

					// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
					// Read the playlist title, the type of content ("song" "audiobook" "podcast")
					// and the index of the content (based on song list, audiobook list etc) from the keyboard
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("ADDTOPL")) {
						String name = "";
						String type = "";
						int index = 0;

						System.out.print("Enter playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}

						System.out.print("Content Type [SONG, AUDIOBOOK]: ");
						if (scanner.hasNext()) {
							type = scanner.next();
							scanner.nextLine();
						}

						System.out.print("Library Content #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}

						try {
							mylibrary.addContentToPlaylist(type, index, name);
						} catch (PlayListNameNotFoundException err) {
							System.out.println(err.getMessage());
						} catch (SongNotFoundException err) {
							System.out.println(err.getMessage());
						} catch (AudioBookNotFoundException err){
							System.out.println(err.getMessage());
						} catch (TypeNotFoundException err){
							System.out.println(err.getMessage());
						}
					}
					// Delete content from play list based on index from the playlist
					// Read the playlist title string and the playlist index
					// see class Library for the method to call
					else if (action.equalsIgnoreCase("DELFROMPL")) {
						String name = "";
						int index = 0;

						System.out.print("Enter playlist name: ");
						if (scanner.hasNext()) {
							name = scanner.next();
							scanner.nextLine();
						}

						System.out.print("Playlist Content #: ");
						if (scanner.hasNext()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}

						try{
							mylibrary.delContentFromPlaylist(index, name);
						}
						catch(PlayListNameNotFoundException err){
							System.out.println(err.getMessage());
						}
						catch (ContentNotFoundException err){
							System.out.println(err.getMessage());
						}
					} else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
					{
						mylibrary.sortSongsByYear();
					} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
					{
						mylibrary.sortSongsByName();
					} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
					{
						mylibrary.sortSongsByLength();
					} else if (action.equalsIgnoreCase("SearchA")){
						String name = "";

						System.out.print("Enter the artist/author: ");
						if(scanner.hasNext()){
							name = scanner.nextLine();
							//scanner.nextLine();
						}
						store.aSearch(name);
					}else if (action.equalsIgnoreCase("Search")){
						String name = "";

						System.out.print("Enter the title: ");
						if(scanner.hasNext()){
							name = scanner.nextLine();
							//scanner.nextLine();
						}
						store.searchTitle(name);
					}else if (action.equalsIgnoreCase("SearchG")){
						String name = "";

						System.out.print("Enter the genre name [POP, ROCK, JAZZ, HIPHOP,RAP,CLASSICAL]: ");

						if (scanner.hasNext()){
							name = scanner.next();
							scanner.nextLine();
						}

						store.genreSearch(name);
					}
					else if (action.equalsIgnoreCase("DownloadA")) {
						String name = "";

						System.out.print("Enter the artist name: ");

						if (scanner.hasNext()) {
							name = scanner.nextLine();
						}

						ArrayList<Integer> artitsts = store.getASearch(name);

						if (artitsts == null) {
							System.out.println(name + " not in store!");
						}
						else {
							for (int i : artitsts) {
								AudioContent content = store.getContent(i+1);
								try{
									mylibrary.download(content);
								}
								catch(SongAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
								catch (AudioBookAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
							}
						}
					}
					else if (action.equalsIgnoreCase("DownloadG")){
						String name = "";
							System.out.print("Enter genre name: ");
							if(scanner.hasNext()){
								name = scanner.next();
							}
						ArrayList<Integer> genre = store.getGenreSearch(name);

						if (genre == null) {
							System.out.println(name + " not in store!");
						}
						else {
							for (int i : genre) {
								AudioContent content = store.getContent(i+1);
								try{
									mylibrary.download(content);
								}
								catch(SongAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
								catch (AudioBookAlreadyExistsException err){
									System.out.println(err.getMessage());
								}
							}
						}

					}
					System.out.print("\n>");
			}
	}
}
