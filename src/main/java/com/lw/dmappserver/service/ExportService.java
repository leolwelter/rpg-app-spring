package com.lw.dmappserver.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.lw.dmappserver.monster.Feature;
import com.lw.dmappserver.monster.Monster;
import com.lw.dmappserver.spell.Spell;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Map;


public class ExportService {
    private ByteArrayOutputStream outputStream;
    private PdfWriter writer;
    private static String logoPath = "_assets/favicon.ico";
    private static String scrollPath = "_assets/scroll-A4.png";
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

    public byte[] exportToPdf(Spell spell) throws Exception {
        // document properties
        outputStream.flush();
        writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // document contents
        writeSpellProperties(document, spell);

        document.close();
        return outputStream.toByteArray();
    }

    public byte[] exportMonstersToPdf(LinkedList<Monster> monsterList) throws Exception {
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

        document.getPdfDocument().removePage(document.getPdfDocument().getLastPage());
        document.close();
        return outputStream.toByteArray();
    }

    public byte[] exportSpellsToPdf(LinkedList<Spell> spellList) throws Exception {
        if (spellList.size() == 0)
            return null;

        // document properties
        outputStream.flush();
        writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // document contents
        for (Spell m: spellList) {
            writeSpellProperties(document, m);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

        document.getPdfDocument().removePage(document.getPdfDocument().getLastPage());
        document.close();
        return outputStream.toByteArray();
    }

    private Document writeMonsterProperties(Document document, Monster monster) throws MalformedURLException, FileNotFoundException {
        // image resources
        File logoFile = ResourceUtils.getFile("classpath:" + logoPath);
        File scrollFile = ResourceUtils.getFile("classpath:" + scrollPath);

        Image logoImage = new Image(ImageDataFactory.create(logoFile.getPath())).setWidth(16);

        // add event handler to PdfDocument that adds the background image and page number every time a page is finalized
        Image backgroundImage = new Image(ImageDataFactory.create(scrollFile.getPath()))
                .scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight())
                .setFixedPosition(0,0);
        BackgroundEventHandler handler = new BackgroundEventHandler(backgroundImage);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, handler);

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
        if (monster.getFeatures().length != 0) {
            features.add(new Text("Features\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
            for (Feature feature : monster.getFeatures()) {
                features
                        .add(new Text(feature.name).setBold())
                        .add(new Tab())
                        .add(new Text("\n"))
                        .add(new Text(feature.description).setTextAlignment(TextAlignment.RIGHT))
                        .add(new Text("\n"));
            }
        }

        // actions
        Paragraph actions = new Paragraph();
        if (monster.getActions().length != 0) {
            actions.add(new Text("Actions\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
            for (Feature act: monster.getActions()) {
                actions
                        .add(new Text(act.name).setBold())
                        .add(new Tab())
                        .add(new Text("\n"))
                        .add(new Text(act.description).setTextAlignment(TextAlignment.RIGHT))
                        .add(new Text("\n"));
            }
        }

        // *legendary* actions
        Paragraph legendaryActions = new Paragraph();
        if (monster.getLegendaryActions().length != 0) {
            legendaryActions.add(new Text("Legendary Actions\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
            for (Feature lAct: monster.getLegendaryActions()) {
                legendaryActions
                        .add(new Text(lAct.name).setBold())
                        .add(new Tab())
                        .add(new Text("\n"))
                        .add(new Text(lAct.description).setTextAlignment(TextAlignment.RIGHT))
                        .add(new Text("\n"));
            }
        }

        // construct document from component parts
        document.add(logoImage)
                .add(baseStats)
                .add(features)
                .add(actions)
                .add(legendaryActions)
                .add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        return document;
    }

    private Document writeSpellProperties(Document document, Spell spell) throws MalformedURLException, FileNotFoundException {
        // image resources
        File logoFile = ResourceUtils.getFile("classpath:" + logoPath);
        File scrollFile = ResourceUtils.getFile("classpath:" + scrollPath);

        Image logoImage = new Image(ImageDataFactory.create(logoFile.getPath())).setWidth(16);

        // add event handler to PdfDocument that adds the background image and page number every time a page is finalized
        Image backgroundImage = new Image(ImageDataFactory.create(scrollFile.getPath()))
                .scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight())
                .setFixedPosition(0,0);
        BackgroundEventHandler handler = new BackgroundEventHandler(backgroundImage);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, handler);

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

        // Spell properties
        Paragraph spellProperties = new Paragraph();
        spellProperties.add(new Text("Properties\n").setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
        for (Map.Entry<String, String> s : spell.getPropertyMap().entrySet()) {
            spellProperties
                    .add(new Text(s.getKey()).setBold())
                    .add(new Tab())
                    .add(new Text("\n"))
                    .add(new Text(s.getValue()).setTextAlignment(TextAlignment.RIGHT))
                    .add(new Text("\n"));
        }

        // construct document from component parts
        document.add(logoImage)
                .add(spellProperties)
                .add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        return document;
    }
}
