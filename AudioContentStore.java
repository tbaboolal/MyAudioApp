import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
// Name: Tyler Baboolal, Student ID: 501176018
// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents;
		private Map<String, Integer> titleSearch;
		private Map<String,ArrayList<Integer>> aSearch;
		private Map<String,ArrayList<Integer>> genreSearch;
		private ArrayList<AudioContent> readFile(){

			ArrayList<AudioContent> contents = new ArrayList<AudioContent>();

			try{
				//Create scanner to interarate over file object store.txt
				Scanner scanner = new Scanner (new File("store.txt"));


				//While there's stuff in scanner interate
				while (scanner.hasNext()){

					//Manually interate through scanner collecting each data line to determine if it's a song or not based off the files format
					String text = "";
					String type = scanner.nextLine();
					String id = scanner.nextLine();
					String title = scanner.nextLine();
					int year = Integer.parseInt(scanner.nextLine());
					int length = Integer.parseInt(scanner.nextLine());
					switch (type){

						//If song create song object
						case "SONG":
							System.out.println("Loading SONG");
							String artist = scanner.nextLine();
							String composer = scanner.nextLine();
							Song.Genre genre = Song.Genre.valueOf(scanner.nextLine());
							String line = scanner.nextLine();
							for(int i =0; i<Integer.parseInt(line);i++){
								text += scanner.nextLine() +"\n";
							}
							contents.add(new Song(title, year, id, Song.TYPENAME, text, length,artist, composer, genre, text));
							break;


						//If Audiobook create audiobook object
						case "AUDIOBOOK":
							System.out.println("Loading AUDIOBOOK");
							String author = scanner.nextLine();
							String narrator = scanner.nextLine();
							int numChaps = Integer.parseInt(scanner.nextLine());
							ArrayList<String> titles = new ArrayList<String>();
							ArrayList<String> chapters = new ArrayList<String>();
							for (int i =0 ; i<numChaps; i++){
								titles.add(scanner.nextLine());
							}

							for (int i = 0; i <numChaps;i++){
								int nums = Integer.parseInt(scanner.nextLine());
								for (int x = 0; x<nums;x++){
									text+= scanner.nextLine()+"\n";
								}
								chapters.add(text);
								text ="";
							}
							AudioBook book = new AudioBook(title, year, id, AudioBook.TYPENAME,  "", length, author, narrator, titles, chapters);
							contents.add(book);
							break;
					}

				}


			}catch(FileNotFoundException err){
				System.out.println(err.getMessage());
				System.exit(1);
			}





			return contents;
		}
		
		public AudioContentStore()
		{


			//Populate contents arraylists
			contents = readFile();


			//Create new map
			titleSearch = new HashMap<String,Integer>();


			//Populate the map
			for (int i = 0; i<contents.size(); i++){
				titleSearch.put(contents.get(i).getTitle(),i);
			}


			//same idea as above
			aSearch = new HashMap<String,ArrayList<Integer>>();

			for (int i = 0; i<contents.size(); i++){
				if(contents.get(i) instanceof Song){
					Song song = (Song)contents.get(i);
					if(aSearch.containsKey(song.getArtist())){
						aSearch.get(song.getArtist()).add(i);
					}
					else{
						aSearch.put(song.getArtist(),new ArrayList<Integer>(Arrays.asList(i)));
					}
				}else{
					AudioBook book = (AudioBook)contents.get(i);
					if(aSearch.containsKey(book.getAuthor())){
						aSearch.get(book.getAuthor()).add(i);
					}
					else{
						aSearch.put(book.getAuthor(),new ArrayList<Integer>(Arrays.asList(i)));
					}
				}

			}

			genreSearch = new HashMap<String,ArrayList<Integer>>();

			for (int i = 0; i< contents.size(); i++){
				if(contents.get(i) instanceof Song){
					Song song = (Song)contents.get(i);

					if (!genreSearch.containsKey(song.getGenre().name())){
						genreSearch.put(song.getGenre().name(),new ArrayList<Integer>(Arrays.asList(i)));
					}
					else{
						genreSearch.get(song.getGenre().name()).add(i);
					}
				}
			}
		  // Create some songs audiobooks and podcasts and to store
			/*
			String file = "Yesterday, all my troubles";
			contents.add(new Song("Yesterday", 1965, "123", Song.TYPENAME, file, 2, "The Beatles", "Paul McCartney", Song.Genre.POP, file));
			
			file = "I'm sorry if I seem uninterested\r\n"
					+ "Or I'm not listenin' or I'm indifferent\r\n"
					+ "Truly, I ain't got no business here\r\n"
					+ "But since my friends are here, I just came to kick it\r\n"
					+ "But really I would rather be at home all by myself not in this room\r\n"
					+ "With people who don't even care about my well being";
			contents.add(new Song("Here", 2015, "391", Song.TYPENAME, file, 3, "Alessia Cara", "Alessia Cara", Song.Genre.POP, file));
			
			file = "Yo, Big Shaq, the one and only\r\n"
					+ "Man's not hot, never hot\r\n"
					+ "Skrrat (GottiOnEm), skidi-kat-kat\r\n"
					+ "Boom\r\n"
					+ "Two plus two is four\r\n"
					+ "Minus one that's three, quick maths\r\n"
					+ "Everyday man's on the block\r\n"
					+ "Smoke trees (Ah)";
			contents.add(new Song("Man's Not Hot", 2017, "374", Song.TYPENAME, file, 2, "Michael Dapaah", "Michael Dapaah", Song.Genre.RAP, file));
			
			file = "The world was on fire and no one could save me but you\r\n"
					+ "It's strange what desire will make foolish people do\r\n"
					+ "I never dreamed that I'd meet somebody like you\r\n"
					+ "And I never dreamed that I'd lose somebody like you";
			contents.add(new Song("Wicked Game", 1989, "185", Song.TYPENAME, file, 4, "Chris Isaak", "Chris Isaak", Song.Genre.ROCK, file));
			
			file = "The lights go out and I can't be saved\r\n"
					+ "Tides that I tried to swim against\r\n"
					+ "Have brought me down upon my knees\r\n"
					+ "Oh, I beg, I beg and plead\r\n"
					+ "Singin' come out of things un said";
			contents.add(new Song("Clocks", 2002, "875", Song.TYPENAME, file, 5, "Coldplay", "Guy Berryman, Chris Martin", Song.Genre.ROCK, file));
			
			file = "I'm waking up to ash and dust\r\n"
					+ "I wipe my brow and I sweat my rust\r\n"
					+ "I'm breathing in the chemicals";
			contents.add(new Song("Radioactive", 2012, "823", Song.TYPENAME, file, 3, "Imagine Dragons", "Josh Mosser, A. Grant, Dan Reynolds, Wayne Sermon, Ben McKee", Song.Genre.ROCK, file));
			
			file = "Birds flying high\r\n"
					+ "You know how I feel\r\n"
					+ "Sun in the sky\r\n"
					+ "You know how I feel\r\n"
					+ "Breeze driftin' on by\r\n"
					+ "You know how I feel\r\n"
					+ "It's a new dawn\r\n"
					+ "It's a new day\r\n"
					+ "It's a new life\r\n"
					+ "For me";
			contents.add(new Song("Feelin' Good", 1965, "875", Song.TYPENAME, file, 3, "Nina Simone", 
					"Anthony Newley, Leslie Bricusse",Song.Genre.JAZZ, file));
			
			file = "Find table spaces, say your social graces\n"
					+ "Bow your head, they're pious here\n"
					+ "But you and I, we're pioneers, we make our own rules\n"
					+ "Our own room, no bias here";
			contents.add(new Song("Wild Things", 2015, "443", Song.TYPENAME, file, 4, "Alessia Cara", "Alessia Cara", Song.Genre.POP, file));
			
			AudioBook book = new AudioBook("Harry Potter and the Goblet of Fire", 2015, "894", AudioBook.TYPENAME,  "", 1236,
					"J.K. Rowling", "Jim Dale", makeHPChapterTitles(), makeHPChapters());
			contents.add(book);
			
			book = new AudioBook("Moby Dick", 2018, "376", AudioBook.TYPENAME,  "", 1422,
					"Herman Melville", "Pete Cross", makeMDChapterTitles(), makeMDChapters());
			contents.add(book);
			
			book = new AudioBook("Shogun", 2018, "284", AudioBook.TYPENAME,  "", 3213,
					"James Clavel", "Ralph Lister", makeSHChapterTitles(), makeSHChapters());
			contents.add(book);
			*/
			// Create a podcast object if you are doing the bonus see the makeSeasons() method below
			// It is currently commented out. It makes use of a class Season you may want to also create
			// or change it to something else
					
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}


		//Search for the title by checking if the map contatins the key string
		public void searchTitle(String name){
			if (titleSearch.containsKey(name)){
				System.out.print(titleSearch.get(name)+1+".");
				contents.get(titleSearch.get(name)).printInfo();
			}
			else{
				System.out.println(name+" does not exists");
			}
		}


		//Same idea as the reset
		public void genreSearch(String name){
			if(genreSearch.containsKey(name)){
				for (int i: genreSearch.get(name)){
					System.out.print((i+1)+".");
					contents.get(i).printInfo();
					System.out.println();
				}
			}else{
				System.out.println(name+" does not exists");
			}
		}

		public void aSearch(String name){
			if (aSearch.containsKey(name)){
				for (int i: aSearch.get(name)){
					System.out.print((i+1)+".");
					contents.get(i).printInfo();
					System.out.println();
				}
			}
			else{
				System.out.println(name+" does not exist");
			}
		}
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		public ArrayList<Integer> getASearch(String name){
			return aSearch.get(name);
		}

		public ArrayList<Integer> getGenreSearch(String name){
			return genreSearch.get(name);
		}
		
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
