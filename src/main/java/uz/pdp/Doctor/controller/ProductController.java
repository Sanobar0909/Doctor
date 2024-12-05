package uz.pdp.Doctor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.AddressDTO;
import uz.pdp.Doctor.dto.OrderDTO;
import uz.pdp.Doctor.dto.ProductDTO;
import uz.pdp.Doctor.model.Address;
import uz.pdp.Doctor.model.Product;
import uz.pdp.Doctor.service.AmbulanceService;
import uz.pdp.Doctor.service.FavoriteService;
import uz.pdp.Doctor.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/medicines")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Online Pharmacy", description = "Manage medications including adding, updating, removing, and ordering.")
public class ProductController {

    private final ProductService productService;
    private final AmbulanceService ambulanceService;
    private final FavoriteService favoriteService;

    public ProductController(ProductService productService, AmbulanceService ambulanceService, FavoriteService favoriteService) {
        this.productService = productService;
        this.ambulanceService = ambulanceService;
        this.favoriteService = favoriteService;
    }

    @Operation(summary = "Add Medication", description = "Use this endpoint to add a new medication to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the medication"),
            @ApiResponse(responseCode = "400", description = "Invalid medication input data"),
            @ApiResponse(responseCode = "409", description = "Medication already exists")
    })
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addMedicine(@RequestParam("name") String name,
                                              @RequestParam("weight") Integer weight,
                                              @RequestParam("price") Long price,
                                              @RequestParam("description") String description,
                                              @RequestParam(required = false, value = "files") MultipartFile files) {
        try {
            ProductDTO productDTO = new ProductDTO(name, weight, price, description);
            String result = productService.addProduct(productDTO, files);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add medication: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Medication", description = "Use this endpoint to update existing medication details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the medication"),
            @ApiResponse(responseCode = "400", description = "Invalid medication data"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMedicine(@PathVariable("id") String id,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("weight") Integer weight,
                                                 @RequestParam("price") Long price,
                                                 @RequestParam("description") String description) {
        try {
            ProductDTO productDTO = new ProductDTO(name, weight, price, description);
            String result = productService.updateProduct(id, productDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update medication: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Remove Medication", description = "Use this endpoint to remove a medication from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed the medication"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeMedicine(@PathVariable("id") String id) {
        try {
            String result = productService.removeProduct(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove medication: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Order Medication", description = "Use this endpoint to order a medication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully ordered the medication"),
            @ApiResponse(responseCode = "400", description = "Invalid order data"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PreAuthorize("hasAuthority('ROLE_ORDER')")
    @PostMapping("/order/{id}")
    public ResponseEntity<String> orderMedicine(@PathVariable("id") String id,
                                                @RequestParam("count") int quantity) {
        try {
            OrderDTO orderDTO = new OrderDTO(id, quantity);
            String result = productService.orderProduct(orderDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to order medication: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/myCard")
    public ResponseEntity<String> getMyCard() {
        String s = productService.myCard();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/cardNumber")
    public ResponseEntity<String> addCardNumber(@RequestParam("cardNumber") String cardNumber) {
        String buy = productService.buy(cardNumber);
        return new ResponseEntity<>(buy, HttpStatus.OK);
    }

    @Operation(summary = "Get Medication", description = "Use this endpoint to retrieve a medication's details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the medication"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PreAuthorize("hasAuthority('ROLE_GET')")
    @GetMapping("/get/{id}")
    public ResponseEntity<String> getMedicine(@PathVariable("id") String id) {
        try {
            String result = productService.getProductDetails(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve medication: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Medication", description = "Use this endpoint to retrieve a medication's details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the medication"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PreAuthorize("hasAuthority('ROLE_GET_ALL')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getMedicine() {
        List<Product> product = productService.getProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<Address> create(AddressDTO addressDTO) {
        Address address = ambulanceService.create(addressDTO);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/getUserLocation")
    public ResponseEntity<Address> getUserLocation() {
        Address userAddress = ambulanceService.getUserAddress();
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @PutMapping("/updateLocation")
    public ResponseEntity<Address> updateLocation(AddressDTO addressDTO) {
        Address address = ambulanceService.update(addressDTO);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markAsFavorite(@RequestParam String productId) {
        String result = favoriteService.markAsFavorite(productId);
        return ResponseEntity.ok(result);
    }
}
