package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.OrderDTO;
import uz.pdp.Doctor.dto.ProductDTO;
import uz.pdp.Doctor.mapper.ProductMapper;
import uz.pdp.Doctor.model.*;
import uz.pdp.Doctor.repository.BasketRepo;
import uz.pdp.Doctor.repository.FilesRepo;
import uz.pdp.Doctor.repository.OrderRepo;
import uz.pdp.Doctor.repository.ProductRepo;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FilesRepo filesRepo;
    private final BasketRepo basketRepo;
    private final OrderRepo orderRepo;
    private final UserService userService;
    private final PaymentService paymentService;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";

    public String addProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = ProductMapper.PRODUCT_MAPPER.toEntity(productDTO);
        Files files = s3StorageService.saveImage(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        product.setFiles(savedFile);
        productRepo.save(product);
        return "Successfully added product.";
    }

    public String updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(productDTO.name());
        product.setWeight(productDTO.weight());
        product.setPrice(productDTO.price());
        product.setDescription(productDTO.description());
        productRepo.save(product);
        return "Successfully updated product.";
    }

    public String removeProduct(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepo.delete(product);
        return "Successfully removed product.";
    }

    public String orderProduct(OrderDTO orderDTO) {
        Product product = productRepo.findById(orderDTO.productId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        User currentUser = userService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("User not authenticated"));

        Payment currentPayment = paymentService.getPaymentForUser(currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for user"));

        Double totalPrice = (double) (product.getPrice() * orderDTO.quantity());

        Order order = Order.builder()
                .product(product)
                .price(totalPrice)
                .weight(product.getWeight() * orderDTO.quantity())
                .build();
        orderRepo.save(order);

        Double subtotal = totalPrice;
        Double taxes = subtotal * 0.1;
        Double total = subtotal + taxes;
        Basket basket = Basket.builder()
                .user(currentUser)
                .order(order)
                .payment(currentPayment)
                .subtotal(subtotal)
                .taxes(taxes)
                .total(total)
                .build();
        basketRepo.save(basket);
        return "Order placed successfully for product: " + product.getName() + " with quantity: " + orderDTO.quantity();
    }


    public String getProductDetails(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        String productDetails = String.format("Product Name: %s\nWeight: %d\nPrice: %d\nDescription: %s",
                product.getName(), product.getWeight(), product.getPrice(), product.getDescription());
        return productDetails;
    }
}
