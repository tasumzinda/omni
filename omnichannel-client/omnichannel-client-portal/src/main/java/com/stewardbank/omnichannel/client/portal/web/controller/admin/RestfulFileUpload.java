/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stewardbank.omnichannel.client.portal.web.controller.admin;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.service.CustomerService;
import com.stewardbank.omnichannel.business.util.HttpStatus;
import java.io.File;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Tasu Muzinda
 */
@RestController
@RequestMapping("/upload")
public class RestfulFileUpload {

    @Resource
    ServletContext context;
    @Resource
    private CustomerService customerService;
    private static final String UPLOAD_DIRECTORY = "/upload";

    @RequestMapping(value = "/signature-upload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public HttpStatus upload(@RequestParam("file") CommonsMultipartFile inputFile, @RequestParam("idNumber") String idNumber) {
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File uploadFile = new File(context.getRealPath(UPLOAD_DIRECTORY) + "/signature");
                if( ! uploadFile.exists()){
                    uploadFile.mkdirs();
                }
                File destinationFile = new File(uploadFile.getPath() + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                Customer item = customerService.getByIdNumber(idNumber);
                item.setSignatureFilePath(destinationFile.getPath());
                customerService.save(item);
                return HttpStatus.OK;
            } catch (Exception e) {
                return HttpStatus.ERROR;
            }
        } else {
            return HttpStatus.ERROR;
        }
    }
    
    @RequestMapping(value = "/id-upload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public HttpStatus uploadID(@RequestParam("file") CommonsMultipartFile inputFile, @RequestParam("idNumber") String idNumber) {
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File uploadFile = new File(context.getRealPath(UPLOAD_DIRECTORY) + "/nationalID");
                if( ! uploadFile.exists()){
                    uploadFile.mkdirs();
                }
                File destinationFile = new File(uploadFile.getPath() + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                Customer item = customerService.getByIdNumber(idNumber);
                item.setIdFilePath(destinationFile.getPath());
                customerService.save(item);
                return HttpStatus.OK;
            } catch (Exception e) {
                return HttpStatus.ERROR;
            }
        } else {
            return HttpStatus.ERROR;
        }
    }
    
    @RequestMapping(value = "/head-shot-upload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public HttpStatus uploadHeadShot(@RequestParam("file") CommonsMultipartFile inputFile, @RequestParam("idNumber") String idNumber) {
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File uploadFile = new File(context.getRealPath(UPLOAD_DIRECTORY) + "/headShot");
                if( ! uploadFile.exists()){
                    uploadFile.mkdirs();
                }
                File destinationFile = new File(uploadFile.getPath() + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                Customer item = customerService.getByIdNumber(idNumber);
                item.setHeadshotFilePath(destinationFile.getPath());
                customerService.save(item);
                return HttpStatus.OK;
            } catch (Exception e) {
                return HttpStatus.ERROR;
            }
        } else {
            return HttpStatus.ERROR;
        }
    }
    
    @RequestMapping(value = "/proof-of-residence-upload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public HttpStatus uploadProofOfResidence(@RequestParam("file") CommonsMultipartFile inputFile, @RequestParam("idNumber") String idNumber) {
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File uploadFile = new File(context.getRealPath(UPLOAD_DIRECTORY) + "/proofOfResidence");
                if( ! uploadFile.exists()){
                    uploadFile.mkdirs();
                }
                File destinationFile = new File(uploadFile.getPath() + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                Customer item = customerService.getByIdNumber(idNumber);
                item.setProofOfResidenceFilePath(destinationFile.getPath());
                customerService.save(item);
                return HttpStatus.OK;
            } catch (Exception e) {
                return HttpStatus.ERROR;
            }
        } else {
            return HttpStatus.ERROR;
        }
    }
}
