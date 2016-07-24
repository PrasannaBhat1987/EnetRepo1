package com.pras.pdf;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pras.constant.Constants;
import com.pras.model.LineItem;
import com.pras.model.Rao;

public class PdfUtil {
	private static String FILE = "D:/PositionPdf.pdf";
	private static String FILE_PATH = "D:/temp/";

	public static void createPdf(Rao rao) {
		try {
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH + rao.getId() + ".pdf"));
		      document.open();

		      Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD|Font.UNDERLINE);
		      
		      PdfPTable table = new PdfPTable(1);
		      
		      Image logo = Image.getInstance(PdfUtil.class.getClassLoader().getResource("logo.jpg"));
		      logo.setAlignment(Image.MIDDLE);
		      logo.scaleAbsoluteHeight(50);
		      logo.scaleAbsoluteWidth(50);
		      document.add(logo);
		      
		      table = new PdfPTable(1);
		      table.setWidthPercentage(100);
		      table.addCell(getCell("ENET Order RAO\n\n", PdfPCell.ALIGN_CENTER, boldFont, false, 0));
		      document.add(table);
		      
		      table = new PdfPTable(3);
		      table.setWidthPercentage(100);
		      table.addCell(getCell("ORDER DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
		      table.addCell(getCell("",1, null, false, 0));
		      table.addCell(getCell("REP DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
		      
		      PdfPCell cellOne = new PdfPCell(addOrderDetails(rao));
		      cellOne.setBorder(PdfPCell.NO_BORDER);
		      table.addCell(cellOne);
		      
		      table.addCell(getCell("", PdfPCell.ALIGN_CENTER, null, false, 0));
		      
		      cellOne = new PdfPCell(addRepDetails(rao));
		      cellOne.setBorder(PdfPCell.NO_BORDER);
		      table.addCell(cellOne);
		      
		      document.add(table);
		      
		      table = new PdfPTable(1);
		      table.setWidthPercentage(100);
		      table.addCell(getCell(Constants.invoiceText,PdfPCell.ALIGN_JUSTIFIED, null, false, 0));
		      document.add(table);
		      //Line Items
		      table = new PdfPTable(4);
		      table.setWidthPercentage(100);
		      table.addCell(getCell("PRODUCT",PdfPCell.ALIGN_CENTER, null, true, 10));
		      table.addCell(getCell("PRICE",PdfPCell.ALIGN_CENTER, null, true, 10));
		      table.addCell(getCell("QUANTITY",PdfPCell.ALIGN_CENTER, null, true, 10));
		      table.addCell(getCell("TOTAL",PdfPCell.ALIGN_CENTER, null, true, 10));
		      
		      List<LineItem> items = rao.getItems();
		      String itemDet = null;
		      int totalQty = 0;
		      float total = 0;
		      float itemTotal = 0;
		      for(LineItem item : items) {
		    	  itemDet = item.getItem();
		    	  if (item.getItemDescription() != null) {
		    		  itemDet = itemDet + "\n" + item.getItemDescription();
		    	  }
		    	  itemTotal = item.getQuantity() * Float.parseFloat(item.getUnitPrice());
		    	  table.addCell(getCell(itemDet,PdfPCell.ALIGN_CENTER, null, true, 10));
			      table.addCell(getCell(item.getUnitPrice(),PdfPCell.ALIGN_CENTER, null, true, 10));
			      table.addCell(getCell(item.getQuantity()+"",PdfPCell.ALIGN_CENTER, null, true, 10));
			      table.addCell(getCell(itemTotal + "",PdfPCell.ALIGN_CENTER, null, true, 10));
			      totalQty+=item.getQuantity();
			      total+=itemTotal;
		      }
		      
		      PdfPCell cell = getCell("ENET Booking Charge",PdfPCell.ALIGN_CENTER, null, true, 10);
		      cell.setColspan(3);
		      table.addCell(cell);
		      table.addCell(getCell(Constants.BOOKING_CHARGE_PER_ITEM * totalQty +"",PdfPCell.ALIGN_CENTER, null, true, 10));
		      
		      cell = getCell("Delivery Charges",PdfPCell.ALIGN_CENTER, null, true, 10);
		      cell.setColspan(3);
		      table.addCell(cell);
		      table.addCell(getCell(rao.getDeliveryCharge() +"",PdfPCell.ALIGN_CENTER, null, true, 10));
		      
		      cell = getCell("Total Amount",PdfPCell.ALIGN_CENTER, null, true, 10);
		      cell.setColspan(3);
		      table.addCell(cell);
		      table.addCell(getCell(rao.getTotal() + "",PdfPCell.ALIGN_CENTER, null, true, 10));
		      
		      document.add(table);
		      
		    //Customer & shipping details
		      table = new PdfPTable(2);
		      table.setWidthPercentage(100);
		      table.addCell(getCell("\n\nCUSTOMER\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
		      //table.addCell(getCell("",1, null, false, 0));
		      table.addCell(getCell("\n\nSHIPPING\n\n",PdfPCell.ALIGN_RIGHT, boldFont, false, 0));
		      
		      cellOne = new PdfPCell(addCustomerDetails(rao));
		      cellOne.setBorder(PdfPCell.NO_BORDER);
		      cellOne.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		      table.addCell(cellOne);
		      
		      //table.addCell(getCell("", PdfPCell.ALIGN_CENTER, null, false, 0));
		      
		      table.addCell(getCell(rao.getDeliveryAddress() + "\n",PdfPCell.ALIGN_RIGHT, null, false, 0));
		      
		      document.add(table);
		      
		      document.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	  public static void main(String[] args) {
	    try {
	      Document document = new Document();
	      PdfWriter.getInstance(document, new FileOutputStream(FILE));
	      document.open();
//	      // Left
//	      Paragraph paragraph = new Paragraph("This is right aligned text");
//	      paragraph.setAlignment(Element.ALIGN_RIGHT);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("This is right aligned text");
//	      paragraph.setAlignment(Element.ALIGN_RIGHT);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("This is right aligned text");
//	      paragraph.setAlignment(Element.ALIGN_RIGHT);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("This is right aligned text");
//	      paragraph.setAlignment(Element.ALIGN_RIGHT);
//	      document.add(paragraph);
//	      // Centered
//	      paragraph = new Paragraph("This is centered text");
//	      paragraph.setAlignment(Element.ALIGN_CENTER);
//	      document.add(paragraph);
//	      // Left
//	      paragraph = new Paragraph("This is left aligned text");
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("This is left aligned text");
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("This is left aligned text");
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      document.add(paragraph);
//	      
//	      
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      Font marFont = FontFactory.getFont("arial unicode ms",BaseFont.IDENTITY_H,true);
//	      paragraph = new Paragraph("ನೀವು ಹೇಗಿದ್ದೀರಿ", marFont);
//	      document.add(paragraph);
//	      paragraph = new Paragraph("ನೀವು ಹೇಗಿದ್ದೀರಿ");
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      document.add(paragraph);
//	      
//	      // Left with indentation
//	      paragraph = new Paragraph("This is left aligned text with indentation");
//	      paragraph.setAlignment(Element.ALIGN_LEFT);
//	      paragraph.setIndentationLeft(50);
//	      document.add(paragraph);

	      Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD|Font.UNDERLINE);
	      
	      Paragraph preface = new Paragraph("ENET order RAO", boldFont); 
	      preface.setAlignment(Element.ALIGN_CENTER);
	      
	      PdfPTable table = new PdfPTable(3);
	      table.setWidthPercentage(100);
	      table.addCell(getCell("ORDER DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
	      table.addCell(getCell("",1, null, false, 0));
	      table.addCell(getCell("REP DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
	      
	      PdfPCell cellOne = new PdfPCell(addOrderDetails(null));
	      cellOne.setBorder(PdfPCell.NO_BORDER);
	      table.addCell(cellOne);
	      
	      table.addCell(getCell("", PdfPCell.ALIGN_CENTER, null, false, 0));
	      
	      cellOne = new PdfPCell(addRepDetails(null));
	      cellOne.setBorder(PdfPCell.NO_BORDER);
	      table.addCell(cellOne);
	      
	      document.add(table);
	      
	      table = new PdfPTable(1);
	      table.setWidthPercentage(100);
	      table.addCell(getCell(Constants.invoiceText,PdfPCell.ALIGN_JUSTIFIED, null, false, 0));
	      document.add(table);
	      //Line Items
	      table = new PdfPTable(4);
	      table.setWidthPercentage(100);
	      table.addCell(getCell("PRODUCT",PdfPCell.ALIGN_CENTER, null, true, 0));
	      table.addCell(getCell("PRICE",PdfPCell.ALIGN_CENTER, null, true, 0));
	      table.addCell(getCell("QUANTITY",PdfPCell.ALIGN_CENTER, null, true, 0));
	      table.addCell(getCell("TOTAL",PdfPCell.ALIGN_CENTER, null, true, 0));
	      
	      PdfPCell cell = getCell("ENET Service Charge",PdfPCell.ALIGN_CENTER, null, true, 0);
	      cell.setColspan(3);
	      table.addCell(cell);
	      table.addCell(getCell("200",PdfPCell.ALIGN_CENTER, null, true, 0));
	      document.add(table);
	      
	      //Customer & shipping details
	      
	      table = new PdfPTable(3);
	      table.setWidthPercentage(100);
	      table.addCell(getCell("CUSTOMER DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
	      table.addCell(getCell("",1, null, false, 0));
	      table.addCell(getCell("SHIPPING DETAILS\n\n",PdfPCell.ALIGN_LEFT, boldFont, false, 0));
	      
	      cellOne = new PdfPCell(addCustomerDetails(null));
	      cellOne.setBorder(PdfPCell.NO_BORDER);
	      table.addCell(cellOne);
	      
	      table.addCell(getCell("", PdfPCell.ALIGN_CENTER, null, false, 0));
	      
	      table.addCell(getCell("vaaaalue\nvalue\nvaaaalue\nvalue\n",PdfPCell.ALIGN_LEFT, null, false, 0));
	      
	      document.add(table);
	      
	      document.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public static PdfPTable addOrderDetails(Rao rao) {
		  PdfPTable table = new PdfPTable(2);
		  
		  String DateToStr = DateFormat.getDateInstance().format(rao.getOrderDate());
		          System.out.println(DateToStr);
		  table.addCell(getCell("Order Date:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(DateToStr + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Website:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getWebsite().getName() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Order #:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getOrderNumber() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("RAO #:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getId() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Status:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getStatus() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  return table;
	  }
	  
	  public static PdfPTable addRepDetails(Rao rao) {
	  	PdfPTable table = new PdfPTable(2);
		  
		  table.addCell(getCell("ID:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getUser().getId() +  "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Name:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getUser().getName() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Email:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getUser().getEmail() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  table.addCell(getCell("Contact:\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  table.addCell(getCell(rao.getUser().getContact() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
		  
		  return table;
	  }
	  
	  public static PdfPTable addCustomerDetails(Rao rao) throws DocumentException {
		  	PdfPTable table = new PdfPTable(2);
			  
			  table.addCell(getCell("ID\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  table.addCell(getCell(rao.getCustomer().getId() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  
			  table.addCell(getCell("Name\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  table.addCell(getCell(rao.getCustomer().getName() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  
			  table.addCell(getCell("Email\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  table.addCell(getCell(rao.getCustomer().getEmail() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  
			  table.addCell(getCell("Contact\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  table.addCell(getCell(rao.getCustomer().getContact() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  
			  table.addCell(getCell("Address\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  table.addCell(getCell(rao.getCustomer().getAddress() + "\n",PdfPCell.ALIGN_LEFT, null, false, 0));
			  
			  float[] columnWidths = new float[] {10f, 20f};
	          table.setWidths(columnWidths);
			  return table;
		  }
	  
	  
	  public static PdfPCell getCell(String text, int alignment, Font font, boolean border, int padding) {
		  PdfPCell cell = null;
		  if(font !=null) {
			  cell = new PdfPCell(new Phrase(text, font));
		  } else {
			  cell = new PdfPCell(new Phrase(text));
		  }
		    
		    cell.setPadding(0);
		    cell.setHorizontalAlignment(alignment);
		    if(!border) {
		    	cell.setBorder(PdfPCell.NO_BORDER);
		    }
		    
		    cell.setPadding(padding);
		    return cell;
		}

}