/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.List;
import javax.ws.rs.Produces;
import model.Beers;
import model.BeersService;
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
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hyoku
 */

@RestController
@RequestMapping("/beers") 
public class BeersController {
    
    @Autowired
    BeersService bs;
    
    @Autowired
    CategoriesService cs;
    
    @Autowired
    StylesService ss;
    
    @GetMapping(value ="/hateoas/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Beers> retriveBrewery(@PathVariable("id") int id) throws Exception {
        Beers beer = bs.findByID(id);
        if (beer  == null) {
            throw new CustomNotFoundException("Not found with id: " + id);
        }
        Resource<Beers> resource = new Resource<Beers>(bs.findByID(id));

        ControllerLinkBuilder linkToAllBeers = linkTo(methodOn(this.getClass()).GetAllBeers());
        ControllerLinkBuilder linkToPagination = linkTo(methodOn(this.getClass()).GetBeersPagination(5, 0));
        ControllerLinkBuilder linkToCategories = linkTo(methodOn(this.getClass()).GetBeerCategoriesDetails(id));
        ControllerLinkBuilder linkToStyles = linkTo(methodOn(this.getClass()).GetBeerStylesDetails(id));
       
        
        resource.add(linkToAllBeers.withRel("All-Beers"));
        resource.add(linkToPagination.withRel("All-Beers-Pagination"));
        resource.add(linkToCategories.withRel("Categories-Details"));
        resource.add(linkToStyles.withRel("Styles-Details"));
        
        return resource;
        
    }
    
    
    
    @GetMapping(value ="/hateoas", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Beers> getAllBeers() throws Exception {
        List<Beers> allBeers = bs.getAllBeersPagination(10, 0);
        
        for (Beers b : allBeers) {
            
            int beerId = b.getBeerId();
            
            Link selfLink = linkTo(methodOn(this.getClass()).getBeer(beerId)).withSelfRel();
            Link linkToCategories = linkTo(methodOn(this.getClass()).GetBeerCategoriesDetails(beerId)).withRel("Categories-Details");
            Link linkToStyles = linkTo(methodOn(this.getClass()).GetBeerStylesDetails(beerId)).withRel("Styles-Details");
          
            b.add(selfLink);
            b.add(linkToCategories);
            b.add(linkToStyles);
        
        }
        
        Link link = linkTo(this.getClass()).slash("displayAll").withSelfRel();
        Resources<Beers> result = new Resources<Beers>(allBeers, link);
        return result;
       
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/displayAll")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Beers> GetAllBeers() {
        return bs.getAllBeers();
    }
    
    
    @GetMapping("/displayAllPagination")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public List<Beers> GetBeersPagination(
        @RequestParam(name="page", defaultValue = "5") Integer page, //int is a primitive data type which has default value zero.
        @RequestParam(name ="size", defaultValue = "0") Integer size
        ){
        return bs.getAllBeersPagination(page, size);
    }
    
    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Beers getBeer(@PathVariable("id") int id) {
        
        Beers beer = bs.findByID(id);
        if(null == beer){
            throw new CustomNotFoundException("Not found by id: " + id);
        }
        return beer;
    }
    
    @GetMapping("/categories/{beerId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Categories GetBeerCategoriesDetails(@PathVariable("beerId") int id) {
        
        Beers beer = bs.findByID(id);
        if(null == beer){
            throw new CustomNotFoundException("Not found by id: " + id);
        }
        Categories cat = cs.findByID(bs.findByID(id).getCatId());
   
        return cat;
    }
    
    
    @GetMapping("/styles/{beerId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE) 
    public Styles GetBeerStylesDetails(@PathVariable("beerId") int id) {
        
        Beers beer = bs.findByID(id);
        if(null == beer){
            throw new CustomNotFoundException("Not found by id: " + id);
        }
        Styles style = ss.findByID(bs.findByID(id).getStyleId());
     
        return style;
    }
    

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> create(@RequestBody Beers b) {
        b.setLastMod(new Date());
        b.setImage("no_image.jpg");
        if (bs.addBeer(b)){
           return new Resource<String>("Successfully added Beer");
        } else return new Resource<String>("Failed");
    }
    
    
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> update(@RequestBody Beers b) {
        b.setLastMod(new Date());
        b.setImage("no_image.jpg");
        if (bs.editBeer(b)){
            return new Resource<String>("Successfully editted Beer");
        } else return new Resource<String>("Failed");
    
    }
    
    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<String> deleteBeer(@PathVariable("id") int id) {
        Beers b = bs.findByID(id);
        if(b == null){
            throw new CustomNotFoundException("Not found by id: " + id);
        }
        
        if (bs.deleteBeer(id)) {
              return new Resource<String>("Successfully Deleted Beer");
        } else return new Resource<String>("Failed");}
    
    
}
