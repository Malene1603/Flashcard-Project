package com.example.flashcardproject;

public class Flashcard {

    // Alle de atributter et flashcard har.
    private String CardID;
    private String Category;
    private String Question;
    private String Artwork;
    private String Artist;
    private String Title;
    private String Subtitle;
    private String Date;
    private String Period;
    private String Medium;
    private String Nationality;
    private String Note;
    private String Tags;

    // Konstruktør, der initialiserer alle attributterne ved oprettelse af et flashcard-objekt.
    public Flashcard(String cardID, String category, String question, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags) {
        this.CardID = cardID;
        this.Category = category;
        this.Question = question;
        this.Artwork = artwork;
        this.Artist = artist;
        this.Title = title;
        this.Subtitle = subtitle;
        this.Date = date;
        this.Period = period;
        this.Medium = medium;
        this.Nationality = nationality;
        this.Note = note;
        this.Tags = tags;
    }

    // Metoder til at hente værdierne af attributterne.
    public String getCardID() {
        return CardID;
    }

    public String getCategory() {
        return Category;
    }

    public String getQuestion() {
        return Question;
    }

    public String getArtwork() {
        return Artwork;
    }

    public String getArtist() {
        return Artist;
    }

    public String getTitle() {
        return Title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public String getDate() {
        return Date;
    }

    public String getPeriod() {
        return Period;
    }

    public String getMedium() {
        return Medium;
    }

    public String getNationality() {
        return Nationality;
    }

    public String getNote() {
        return Note;
    }

    public String getTags() {
        return Tags;
    }


    // Metoder til at sætte værdierne af attributterne.
    public void setCardID(String cardID) {
        CardID = cardID;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void setArtwork(String artwork) {
        Artwork = artwork;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public void setMedium(String medium) {
        Medium = medium;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setTags(String tags) {
        Tags = tags;
    }
}
