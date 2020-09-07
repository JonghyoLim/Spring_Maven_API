/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.Produces;
import model.Breweries;
import model.BreweriesGeoCodeService;
import model.BreweriesService;
import model.BreweriesGeocode;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

//import java.util.Properties;

/**
 *
 * @author hyoku
 */
@RestController
@RequestMapping("/breweries") 
public class BreweriesController {
    
    @Autowired
    BreweriesService service;
    
    @Autowired
    BreweriesGeoCodeService geoCodeService;
        
    
    @GetMapping(value ="/hateoas/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Breweries> retriveBrewery(@PathVariable("id") int id) throws Exception {
        Breweries brewery = service.findById(id);
        if (brewery  == null) {
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        Resource<Breweries> resource = new Resource<Breweries>(brewery);
        
       
        
        ControllerLinkBuilder linkToAllBreweries = linkTo(methodOn(this.getClass()).GetBreweries());
        ControllerLinkBuilder linkToPagination = linkTo(methodOn(this.getClass()).GetBreweriesPagination(5, 10));
        ControllerLinkBuilder linkToGeoCode = linkTo(methodOn(this.getClass()).GetBreweryGeoCodeDetails(id));
        ControllerLinkBuilder linkToMap = linkTo(methodOn(this.getClass()).mapBreweryLocation(id));
        ControllerLinkBuilder linkToQrCode = linkTo(methodOn(this.getClass()).QR_Code(id));
        ControllerLinkBuilder linkToSendEmail = linkTo(methodOn(this.getClass()).sendEmail("K00222516@student.lit.ie", id));
        
        resource.add(linkToAllBreweries.withRel("All-Breweries"));
        resource.add(linkToPagination.withRel("All-Breweries-Pagination"));
        resource.add(linkToGeoCode.withRel("Latitudinal and Longitudinal"));
        resource.add(linkToSendEmail.withRel("Send-Email"));
        resource.add(linkToMap.withRel("Map"));
        resource.add(linkToQrCode.withRel("QR-Code"));
        
        return resource;
    }
    
    
    @GetMapping(value ="/hateoas", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Breweries> getAllBreweries() throws Exception {
        List<Breweries> allBreweries = service.getAllBrewsPagination(5, 0);
                
        for (Breweries b : allBreweries) {
            
            int breweryId = b.getBreweryId();
            
            Link selfLink = linkTo(methodOn(this.getClass()).getBrewery(breweryId)).withSelfRel();
            Link linkToGeoCode = linkTo(methodOn(this.getClass()).GetBreweryGeoCodeDetails(breweryId)).withRel("Latitudinal and Longitudinal");
            Link linkToMap = linkTo(methodOn(this.getClass()).mapBreweryLocation(breweryId)).withRel("Map");
            Link linkToSendEmail = linkTo(methodOn(this.getClass()).sendEmail("K00222516@student.lit.ie", breweryId)).withRel("Send-Email");
            Link linkToQrCode = linkTo(methodOn(this.getClass()).QR_Code(breweryId)).withRel("QR-Code");
            
            b.add(selfLink);
            b.add(linkToGeoCode);
            b.add(linkToSendEmail);
            b.add(linkToMap);
            b.add(linkToQrCode);
        
        }
        
        Link link = linkTo(this.getClass()).slash("displayAll").withSelfRel();
        Resources<Breweries> result = new Resources<Breweries>(allBreweries, link);
        return result;
       
    }
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////reweries////////////////////////////////////////////////////////
    
    @GetMapping("/displayAll")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Breweries> GetBreweries() {
        return service.getAllBrews();
    } 
    
    @GetMapping("/displayAllPagination")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Breweries> GetBreweriesPagination(
            @RequestParam(name="page", defaultValue = "5") Integer page, //int is a primitive data type which has default value zero.
            @RequestParam(name ="size", defaultValue = "0") Integer size
            ){
        return service.getAllBrewsPagination(page, size);
    }
    
    
    
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Breweries getBrewery(@PathVariable("id") int id) {
        Breweries brewery = service.findById(id);
        if(null == brewery){
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        return brewery;
    }
    
    
    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> deleteBrewery(@PathVariable("id") int id) {
        Breweries b = service.findById(id);
        if(b == null){
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        
        if (service.deleteBrewery(id)){
            return new Resource<String>("Successfully Delete Brewery");
        } else return new Resource<String>("Failed"); 
    }
    
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> create(@RequestBody Breweries b) {
        b.setImage("no_image.jpg");
        b.setLastMod(new Date());
        if (service.addBrewery(b)){
            return new Resource<String>("Successfully created Brewery");
        } else return new Resource<String>("Failed");
    }
    
    
    @PutMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> update(@RequestBody Breweries b) {
        b.setImage("no_image.jpg");
        b.setLastMod(new Date());
        if (service.editBrewery(b)){
            return new Resource<String>("Successfully editted Brewery");
        } else return new Resource<String>("Failed");
    }
    
    
    @GetMapping("/geocode/{breweryId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public BreweriesGeocode GetBreweryGeoCodeDetails(@PathVariable("breweryId") int id) {
        BreweriesGeocode breweriesGeocode = geoCodeService.getBreweryGeocodeById(id);
        if(null == breweriesGeocode ){
           throw new CustomNotFoundException("Not found with id: " + id);
        }
        return breweriesGeocode;
    }
    

   
    @GetMapping(value ="/map/{breweryId}", produces = MediaType.TEXT_HTML_VALUE)
    public Resource<String> mapBreweryLocation (@PathVariable("breweryId") int id) {
      
        BreweriesGeocode bs = geoCodeService.getBreweryGeocodeById(id);
        Breweries b = service.findById(id);
        if (bs == null){
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        
        System.out.println("Latitute: " + bs.getLatitude() + "\nLongitude: " + bs.getLongitude());
         
        return new Resource<String>( "<html><body>"
            + "<h2>" + b.getName() + "</h2>"
            + "<iframe width='1150px' height='700px' id='mapcanvas' src='https://maps.google.com/maps?q=" + bs.getLatitude() + ", " + bs.getLongitude() 
            + "&z=16&ie=UTF8&iwloc=&output=embed' frameborder='0' scrolling='yes' marginheight='10' marginwidth='10'></iframe>"
            +  "<body><html>");
    }
    
    
    
    @GetMapping(value ="/QRCode/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> QR_Code(@PathVariable("id") int id) throws Exception {
      
        Breweries b = service.findById(id);
        if(b == null){
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        BufferedImage image = gernerateQRCode(b.getWebsite());
        ByteArrayOutputStream  bos = new ByteArrayOutputStream();
        ImageIO.write(image, "gif", bos);
        InputStream is = new ByteArrayInputStream(bos.toByteArray());

        return new ResponseEntity<byte[]> (IOUtils.toByteArray(is), HttpStatus.OK);
     
    }
    
    
    public BufferedImage gernerateQRCode(String qrCodeText) throws Exception {
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 350, 350);
        
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
        
    }
    
    
    
    @GetMapping("/sendMail")
    public Resource<String> sendEmail(@RequestParam(value="email", required = true) String email, @RequestParam("id") int id) {
        Breweries b = service.findById(id);
        if(b == null){
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        
        return new Resource<String> (service.sendEmail(email, id));
    }
    
    
}