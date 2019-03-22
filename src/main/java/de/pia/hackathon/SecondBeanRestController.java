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

	@GetMapping("/offerproduct")
	public TchiboProfile offerProduct(@RequestParam String pid) {
		Product product;
		if ("676699932".equals(pid)) {
			product = new Product();
			product.setId(pid);
			product.setName("Danny Dino");
			product.setPrice("99,99");
			product.setImageUrl("http://localhost:8080/images/professionalPhotoFromTchibo.jpg");
		}
		else {
			product = productRepository.findById(pid).get();
		}
		Product product2 = productRepository.findById("400078565").get();
		TchiboProfile tchiboProfile = new TchiboProfile();
		TchiboProfile bob = new TchiboProfile("bob");
		TchiboProfile lisa = new TchiboProfile("lisa");
		tchiboProfile.addFriend(bob);
		tchiboProfile.addFriend(lisa);
		tchiboProfile.addSellingProduct(new Transaction(new Date(), bob, productToTchiboProduct.apply(product)));
		tchiboProfile.addSellingProduct(new Transaction(new Date(), bob, productToTchiboProduct.apply(product2)));

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
		return asList(new ProductTag("auto", 4),
				new ProductTag("Schwarz", 5),
				new ProductTag("5er Pack", 4));
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
		TchiboProduct product1 = new TchiboProduct("Damen-Thermo-Kapuzenlaufshirt");
		product1.pid = "200020329";
		product1.price = "10,00";
		product1.imageUrl = "https://media2.tchibo-content.de/newmedia/art_img/10/2a/96955586f0d2/MAIN_HD-IMPORTED"
				+ "/48623742cf20de61/Damen-Thermo-Kapuzenlaufshirt-Anthrazit.jpg";
		TchiboProduct product2 = new TchiboProduct("2 Paar Socken mit Baumwolle");
		product2.pid = "400074452";
		product2.price = "4,00";
		product2.imageUrl = "https://media2.tchibo-content.de/newmedia/art_img/MAIN_HD-IMPORTED/bf5394772f1dcb33/.jpg";
		return asList(product1, product2);
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


