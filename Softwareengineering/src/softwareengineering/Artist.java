package softwareengineering;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Artist {
    private String ID;
    private String Name;
    private String Address;
    private String Birthdate;
    private String Bio;
    private List<String> Occupations;
    private List<String> Genres;
    private List<String> Awards;

    public Artist(String id, String name, String address, String birthdate, String bio,
                  List<String> occupations, List<String> genres, List<String> awards) {
        ID = id;
        Name = name;
        Address = address;
        Birthdate = birthdate;
        Bio = bio;
        Occupations = occupations;
        Genres = genres;
        Awards = awards;
    }

    public void Artist(String id2, String name2, String address2, String birthdate2, String bio2, List<String> occupations2,
			List<String> genres2, List<String> awards2) {
		// TODO Auto-generated constructor stub
	}

	public boolean addArtist() {
        String artistString = String.format("%s|%s|%s|%s|%s|%s|%s|%s%n",
                ID, Name, Address, Birthdate, Bio, String.join(",", Occupations), String.join(",", Genres), String.join(",", Awards));

        try (FileWriter writer = new FileWriter("artists.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(artistString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean updateArtist() {
        if (isValidArtistID(ID) && isValidBirthdate(Birthdate) && isValidAwardsForUpdate(Awards)) {
            List<Artist> existingArtists = readArtistsFromFile();

            boolean artistFound = false;

            for (int i = 0; i < existingArtists.size(); i++) {
                Artist currentArtist = existingArtists.get(i);
                if (currentArtist.ID.equals(this.ID)) {
                    // Update the artist's information
                    currentArtist.Name = this.Name;
                    currentArtist.Address = this.Address;
                    currentArtist.Birthdate = this.Birthdate;
                    currentArtist.Bio = this.Bio;
                    currentArtist.Occupations = new ArrayList<>(this.Occupations);
                    currentArtist.Genres = new ArrayList<>(this.Genres);
                    currentArtist.Awards = new ArrayList<>(this.Awards);
                    artistFound = true;
                    break;
                }
            }

            if (!artistFound) {
                System.out.println("Artist with ID " + this.ID + " not found for update.");
                return false;
            }

            // Write the updated list back to the file
            writeArtistsToFile(existingArtists);
            System.out.println("Artist successfully added to Artists.txt.");
            return true;
        }
        return false;
    }

    private void writeArtistsToFile(List<Artist> existingArtists) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("Artists.txt"))) ) {
            for (Artist artist : existingArtists) {
                String artistString = String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                        artist.ID, artist.Name, artist.Address, artist.Birthdate, artist.Bio,
                        String.join(",", artist.Occupations), String.join(",", artist.Genres), String.join(",", artist.Awards));
                writer.println(artistString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Artist> readArtistsFromFile() {
        List<Artist> artists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Artists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    List<String> occupations = Arrays.asList(parts[5].split(","));
                    List<String> genres = Arrays.asList(parts[6].split(","));
                    List<String> awards = Arrays.asList(parts[7].split(","));
                    Artist artist = new Artist(parts[0], parts[1], parts[2], parts[3], parts[4], occupations, genres, awards);
                    artists.add(artist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artists;
    }



    private boolean isValidArtistID(String id) {
        return Pattern.matches("[5-9]{3}[A-Z]{5}[!@#$%^&*]{2}", id);
    }

    private boolean isValidBirthdate(String birthdate) {
        return Pattern.matches("\\d{2}-\\d{2}-\\d{4}", birthdate);
    }

    private boolean isValidAwardsForUpdate(List<String> awards) {
        for (String award : awards) {
            String[] parts = award.split(",\\s");
            if (parts.length != 2) {
                return false;
            }
            String year = parts[0];
            if (!Pattern.matches("\\d{4}", year)) {
                return false;
            }
            String title = parts[1];
            if (title.length() < 4 || title.length() > 10) {
                return false;
            }
        }
        return awards.size() <= 3;
    }
}