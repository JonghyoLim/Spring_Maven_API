/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Date;
import javax.ws.rs.Produces;
import model.Categories;
import model.CategoriesService;
import model.Styles;
import model.StylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hyoku
 */
@RestController
@RequestMapping("/styles") 
public class StyleController {
    
    @Autowired
    StylesService ss;
    
    @Autowired
    CategoriesService cs;
    
    
    @GetMapping(value ="/hateoas/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Styles> retriveStyle(@PathVariable("id") int id) throws Exception {
        
        Styles style = ss.findByID(id);
        if (style  == null) {
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        
        Resource<Styles> resource = new Resource<Styles>(ss.findByID(id));

        ControllerLinkBuilder linkToAllStyles= linkTo(methodOn(this.getClass()).GetAllStyles());
        ControllerLinkBuilder linkToPagination = linkTo(methodOn(this.getClass()).GetBeersPagination(5, 0));
        ControllerLinkBuilder linkToCategories = linkTo(methodOn(this.getClass()).GetStyleCategoriesDetails(id));
     
        resource.add(linkToAllStyles.withRel("All-Styles"));
        resource.add(linkToPagination.withRel("All-Styles-Pagination"));
        resource.add(linkToCategories.withRel("Categories-Details"));
        
        return resource;
        
    }
    
        
    @GetMapping(value ="/hateoas", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Styles> getAllBeers() throws Exception {
        List<Styles> allStyles = ss.getAllStylesPagination(10, 0);
        
        for (Styles s : allStyles) {
            
            int styleId = s.getStyleId();
            
            Link selfLink = linkTo(methodOn(this.getClass()).GetAllStyles()).withSelfRel();
            Link linkToCategories = linkTo(methodOn(this.getClass()).GetStyleCategoriesDetails(styleId)).withRel("Categories-Details");
  
            s.add(selfLink);
            s.add(linkToCategories);
      
        }
        
        Link link = linkTo(this.getClass()).slash("displayAll").withSelfRel();
        Resources<Styles> result = new Resources<Styles>(allStyles, link);
        return result;
       
    }

    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/displayAll")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Styles> GetAllStyles() {
        return ss.getAllStyles();
    }
    
   
    @GetMapping("/displayAllPagination")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Styles> GetBeersPagination(
        @RequestParam(name="page", defaultValue = "10") Integer page, //int is a primitive data type which has default value zero.
        @RequestParam(name ="size", defaultValue = "0") Integer size
        ){
        return ss.getAllStylesPagination(page, size);
    }
    
    
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Styles getStyle(@PathVariable("id") int id) {
        
        Styles style = ss.findByID(id);
        if(null == style){
            throw new CustomNotFoundException("Not found by id: " + id);
        }
        return style;
    }
    
    
    
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> create(@RequestBody Styles s) {
        s.setLastMod(new Date());
        if (ss.addStyle(s)){
           return new Resource<String>("Successfully added Style");
        } else return new Resource<String>("Failed");
    }
    
    @PutMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> update(@RequestBody Styles s) {
        s.setLastMod(new Date());
        if (ss.editStyle(s)){
            return new Resource<String>("Successfully edit Style");
        } else return new Resource<String>("Failed");
    }
    
    
    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> deleteStyle(@PathVariable("id") int id) {
        if (ss.deleteStyle(id)){
            return new Resource<String>("Successfully Delete Style");
        } else return new Resource<String>("Failed");
    }
    
    
    @GetMapping("/categories/{styleId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Categories GetStyleCategoriesDetails(@PathVariable("styleId") int id) {
        return cs.findByID(ss.findByID(id).getCatId());
        
    }
}
