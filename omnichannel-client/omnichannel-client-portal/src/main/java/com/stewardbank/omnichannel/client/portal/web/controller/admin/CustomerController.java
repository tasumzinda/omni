/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stewardbank.omnichannel.client.portal.web.controller.admin;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.service.CustomerService;
import com.stewardbank.omnichannel.business.util.dto.ItemDeleteDTO;
import com.stewardbank.omnichannel.client.portal.util.AppMessage;
import com.stewardbank.omnichannel.client.portal.util.MessageType;
//import com.stewardbank.omnichannel.client.portal.util.TableEvent;
import com.stewardbank.omnichannel.client.portal.web.controller.BaseController;
import com.stewardbank.omnichannel.client.portal.web.validator.CustomerValidator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Tasu Muzinda
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;
    @Resource
    private CustomerValidator validator;
    private static final String UPLOAD_DIRECTORY = "/upload";

    private String setUpModel(ModelMap model, Customer item) {
        model.addAttribute("pageTitle", appPrefix + "Create/ Edit Customer");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "index?type=3");
        return "admin/customerForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String customerForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        Customer p = new Customer();
        if (id != null) {
            p = customerService.get(id);
        }
        return setUpModel(model, p);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute("item") @Valid Customer customer, BindingResult result, ModelMap model) {
        validator.validate(customer, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, customer);
        }
        customerService.save(customer);
        return "redirect:upload?type=1&idNumber=" + customer.getIdNumber();
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String userCustomer(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", appPrefix + "Customer List");
        model.addAttribute("items", customerService.getAll());
        if (type != null) {
            model.addAttribute("message", getMessage(type));
        }
        return "admin/customerList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getUserDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Customer customer = customerService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, customer.getFirstName() + " " + customer.getLastName(), "user.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", appPrefix + "Delete " + customer.getFirstName() + " " + customer.getLastName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteUser(@Valid ItemDeleteDTO dto) {
        customerService.delete(customerService.get(dto.getId()));
        return "redirect:user.list?type=2";
    }

    @RequestMapping("/upload")
    public String getUploadForm(ModelMap model, @RequestParam(required = false) Integer type, @RequestParam(required = false) String idNumber) {
        if (type != null) {
            model.addAttribute("message", getMessage(type));
        }
        Customer current = customerService.getByIdNumber(idNumber);
        model.addAttribute("idNumber", current.getIdNumber());
        System.out.println("Current: " + current.getFirstName());
        return "admin/customerUploadFiles";
    }

    @RequestMapping(value = "/save-file", method = RequestMethod.POST)
    public String saveimage(@RequestParam CommonsMultipartFile signatureFilePath, @RequestParam CommonsMultipartFile headshotFilePath, @RequestParam CommonsMultipartFile idFilePath, @RequestParam CommonsMultipartFile proofOfResidenceFilePath, @RequestParam("idNumber") String idNumber,
            HttpServletRequest session) throws Exception {
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String signatureFileName = idNumber + getFileExtension(signatureFilePath);
        String headShotFileName = idNumber + getFileExtension(headshotFilePath);
        String idFileName = idNumber + getFileExtension(idFilePath);
        String proofOfResidenceFileName = idNumber + getFileExtension(proofOfResidenceFilePath);
        File signatureFileUploadDirectory = new File(path + "/signature");
        File idFileUploadDirectory = new File(path + "/nationalID");
        File headShotFileUploadDirectory = new File(path + "/headShot");
        File proofOfResidenceFileUploadDirectory = new File(path + "/proofOfResidence");
        if( ! signatureFileUploadDirectory.exists()){
            signatureFileUploadDirectory.mkdirs();
        }
        if( ! idFileUploadDirectory.exists()){
            idFileUploadDirectory.mkdirs();
        }
        if( ! headShotFileUploadDirectory.exists()){
            headShotFileUploadDirectory.mkdirs();
        }
        if( ! proofOfResidenceFileUploadDirectory.exists()){
            proofOfResidenceFileUploadDirectory.mkdirs();
        }
        /*byte[] bytes = signatureFilePath.getBytes();
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                signatureFileUploadDirectory + File.separator + signatureFileName))) {
            stream.write(bytes);
            stream.flush();
        }*/
        writeToFile(signatureFilePath, signatureFileUploadDirectory, signatureFileName);
        writeToFile(idFilePath, idFileUploadDirectory, idFileName);
        writeToFile(headshotFilePath, headShotFileUploadDirectory, headShotFileName);
        writeToFile(proofOfResidenceFilePath, proofOfResidenceFileUploadDirectory, proofOfResidenceFileName);

        return "redirect:user.list?type=2";
    }
    
    public String getFileExtension(CommonsMultipartFile file){
        String contentType = file.getContentType();
        int indexPoint = contentType.indexOf("/") + 1;
        return "." + contentType.substring(indexPoint);
    }
    
    public void writeToFile(CommonsMultipartFile file, File uploadDirectory, String fileName){
        byte[] bytes = file.getBytes();
        try{
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                uploadDirectory + File.separator + fileName));
            stream.write(bytes);
            stream.flush();
        }catch(IOException ex){
        }
    }
    
    @RequestMapping(value = "/audit-report", method = RequestMethod.GET)
    public void createAuditReport(HttpServletResponse response, HttpServletRequest request, @RequestParam("id") String id){
        Customer item = customerService.get(id);
        Document document = new Document();
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font generalFont = new Font(Font.FontFamily.HELVETICA, 10);
        try{
            response.setContentType("application/pdf");
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingBefore(10f);
            headerTable.setSpacingAfter(10f);
            headerTable.setWidths(new float[]{1f, 1f, 1f});
            PdfPCell cell1 = new PdfPCell();
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Paragraph("AUDIT REPORT", headerFont));
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell3.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell3);
            document.add(headerTable);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            float[] columnWidths = {1f, 1f};
            table.setWidths(columnWidths);
            PdfPCell dateLabel = new PdfPCell(new Paragraph("Date Account Opened", labelFont));
            dateLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
            dateLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(dateLabel);
            PdfPCell date = new PdfPCell(new Paragraph(item.getDateCreated() + "", generalFont));
            date.setHorizontalAlignment(Element.ALIGN_LEFT);
            date.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(date);
            PdfPCell timeLabel = new PdfPCell(new Paragraph("Time Account Opened", labelFont));
            timeLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
            timeLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(timeLabel);
            PdfPCell time = new PdfPCell(new Paragraph(item.getTimeCreated(), generalFont));
            time.setHorizontalAlignment(Element.ALIGN_LEFT);
            time.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(time);
            PdfPCell agentLabel = new PdfPCell(new Paragraph("Agent", labelFont));
            agentLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
            agentLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(agentLabel);
            User user = item.getCreatedBy();
            PdfPCell agent = new PdfPCell(new Paragraph(user.getFirstName() + " " + user.getLastName(), generalFont));
            agent.setHorizontalAlignment(Element.ALIGN_LEFT);
            agent.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(agent);
            PdfPCell numAccountsLabel = new PdfPCell(new Paragraph("Number of Accounts Opened", labelFont));
            numAccountsLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
            numAccountsLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(numAccountsLabel);
            PdfPCell numAccounts = new PdfPCell(new Paragraph(customerService.getCount(user) + "", generalFont));
            numAccounts.setHorizontalAlignment(Element.ALIGN_LEFT);
            numAccounts.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(numAccounts);
            document.add(table);
            document.close();
            writer.flush();
            writer.close();
        }catch(IOException | DocumentException ex){
            
        }
    }
}
