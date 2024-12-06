package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.OrderDTO;
import uz.pdp.Doctor.dto.ProductDTO;
import uz.pdp.Doctor.mapper.ProductMapper;
import uz.pdp.Doctor.model.*;
import uz.pdp.Doctor.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FilesRepo filesRepo;
    private final FavoriteRepo favoriteRepo;
    private final BasketRepo basketRepo;
    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final UserService userService;
    private final S3StorageService s3StorageService;
    private final AmbulanceService ambulanceService;
    private final String AWS_PUBLIC = "public";
    //    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";

    public String addProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = ProductMapper.PRODUCT_MAPPER.toEntity(productDTO);
        if (file != null && !file.isEmpty()) {
            Files files = s3StorageService.save(file, AWS_PUBLIC);
            files.setUrl(AWS_URL + files.getPath());
            Files savedFile = filesRepo.save(files);
            product.setFiles(savedFile);
        } else {
            product.setFiles(null);
        }
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
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseGet(() -> Basket.builder().user(currentUser).orders(new ArrayList<>()).build());

        if (basket.getId() == null) {
            basketRepo.save(basket);
        }

        Order order = Order.builder()
                .product(product)
                .user(currentUser)
                .count(orderDTO.quantity())
                .basket(basket)
                .build();
        orderRepo.save(order);

        basket.getOrders().add(order);
        basketRepo.save(basket);
        return "Basket updated with product: " + product.getName();
    }


    public String myCard() {
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No basket found for this user."));

        List<Order> userOrders = basket.getOrders();
        if (userOrders.isEmpty()) {
            return "Your basket is empty. Add some products to view them here.";
        }

        StringBuilder orderDetails = new StringBuilder("Basket - Payment Details:\n\n");
        double subtotal = 0.0;
        double taxRate = 0.05;

        for (Order order : userOrders) {
            Product product = order.getProduct();
            double itemTotalPrice = product.getPrice() * order.getCount();
            subtotal += itemTotalPrice;

            orderDetails.append("Product: ").append(product.getName()).append("\n")
                    .append("Weight: ").append(product.getWeight()).append(" kg\n")
                    .append("Price per Unit: $").append(product.getPrice()).append("\n")
                    .append("Quantity: ").append(order.getCount()).append("\n")
                    .append("Item Total: $").append(String.format("%.2f", itemTotalPrice)).append("\n\n");
        }

        double taxes = subtotal * taxRate;
        double totalPrice = subtotal + taxes;

        orderDetails.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n")
                .append("Taxes (5%): $").append(String.format("%.2f", taxes)).append("\n")
                .append("Total: $").append(String.format("%.2f", totalPrice)).append("\n");
        Payment payment = Payment.builder()
                .subtotals(subtotal)
                .taxes(taxes)
                .totals(totalPrice)
                .user(currentUser)
                .build();
        paymentRepo.save(payment);
        return orderDetails.append("\nPlease enter your card number to complete the purchase.").toString();
    }


    public String buy(String cardNumber) {
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No basket found for this user."));
        if (cardNumber == null || cardNumber.length() != 16) {
            return "Invalid card number. Please enter a valid 16-digit card number.";
        }
        Payment payment = paymentRepo.findTopByUserOrderByIdDesc(currentUser);
        if (payment == null) {
            return "No payment details found. Please add items to your basket first.";
        }
        payment.setCardNumber(cardNumber);
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);
        basket.getOrders().clear();
        basketRepo.save(basket);
        return "Payment of $" + String.format("%.2f", payment.getTotals()) + " was successful. Your order has been placed!";
    }


    public String getProductDetails(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        String fileUrl = (product.getFiles() != null && product.getFiles().getUrl() != null)
                ? product.getFiles().getUrl()
                : "No image available";
        boolean isFavorite = favoriteRepo.findByProduct(product)
                .map(Favorite::isFavorite)
                .orElse(false);
        String favoriteMark = isFavorite ? "❤️" : "";
        String productDetails = String.format(
                "%s \nProduct Name: %s \nWeight: %d ml\nPrice: %d $\nDescription: %s \nPicture: %s",
                favoriteMark,product.getName(),product.getWeight(), product.getPrice(), product.getDescription(), fileUrl);
        return productDetails;
    }

    public List<Product> getProduct() {
        List<Product> all = productRepo.findAll();
        return all;
    }
}
