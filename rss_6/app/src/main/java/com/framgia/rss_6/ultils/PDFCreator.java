package com.framgia.rss_6.ultils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFCreator {
    private static Image mImageParagraph;

    public boolean write(String title, String publishedDate, String image, String description,
                         String
                             Author) {
        try {
            String filepath = Constant.SD_PATH + title + Constant.PDF_EXTENSION;
            File file = new File(filepath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Document document = new Document();
            PdfWriter.getInstance(document,
                new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            Paragraph titleParagraph = new Paragraph();
            Paragraph dateParagraph = new Paragraph();
            Paragraph descriptionParagraph = new Paragraph();
            Paragraph authorParagraph = new Paragraph();
            titleParagraph.add(title);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            dateParagraph.add(publishedDate);
            dateParagraph.setAlignment(Element.ALIGN_CENTER);
            descriptionParagraph.add(description);
            descriptionParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
            authorParagraph.add(Author);
            authorParagraph.setAlignment(Element.ALIGN_CENTER);
            mImageParagraph = Image.getInstance(image);
            mImageParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titleParagraph));
            document.add(new Paragraph(dateParagraph));
            document.add(mImageParagraph);
            document.add(new Paragraph(descriptionParagraph));
            document.add(new Paragraph(authorParagraph));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return true;
    }
}