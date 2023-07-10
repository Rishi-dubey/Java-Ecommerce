package dev.Ecommerce.Clothes;

import dev.Ecommerce.Clothes.dto.productcart;
import dev.Ecommerce.Clothes.models.Bag;
import dev.Ecommerce.Clothes.models.Confirm;
import dev.Ecommerce.Clothes.models.products;
import dev.Ecommerce.Clothes.repository.BagRepository;
import dev.Ecommerce.Clothes.repository.ConfirmRepository;
import dev.Ecommerce.Clothes.repository.ProductRepository;
import dev.Ecommerce.Clothes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    ProductRepository i;

    @Autowired
    ConfirmRepository confirmRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BagRepository bagRepository;

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @GetMapping("/home")
    public String displayHome(Model models) {
        List<products> data = new ArrayList<products>(i.findAll());
        models.addAttribute("data", data);
        return "home.html";
    }


    @GetMapping("/bag")
    public String getBag(Model models) {
        List<Bag> bag = new ArrayList<Bag>(bagRepository.findAll());
        List<productcart> Productcart = new ArrayList<>();

        for (Bag c : bag) {
            if (!c.isCheckout()) {
                products product = i.findById(c.getProductID().getId()).orElse(null);

                productcart bagP = new productcart();
                bagP.setOrderID(c.getOrderID());
                bagP.setpID(product.getId());
                bagP.setName(product.getName());
                bagP.setPrice((Double.parseDouble(product.getPrice()) * c.getQuantity()) + "");
                bagP.setQuantity(c.getQuantity());

                Productcart.add(bagP);
            }

        }
        models.addAttribute("Productcart", Productcart);

        return "bag.html";
    }


        @PostMapping("/update/bag")
    public String update(@RequestParam (value = "quantity",required = false) Integer quantity,
                         @RequestParam (value = "itemId") Integer orderID){

        Bag c = bagRepository.findById(orderID).orElse(null);
        c.setQuantity(quantity);
        bagRepository.save(c);
        return "redirect:/bag";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "itemId")Integer orderID){
        bagRepository.deleteById(orderID);
        return "redirect:/bag";
    }


    @PostMapping(value = "/add-bag")
    public String addtobag(@RequestParam("itemId") int itemId, Model models){

        products items = i.findById(itemId).orElse(null);

        if(items != null){
            Bag bag = new Bag();
            bag.setProductID(items);
            bag.setQuantity(1);
            bag.setTimestamp(System.currentTimeMillis()+"");
            bag.setCheckout(false);

            try {
                bagRepository.save(bag);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        models.addAttribute("itemAdded", true);

        return "redirect:/home";

    }
//
//
//}
    @GetMapping("/login/admin")
    public String login() {
        return "login.html";
    }

    @PostMapping("/login/admin")
    public String verifyAdmin(@RequestParam(name = "username", required = true) String username,
                              @RequestParam(name = "password", required = true) String pwd, HttpSession session) {

//        if(inMemoryUserDetailsManager.userExists(username)){
//            return  "admin_panel.html";
//        }
//
//        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
//        if (userDetails != null && pwd.equals(userDetails.getPassword())) {
//            session.setAttribute("user", userDetails);
//            return "admin_panel.html";
//        }

        if (username != null && pwd != null) {
            if (inMemoryUserDetailsManager.userExists(username)) {
//                session.setAttribute("user", inMemoryUserDetailsManager.loadUserByUsername(username));
                return "redirect:/admin_panel";
            }

        }
        return "redirect:/home";

    }


    @GetMapping("/admin_panel")
    public String admin_panel(Model models) {

        List<Confirm> orders = confirmRepository.findAll();

        List<products> items = i.findAll();

        models.addAttribute("order", orders);
        models.addAttribute("items", items);
        return "admin_panel.html";

    }


    @PostMapping("/item/update")
    public String updateitem(@RequestParam("name") String name,
                             @RequestParam("id") int id,
                             @RequestParam("category")String category,
                             @RequestParam("description")String description,
                             @RequestParam("price")String price,
                             @RequestParam("image")String image){



        products items = i.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        items.setName(name);
        items.setCategory(category);
        items.setDescription(description);
        items.setPrice(price);
        items.setImage(image);

        i.save(items);

        return "redirect:/admin_panel";

    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam("id") int id) {
        i.deleteById(id);
        return "redirect:/admin_panel";
    }

    @PostMapping("/add-item")
    public String additemAdmin(@RequestParam("name") String name,
                               @RequestParam("category")String category,
                               @RequestParam("description")String description,
                               @RequestParam("price")String price,
                               @RequestParam("image")String image){

        products items = new products();
        items.setName(name);
        items.setCategory(category);
        items.setDescription(description);
        items.setPrice(price);
        items.setImage(image);

        i.save(items);
        return "redirect:/admin_panel";
    }
    @GetMapping("/add-item")
    public String displayAdd(){
        return "new_products.html";
    }


    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
    }

    @PostMapping("/verified")
    public String checkboutpass(@RequestParam(name = "email")String email,
                                @RequestParam(name = "name")String name,
                                @RequestParam(name="address")String address,
                                @RequestParam(name = "credit")String c,
                                Model models){

        List<Bag> bagID = new ArrayList<>(bagRepository.findAll());

        List<Integer> id = new ArrayList<>();

        int price = 0;
        for (Bag x : bagID){
            if(!x.isCheckout()) {
                id.add(x.getProductID().getId());
            }
        }



        String totalPrice = bagRepository.getTotalPrice() + "";

        System.out.println(totalPrice);


        Confirm confirm = new Confirm();
        confirm.setPIDs(id);
        confirm.setName(name);
        confirm.setEmail(email);
        confirm.setPrice(totalPrice);
        confirm.setAddress(address);
        confirm.setTimestamp(System.currentTimeMillis()+"");

        if(confirmRepository.save(confirm) != null){
            List<Bag> update = bagRepository.findAll();
            int i;
            for (Bag x : update){
                x.setCheckout(true);
                bagRepository.save(x);
            }
        };

        models.addAttribute("checkout",totalPrice + "USD");


        return "redirect:/home.html";
    }

    @GetMapping("/product/shirts")
    public String shirtsCat(Model models) {
        List<products> d = new ArrayList<products>(i.findAllByCategory("shirts"));
        models.addAttribute("d", d);
        return "shirts.html";
    }

    @GetMapping("/product/pants")
    public String pantsCat(Model models) {
        List<products> d = new ArrayList<products>(i.findAllByCategory("pants"));
        models.addAttribute("d", d);
        return "pants.html";
    }

    @GetMapping("/product/hoodies")
    public String hoodiesCat(Model models) {
        List<products> d = new ArrayList<products>(i.findAllByCategory("hoodies"));
        models.addAttribute("d", d);
        return "hoodies.html";
    }

    @GetMapping("/product/shoes")
    public String shoesCat(Model models) {
        List<products> d = new ArrayList<products>(i.findAllByCategory("shoes"));
        models.addAttribute("d", d);
        return "shoes.html";
    }

    @GetMapping("/About")
    public String aboutpage(){
        return "About.html";
    }


    @GetMapping("/products/category/{category}")
    public List<products> getProductsByCategory(@PathVariable String category) {
        return i.findByCategory(category);
    }

    @PostMapping("/products")
    public products createProduct(@RequestBody products items) {
        return i.save(items);
    }

}