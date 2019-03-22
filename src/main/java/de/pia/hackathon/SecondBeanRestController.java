package de.pia.hackathon;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class SecondBeanRestController {

	@GetMapping("/profile")
	@ResponseBody
	public TchiboProfile profile() {
		TchiboProfile tchiboProfile = new TchiboProfile();
		TchiboProfile bob = new TchiboProfile("bob");
		TchiboProfile lisa = new TchiboProfile("lisa");
		tchiboProfile.addFriend(bob);
		tchiboProfile.addFriend(lisa);
		TchiboProduct theoTiger = new TchiboProduct("Theo Tiger");
		theoTiger.price = 99.78;
		theoTiger.imageUrl = "http://localhost:8080/api/image?imageId=demo_image";
		Transaction boughtProduct = new Transaction(new Date(), lisa, theoTiger);
		tchiboProfile.addBoughtProduct(boughtProduct);
		TchiboProduct megaMinion = new TchiboProduct("Mega Minion");
		megaMinion.price = 1000.0;
		Transaction sellingProduct = new Transaction(new Date(), bob, megaMinion);
		tchiboProfile.addSellingProduct(sellingProduct);

		return tchiboProfile;
	}

	@GetMapping("/product")
	public TchiboProduct getProduct(@RequestParam byte[] image) {

		return new TchiboProduct("Theo Tiger");
	}

	@GetMapping("/search")
	public List<TchiboProduct> searchProducts(@RequestParam String searchText) {
		TchiboProduct product1 = new TchiboProduct("Theo Tiger");
		product1.price = 9.99;
		product1.searchTags = asList(new TopTag("4er", 19), new TopTag("gepolstert", 77), new TopTag("pink", 3));
		TchiboProduct product2 = new TchiboProduct("Leo Lausemaus");
		product2.price = 10.99;
		product2.searchTags = asList(new TopTag("8er", 3), new TopTag("foo", 44), new TopTag("bar", 23));
		return asList(product1, product2);
	}

	@GetMapping("/tagclicked")
	public List<TchiboProduct> tagClicked(@RequestParam String tagText) {
		TchiboProduct product1 = new TchiboProduct("Theo Tiger");
		product1.price = 9.99;
		product1.searchTags = asList(new TopTag("4er", 19), new TopTag("gepolstert", 77), new TopTag("pink", 3));
		return asList(product1);
	}

	@GetMapping("/toptags")
	public List<TopTag> topTags() {
		return asList(new TopTag("top1", 10), new TopTag("top2", 5), new TopTag("top3", 13));
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
		product1.price = 9.90;
		product1.imageId = "demo_image";
		product1.imageUrl = "http://localhost:8080/api/image?imageId=demo_image";
		TchiboProduct product2 = new TchiboProduct("Theo Tiger");
		product2.name = "p2";
		product2.price = 4.99;
		product2.imageId = "demo_image2";
		product2.imageUrl = "http://localhost:8080/api/image?imageId=demo_image2";
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

class TopTag {

	private String tag;
	private int count;

	public TopTag() {}

	public TopTag(String tag, int count) {
		this.tag = tag;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public String getTag() {
		return tag;
	}
}

class TchiboProduct {

	String name;
	Double price;
	String imageId;
	String imageUrl;
	List<TopTag> searchTags;

	public TchiboProduct(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public List<TopTag> getSearchTags() {
		return searchTags;
	}

	public String getName() {
		return name;
	}

	public String getImageId() {
		return imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

class Transaction {

	private Date transactionDate;
	private TchiboProfile toUser;
	private TchiboProduct product;

	public Transaction() {}

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

	public void addFriend(TchiboProfile friend){
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


