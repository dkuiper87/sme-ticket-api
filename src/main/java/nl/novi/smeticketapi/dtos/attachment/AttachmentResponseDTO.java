package nl.novi.smeticketapi.dtos.attachment;

public class AttachmentResponseDTO {
    private Long id;
    private String fileName;
    private String contentType;

    //Getters and setters

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getContentType() {return contentType;}
    public void setContentType(String contentType) {this.contentType = contentType;}
}
