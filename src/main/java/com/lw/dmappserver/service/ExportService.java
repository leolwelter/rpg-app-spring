package com.lw.dmappserver.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.lw.dmappserver.monster.Feature;
import com.lw.dmappserver.monster.Monster;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Map;


public class ExportService {
    private ByteArrayOutputStream outputStream;
    private PdfWriter writer;
    private static String logoPath = "/home/leo/work/dm-app-server/src/main/resources/static/favicon.ico";

    public ExportService() {
        outputStream = new ByteArrayOutputStream();
    }

    public byte[] exportToPdf(Monster monster) throws Exception {
        // document properties
        outputStream.flush();
        writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // document contents
        writeMonsterProperties(document, monster);


        document.close();
        return outputStream.toByteArray();
    }

    public byte[] exportToPdf(LinkedList<Monster> monsterList) throws Exception {
        if (monsterList.size() == 0)
            return null;


        // document properties
        outputStream.flush();
        writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // document contents
        for (Monster m: monsterList) {
            writeMonsterProperties(document, m);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        document.getPdfDocument().removePage(document.getPdfDocument().getNumberOfPages());
        document.close();
        return outputStream.toByteArray();
    }

    private Document writeMonsterProperties(Document document, Monster monster) throws MalformedURLException {
        // logo
        Image image = new Image(ImageDataFactory.create(logoPath)).setWidth(16);

        // set column configurations
        float offSet = 36;
        float gutter = 23;
        int nColumns = 2;
        float columnWidth = (PageSize.A4.getWidth() - offSet * nColumns) / nColumns - gutter;
        float columnHeight = PageSize.A4.getHeight() - offSet * nColumns;
        Rectangle[] columns = {
                new Rectangle(offSet, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth + gutter, offSet, columnWidth, columnHeight)
        };
        document.setRenderer(new ColumnDocumentRenderer(document, columns));
        document.setTextAlignment(TextAlignment.JUSTIFIED);

        // base stats
        Paragraph baseStats = new Paragraph();
        baseStats.add(new Text("Base Stats\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
        for (Map.Entry<String, String> s : monster.getBasicStats().entrySet()) {
            baseStats
                .add(new Text(s.getKey()).setBold())
                .add(new Tab())
                .add(new Text("\n"))
                .add(new Text(s.getValue()).setTextAlignment(TextAlignment.RIGHT))
                .add(new Text("\n"));
        }

        // features
        Paragraph features = new Paragraph();
        features.add(new Text("Features\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
        for (Feature feature : monster.getFeatures()) {
            features
                .add(new Text(feature.name).setBold())
                .add(new Tab())
                .add(new Text("\n"))
                .add(new Text(feature.description).setTextAlignment(TextAlignment.RIGHT))
                .add(new Text("\n"));
        }

        // actions
        Paragraph actions = new Paragraph();
        actions.add(new Text("Actions\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
        for (Feature act: monster.getActions()) {
            actions
                    .add(new Text(act.name).setBold())
                    .add(new Tab())
                    .add(new Text("\n"))
                    .add(new Text(act.description).setTextAlignment(TextAlignment.RIGHT))
                    .add(new Text("\n"));
        }



        // construct document from component parts
        document.add(image)
                .add(baseStats)
                .add(features)
                .add(actions);


        // write page number to current page
        writePageNumber(document).add(new AreaBreak(AreaBreakType.NEXT_PAGE));


        return document;
    }

    private Document writePageNumber(Document document) {
        float x = 595 / 2;
        float y = 20;
        int i = document.getPdfDocument().getNumberOfPages();
        document.add(new Paragraph(String.format("%s", i))
                .setFixedPosition(x, y, 50)
                .setMargins(0,0,0,0)
        );

        return document;
    }

}
