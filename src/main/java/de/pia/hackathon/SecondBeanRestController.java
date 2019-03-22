package de.pia.hackathon;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.pia.hackathon.solrservice.ProductRepository;
import de.pia.hackathon.solrservice.ProductRepository.Product;

@RestController
@RequestMapping("/api")
public class SecondBeanRestController {

	@Autowired private ProductRepository productRepository;

	@GetMapping("/profile")
	@ResponseBody
	public TchiboProfile profile() {
		Product product = productRepository.findById("400074462").get();
		Product product2 = productRepository.findById("400078565").get();
		TchiboProfile tchiboProfile = new TchiboProfile();
		TchiboProfile bob = new TchiboProfile("bob");
		TchiboProfile lisa = new TchiboProfile("lisa");
		tchiboProfile.addFriend(bob);
		tchiboProfile.addFriend(lisa);
		TchiboProduct theoTiger = new TchiboProduct(product.getName());
		theoTiger.pid = product.getId();
		theoTiger.price = "99.78";
		theoTiger.imageUrl = product.getImageUrl();
		Transaction boughtProduct = new Transaction(new Date(), lisa, theoTiger);
		tchiboProfile.addBoughtProduct(boughtProduct);
		TchiboProduct megaMinion = new TchiboProduct(product2.getName());
		megaMinion.price = "1000.0";
		megaMinion.pid = product2.getId();
		megaMinion.imageUrl = product2.getImageUrl();
		Transaction sellingProduct = new Transaction(new Date(), bob, megaMinion);
		tchiboProfile.addSellingProduct(sellingProduct);

		return tchiboProfile;
	}

	@GetMapping("/product")
	public TchiboProduct getProduct(@RequestParam byte[] image) {

		return new TchiboProduct("Theo Tiger");
	}

	@GetMapping("/offerproduct")
	public TchiboProfile offerProduct(@RequestParam String pid) {
		Product product = productRepository.findById("400074462").get();
		Product product2 = productRepository.findById("400078565").get();
		TchiboProfile tchiboProfile = new TchiboProfile();
		TchiboProfile bob = new TchiboProfile("bob");
		TchiboProfile lisa = new TchiboProfile("lisa");
		tchiboProfile.addFriend(bob);
		tchiboProfile.addFriend(lisa);
		TchiboProduct theoTiger = productToTchiboProduct.apply(product);
		TchiboProduct megaMinion = new TchiboProduct("Mega Minion");
		megaMinion.price = "1000.0";
		megaMinion.pid = product2.getId();
		megaMinion.imageUrl = product2.getImageUrl();
		tchiboProfile.addSellingProduct(new Transaction(new Date(), bob, megaMinion));
		tchiboProfile.addSellingProduct(new Transaction(new Date(), bob, theoTiger));

		return tchiboProfile;
	}

	Function<Product, TchiboProduct> productToTchiboProduct = t -> {
		TchiboProduct myProduct = new TchiboProduct(t.getName());
		myProduct.pid = t.getId();
		myProduct.price = t.getPrice();
		myProduct.imageUrl = t.getImageUrl();
		myProduct.searchProductTags.add(new ProductTag(t.getColor(), 10));
		myProduct.searchProductTags.add(new ProductTag(t.getAssortmentCategory1(), 11));
		myProduct.searchProductTags.add(new ProductTag(t.getAssortmentCategory2(), 12));
		myProduct.searchProductTags.add(new ProductTag(t.getAssortmentCategory3(), 13));
		return myProduct;
	};

	@GetMapping("/search")
	public List<TchiboProduct> searchProducts(@RequestParam String searchText) {
		return productRepository.findByCustomQuery(searchText)
				.stream()
				.map(productToTchiboProduct)
				.collect(Collectors.toList());
	}

	@GetMapping("/tagclicked")
	public List<TchiboProduct> tagClicked(@RequestParam String tagText) {
		return productRepository.findByTag(tagText)
				.stream()
				.map(productToTchiboProduct)
				.collect(Collectors.toList());
	}

	@GetMapping("/toptags")
	public List<ProductTag> topTags() {
		return asList(new ProductTag("top1", 10), new ProductTag("top2", 5), new ProductTag("top3", 13));
	}

	@GetMapping("/image")
	public byte[] getImage(@RequestParam String imageId) {
		try {
			String path = String.format("images/%s.jpg", imageId);
			ClassPathResource ressource = new ClassPathResource(path, this.getClass().getClassLoader());
			return IOUtils.toByteArray(ressource.getInputStream());
		}
		catch (IOException e) {
			return new byte[0];
		}
	}

	@GetMapping("/toplist")
	public List<TchiboProduct> topList() {
		TchiboProduct product1 = new TchiboProduct("Mega Minion");
		product1.name = "p1";
		product1.price = "9.90";
		TchiboProduct product2 = new TchiboProduct("Theo Tiger");
		product2.name = "p2";
		product2.price = "4.99";
		return asList(product1, product2);
	}

	@GetMapping("/communitylist")
	public List<TchiboProduct> communitylist() {

		return new ArrayList<>();
	}

	@GetMapping("/topRated")
	public List<TchiboProduct> topRated() {

		return new ArrayList<>();
	}

}

class ProductTag {

	private String name;
	private int count;

	public ProductTag() {
	}

	public ProductTag(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public String getName() {
		return name;
	}
}

class TchiboProduct {

	String pid;
	String name;
	String price;
	String imageUrl;
	List<ProductTag> searchProductTags = new ArrayList<>();

	public TchiboProduct(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public String getPrice() {
		return price;
	}

	public List<ProductTag> getSearchProductTags() {
		return searchProductTags;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

class Transaction {

	private Date transactionDate;
	private TchiboProfile toUser;
	private TchiboProduct product;

	public Transaction() {
	}

	public Transaction(Date transactionDate, TchiboProfile toUser, TchiboProduct product) {
		this.transactionDate = transactionDate;
		this.toUser = toUser;
		this.product = product;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public TchiboProfile getToUser() {
		return toUser;
	}

	public TchiboProduct getProduct() {
		return product;
	}
}

class TchiboProfile {

	private String name;
	private List<Transaction> boughtProducts = new ArrayList<>();
	private List<Transaction> sellingProducts = new ArrayList<>();
	private List<TchiboProfile> friends = new ArrayList<>();

	public TchiboProfile() {
	}

	public TchiboProfile(String name) {
		this.name = name;
	}

	public void addFriend(TchiboProfile friend) {
		friends.add(friend);
	}

	public String getName() {
		return name;
	}

	public List<Transaction> getBoughtProducts() {
		return boughtProducts;
	}

	public List<TchiboProfile> getFriends() {
		return friends;
	}

	public void addBoughtProduct(Transaction transaction) {
		this.boughtProducts.add(transaction);
	}

	public List<Transaction> getSellingProducts() {
		return sellingProducts;
	}

	public void addSellingProduct(Transaction product) {
		this.sellingProducts.add(product);
	}
}


