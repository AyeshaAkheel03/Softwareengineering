package softwareengineering;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> occupations = new ArrayList<>();
        occupations.add("Singer");
        occupations.add("Songwriter");

        List<String> genres = new ArrayList<>();
        genres.add("Jazz");
        genres.add("Blues");

        List<String> awards = new ArrayList<>();
        awards.add("2025, Best Jazz Performance");

        Artist artistToAdd = new Artist("898AAARR_%", "Lisa", "New York|NY|USA", "29-01-1990",
                "This is a valid bio with more than 10 words.", occupations, genres, awards);

        if (artistToAdd.addArtist()) {
            System.out.println("Artist successfully added to Artists.txt.");
        } else {
            System.out.println("Sorry validation error, Artist was not added.");
            return;
        }

        List<String> newOccupations = new ArrayList<>();
        newOccupations.add("Songwriter");

        Artist artistToUpdate = new Artist("789MMMRR_%", "Updated Name", "Updated Address", "01-01-1990",
                "Updated bio", newOccupations, genres, awards);

        if (artistToUpdate.updateArtist()) {
            System.out.println("Artist updated successfully.");
        } else {
            System.out.println("Failed to update the artist.");
        }
    }
}