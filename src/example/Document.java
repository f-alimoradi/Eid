package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content;
    private Date CreationDate;
    private Date LastModificationDate;
    public static final int DOCUMENT_ENTITY_CODE = 10;

    public Document(String content) {
        this.content = content;
    }
    @Override
    public Document copy() {
        Document copyDocument = new Document(content);
        copyDocument.id = id;
        Date copyCreationDate = new Date(CreationDate.getTime());
        Date copyLastModificationDate = new Date(LastModificationDate.getTime());
        copyDocument.CreationDate = copyCreationDate;
        copyDocument.LastModificationDate = copyLastModificationDate;

        return copyDocument;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        CreationDate = date;
    }
    @Override
    public Date getCreationDate() {
        return CreationDate;
    }
    @Override
    public void setLastModificationDate(Date date) {
        LastModificationDate = date;
    }
    @Override
    public Date getLastModificationDate() {
        return LastModificationDate;
    }
}

