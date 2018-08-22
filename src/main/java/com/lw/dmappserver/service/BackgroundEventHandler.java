package com.lw.dmappserver.service;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;

public class BackgroundEventHandler implements IEventHandler {
    private Image image;

    public BackgroundEventHandler(Image image) {
        this.image = image;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(),
                page.getResources(), pdfDoc);
        Rectangle area = page.getPageSize();
        float x = 595 / 2;
        float y = 20;
        int i = pdfDoc.getNumberOfPages() - 1;
        new Canvas(canvas, pdfDoc, area)
                .add(image)
                .add(new Paragraph(String.format("%s", i))
                    .setFixedPosition(x - 25, y, 50)
                    .setMargins(0,0,0,0)
                );
    }




}
