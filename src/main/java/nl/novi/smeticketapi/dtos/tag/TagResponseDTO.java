package nl.novi.smeticketapi.dtos.tag;

public class TagResponseDTO {
    private Long id;
    private String name;
    private String colorHex;

    //Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getColorHex(){return colorHex;}
    public void setColorHex(String colorHex){this.colorHex = colorHex;}
}